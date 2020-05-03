import java.io.IOException;
import java.net.ServerSocket;

public class server {

    public static void main(String[] args) {
        int conns = 0;
        Model model;
        ClientHandler player1, player2;
        
        try {
            ServerSocket server = new ServerSocket(5000);
            while(true){
                System.out.println("hlell");
                model = new Model();
                player1 = new ClientHandler(server.accept(), "1", model);
                player1.start();
                player2 = new ClientHandler(server.accept(), "2", model);
                player1.setOpponent(player2);
                player2.setOpponent(player1);
                model.setCurrentPlayer(player1);
                player2.start();
                try {
                    player1.join();
                    player2.join();
                } catch (InterruptedException ex) {}
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
