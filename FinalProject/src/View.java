import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.Arrays;
import javax.swing.*;

public class View {
    private final JFrame frame;
    private final JButton buttons[];
    private final int numOfButtons = 7;
    private final JPanel buttPanel;
    private final Integer board[][];
    private JList list;
    private final DefaultListModel listModel;
    private final DrawPanel drawPanel;
    
    public View(){
        frame = new JFrame("Connect Four");
        buttons = new JButton[numOfButtons];
        buttPanel = new JPanel();
        listModel = new DefaultListModel();
        list = new JList(listModel);
        board = new Integer[6][7];
        
        for(Integer[] row: board){
            Arrays.fill(row, 0);
        }
        
        for (int i = 0; i < numOfButtons; i++){
            buttons[i] = new JButton(Integer.toString(i+1));
            buttPanel.add(buttons[i]);
        }
        drawPanel = new DrawPanel();
        frame.setSize(1000, 600);
        frame.getContentPane().add(BorderLayout.EAST, list);
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
    
    public void addListData(String item){
        listModel.addElement(item);
        list = new JList(listModel);
        frame.repaint();
    }
    
    public void setTitle(String s){
        frame.setTitle("Player " + s);
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
                            break;
                        case 2: 
                            g2.setColor(Color.YELLOW);
                            break;
                        default:
                            g2.setColor(Color.WHITE);
                            break;
                    }
                    g2.fillOval((85*column)+spaces, (85*row)+spaces,
                                    height, width);
                }
            }
        }
    }
    public void paint(){
        drawPanel.repaint();
    }
}