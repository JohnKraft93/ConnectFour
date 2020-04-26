
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View {
    //build gui
    private final JFrame frame;
    private final JButton buttons[];
    private final int numOfButtons = 7;
    private final JPanel buttPanel;
    public Integer board[][];
    private final DrawPanel drawPanel;
    
    public View(){
        frame = new JFrame("Connect Four");
        buttons = new JButton[numOfButtons];
        buttPanel = new JPanel();
               
        board = new Integer[6][7];
        
        for(Integer[] row: board){
            Arrays.fill(row, 0);
        }
        
        for (int i = 0; i < numOfButtons; i++){
            buttons[i] = new JButton(Integer.toString(i));
            buttPanel.add(buttons[i]);
        }
        drawPanel = new DrawPanel();
        frame.setSize(665, 600);
        //frame.getContentPane().add(BorderLayout.NORTH, title);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, buttPanel);
        frame.setVisible(true);
        
    }
    
    public void setWindowListener(WindowListener wl){
        frame.addWindowListener(wl);
    }
    
    public void setAddButtonActionListner(ActionListener al){
        for(int i = 0; i < numOfButtons; i++){
            buttons[i].addActionListener(al);
        }
    }
    
    public void setBoard(int row, int col, int player){
        board[row][col] = player;
        for(Integer[] x: board){
            System.out.println(Arrays.toString(x));
        }
        
    }
    
    public void disableButtonActionListeners() {
        for(JButton b : buttons){
            b.setEnabled(false);
        }
    }
    
    public void enableButtonActionListeners() {
        for(JButton b : buttons){
            b.setEnabled(true);
        }
    }
    
    public void setTitle(String s){
        frame.setTitle(s);
        frame.repaint();
    }
    
    public class DrawPanel extends JPanel {
        
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g);
            setBackground(Color.BLUE);
            int spaces = 15;
            int height = 70;
            int width = 70;
            for(int row = 0; row < 6; row++){
                for(int column = 0; column < 7; column++){
                    switch (board[row][column]){
                        case 1:
                            g2.setColor(Color.RED);
                            g2.fillOval((85*column)+spaces, (85*row)+spaces,
                                    height, width);
                            break;
                        case 2: 
                            g2.setColor(Color.YELLOW);
                            g2.fillOval((85*column)+spaces, (85*row)+spaces,
                                    height, width);
                            break;
                        default:
                            g2.setColor(Color.WHITE);
                            g2.fillOval((85*column)+spaces, (85*row)+spaces,
                                    height, width);
                            //default
                            break;
                    }
                    
                    //System.out.println("next");
                }
            }
            
            /*
            for(int i = 0; i < 7 * 70; i+=70){
                for(int j = 0; j < 6 * 70; j+=70){
                    g2.setColor(Color.black);
                    switch (board[j/70][i/70]) {
                        case 1:
                            g2.setColor(Color.red);
                            g2.fillOval(i+75, j+75, 60, 60);
                            //g.fillRect(i+75, j+75, 60, 60);
                            break;
                        case 2:
                            g2.setColor(Color.blue);
                            g2.fillOval(i+75, j+75, 60, 60);
                            break;
                        default:
                            g2.setColor(Color.BLACK);
                            g2.drawOval(i+70, j+70, 70, 70);
                            g2.setColor(Color.WHITE);
                            g2.fillOval(i+72, j+72, 68, 68);
                            break;
                    }
                }
            }
            */
        }
    }
    
    public void paint(){
        drawPanel.repaint();
    }
    
}


