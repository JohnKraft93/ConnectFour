import java.io.IOException;
import java.net.ServerSocket;


public class server {

    public static void main(String[] args){
        Model model;

        try {
            ServerSocket server = new ServerSocket(5000);
            model = new Model();
            
            ClientHandler player1 = new ClientHandler(server.accept(), "1", model);
            ClientHandler player2 = new ClientHandler(server.accept(), "2", model);
            player1.setOpponent(player2);
            player2.setOpponent(player1);
            model.CurrentPlayer = player1;
            player1.start();
            player2.start();
                
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
