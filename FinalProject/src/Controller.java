import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class Controller {
    private PrintWriter out;
    private BufferedReader inn;
    private Socket clientSocket;
    private View view;
    private int col;
    
    public Controller(){
        view = new View();
        try {
            System.out.println("client connecting to 127.0.0.1:5000...");
            clientSocket = new Socket("127.0.0.1", 5000);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            inn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
        //view.setAddButtonAction(new)
        view.setAddButtonActionListner(new ButtonActionListeners());
        view.setWindowListener(new myWindowListener(out, inn, clientSocket));
        
        beginGame();
    }
    
    public final void beginGame(){
        String response;
        try { 
            response = inn.readLine();
            if(response.startsWith("1")){
                //enable buttons
                System.out.println(response);
                view.setTitle(response);
            } else if (response.startsWith("2")){
                //disable buttons
                System.out.println(response);
                view.setTitle(response);                
            }
            while(true) {
                response = inn.readLine();
                if(response.startsWith("VALID")) {
                    System.out.println(response);
                    //disable buttons
                    view.setBoard(Integer.parseInt(response.substring(6, 7)),
                            Integer.parseInt(response.substring(7, 8)),
                            Integer.parseInt(response.substring(8, 9)));
                    view.paint();
                } else if(response.startsWith("OPPONENT")) {
                    System.out.println(response);
                    //enable buttons
                    System.out.println("OPPONENT");
                    System.out.println(response);
                    view.setBoard(Integer.parseInt(response.substring(9, 10)),
                            Integer.parseInt(response.substring(10, 11)), 
                            Integer.parseInt(response.substring(11, 12)));
                    view.paint();
                }
            }
        } catch (IOException e) {}

    }
    
    public class ButtonActionListeners implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("client> send request to server: " + e.getActionCommand());
            col = Integer.parseInt(e.getActionCommand());
            out.println("MOVE " + e.getActionCommand());
            out.flush(); 
        }
    }
}