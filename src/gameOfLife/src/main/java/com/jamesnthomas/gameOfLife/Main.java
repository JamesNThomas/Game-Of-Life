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

    private int height = 675;
    private int width = 525;
    private int delay = 50;
    private boolean running = false;

    public static void main(String[] args) {

        final Main main = new Main();

        String[] optionsStrings = { "PRESETS", "", "Glider", "Vertical Divider", "Gosper Glider Gun",
                "Lightweight Spaceship", "Random" };
        JComboBox options = new JComboBox(optionsStrings);

        final Board board = new Board();
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BorderLayout());
        boardPanel.add(board);

        JButton start = new JButton("Start");
        JButton clear = new JButton("Clear");
        JButton stop = new JButton("Stop");
        JPanel controlPanel = new JPanel();
        controlPanel.add(clear);
        controlPanel.add(start);
        controlPanel.add(stop);
        controlPanel.add(options);

        JLabel speedSliderLabel = new JLabel("SPEED", JLabel.CENTER);
        JSlider speedSlider = new JSlider(0, 100);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setMinorTickSpacing(10);
        speedSlider.setPaintTicks(true);
        JPanel speedLabelPanel = new JPanel();
        JPanel speedSliderPanel = new JPanel();
        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.Y_AXIS));
        speedLabelPanel.add(speedSliderLabel);
        speedSliderPanel.add(speedSlider);
        speedPanel.add(speedLabelPanel);
        speedPanel.add(speedSliderPanel);

        JLabel sizeSliderLabel = new JLabel("SIZE", JLabel.CENTER);
        JSlider sizeSlider = new JSlider(50, 100);
        sizeSlider.setMajorTickSpacing(10);
        sizeSlider.setMinorTickSpacing(5);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setValue(50);
        JPanel sizeLabelPanel = new JPanel();
        JPanel sizeSliderPanel = new JPanel();
        JPanel sizePanel = new JPanel();
        sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.Y_AXIS));
        sizeLabelPanel.add(sizeSliderLabel);
        sizeSliderPanel.add(sizeSlider);
        sizePanel.add(sizeLabelPanel);
        sizePanel.add(sizeSliderPanel);

        JPanel sliderPanel = new JPanel();
        sliderPanel.add(speedPanel);
        sliderPanel.add(sizePanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(boardPanel);
        mainPanel.add(controlPanel);
        mainPanel.add(sliderPanel);

        final JFrame window = new JFrame();
        window.setSize(main.getWidth(), main.getHeight());
        window.setTitle("Conway's Game of Life");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // ---Timer loop for the game--------------------------------------------
        final Timer time = new Timer(main.getDelay(), new ActionListener() {
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
                main.setDelay(100 - ((JSlider) e.getSource()).getValue());
                if (main.getDelay() < 100) {
                    if (main.getRunning())
                        time.start();
                    time.setDelay(main.getDelay());
                } else
                    time.stop();
            }
        });

        // ---Listener for size slider-------------------------------------------
        sizeSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if(!source.getValueIsAdjusting()) {
                    main.setWidth((source.getValue() * 10) + 25);
                    main.setHeight((source.getValue() * 10) + 175);
                    board.setBoardSize(source.getValue());
                    board.resizeBoard();
                    board.revalidate();
                    board.repaint();
                    window.setSize(main.getWidth(), main.getHeight());
                    window.repaint();
                }
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
                if (main.getDelay() < 100) {
                    main.setRunning(true);
                    time.start();
                }
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

    private void setDelay(int delay) {
        this.delay = delay;
    }

    private int getDelay() {
        return this.delay;
    }

    public int getWidth() {
        return this.width;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    private void setHeight(int height) {
        this.height = height;
    }
}
