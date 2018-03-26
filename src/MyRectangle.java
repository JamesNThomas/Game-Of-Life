
import java.awt.Color;
import java.awt.Rectangle;

public class MyRectangle extends Rectangle {
    
    private Color color;
    private boolean health;
    private int neighbors;
    
    //---Constructor------------------------------------------------------------
    public MyRectangle(int x, int y, int h, int w) {
        super(x, y, h, w);
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
}
