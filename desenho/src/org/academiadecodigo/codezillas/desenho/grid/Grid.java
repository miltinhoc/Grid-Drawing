package org.academiadecodigo.codezillas.desenho.grid;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Grid {

    public static final int PADDING = 10;
    private int cellSize = 15;
    private int cols;
    private int rows;
    private Rectangle field;

    public Grid(int cols, int rows){
        this.cols = cols;
        this.rows = rows;
    }

    public void init(){
        this.field = new Rectangle(PADDING, PADDING, cols * cellSize, rows * cellSize);
        this.field.draw();
    }

    public GridPosition makeGridPosition(int col, int row){
        return new GridPosition(this, col, row);
    }

    public GridPosition makeGridPosition(int col, int row, boolean p){
        return new GridPosition(this, col, row, p);
    }

    public int getCols(){
        return cols;
    }

    public int getRows(){
        return rows;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int rowToY(int row) {
        return PADDING + cellSize * row;
    }

    public int columnToX(int column) {
        return PADDING + cellSize * column;
    }

}
