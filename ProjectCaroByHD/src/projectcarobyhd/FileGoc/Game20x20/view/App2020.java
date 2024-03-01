/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectcarobyhd.FileGoc.Game20x20.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


import projectcarobyhd.FileGoc.Game20x20.cell.RoundedBorder;
import projectcarobyhd.FileGoc.Game20x20.setting.Setting;
import projectcarobyhd.FileGoc.Game20x20.setting.Value;

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
import projectcarobyhd.FileGoc.Game20x20.AI.CaroAI;
import projectcarobyhd.FileGoc.GiaoDienChinh;


public class App2020 extends JFrame implements MouseListener{

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel TableCells; // Panel chứa ma trận cells
	private Border cellBorder; // tạo đường viền của mỗi cell
	private JLabel[][] cell; // Ma trận cells
	private JLabel player1ClickedCell; // cell được user click chọn
	private JLabel player2ClickedCell; // cell được AI click chọn
	private JLabel lblUserScore; // điểm của User
	private JLabel lblAIScore; // điểm của AI
	
	private CaroAI caro;
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
					App2020 frame = new App2020();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// tạo màn chơi mới
	public void newGame() {
                caro = new CaroAI(setting.getMode(), setting.getMaxD());
		
		player1ClickedCell = null;
		player2ClickedCell = null;
		
		for (int i = 0; i < Value.SIZE; i++) {
			for (int j = 0; j < Value.SIZE; j++) {
				cell[i][j].setBackground(Value.BACKGROUND_COLOR);
				cell[i][j].setText("");
			}
		}
		if(setting.getMode() == 1) {
			// cập nhật nước đi của AI
			int x = caro.getNextX();
			int y = caro.getNextY();
			updateTableCells(x, y, Value.AI_VALUE);
		}
	}


	
	public void updateTableCells(int x, int y, int player) {
		if(player == Value.AI_VALUE) {
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
	public App2020() {
		/*--------------Set các giá trị mặc định--------------*/
		setResizable(false);
		setTitle("Cờ Caro bản đồ 20x20");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Value.SIZE*Value.CELL_WIDTH+3*Value.MARGIN+280, Value.SIZE*Value.CELL_WIDTH+50);//kích thước của mỗi phần thay đổi tự động khi thay đổi SIZE(số hàng/số cột)
		setLocationRelativeTo(null);
		
		setting = new Setting();
		caro = new CaroAI(setting.getMode(), setting.getMaxD()); // khởi tạo CaroAI
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
				cell[i][j].addMouseListener(this); // add hàm bắt sự kiện click chuột
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
		
		JLabel lblMode = new JLabel("Lượt đi:");
		lblMode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMode.setForeground(new Color(0, 0, 139));
		lblMode.setFont(new Font("Times", Font.BOLD, 20));
		lblMode.setBounds(10, 200, 254, 20);
		view.add(lblMode);
		
		
		// chọn mode chơi
		JRadioButton rdbtnUserPlaysFirst = new JRadioButton("Người chơi đi trước");
		JRadioButton rdbtnAiPlaysFirst = new JRadioButton("Máy đi trước");
                
                if(setting.getMode() == 0) {
			rdbtnUserPlaysFirst.setSelected(true);
		}
		else if(setting.getMode() == 1){
			rdbtnAiPlaysFirst.setSelected(true);
		}
                
		JLabel lblLevel = new JLabel("Độ khó:");
		lblLevel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLevel.setForeground(new Color(0, 0, 139));
		lblLevel.setFont(new Font("Times", Font.BOLD, 20));
		lblLevel.setBounds(10, 310, 254, 20);
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
		rdbtnUserPlaysFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setting.getMode() != 0) {
					int result = JOptionPane.showConfirmDialog(null, "Xác nhận đổi chế độ chơi?", "Xác nhận", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						setting.setMode(0); // cập nhật mode
						newGame(); // clear màn chơi cũ
					}else {
						if(setting.getMode() == 1) {
							rdbtnAiPlaysFirst.setSelected(true);
						}
					}
				}
			}
		});
		rdbtnUserPlaysFirst.setFont(new Font("Times", Font.PLAIN, 16));
		buttonGroup1.add(rdbtnUserPlaysFirst);
		rdbtnUserPlaysFirst.setOpaque(false);
		rdbtnUserPlaysFirst.setBounds(26, 230, 232, 23);
		view.add(rdbtnUserPlaysFirst);
		
		rdbtnAiPlaysFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setting.getMode() != 1) {
					int result = JOptionPane.showConfirmDialog(null, "Xác nhận đổi chế độ chơi?", "Xác nhận", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						setting.setMode(1); // cập nhật mode
						newGame(); // clear màn chơi cũ
					}else {
						if(setting.getMode() == 0) {
							rdbtnUserPlaysFirst.setSelected(true);
						}
					}
				}
			}
		});
		rdbtnAiPlaysFirst.setFont(new Font("Times", Font.PLAIN, 16));
		buttonGroup1.add(rdbtnAiPlaysFirst);
		rdbtnAiPlaysFirst.setOpaque(false);
		rdbtnAiPlaysFirst.setBounds(26, 266, 232, 23);
		view.add(rdbtnAiPlaysFirst);
		
		
		// 3 cái gạch ngang
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.DARK_GRAY);
		separator.setBounds(10, 300, 254, 2);
		view.add(separator);
		
                
                // dễ = 1;khó = 7		
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
		rdbtneasy.setBounds(26, 350, 232, 23);
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
		rdbtnhard.setBounds(26, 395, 232, 23);
		view.add(rdbtnhard);
                
                
                
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.DARK_GRAY);
		separator_1.setBackground(Color.GRAY);
		separator_1.setBounds(10, 191, 254, 2);
		view.add(separator_1);
                
                JSeparator separator_2 = new JSeparator();
		separator_1.setForeground(Color.DARK_GRAY);
		separator_1.setBackground(Color.GRAY);
		separator_1.setBounds(10, 440, 254, 2);
		view.add(separator_1);
		
		// tổng kết quả thắng thua của 2 người chơi
		JLabel lblUser = new JLabel("Điểm người");
		lblUser.setForeground(new Color(220, 20, 60));
		lblUser.setFont(new Font("Times", Font.BOLD, 16));
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setBounds(10, 91, 122, 20);
		view.add(lblUser);
		
		JLabel lblAI = new JLabel("Điểm máy");
		lblAI.setForeground(new Color(0, 139, 139));
		lblAI.setFont(new Font("Times", Font.BOLD, 16));
		lblAI.setHorizontalAlignment(SwingConstants.CENTER);
		lblAI.setBounds(142, 91, 122, 20);
		view.add(lblAI);
		
		lblUserScore = new JLabel("0");
		lblUserScore.setForeground(new Color(65, 105, 225));
		lblUserScore.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		lblUserScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserScore.setBounds(10, 122, 122, 20);
		view.add(lblUserScore);
		
		lblAIScore = new JLabel("0");
		lblAIScore.setForeground(new Color(0, 128, 0));
		lblAIScore.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		lblAIScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblAIScore.setBounds(142, 122, 122, 20);
		view.add(lblAIScore);
		
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
		btnNewGame.setBounds(9, 480, 110, 37);
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
		btnExitGame.setBounds(156, 480, 110, 37);
		view.add(btnExitGame);
		
		// nút Thông tin
		JButton btnInfo = new JButton("Màn hình chính");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GiaoDienChinh a = new GiaoDienChinh();
                                a.setLocationRelativeTo(null);
                                a.setTitle("Game Cờ Caro");
                                a.setVisible(true);
                                dispose();
			}
		});
		btnInfo.setOpaque(false);
		btnInfo.setFont(new Font("Times", Font.BOLD, 12));
		btnInfo.setBorder(new RoundedBorder(10));
		btnInfo.setBounds(156, 540, 110, 37);
		view.add(btnInfo);
		
		// nút Luật chơi
		JButton btnIntroduce = new JButton("Luật chơi");
		btnIntroduce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				notification = getNotificationInstance();
				notification.show("Thông tin", "Giới Thiệu Luật Chơi", Value.INTRODUCE_MESSAGE);
			}
		});
		btnIntroduce.setOpaque(false);
		btnIntroduce.setFont(new Font("Times", Font.BOLD, 12));
		btnIntroduce.setBorder(new RoundedBorder(10));
		btnIntroduce.setBounds(9, 540, 110, 37);
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
		btnContinue.setBounds(156, 600, 110, 37);
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
		btnSave.setBounds(9, 600, 110, 37);
		view.add(btnSave);
		
		// Nếu chế độ AI đánh trước => cập nhật lượt đầu của AI
		if(setting.getMode() == 1) {
			updateTableCells(caro.getNextX(), caro.getNextX(), Value.AI_VALUE);
		}
}
	
	

	// lấy tọa độ click chuột
	@Override
	public void mouseClicked(MouseEvent element) {
		int x = -1, y = -1; // lưu tọa độ
		// lấy tọa độ user click
		for (int i = 0; i < Value.SIZE; i++) {
			boolean fl = false;
			for (int j = 0; j < Value.SIZE; j++) {
				if(cell[i][j] == element.getSource()) { // nếu người dùng click vào ô này
					x = i;
					y = j;
					fl = true;
					break;
				}
			}
			if(fl) break; // dừng kiểm tra
		}
		
		//kiểm tra số lần click của user
		if(element.getClickCount() == 1) { // người dùng click dạo(click 1 lần)
			if(player1ClickedCell != null && player1ClickedCell != player2ClickedCell) {
				player1ClickedCell.setBackground(Value.BACKGROUND_COLOR); // đặt lại màu clickedCell cũ
			}
			player1ClickedCell = cell[x][y]; // cập nhật clickedCell
			player1ClickedCell.setBackground(Value.CLICK_CELL_COLOR); // làm nổi bật ô được click
		}
		
		else if(element.getClickCount() == 2) { // người dùng chọn đánh ô này
			if(caro.isClickable(x, y)) {//nếu ô này chưa được đánh
				
				caro.update(x, y, Value.USER_VALUE); // update matrix		    
				// Cập nhật bước đi của User
				updateTableCells(x, y, Value.USER_VALUE);	
				
				// Kiểm tra trạng thái của bàn cờ sau khi User đánh
				if(checkResult(Value.USER_VALUE)) {
					return;
				}
				
				
				// Nếu user không thắng và bàn cờ chưa full thì đến lượt AI đánh
				caro.nextStep();
				// Cập nhật bước đi của AI
				x = caro.getNextX();
				y = caro.getNextY();
				updateTableCells(x, y, Value.AI_VALUE);
				
				// Kiểm tra trạng thái của bàn cờ sau khi AI đánh
				if(checkResult(Value.AI_VALUE)) {
					return;
				}
			}
		}
	}

	// check win
	private boolean checkResult(int player) {
		if(player == Value.USER_VALUE) {
			boolean result = caro.checkWinner(Value.USER_VALUE);
			if(result == true) {
				System.out.println("User thắng!");
				JOptionPane.showMessageDialog(null, "Bạn đã thắng!");
				int currentPoint = Integer.valueOf(lblUserScore.getText())+1;
				lblUserScore.setText(String.valueOf(currentPoint));
				newGame();
				return true; // kết thúc màn chơi
			}
		}
		else {
			boolean result = caro.checkWinner(Value.AI_VALUE);
			if(result == true) {
				System.out.println("AI thắng!");
				JOptionPane.showMessageDialog(null, "AI đã thắng!");
				int currentPoint = Integer.valueOf(lblAIScore.getText())+1;
				lblAIScore.setText(String.valueOf(currentPoint));
				newGame();
				return true;
			}
		}
		if(caro.isOver()) {
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
	
	
	
	// Override
	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
	}
}