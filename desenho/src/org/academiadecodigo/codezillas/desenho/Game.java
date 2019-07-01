package org.academiadecodigo.codezillas.desenho;

import org.academiadecodigo.codezillas.desenho.gameobjects.Cell;
import org.academiadecodigo.codezillas.desenho.gameobjects.PlayerCell;
import org.academiadecodigo.codezillas.desenho.grid.Grid;
import org.academiadecodigo.simplegraphics.graphics.Color;

public class Game {
    int cols;
    int rows;
    private Grid grid;
    private Cell[][] cells;

    public Game(int cols, int rows){
        this.cols = cols;
        this.rows = rows;
        grid = new Grid(cols,rows);
    }

    public void init(){
        grid.init();
    }

    private void createGrid(){
        cells = new Cell[cols][rows];

        for (int i = 0; i < grid.getCols(); i++) {
            cells[i][0] = new Cell(grid.makeGridPosition(i,0));
            cells[i][0].setGrid(grid);
            for (int j = 0; j < grid.getRows(); j++) {
                cells[i][j] = new Cell(grid.makeGridPosition(i,j));
                cells[i][j].setGrid(grid);
            }
        }
    }

    public void start(){

        createGrid();

        PlayerCell playerCell = new PlayerCell(grid.makeGridPosition(0,0,true),cells);
        playerCell.init();
        playerCell.setGrid(grid);
        playerCell.getPos().setColor(Color.MAGENTA);
    }
}
