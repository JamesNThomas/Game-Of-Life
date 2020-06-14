package com.jamesnthomas.gameOfLife;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class Board extends JPanel {

    private Graphics2D g;
    private ArrayList<Cell> rectArray;
    private int neighbors;
    private int[] gosperGunArray = {512, 351, 352, 401, 402, 361, 411, 461, 312,
        263, 264, 563, 564, 316, 367, 418, 417, 467,
        516, 415, 371, 321, 271, 272, 322, 372, 423,
        223, 225, 175, 475, 425, 285, 286, 335, 336};
    private int[] spaceshipArray = {502, 505, 556, 656, 606, 655, 653, 654, 602};
    private int[] gliderArray = {53, 104, 152, 153, 154};
    
    //---Default Constructor----------------------------------------------------
    public Board() {

        rectArray = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            for (int j = 1; j <= 50; j++) {
                rectArray.add(new Cell(j * 10,
                                       i * 10,
                                       10, 10,
                                       j, i));
            }
        }
    }

    //---Setting up Grid/Array--------------------------------------------------
    public void paintComponent(Graphics g) {

        this.g = (Graphics2D) g;

        super.paintComponent(g);

        for (int i = 0; i < rectArray.size(); i++) {

            if (rectArray.get(i).getColor() != null) {
                this.g.setPaint(rectArray.get(i).getColor());
                this.g.fill(rectArray.get(i));
            }
            this.g.drawRect(rectArray.get(i).x, rectArray.get(i).y, 10, 10);
        }
    }

    //---Checks for Clicked Rectangle; turns it black---------------------------
    public void checkPoint(double x, double y) {
        Rectangle tempRect = new Rectangle();

        int tempX = ((int) ((x / 10) - .5));
        int tempY = ((int) ((y - 24) / 10));

        System.out.println(tempX + " , " + tempY);

        for (int i = 0; i < rectArray.size(); i++) {
            tempRect = rectArray.get(i);
            if (((tempX * 10) == tempRect.x) && ((tempY * 10) == tempRect.y)) {
                if (rectArray.get(i).getColor() == null) {
                    rectArray.get(i).setColor(Color.BLACK);
                    rectArray.get(i).setHealth(true);
                    System.out.println(i);
                } else {
                    rectArray.get(i).setColor(null);
                    rectArray.get(i).setHealth(false);
                }
            }
        }
    }
    
    //---Resets Grid/Array------------------------------------------------------
    public void clearRect() {
        for (int i = 0; i < rectArray.size(); i++) {
            rectArray.get(i).setColor(null);
            rectArray.get(i).setHealth(false);
        }
    }
    
    //---Iterates Through Each Rectangle, Checking its Neighbors----------------
    public void ruleCheck() {

        neighbors = 0;

        int tempX = 0;
        int tempY = 0;

        for (int i = 0; i < rectArray.size(); i++) {
            neighbors = 0;

            tempX = ((int) ((rectArray.get(i).x / 10) - .5));
            tempY = ((int) ((rectArray.get(i).y - 24) / 10));
            
            // Upper-left Corner
            if (i == 0) {
                if (rectArray.get(i + 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 51).getHealth() == true) {
                    neighbors++;
                }
            } else if (i == 49) { // Upper-right Cornner
                if (rectArray.get(i - 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 49).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 50).getHealth() == true) {
                    neighbors++;
                }
            } else if (i == 2450) { // Lower-left Corner
                if (rectArray.get(i + 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 49).getHealth() == true) {
                    neighbors++;
                }
            } else if (i == 2499) { // Lower-right Corner
                if (rectArray.get(i - 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 51).getHealth() == true) {
                    neighbors++;
                }
            } else if ((i >= 1) && (i <= 48)) { // Top row
                if (rectArray.get(i - 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 49).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 51).getHealth() == true) {
                    neighbors++;
                }
            } else if ((((rectArray.get(i).x) == 10)) && 
                    (tempY <= 49) && 
                    (i != 0) && 
                    (i != 2450)) { // Left-most Column
                if (rectArray.get(i - 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 48).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 51).getHealth() == true) {
                    neighbors++;
                }

            } else if ((i >= 2451) && (i <= 2498)) { // Bottom Row
                if (rectArray.get(i - 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 51).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 49).getHealth() == true) {
                    neighbors++;
                }
            } else if ((((rectArray.get(i).x) == 500)) && 
                    (tempY <= 49) && (i != 49) && 
                    (i != 2499)) { // Right-most Column
                if (rectArray.get(i - 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 49).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 51).getHealth() == true) {
                    neighbors++;
                }
            } else { // All others
                if (rectArray.get(i + 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 1).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 50).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 49).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i - 51).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 49).getHealth() == true) {
                    neighbors++;
                }

                if (rectArray.get(i + 51).getHealth() == true) {
                    neighbors++;
                }
            }
            
            // Assigning neighbors
            rectArray.get(i).setNeighbors(neighbors);
        }
        
        
        // Applying rules of the "game"
        for (int i = 0; i < rectArray.size(); i++) {
            if (rectArray.get(i).getNeighbors() < 2) {
                rectArray.get(i).setHealth(false);
                rectArray.get(i).setColor(null);
            } else if (rectArray.get(i).getNeighbors() > 3) {
                rectArray.get(i).setHealth(false);
                rectArray.get(i).setColor(null);
            } else if (rectArray.get(i).getNeighbors() == 3) {
                rectArray.get(i).setHealth(true);
                rectArray.get(i).setColor(Color.BLACK);
            }
        }
    }
    
    //---Draws a glider pattern-------------------------------------------------
    public void glider() {
        for (int i = 0; i < gliderArray.length; i++) {
            rectArray.get(gliderArray[i]).setHealth(true);
            rectArray.get(gliderArray[i]).setColor(Color.BLACK);
        }
    }
    
    //---Applies a vertical column ("Vertical Divider)--------------------------
    public void vertical() {
        for (int i = 24; i <= 2474; i += 50) {
            rectArray.get(i).setHealth(true);
            rectArray.get(i).setColor(Color.BLACK);
        }
    }
    
    //---Applies black squares at random----------------------------------------
    public void random() {
        Random rand = new Random();

        for (int i = 0; i < rectArray.size(); i++) {
            if (rand.nextBoolean() == true) {
                rectArray.get(i).setHealth(true);
                rectArray.get(i).setColor(Color.BLACK);
            }
        }
    }

    //---Applies the Gosper Glider Gun pattern----------------------------------
    public void gosperGun() {
        for (int i = 0; i < gosperGunArray.length; i++) {
            rectArray.get(gosperGunArray[i] + 2).setHealth(true);
            rectArray.get(gosperGunArray[i] + 2).setColor(Color.BLACK);
        }
    }
    
    //---Applies a "lightweight" spaceship pattern------------------------------
    public void lwSpaceship() {
        for (int i = 0; i < spaceshipArray.length; i++) {
            rectArray.get(spaceshipArray[i]).setHealth(true);
            rectArray.get(spaceshipArray[i]).setColor(Color.BLACK);
        }
    }
}
