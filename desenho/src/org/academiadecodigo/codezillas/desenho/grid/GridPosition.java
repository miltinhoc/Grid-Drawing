package org.academiadecodigo.codezillas.desenho.grid;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class GridPosition {

    private Grid grid;
    private Rectangle rectangle;
    private int col;
    private int row;
    private boolean painted;

    GridPosition(Grid grid, int col, int row) {
        this.grid = grid;
        this.col = col;
        this.row = row;

        int x = grid.columnToX(col);
        int y = grid.rowToY(row);

        this.rectangle = new Rectangle(x, y,grid.getCellSize(),grid.getCellSize());
        erase();
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    GridPosition(Grid grid, int col, int row, boolean p) {
        this.grid = grid;
        this.col = col;
        this.row = row;

        int x = grid.columnToX(col);
        int y = grid.rowToY(row);

        this.rectangle = new Rectangle(x, y,grid.getCellSize(),grid.getCellSize());
        paint();
    }

    private void setPos(int col, int row) {
        this.col = col;
        this.row = row;
        paint();
    }

    public void moveInDirection(GridDirection direction, int distance) {

        int initialCol = getCol();
        int initialRow = getRow();

        switch (direction) {

            case UP:
                moveUp(distance);
                break;
            case DOWN:
                moveDown(distance);
                break;
            case LEFT:
                moveLeft(distance);
                break;
            case RIGHT:
                moveRight(distance);
                break;
        }

        int dx = grid.columnToX(getCol()) - grid.columnToX(initialCol);
        int dy = grid.rowToY(getRow()) - grid.rowToY(initialRow);

        this.rectangle.translate(dx,dy);

    }

    private Grid getGrid() {
        return grid;
    }

    private void moveUp(int dist) {
        int maxRowsUp = Math.min(dist,getRow());
        setPos(getCol(), getRow() - maxRowsUp);
    }

    private void moveDown(int dist) {
        int maxRowsDown = Math.min(getGrid().getRows() - (getRow() + 1),dist);
        setPos(getCol(), getRow() + maxRowsDown);
    }

    private void moveLeft(int dist) {
        int maxRowsLeft = Math.min(dist,getCol());
        setPos(getCol() - maxRowsLeft, getRow());
    }

    private void moveRight(int dist) {
        int maxRowsRight = Math.min(getGrid().getCols() - (getCol() + 1), dist);
        setPos(getCol() + maxRowsRight, getRow());
    }

    public void erase(){
        rectangle.draw();
        painted = false;
    }

    public boolean isPainted() {
        return painted;
    }

    public void paint(){
        rectangle.fill();
        painted = true;
    }

    public void setColor(Color color){
        this.rectangle.setColor(color);
    }
}
