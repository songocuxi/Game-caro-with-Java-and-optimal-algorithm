/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectcarobyhd.FileGoc.Game20x20.setting;

import java.awt.Color;

public class Value {


	
	public static final String INTRODUCE_MESSAGE = 
		"\t\t\t\t\tChi tiết luật chơi"
		+ "\r\n" 
		+"Ban đầu loại cờ này được chơi bằng các quân cờ vây (quân cờ màu trắng và đen) "
		+ "trên một bàn cờ."
		+ "\n"
		+ "Người thắng là người đầu tiên có được một "
		+ "chuỗi liên tục gồm 4 quân hàng ngang, hoặc dọc, hoặc chéo không bị chặn đầu nào."
		+ "\n"
		+ "Nếu bị chặn một đầu thì người đó cần có chuỗi 5 quân liên tục mới thắng."
		+ "\n"
		+ "Nếu chuỗi 5 quân liên tục bị chặn cả hai đầu thì trò chơi tiếp tục.Một khi đã đặt xuống, "
		+ "các quân cờ không thể di chuyển hoặc bỏ ra khỏi bàn, do đó loại cờ này có thể chơi bằng giấy bút."
		+ "\n"
		+ "Ở Việt Nam, cờ này thường chơi trên giấy tập học sinh (đã có sẵn các ô ca-rô), "
		+ "dùng bút đánh dấu hình tròn (O) và chữ X để đại diện cho 2 quân cờ."
		+ "\n"
		+ "\t\t\t\t\t\t\tSource: wikipedia.com";
	
	/**
	 *  số hàng/số cột mặc định
	 */
	public static final int SIZE = 20;
	/**
	 *  chế độ chơi mặc định: User đi trước
	 */
	public static final int DEFAULT_MODE = 0; 
	/**
	 *  độ rộng của mỗi cell
	 */
	public static final int CELL_WIDTH = 48; 
	/**
	 *  khoảng cách giữa các panel 
	 */
	public static final int MARGIN = 10; 
	/**
	 *  cỡ chữ trong mỗi cell
	 */
	public static final int TEXT_CELL_SIZE = 40; 
	/**
	 *  màu chữ mặc định của X
	 */
	public static final Color USER_TEXT_COLOR = Color.red; 
	/**
	 *  màu chữ mặc định của O
	 */
	public static final Color AI_TEXT_COLOR = Color.BLUE; 
	/**
	 *  màu mặc định của mỗi ô vuông
	 */
	public static final Color CELL_COLOR = Color.white; 
	
	/**
	 * màu mặc định khi user click vào một ô trong bàn cũ?
	 */
	public static final Color CLICK_CELL_COLOR = Color.LIGHT_GRAY;
	/**
	 * màu ô được chọn
	 */
	public static final Color BACKGROUND_COLOR = Color.white; 
	/**
	 * Giá trị mặc định của USER
	 */
	public static final int USER_VALUE = 1;
	/**
	 * Giá trị mặc định của AI
	 */
	public static final int AI_VALUE = 2;
	/**
	 * Độ sâu tìm kiếm tối đa
	 */
	public static final int MAX_DEPTH = 7;
	/**
	 * số lượng lấy ra tối đa của danh sách các ô được lượng giá cao nhất
	 */
	public static final int MAX_NUM_OF_HIGHEST_CELL_LIST = 8;



}