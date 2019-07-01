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
    private final int SPEED = 1;

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

        KeyboardEvent[] keyboardEvents = {down, up, right, left, space, save, clear, load, redColor, blackColor};

        for (KeyboardEvent k : keyboardEvents) {
            keyboard.addEventListener(k);
        }

    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public GridPosition getPos() {
        return pos;
    }

    private void accelerate(GridDirection direction, int speed) {
        this.currentDirection = direction;
        pos.moveInDirection(direction, speed);
    }

    private void checkCells(){
        if (cells[pos.getCol()][pos.getRow()].getPos().isPainted()){
            list.remove(cells[pos.getCol()][pos.getRow()]);
            cells[pos.getCol()][pos.getRow()].getPos().setColor(Color.BLACK);
            cells[pos.getCol()][pos.getRow()].getPos().erase();
            return;
        }
        list.add(cells[pos.getCol()][pos.getRow()]);
        cells[pos.getCol()][pos.getRow()].getPos().setColor(color);
        cells[pos.getCol()][pos.getRow()].getPos().paint();

    }

    private void clearCells(){

        while (!list.isEmpty()){
            list.element().getPos().setColor(Color.BLACK);
            list.element().getPos().erase();
            list.remove();
        }
    }

    private void readFile(){

        try {

            FileReader fileReader = new FileReader("teste.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            int lines = 0;

            while ( (line = bufferedReader.readLine()) != null ){

                String[] values = line.split("");

                for (int i = 0; i < grid.getCols(); i++) {
                    if (values[i].equals("0")){
                        cells[i][lines].getPos().erase();
                        continue;
                    }
                    list.add(cells[i][lines]);
                    cells[i][lines].getPos().paint();
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

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < grid.getRows(); i++) {
                for (int j = 0; j < grid.getCols(); j++) {
                    sb.append(cellContent(j,i));
                }
                sb.append("\n");
            }

            bufferedWriter.write(sb.toString(), 0, sb.length());
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
                accelerate(GridDirection.LEFT, SPEED);
                currentDirection = GridDirection.LEFT;
                break;
            case KeyboardEvent.KEY_RIGHT:
                currentDirection = GridDirection.RIGHT;
                accelerate(GridDirection.RIGHT, SPEED);
                break;
            case KeyboardEvent.KEY_UP:
                currentDirection = GridDirection.UP;
                accelerate(GridDirection.UP, SPEED);
                break;
            case KeyboardEvent.KEY_DOWN:
                currentDirection = GridDirection.DOWN;
                accelerate(GridDirection.DOWN, SPEED);
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
