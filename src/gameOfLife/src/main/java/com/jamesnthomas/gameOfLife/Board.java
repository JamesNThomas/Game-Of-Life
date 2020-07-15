package com.jamesnthomas.gameOfLife;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JPanel;

public class Board extends JPanel {

    private Graphics2D g;
    private HashMap<String, Cell> cells;
    private int boardSize;
    private int neighbors;
    private String[] gosperGunArray = {"6,2", "7,2", "7,3", "6,3", "6,12",
        "7,12", "8,12", "9,13", "10,14", "10,15", "5,13", "4,14", "4,15",
        "5,17", "6,18", "7,18", "8,18", "9,17", "7,19", "7,16", "6,22",
        "6,23", "5,23", "5,22", "4,22", "4,23", "3,24", "7,24", "3,26",
        "2,26", "7,26", "8,26", "4,36", "5,36", "5,37", "4,37"};
    private String[] spaceshipArray = 
        {"13,2", "15,2", "16,3", "16,4", "16,5", "16,6", "15,6", "14,6", "13,5"};
    private String[] gliderArray = {"4,2", "4,3", "4,4", "3,4", "2,3"};
    
    //---Default Constructor----------------------------------------------------
    public Board() {

        cells = new HashMap<String, Cell>();

        for (int i = 1; i <= 50; i++) {
            for (int j = 1; j <= 50; j++) {
                cells.put(i + "," + j, new Cell(j * 10, i * 10, j, i));
            }
        }
        System.out.println(cells.size());
    }

    //---Board Size Accessor----------------------------------------------------
    public void setBoardSize(int size) {
        this.boardSize = size;
    }

    //---Board Size Mutator-----------------------------------------------------
    public int getBoardSize() {
        return this.boardSize;
    }

    //---Resizes Board in n x n dimensions--------------------------------------
    public void resizeBoard() {
        cells.clear();
        for (int i = 1; i <= this.boardSize; i++) {
            for (int j = 1; j <= this.boardSize; j++) {
                cells.put(i + "," + j, new Cell(j * 10, i * 10, j, i));
            }
        }
    }

    //---Setting up Grid/Array--------------------------------------------------
    public void paintComponent(Graphics g) {

        this.g = (Graphics2D) g;

        super.paintComponent(g);


        for (Cell cell : cells.values()) {
            if (cell.getColor() != null) {
                this.g.setPaint(cell.getColor());
                this.g.fill(cell);
            }
            this.g.drawRect(cell.x, cell.y, 10, 10);
        }
    }

    //---Checks for Clicked Rectangle; turns it black---------------------------
    public void checkPoint(double x, double y) {
        Rectangle tempRect = new Rectangle();

        int tempX = ((int) ((x / 10) - .5));
        int tempY = ((int) ((y - 24) / 10));

        System.out.println(tempX + " , " + tempY);

        for (Cell cell : cells.values()) {
            if (((tempX * 10) == cell.x) && ((tempY * 10) == cell.y)) {
                if (cell.getColor() == null) {
                    cell.setColor(Color.BLACK);
                    cell.setHealth(true);
                } else {
                    cell.setColor(null);
                    cell.setHealth(false);
                }
            }
        }
    }
    
    //---Resets Grid/Array------------------------------------------------------
    public void clearRect() {
        for (Cell cell : cells.values()) {
            cell.setColor(null);
            cell.setHealth(false);
        }
    }

    private int computeNeighbors(Cell cell, 
                                    HashMap<String, Cell> healthyCells) {
        neighbors = 0;

        if (healthyCells.containsKey(
            (cell.getCoordX() - 1) + "," + cell.getCoordY()))
            neighbors++;
        if (healthyCells.containsKey(
            (cell.getCoordX()- 1) + "," + (cell.getCoordY() + 1)))
            neighbors++;
        if (healthyCells.containsKey(
            (cell.getCoordX() - 1) + "," + (cell.getCoordY() - 1)))
            neighbors++;
        if (healthyCells.containsKey(
            cell.getCoordX() + "," + (cell.getCoordY() + 1)))
            neighbors++;
        if (healthyCells.containsKey(
            cell.getCoordX() + "," + (cell.getCoordY() - 1)))
            neighbors++;
        if (healthyCells.containsKey(
            (cell.getCoordX() + 1) + "," + cell.getCoordY()))
            neighbors++;
        if (healthyCells.containsKey(
            (cell.getCoordX() + 1) + "," + (cell.getCoordY() + 1)))
            neighbors++;
        if (healthyCells.containsKey(
            (cell.getCoordX() + 1) + "," + (cell.getCoordY() - 1)))
            neighbors++;

        return neighbors;
    }
    
    //---Iterates Through Each Rectangle, Checking its Neighbors----------------
    public void ruleCheck() {
        HashMap<String, Cell> healthyCells = new HashMap<>();
        for (Cell cell : cells.values()) {
            if (cell.getHealth())
                healthyCells.put(cell.getCoordX() + "," + cell.getCoordY(), cell);
        }

        for (Cell cell : cells.values()) { 
            cell.setNeighbors(computeNeighbors(cell, healthyCells));
        }
        
        
        // Applying rules of the "game"
        for (Cell cell : cells.values()) {
            if (cell.getNeighbors() < 2) {
                cell.setHealth(false);
                cell.setColor(null);
            } else if (cell.getNeighbors() > 3) {
                cell.setHealth(false);
                cell.setColor(null);
            } else if (cell.getNeighbors() == 3) {
                cell.setHealth(true);
                cell.setColor(Color.BLACK);
            }
        }
    }
    
    //---Draws a glider pattern-------------------------------------------------
    public void glider() {
        for (String index : gliderArray) {
            Cell cell = cells.get(index);
            cell.setHealth(true);
            cell.setColor(Color.BLACK);
        }
    }
    
    //---Applies a vertical column ("Vertical Divider)--------------------------
    public void vertical() {
        int position = (int) this.boardSize / 2;
        for (int i = 1; i <= this.boardSize; i ++) {
            Cell cell = cells.get(i + "," + position);
            cell.setHealth(true);
            cell.setColor(Color.BLACK);
        }
    }
    
    //---Applies black squares at random----------------------------------------
    public void random() {
        Random rand = new Random();

        for (Cell cell : cells.values()) {
            if (rand.nextBoolean() == true) {
                cell.setHealth(true);
                cell.setColor(Color.BLACK);
            }
        }
    }

    //---Applies the Gosper Glider Gun pattern----------------------------------
    public void gosperGun() {
        for (String index : gosperGunArray) {
            Cell cell = cells.get(index);
            cell.setHealth(true);
            cell.setColor(Color.BLACK);
        }
    }
    
    //---Applies a "lightweight" spaceship pattern------------------------------
    public void lwSpaceship() {
        for (String index : spaceshipArray) {
            Cell cell = cells.get(index);
            cell.setHealth(true);
            cell.setColor(Color.BLACK);
        }
    }
}
