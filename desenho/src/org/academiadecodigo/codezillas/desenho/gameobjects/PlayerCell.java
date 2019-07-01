package org.academiadecodigo.codezillas.desenho.gameobjects;

import org.academiadecodigo.codezillas.desenho.grid.Grid;
import org.academiadecodigo.codezillas.desenho.grid.GridDirection;
import org.academiadecodigo.codezillas.desenho.grid.GridPosition;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;
import java.util.LinkedList;

public class PlayerCell implements KeyboardHandler {

    private LinkedList<Cell> list;
    private Grid grid;
    private GridPosition pos;
    private Keyboard keyboard;
    private GridDirection currentDirection;
    private Cell[][] cells;
    private Color color;

    public PlayerCell(GridPosition pos, Cell[][] cells) {
        color = Color.BLACK;
        this.pos = pos;
        keyboard = new Keyboard(this);
        this.cells = cells;
        list = new LinkedList<>();
    }

    public void init(){

        KeyboardEvent left = new KeyboardEvent();
        left.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        left.setKey(KeyboardEvent.KEY_LEFT);

        KeyboardEvent leftReleased = new KeyboardEvent();
        leftReleased.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        leftReleased.setKey(KeyboardEvent.KEY_LEFT);

        KeyboardEvent right = new KeyboardEvent();
        right.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        right.setKey(KeyboardEvent.KEY_RIGHT);

        KeyboardEvent rightReleased = new KeyboardEvent();
        rightReleased.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        rightReleased.setKey(KeyboardEvent.KEY_RIGHT);

        KeyboardEvent up = new KeyboardEvent();
        up.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        up.setKey(KeyboardEvent.KEY_UP);

        KeyboardEvent upReleased = new KeyboardEvent();
        upReleased.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        upReleased.setKey(KeyboardEvent.KEY_UP);

        KeyboardEvent down = new KeyboardEvent();
        down.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        down.setKey(KeyboardEvent.KEY_DOWN);

        KeyboardEvent downReleased = new KeyboardEvent();
        downReleased.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        downReleased.setKey(KeyboardEvent.KEY_DOWN);

        KeyboardEvent space = new KeyboardEvent();
        space.setKey(KeyboardEvent.KEY_SPACE);
        space.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent save = new KeyboardEvent();
        save.setKey(KeyboardEvent.KEY_S);
        save.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent clear = new KeyboardEvent();
        clear.setKey(KeyboardEvent.KEY_C);
        clear.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent load = new KeyboardEvent();
        load.setKey(KeyboardEvent.KEY_L);
        load.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);


        KeyboardEvent redColor = new KeyboardEvent();
        redColor.setKey(KeyboardEvent.KEY_1);
        redColor.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent blackColor = new KeyboardEvent();
        blackColor.setKey(KeyboardEvent.KEY_2);
        blackColor.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        keyboard.addEventListener(down);
        keyboard.addEventListener(up);
        keyboard.addEventListener(right);
        keyboard.addEventListener(left);
        keyboard.addEventListener(space);
        keyboard.addEventListener(save);
        keyboard.addEventListener(clear);
        keyboard.addEventListener(load);
        keyboard.addEventListener(redColor);
        keyboard.addEventListener(blackColor);
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public GridPosition getPos() {
        return pos;
    }

    public void accelerate(GridDirection direction, int speed) {
        this.currentDirection = direction;
        pos.moveInDirection(direction, 1);
    }

    private void checkCells(){
        if (cells[pos.getCol()][pos.getRow()].getPos().isPainted()){
            list.remove(cells[pos.getCol()][pos.getRow()]);
            cells[pos.getCol()][pos.getRow()].getPos().setColor(Color.BLACK);
            cells[pos.getCol()][pos.getRow()].getPos().show();
            return;
        }
        list.add(cells[pos.getCol()][pos.getRow()]);
        cells[pos.getCol()][pos.getRow()].getPos().setColor(color);
        cells[pos.getCol()][pos.getRow()].getPos().showP();

    }

    private void clearCells(){

        while (!list.isEmpty()){
            list.element().getPos().show();
            list.remove();
        }
    }

    public void readFile(){

        try {

            FileReader fileReader = new FileReader("teste.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            int lines = 0;

            while ( (line = bufferedReader.readLine()) != null ){

                String[] values = line.split("");

                for (int i = 0; i < grid.getCols(); i++) {
                    if (values == null){return;}
                    if (values[i].equals("0")){
                        cells[i][lines].getPos().show();
                        continue;
                    }
                    cells[i][lines].getPos().showP();
                }

                lines++;
            }

            fileReader.close();
            bufferedReader.close();

        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private String cellContent(int col, int row){
        return cells[col][row].getPos().isPainted() ? "1" : "0";
    }

    private void saveToFile(){
        try {
            FileWriter fileWriter = new FileWriter("teste.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String line = "";
            int lines = 0;


            for (int i = 0; i < grid.getRows(); i++) {
                for (int j = 0; j < grid.getCols(); j++) {
                    line += cellContent(j,i);
                }
                line+="\n";
            }

            bufferedWriter.write(line);
            bufferedWriter.flush();
            bufferedWriter.close();

        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        int key = keyboardEvent.getKey();

        switch (key){
            case KeyboardEvent.KEY_LEFT:
                accelerate(GridDirection.LEFT, 1);
                currentDirection = GridDirection.LEFT;
                break;
            case KeyboardEvent.KEY_RIGHT:
                currentDirection = GridDirection.RIGHT;
                accelerate(GridDirection.RIGHT, 1);
                break;
            case KeyboardEvent.KEY_UP:
                currentDirection = GridDirection.UP;
                accelerate(GridDirection.UP, 1);
                break;
            case KeyboardEvent.KEY_DOWN:
                currentDirection = GridDirection.DOWN;
                accelerate(GridDirection.DOWN, 1);
                break;
            case KeyboardEvent.KEY_SPACE:
                checkCells();
                break;
            case KeyboardEvent.KEY_S:
                saveToFile();
                break;
            case KeyboardEvent.KEY_C:
                clearCells();
                break;
            case KeyboardEvent.KEY_L:
                readFile();
                break;
            case KeyboardEvent.KEY_1:
                color = Color.RED;
                break;
            case KeyboardEvent.KEY_2:
                color = Color.BLACK;
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {}
}
