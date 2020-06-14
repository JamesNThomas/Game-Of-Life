package com.jamesnthomas.gameOfLife;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main extends JFrame {

    private static final int CANVAS_HEIGHT = 650;
    private static final int CANVAS_WIDTH = 525;
    private boolean running = false;

    public static void main(String[] args) {

        final Main main = new Main();

        String[] optionsStrings = { "PRESETS", "", "Glider", "Vertical Divider", "Gosper Glider Gun",
                "Lightweight Spaceship", "Random" };
        JComboBox options = new JComboBox(optionsStrings);

        final Board board = new Board();
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BorderLayout());
        boardPanel.add(board, BorderLayout.CENTER);

        JButton start = new JButton("Start");
        JButton clear = new JButton("Clear");
        JButton stop = new JButton("Stop");
        JPanel controlPanel = new JPanel();
        controlPanel.add(clear);
        controlPanel.add(start);
        controlPanel.add(stop);
        controlPanel.add(options);

        JLabel speedSliderLabel = new JLabel("SPEED", JLabel.CENTER);
        JPanel sliderLabelPanel = new JPanel();
        sliderLabelPanel.add(speedSliderLabel);

        JSlider speedSlider = new JSlider(0, 100);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setMinorTickSpacing(10);
        speedSlider.setPaintTicks(true);
        JPanel sliderPanel = new JPanel();
        sliderPanel.add(speedSlider);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(boardPanel);
        mainPanel.add(controlPanel);
        mainPanel.add(sliderLabelPanel);
        mainPanel.add(sliderPanel);

        JFrame window = new JFrame();
        window.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        window.setTitle("Conway's Game of Life");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // ---Timer loop for the game--------------------------------------------
        final Timer time = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.ruleCheck();
                board.revalidate();
                board.repaint();
            }
        });

        // ---Listener for speed slider------------------------------------------
        speedSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                int delay = 100 - ((JSlider) e.getSource()).getValue();
                if (delay < 100) {
                    if (main.getRunning())
                        time.start();
                    time.setDelay(delay);
                } else
                    time.stop();
            }
        });
        
        //---Listener for each rectangle----------------------------------------
        window.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                board.checkPoint(p.getX(), p.getY());
                board.revalidate();
                board.repaint();
            }
        });
        
        //---Listener to clear the grid-----------------------------------------
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                board.clearRect();
                board.revalidate();
                board.repaint();
            }
        });
        
        //---Listener to start the game-----------------------------------------
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                main.setRunning(true);
                time.start();
            }
        });
        
        //---Listener to stop the game------------------------------------------
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.setRunning(false);
                time.stop();
            }

        });
        
        //---Listener to apply various patterns---------------------------------
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox options = (JComboBox) ae.getSource();
                
                if(options.getSelectedItem().equals("Glider")) {
                    board.clearRect();
                    board.glider();
                    System.out.println("Check");
                } else if(options.getSelectedItem().equals("Vertical Divider")) {
                    board.clearRect();
                    board.vertical();
                } else if(options.getSelectedItem().equals("Random")) {
                    board.clearRect();
                    board.random();
                } else if(options.getSelectedItem().equals(
                                                         "Gosper Glider Gun")) {
                    board.clearRect();
                    board.gosperGun();
                } else if(options.getSelectedItem().equals("Lightweight "
                                                         + "Spaceship")) {
                    board.clearRect();
                    board.lwSpaceship();
                }
                
                board.revalidate();
                board.repaint();
            }
            
        });

        window.add(mainPanel);
        window.setVisible(true);
    }

    private void setRunning(boolean running) {
        this.running = running;
    }

    private boolean getRunning() {
        return this.running;
    }
}
