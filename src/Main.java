
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Main extends JFrame {

    public static final int CANVAS_HEIGHT = 600;
    public static final int CANVAS_WIDTH = 525;

    public static void main(String[] args) {

        String[] optionsStrings = {"Glider", "Vertical Divider", "Gosper Glider Gun", 
                                   "Lightweight Spaceship", "Random"};
        JComboBox options = new JComboBox(optionsStrings);

        DrawComp DC = new DrawComp();
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.add(DC, BorderLayout.CENTER);

        JButton start = new JButton("Start");
        JButton clear = new JButton("Clear");
        JButton stop = new JButton("Stop");
        JPanel panel2 = new JPanel();
        panel2.add(clear);
        panel2.add(start);
        panel2.add(stop);
        panel2.add(options);


        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        panel4.add(panel1);
        panel4.add(panel2);

        JFrame window = new JFrame();
        window.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        window.setTitle("Conway's Game of Life");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        //---Timer loop for the game--------------------------------------------
        Timer time = new Timer(60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                DC.ruleCheck();
                DC.revalidate();
                DC.repaint();
            }
        });
        
        //---Listener for each rectangles---------------------------------------
        window.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                DC.checkPoint(p.getX(), p.getY());

                DC.revalidate();
                DC.repaint();
            }
        });
        
        //---Listener to clear the grid-----------------------------------------
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                DC.clearRect();
                DC.revalidate();
                DC.repaint();
            }
        });
        
        //---Listener to start the game-----------------------------------------
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                time.start();
            }
        });
        
        //---Listener to stop the game------------------------------------------
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                time.stop();
            }

        });
        
        //---Listener to apply various patterns---------------------------------
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox options = (JComboBox) ae.getSource();
                
                if(options.getSelectedItem().equals("Glider")) {
                    DC.clearRect();
                    DC.glider();
                    System.out.println("Check");
                } else if(options.getSelectedItem().equals("Vertical Divider")) {
                    DC.clearRect();
                    DC.vertical();
                } else if(options.getSelectedItem().equals("Random")) {
                    DC.clearRect();
                    DC.random();
                } else if(options.getSelectedItem().equals(
                                                         "Gosper Glider Gun")) {
                    DC.clearRect();
                    DC.gosperGun();
                } else if(options.getSelectedItem().equals("Lightweight "
                                                         + "Spaceship")) {
                    DC.clearRect();
                    DC.lwSpaceship();
                }
                
                DC.revalidate();
                DC.repaint();
            }
            
        });

        window.add(panel4);
        window.setVisible(true);
    }
}
