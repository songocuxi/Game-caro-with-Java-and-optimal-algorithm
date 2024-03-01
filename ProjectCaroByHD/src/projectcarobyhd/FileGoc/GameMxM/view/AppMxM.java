/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectcarobyhd.FileGoc.GameMxM.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


import projectcarobyhd.FileGoc.GameMxM.cell.RoundedBorder;
import projectcarobyhd.FileGoc.GameMxM.setting.Setting;
import projectcarobyhd.FileGoc.GameMxM.setting.Value;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import projectcarobyhd.FileGoc.GameMxM.AI.CaroAI2;
import projectcarobyhd.FileGoc.GameMxM.AI.CaroAI1;
import projectcarobyhd.FileGoc.GiaoDienChinh;


public class AppMxM extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel TableCells; // Panel chứa ma trận cells
	private Border cellBorder; // tạo đường viền của mỗi cell
	private JLabel[][] cell; // Ma trận cells
	private JLabel player1ClickedCell; // cell được AI1 chọn
	private JLabel player2ClickedCell; // cell được AI2 chọn
	private JLabel lblAI1Score; // điểm của User
	private JLabel lblAI2Score; // điểm của AI
	
	private CaroAI2 caro2;
        private CaroAI1 caro1;
	private Setting setting;
	private Notification notification;
	
	public static final int TEXT_CELL_SIZE = Value.TEXT_CELL_SIZE; // cỡ chữ trong mỗi cell
	
	
	private final ButtonGroup buttonGroup1 = new ButtonGroup();
        private final ButtonGroup buttonGroup2 = new ButtonGroup();
	

	// hàm main
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppMxM frame = new AppMxM();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// tạo màn chơi mới
	public void newGame() {
                caro2 = new CaroAI2(setting.getMaxD());
		caro1 = new CaroAI1(setting.getMaxD());
		player1ClickedCell = null;
		player2ClickedCell = null;
		
		for (int i = 0; i < Value.SIZE; i++) {
			for (int j = 0; j < Value.SIZE; j++) {
				cell[i][j].setBackground(Value.BACKGROUND_COLOR);
				cell[i][j].setText("");
			}
		}
		// cập nhật nước đi của AI
                int x = caro1.getNextX();
                int y = caro1.getNextY();
                updateTableCells(x, y, Value.AI1_VALUE);
                int i = caro2.getNextX();
                int j = caro2.getNextY();
                updateTableCells(i, j, Value.AI2_VALUE);
             
	}


	
	public void updateTableCells(int x, int y, int player) {
		if(player == Value.AI2_VALUE) {
			if(player2ClickedCell != null && player1ClickedCell != player2ClickedCell) {
				player2ClickedCell.setBackground(Value.BACKGROUND_COLOR); // đặt lại màu clickedCell cũ
			}
			player2ClickedCell = cell[x][y]; // cập nhật clickedCell
			player2ClickedCell.setBackground(Value.CLICK_CELL_COLOR); // làm nổi bật ô được click
			// AI - PLAYER 2 đánh
			
			player2ClickedCell.setForeground(Value.AI_TEXT_COLOR);
			player2ClickedCell.setText("O");
		}
		else {
			if(player1ClickedCell != null && player1ClickedCell != player2ClickedCell) {
				player1ClickedCell.setBackground(Value.BACKGROUND_COLOR); // đặt lại màu clickedCell cũ
			}
			player1ClickedCell = cell[x][y]; // cập nhật clickedCell
			player1ClickedCell.setBackground(Value.CLICK_CELL_COLOR); // làm nổi bật ô được click
					
			player1ClickedCell.setForeground(Value.USER_TEXT_COLOR);	
			cell[x][y].setText("X");
		}
	}
	
	
	
	/**
	 * Create the frame.
	 */
	public AppMxM() {
		/*--------------Set các giá trị mặc định--------------*/
		setResizable(false);
		setTitle("Cờ Caro máy với máy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Value.SIZE*Value.CELL_WIDTH+3*Value.MARGIN+280, Value.SIZE*Value.CELL_WIDTH+50);//kích thước của mỗi phần thay đổi tự động khi thay đổi SIZE(số hàng/số cột)
		setLocationRelativeTo(null);
		
		setting = new Setting();
		caro2 = new CaroAI2(setting.getMaxD()); // khởi tạo CaroAI1
                caro1 = new CaroAI1(setting.getMaxD()); // khởi tạo CaroAI2
		cellBorder = new LineBorder(Color.black, 3); // tạo border cho mỗi cell trong ma trận
		
		/*------------------Tạo các đối tượng------------------*/
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TableCells = new JPanel();
		TableCells.setLayout(new GridLayout(Value.SIZE, Value.SIZE, 0, 0));
		TableCells.setFont(new Font("Tahoma", Font.PLAIN, 14));
		TableCells.setBounds(Value.MARGIN, Value.MARGIN, Value.SIZE*Value.CELL_WIDTH, Value.SIZE*Value.CELL_WIDTH);
		contentPane.add(TableCells);
		
		// Tạo ma trận và add vào TableCells
		cell = new JLabel[Value.SIZE][Value.SIZE];
		for (int i = 0; i < Value.SIZE; i++) {
			for (int j = 0; j < Value.SIZE; j++) {
				cell[i][j] = new JLabel();
				cell[i][j].setSize(Value.CELL_WIDTH, Value.CELL_WIDTH); // kích cỡ mỗi cell
				cell[i][j].setBackground(setting.getCellColor());
				cell[i][j].setOpaque(true);
				cell[i][j].setBorder(cellBorder);
				cell[i][j].setFont(new Font("Comic Sans MS", Font.BOLD, TEXT_CELL_SIZE));
				cell[i][j].setForeground(setting.getxColor());
				cell[i][j].setHorizontalAlignment(SwingConstants.CENTER); // căn giữa chữ
				TableCells.add(cell[i][j]); // add cell vào TableCells
			}
		}
		// giao diện
		JPanel view = new JPanel();
		view.setBackground(new Color(153, 255, 255));
		view.setBounds(Value.SIZE*Value.CELL_WIDTH+2*Value.MARGIN, Value.MARGIN, 274, Value.SIZE*Value.CELL_WIDTH);
		contentPane.add(view);
		view.setLayout(null);
		
		JLabel lbltitle = new JLabel("GAME CARO");
		lbltitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbltitle.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		lbltitle.setBounds(10, 11, 254, 50);
		view.add(lbltitle);
		

                
		JLabel lblLevel = new JLabel("Độ khó:");
		lblLevel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLevel.setForeground(new Color(0, 0, 139));
		lblLevel.setFont(new Font("Times", Font.BOLD, 20));
		lblLevel.setBounds(10, 200, 254, 20);
		view.add(lblLevel);
                
                //Chọn bàn cờ
                JRadioButton rdbtneasy = new JRadioButton("Dễ");
                JRadioButton rdbtnhard = new JRadioButton("Khó");
		

		if(setting.getMaxD() == 1){
                    rdbtneasy.setSelected(true);
                }
                
                else if(setting.getMaxD() == 7){
                    rdbtnhard.setSelected(true);
                }
		// user = 0; ai = 1		
		
		// 3 cái gạch ngang
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.DARK_GRAY);
		separator.setBounds(10, 340, 254, 2);
		view.add(separator);
		
                
                // dễ = 1; khó = 7		
		rdbtneasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setting.getMaxD() != 1) {
					int result = JOptionPane.showConfirmDialog(null, "Xác nhận đổi độ khó?", "Xác nhận", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						setting.setMaxD(1); // cập nhật độ khó
						newGame(); // clear màn chơi cũ
					}else {rdbtnhard.setSelected(true);
						
					}
				}
			}
		});
		rdbtneasy.setFont(new Font("Times", Font.PLAIN, 16));
		buttonGroup2.add(rdbtneasy);
		rdbtneasy.setOpaque(false);
		rdbtneasy.setBounds(26, 234, 232, 23);
		view.add(rdbtneasy);
		
		rdbtnhard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setting.getMaxD() != 7) {
					int result = JOptionPane.showConfirmDialog(null, "Xác nhận đổi độ khó?", "Xác nhận", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						setting.setMaxD(7); // cập nhật map
						newGame(); // clear màn chơi cũ
					}else {
						if(setting.getMaxD() == 0) {
							rdbtneasy.setSelected(true);
						} 
					}
				}
			}
		});
		rdbtnhard.setFont(new Font("Times", Font.PLAIN, 16));
		buttonGroup2.add(rdbtnhard);
		rdbtnhard.setOpaque(false);
		rdbtnhard.setBounds(26, 286, 232, 23);
		view.add(rdbtnhard);
                
                

		
		// tổng kết quả thắng thua của 2 người chơi
		JLabel lblUser = new JLabel("Điểm máy 1");
		lblUser.setForeground(new Color(220, 20, 60));
		lblUser.setFont(new Font("Times", Font.BOLD, 16));
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setBounds(10, 91, 122, 20);
		view.add(lblUser);
		
		JLabel lblAI = new JLabel("Điểm máy 2");
		lblAI.setForeground(new Color(0, 139, 139));
		lblAI.setFont(new Font("Times", Font.BOLD, 16));
		lblAI.setHorizontalAlignment(SwingConstants.CENTER);
		lblAI.setBounds(142, 91, 122, 20);
		view.add(lblAI);
		
		lblAI1Score = new JLabel("0");
		lblAI1Score.setForeground(new Color(65, 105, 225));
		lblAI1Score.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		lblAI1Score.setHorizontalAlignment(SwingConstants.CENTER);
		lblAI1Score.setBounds(10, 122, 122, 20);
		view.add(lblAI1Score);
		
		lblAI2Score = new JLabel("0");
		lblAI2Score.setForeground(new Color(0, 128, 0));
		lblAI2Score.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		lblAI2Score.setHorizontalAlignment(SwingConstants.CENTER);
		lblAI2Score.setBounds(142, 122, 122, 20);
		view.add(lblAI2Score);
		
		// nút tạo game mới, reset trò chơi
		JButton btnNewGame = new JButton("Chơi game mới");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn chơi mới?", "Xác nhận", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					newGame();
				}
			}
		});
		btnNewGame.setFont(new Font("Times", Font.BOLD, 12));
		btnNewGame.setBounds(9, 360, 110, 37);
		btnNewGame.setOpaque(false);
		btnNewGame.setBorder(new RoundedBorder(10));
		view.add(btnNewGame);
		
		// nút thoát chương trình
		JButton btnExitGame = new JButton("Thoát");
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn đóng trò chơi?", "Xác nhận", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) System.exit(0); // thoát game
			}
		});
		btnExitGame.setFont(new Font("Times", Font.BOLD, 12));
		btnExitGame.setOpaque(false);
		btnExitGame.setBorder(new RoundedBorder(10));
		btnExitGame.setBounds(156, 360, 110, 37);
		view.add(btnExitGame);
		
		// nút Thông tin
		JButton btnInfo = new JButton("Màn hình chính");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GiaoDienChinh a = new GiaoDienChinh();
                                a.setLocationRelativeTo(null);
                                a.setTitle("Game Cờ Caro");
                                a.setVisible(true);
			}
		});
                btnInfo.setOpaque(false);
		btnInfo.setFont(new Font("Times", Font.BOLD, 12));
		btnInfo.setBorder(new RoundedBorder(10));
		btnInfo.setBounds(156, 420, 110, 37);
		view.add(btnInfo);
                JButton btnPlay = new JButton("Đánh tiếp");
		btnPlay.addActionListener(new ActionListener() {
                    int a = 0;
			public void actionPerformed(ActionEvent e) {
                            a++;
                            int x = -1, y = -1, i  = -1, j = -1;
                            if(a%2==0){
                                caro2.nextStep();
                                // Cập nhật bước đi của AI
                                i = caro2.getNextX();
                                j = caro2.getNextY();
                                updateTableCells(x, y, Value.AI2_VALUE);

                                // Kiểm tra trạng thái của bàn cờ sau khi AI đánh
                                if(checkResult(Value.AI2_VALUE)) {
                                        return;
                                }

                                //System.out.println("A");
                            }
                                else{
                                caro1.nextStep();
                                // Cập nhật bước đi của AI
                                x = caro1.getNextX();
                                y = caro1.getNextY();
                                updateTableCells(x, y, Value.AI1_VALUE);

                                // Kiểm tra trạng thái của bàn cờ sau khi AI đánh
                                if(checkResult(Value.AI1_VALUE)) {
                                        return;
                                }
                                //System.out.println("B");
                                }
			}
		});
		btnPlay.setOpaque(false);
		btnPlay.setFont(new Font("Times", Font.BOLD, 12));
		btnPlay.setBorder(new RoundedBorder(10));
		btnPlay.setBounds(95, 540, 110, 37);
		view.add(btnPlay);
		
		// nút Luật chơi
		JButton btnIntroduce = new JButton("Luật chơi");
		btnIntroduce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				notification = getNotificationInstance();
				notification.show("Thông tin trò chơi", "Giới Thiệu Luật Chơi", Value.INTRODUCE_MESSAGE);
			}
		});
		btnIntroduce.setOpaque(false);
		btnIntroduce.setFont(new Font("Times", Font.BOLD, 12));
		btnIntroduce.setBorder(new RoundedBorder(10));
		btnIntroduce.setBounds(9, 420, 110, 37);
		view.add(btnIntroduce);
                // nút tiếp tục chơi
		JButton btnContinue = new JButton("Tiếp tục");
		btnIntroduce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				notification = getNotificationInstance();
				notification.show("Thông tin", "Giới Thiệu Luật Chơi", Value.INTRODUCE_MESSAGE);
			}
		});
		btnContinue.setOpaque(false);
		btnContinue.setFont(new Font("Times", Font.BOLD, 12));
		btnContinue.setBorder(new RoundedBorder(10));
		btnContinue.setBounds(156, 480, 110, 37);
		view.add(btnContinue);
                
                // nút lưu và thoát trò chơi
		JButton btnSave = new JButton("Lưu và thoát");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				notification = getNotificationInstance();
				notification.show("Thông tin", "Giới Thiệu Luật Chơi", Value.INTRODUCE_MESSAGE);
			}
		});
		btnSave.setOpaque(false);
		btnSave.setFont(new Font("Times", Font.BOLD, 12));
		btnSave.setBorder(new RoundedBorder(10));
		btnSave.setBounds(9, 480, 110, 37);
		view.add(btnSave);
		

}
	



	// check win
	private boolean checkResult(int player) {
		if(player == Value.AI1_VALUE) {
			boolean result = caro2.checkWinner(Value.AI1_VALUE);
			if(result == true) {
				System.out.println("Máy 1 thắng!");
				JOptionPane.showMessageDialog(null, "Máy 1 đã thắng!");
				int currentPoint = Integer.valueOf(lblAI1Score.getText())+1;
				lblAI1Score.setText(String.valueOf(currentPoint));
				newGame();
				return true; // kết thúc màn chơi
			}
		}
		else {
			boolean result = caro2.checkWinner(Value.AI2_VALUE);
			if(result == true) {
				System.out.println("Máy 2 thắng!");
				JOptionPane.showMessageDialog(null, "Máy 2 đã thắng!");
				int currentPoint = Integer.valueOf(lblAI2Score.getText())+1;
				lblAI2Score.setText(String.valueOf(currentPoint));
				newGame();
				return true;
			}
		}
		if(caro2.isOver()) {
			System.out.println("Hòa!");
			JOptionPane.showMessageDialog(null, "Hòa!");
			newGame();
			return true;
		}
		return false;
	}
	
	public Notification getNotificationInstance() {
		if(notification == null) notification = new Notification();
		return notification;
	}
	
	

}