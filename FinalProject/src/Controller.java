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
        view.setAddButtonActionListner(new ButtonActionListeners(out));
        view.setWindowListener(new myWindowListener(out, inn, clientSocket));
        view.disableButtonActionListeners();
        beginGame();
    }
    
    public final void beginGame(){
        String response;
        try { 
            while(true) {
                response = inn.readLine();
                if(response.startsWith("BEGIN")){
                    String player = response.substring(6);
                    view.setTitle(player);
                    if(player.equals("1")){
                        view.enableButtonActionListeners();
                    } else if(player.equals("2")){
                        view.disableButtonActionListeners();
                    }
                }
                if(response.startsWith("VALID")) {
                    //disable buttons
                    view.disableButtonActionListeners();
                    view.setBoard(Integer.parseInt(response.substring(6, 7)),
                            Integer.parseInt(response.substring(7, 8)),
                            Integer.parseInt(response.substring(8, 9)));
                    view.paint();
                } else if(response.startsWith("OPPONENT")) {
                    //enable buttons
                    view.enableButtonActionListeners();
                    view.setBoard(Integer.parseInt(response.substring(9, 10)),
                            Integer.parseInt(response.substring(10, 11)), 
                            Integer.parseInt(response.substring(11, 12)));
                    view.paint();
                } else if (response.startsWith("WINNER")){
                    view.disableButtonActionListeners();
                    view.addListData("Player " + response.substring(7) + " WON!");
                } else if (response.startsWith("MESS")){
                    view.addListData(response.substring(5));
                } else if(response.startsWith("EXITING")){
                    view.addListData(response.substring(8));
                    try {
                        Thread.sleep(5000);
                        out.close();
                        inn.close();
                        clientSocket.close();
                        System.exit(0);
                    } catch (InterruptedException ex) {}
                }
            }
        } catch (IOException e) {}
    }
}