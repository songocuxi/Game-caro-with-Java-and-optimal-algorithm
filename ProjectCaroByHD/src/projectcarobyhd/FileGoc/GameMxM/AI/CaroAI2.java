package projectcarobyhd.FileGoc.GameMxM.AI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HUY DUNG
 */

import java.util.ArrayList;
import java.util.Random;

import projectcarobyhd.FileGoc.GameMxM.cell.Cell;
import projectcarobyhd.FileGoc.GameMxM.cell.EvalCell;
import projectcarobyhd.FileGoc.GameMxM.state.State;
import projectcarobyhd.FileGoc.GameMxM.setting.Value;



public class CaroAI2 {
    Random rand;
    private int nextX;
    private int nextY;
    private int maxdepth;
    private State root; // Trạng thái hiện tại của trò chơi
    private Heuristic2 heuristic;
    private HeuristicNoob2 heuristic1;

    
    public CaroAI2(int maxdepth) {
        rand = new Random();
        this.maxdepth = maxdepth;
	root = new State(); 
        heuristic = new Heuristic2();
        heuristic1 = new HeuristicNoob2();
    }

    
    public int getNextX() {
    	return nextX;
    }
    
    public int getNextY() {
    	return nextY;
    }
    
    // kiểm tra thắng
	public boolean checkWinner(int player) {
		return root.checkWinner(player);
	}
	
	// cập nhật ma trận
	public void update(int x, int y, int value) {
		root.update(x, y, value);
	}
	
	// kiểm tra xem ô này có đánh được không
	public boolean isClickable(int x, int y) {
		return root.isClickable(x, y);
	}
    
