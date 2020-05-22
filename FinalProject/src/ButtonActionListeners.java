import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ButtonActionListeners implements ActionListener {
    PrintWriter out;
    public ButtonActionListeners(PrintWriter out){
        this.out = out;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("client> send request to server: " + e.getActionCommand());
        out.println("MOVE " + Integer.toString(Integer.parseInt(e.getActionCommand())-1));
        out.flush();
        out.flush();
    }
}