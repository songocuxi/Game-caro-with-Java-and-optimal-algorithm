/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectcarobyhd.FileGoc.Game9x9.cell;

// lưu lượng giá bàn cờ

public class EvalCell {
	private Cell cell;
	private int value;
	
	public EvalCell() {
		cell = new Cell();
		value = 0;
	}
	
	public EvalCell(Cell cell, int value) {
		this.cell = cell;
		this.value = value;
	}
	
	public EvalCell(int x, int y, int value) {
		this.cell = new Cell(x, y);
		this.value = value;
	}
	
	public Cell getCell() {
		return this.cell;
	}
	
	public int getX() {
		return this.cell.getX();
	}
	
	public int getY() {
		return this.cell.getY();
	}
	
	public int getValue() {
		return this.value;
	}
}