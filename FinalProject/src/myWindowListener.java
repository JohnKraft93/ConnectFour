import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class myWindowListener implements WindowListener {
    
    private final PrintWriter out;
    private final BufferedReader inn;
    private final Socket clientSocket;
    
    public myWindowListener(PrintWriter out, BufferedReader inn,
            Socket clientSocket) {
        this.out = out;
        this.inn = inn;
        this.clientSocket = clientSocket;
    }
    
    @Override 
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {
            out.println("EXIT");
            out.flush();

        try{
            out.close();
            inn.close();
            clientSocket.close();
            System.exit(0);
        } catch (IOException io){
            io.printStackTrace();
        } 
    }
    
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {} 
}

