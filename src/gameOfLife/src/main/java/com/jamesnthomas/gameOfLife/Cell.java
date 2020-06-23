package com.jamesnthomas.gameOfLife;

import java.awt.Color;
import java.awt.Rectangle;

public class Cell extends Rectangle {
    
    private Color color;
    private boolean health;
    private int neighbors, coordX, coordY;
    
    //---Constructor------------------------------------------------------------
    public Cell(int x,
                int y,
                int h,
                int w,
                int coordX,
                int coordY) {
        super(x, y, h, w);
        this.coordX = coordX;
        this.coordY = coordY;
        this.health = false;
    }
    
    //---Sets the color of the rectangle----------------------------------------
    public void setColor(Color color) {
        this.color = color;
    }
    
    //---Returns the color of the rectangle-------------------------------------
    public Color getColor() {
        return this.color;
    }
    
    //---Sets the "health" (alive or dead) of the rectangle---------------------
    public void setHealth(boolean health) {
        this.health = health;
    }
    
    //---Returns the "health" (alive or dead) of the rectangle------------------
    public boolean getHealth() {
        return this.health;
    }
    
    //---Sets the number of neighbors for the rectangle-------------------------
    public void setNeighbors(int neighbors) {
        this.neighbors = neighbors;
    }
    
    //---Returns the number of neighbors----------------------------------------
    public int getNeighbors() {
        return this.neighbors;
    }

    public int getCoordX() {
        return this.coordX;
    }

    public int getCoordY() {
        return this.coordY;
    }
}
