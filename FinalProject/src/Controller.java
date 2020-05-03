import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class Controller {
    private PrintWriter out;
    private BufferedReader inn;
    private Socket clientSocket;
    private View view;
    
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
        view.setAddButtonActionListner(new ButtonActionListeners());
        view.setWindowListener(new myWindowListener(out, inn, clientSocket));
        view.disableButtonActionListeners();
        beginGame();
    }
    
    public final void beginGame(){
        String response;
        try { 
            while(true) {
                response = inn.readLine();
                System.out.println(response);
                if(response.startsWith("BEGIN")){
                    System.out.println(response);
                    String player = response.substring(6);
                    view.setTitle(player);
                    if(player.equals("1")){
                        view.enableButtonActionListeners();
                    } else if(player.equals("2")){
                        view.disableButtonActionListeners();
                    }
                }
                if(response.startsWith("VALID")) {
                    System.out.println(response);
                    //disable buttons
                    view.disableButtonActionListeners();
                    view.setBoard(Integer.parseInt(response.substring(6, 7)),
                            Integer.parseInt(response.substring(7, 8)),
                            Integer.parseInt(response.substring(8, 9)));
                    view.paint();
                } else if(response.startsWith("OPPONENT")) {
                    System.out.println(response);
                    //enable buttons
                    view.enableButtonActionListeners();
                    System.out.println("OPPONENT");
                    System.out.println(response);
                    view.setBoard(Integer.parseInt(response.substring(9, 10)),
                            Integer.parseInt(response.substring(10, 11)), 
                            Integer.parseInt(response.substring(11, 12)));
                    view.paint();
                } else if (response.startsWith("WINNER")){
                    view.disableButtonActionListeners();
                    view.addListData("Player " + response.substring(7) + " WON!");
                } else if (response.startsWith("MESS")){
                    System.out.println("hello there");
                    view.addListData(response.substring(5));
                }
            }
        } catch (IOException e) {}
    }
    
    public class ButtonActionListeners implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("client> send request to server: " + e.getActionCommand());
            out.println("MOVE " + Integer.toString(Integer.parseInt(e.getActionCommand())-1));
            out.flush();
            out.flush();
        }
    }
}