	// kiểm tra bàn cờ đã full chưa
	public boolean isOver() {
		return root.isOver();
	}
    

  
	// tìm kiếm nước đi kế tiếp cho AI
    public void nextStep() {
    	Cell choice = alphaBeta(root); // tìm kiếm bước đi tối ưu nhất
    	if(choice == null) System.out.println("~ Lỗi! Không tìm thấy nước đi!");
    	else {
    		this.nextX = choice.getX(); // cập nhật nextX, nextY
        	this.nextY = choice.getY();
        		
    		if(!isClickable(this.nextX, this.nextY)) {
        		System.out.println("~ Lỗi! nước đi bị trùng!");
        	}
        	else {
        		update(nextX, nextY, Value.AI2_VALUE); // cập nhật root
        	}
    	}
    }
   
    
    public Cell alphaBeta(State state) {
        if(this.maxdepth == 1){
            State remState = new State(state.getState()); // copy lại giá trị của state
            heuristic1.evaluateEachCell(remState, Value.AI2_VALUE);
            //System.out.println("Bảng lượng giá các ô:");
            //heuristic.printEvalState(); // hiển thị bảng lượng giá
            ArrayList<EvalCell> list = heuristic1.getOptimalList(); // lấy danh sách những ô tối ưu để xét
            /*
            System.out.println("List bước đi tối ưu:(Max:"+Value.MAX_NUM_OF_HIGHEST_CELL_LIST+")");
            */
            int maxValue = Integer.MIN_VALUE;
            int n = list.size();
            ArrayList<EvalCell> ListChoose = new ArrayList<EvalCell>();
            for (int i = 0; i < n; i++) { // giả sử chọn một trong các phần tử trong list để tìm kiếm bước đi tối ưu
                // Giả sử AI đi ô này 
                    remState.getState()[list.get(i).getX()][list.get(i).getY()] = Value.AI2_VALUE;
                    System.out.println("Với nước đi " + list.get(i).getX() + " " + list.get(i).getY() + ":");
                int value = minValue(remState, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
                System.out.println("lượng giá trạng thái của nước đi này:" + value);
                if (maxValue < value) {
                    maxValue = value;
                    ListChoose.clear();
                    ListChoose.add(list.get(i));
                } else if (maxValue == value) ListChoose.add(list.get(i));
                // reset lại trạng thái của ô này
                remState.getState()[list.get(i).getX()][list.get(i).getY()] = 0;
            }
            n = ListChoose.size(); // lấy số phần tử có điểm cao nhất
            int x = rand.nextInt(n); // chọn ra một
            return ListChoose.get(x).getCell();
        }
        else{
            State remState = new State(state.getState()); // copy lại giá trị của state
            heuristic.evaluateEachCell(remState, Value.AI2_VALUE);
            //System.out.println("Bảng lượng giá các ô:");
            //heuristic.printEvalState(); // hiển thị bảng lượng giá
            ArrayList<EvalCell> list = heuristic.getOptimalList(); // lấy danh sách những ô tối ưu để xét
            /*
            System.out.println("List bước đi tối ưu:(Max:"+Value.MAX_NUM_OF_HIGHEST_CELL_LIST+")");
            */
            int maxValue = Integer.MIN_VALUE;
            int n = list.size();
            ArrayList<EvalCell> ListChoose = new ArrayList<EvalCell>();
            for (int i = 0; i < n; i++) { // giả sử chọn một trong các phần tử trong list để tìm kiếm bước đi tối ưu
                // Giả sử AI đi ô này 
                    remState.getState()[list.get(i).getX()][list.get(i).getY()] = Value.AI2_VALUE;
                    System.out.println("Với nước đi " + list.get(i).getX() + " " + list.get(i).getY() + ":");
                int value = minValue(remState, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
                System.out.println("lượng giá trạng thái của nước đi này:" + value);
                if (maxValue < value) {
                    maxValue = value;
                    ListChoose.clear();
                    ListChoose.add(list.get(i));
                } else if (maxValue == value) ListChoose.add(list.get(i));
                // reset lại trạng thái của ô này
                remState.getState()[list.get(i).getX()][list.get(i).getY()] = 0;
            }
            n = ListChoose.size(); // lấy số phần tử có điểm cao nhất
            int x = rand.nextInt(n); // chọn ra một
            return ListChoose.get(x).getCell();
        }
        
    }

    // hàm lượng giá cho AI
    // cắt tỉa alphabeta
    private int maxValue(State state, int alpha, int beta, int depth) {
        if(this.maxdepth == 1){
            if (depth >= this.maxdepth || state.checkWinner(Value.AI2_VALUE) || state.isOver()) {
            int val = heuristic1.evaluateState(state);
            return val;
            }
            heuristic1.evaluateEachCell(state, Value.AI2_VALUE);
            ArrayList<EvalCell> list = heuristic1.getOptimalList();
            for (int i = 0; i < list.size(); i++) {
                state.getState()[list.get(i).getCell().getX()][list.get(i).getCell().getY()] = Value.AI2_VALUE;
                alpha = Math.max(alpha, minValue(state, alpha, beta, depth + 1));
                state.getState()[list.get(i).getCell().getX()][list.get(i).getCell().getY()] = 0;
                if (alpha >= beta) {
                    break;
                }
            }
            return alpha;
        } else{
            if (depth >= this.maxdepth || state.checkWinner(Value.AI2_VALUE) || state.isOver()) {
            int val = heuristic.evaluateState(state);
            return val;
            }
            heuristic.evaluateEachCell(state, Value.AI2_VALUE);
            ArrayList<EvalCell> list = heuristic.getOptimalList();
            for (int i = 0; i < list.size(); i++) {
                state.getState()[list.get(i).getCell().getX()][list.get(i).getCell().getY()] = Value.AI2_VALUE;
                alpha = Math.max(alpha, minValue(state, alpha, beta, depth + 1));
                state.getState()[list.get(i).getCell().getX()][list.get(i).getCell().getY()] = 0;
                if (alpha >= beta) {
                    break;
                }
            }
            return alpha;
        }
        
    }

    // hàm lượng giá cho User
    private int minValue(State state, int alpha, int beta, int depth) {
        if(this.maxdepth == 1){
            if (depth >= this.maxdepth || state.checkWinner(Value.AI1_VALUE) || state.isOver()) {
                int val = heuristic1.evaluateState(state);
                return val;
            }
            heuristic1.evaluateEachCell(state, Value.AI1_VALUE);
            ArrayList<EvalCell> list = heuristic1.getOptimalList(); // lấy list các nước đi tối ưu
            for (int i = 0; i < list.size(); i++) {
                state.getState()[list.get(i).getCell().getX()][list.get(i).getCell().getY()] = Value.AI1_VALUE;//  1
                beta = Math.min(beta, maxValue(state, alpha, beta, depth + 1));
                state.getState()[list.get(i).getCell().getX()][list.get(i).getCell().getY()] = 0;
                if (alpha >= beta) {
                    break;
                }
            }
            return beta;
        } else{
            if (depth >= this.maxdepth || state.checkWinner(Value.AI1_VALUE) || state.isOver()) {
                int val = heuristic.evaluateState(state);
                return val;
            }
            heuristic.evaluateEachCell(state, Value.AI1_VALUE);
            ArrayList<EvalCell> list = heuristic.getOptimalList(); // lấy list các nước đi tối ưu
            for (int i = 0; i < list.size(); i++) {
                state.getState()[list.get(i).getCell().getX()][list.get(i).getCell().getY()] = Value.AI1_VALUE;//  1
                beta = Math.min(beta, maxValue(state, alpha, beta, depth + 1));
                state.getState()[list.get(i).getCell().getX()][list.get(i).getCell().getY()] = 0;
                if (alpha >= beta) {
                    break;
                }
            }
            return beta;
        }
    }
}