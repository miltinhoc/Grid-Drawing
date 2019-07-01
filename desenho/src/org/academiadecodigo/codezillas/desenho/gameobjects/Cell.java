package org.academiadecodigo.codezillas.desenho.gameobjects;

import org.academiadecodigo.codezillas.desenho.grid.Grid;
import org.academiadecodigo.codezillas.desenho.grid.GridPosition;

public class Cell {
    private Grid grid;
    private GridPosition pos;

    public Cell(GridPosition pos){
        this.pos = pos;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public GridPosition getPos() {
        return pos;
    }

}
