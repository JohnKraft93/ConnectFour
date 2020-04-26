import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class Model {
    Integer[][] grid = new Integer[6][7]; 
    ClientHandler CurrentPlayer;
    
    public Model() {
        for(Integer[] row: grid) {
            Arrays.fill(row, 0);
        }
    }

    public synchronized Integer validMove(ClientHandler player, int col){
        if(player == CurrentPlayer){
            for(int i = 5; i > 0; i--){
                if(grid[i][col] == 0){
                    System.out.println("valid move");
                    grid[i][col] = Integer.parseInt(player.player);
                    CurrentPlayer = CurrentPlayer.opponent;
                    CurrentPlayer.OpponentMoved(col, i);
                    return i;
                }
            }
            //check for full column but not now
        }
        return -1;
    }
}

class ClientHandler extends Thread {
    Socket conn;
    PrintWriter out;
    BufferedReader reader;
    Model model;
    ClientHandler opponent;
    String player;
    
    public ClientHandler(Socket conn, String player, Model model){
        this.conn = conn;
        this.player = player;
        this.model = model;
        
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = new PrintWriter(conn.getOutputStream(), true);
            out.println(player);
            out.flush();
            out.flush();
            System.out.println("YOO");
        } catch (IOException e){}
        
    }
    
    public void setOpponent(ClientHandler op){
        this.opponent = op;
    }
    
    public void OpponentMoved(int col, int row){
        System.out.println("opponentmoved");
        out.println("OPPONENT " + row + col + opponent.player);
        out.flush();
    }
    
    @Override
    public void run() {
        while(true){
            System.out.println("waiting");
            if(this == model.CurrentPlayer){
                System.out.println("server> waiting for client " +
                                player + " to send data..");
                String pos;
                try {
                    if((pos = reader.readLine()) != null) {
                        System.out.println(pos);
                        if(pos.startsWith("MOVE")){
                            int col = Integer.parseInt(pos.substring(5));
                            System.out.println(col);
                            int row;
                            if((row = model.validMove(this, col)) != -1) {
                                System.out.println("PASS THrough valid move");
                                out.println("VALID " + row + col + player);
                                out.flush();
                            }
                        }
                    }
                } catch (IOException ex) {}
            }
        }
    }

}