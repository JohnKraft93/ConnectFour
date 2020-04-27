import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Model {
    int maxCol = 7;
    int maxRow = 6;
    Integer[][] grid = new Integer[maxRow][maxCol]; 
    ClientHandler CurrentPlayer;
    
    public Model() {
        for(Integer[] row: grid) {
            Arrays.fill(row, 0);
        }
    }

    public synchronized Integer validMove(ClientHandler player, int col){
        if(player == CurrentPlayer){
            for(int i = maxRow-1; i >= 0; i--){
                if(grid[i][col] == 0){
                    System.out.println("valid move");
                    grid[i][col] = Integer.parseInt(player.player);
                    CurrentPlayer = CurrentPlayer.opponent;
                    return i;
                }
            }
        }
        return -1;
    }
    
    public boolean CheckforWinner(ClientHandler player, int row, int col) {
        int count = 0;
        //check for horizontal winner
        for(int i = 0; i < maxCol; i++){
            if(grid[row][i]==Integer.parseInt(player.player)){
                count++;
            } else { count = 0;}
            if(count >= 4){
                return true;
            }            
        }
        //check for vertical winner
        for(int i = 0; i < maxRow; i++){
            if(grid[i][col]==Integer.parseInt(player.player)){
                count++;
            } else { count = 0;}
            if(count >= 4){
                return true;
            }            
        }
  
        //check for diagonal winner
        
        return false;
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
        } catch (IOException e){}
    }
    
    public void setOpponent(ClientHandler op){
        this.opponent = op;
    }
    
    public void OpponentMoved(int col, int row){
        out.println("OPPONENT " + row + col + opponent.player);
        out.flush();
    }
    
    @Override
    public void run() {
        if(opponent == null){
                out.println("MESS " + "Waiting for another player to connect...");
                out.flush();
        }
        
        while(opponent == null){
            Thread.yield();
        }
        
        out.println("BEGIN " + player);
        out.flush();
        //messageConnection();
        bothConnected();
    }
    
    public void messageConnection(){
        out.println("MESS " + "Opponent has connected. Player \n" 
                + model.CurrentPlayer.player + " is up first.");
        out.flush();
    }
    
    public void bothConnected() {
        while(true){
            Thread.yield();
            if(this == model.CurrentPlayer){
                //System.out.println("server> waiting for client " +
                //                player + " to send data..");
                String pos;
                try {
                    if((pos = reader.readLine()) != null) {
                        System.out.println(pos);
                        if(pos.startsWith("MOVE")){
                            int col = Integer.parseInt(pos.substring(5));
                            System.out.println(col);
                            int row;
                            if((row = model.validMove(this, col)) != -1) {
                                model.CurrentPlayer.OpponentMoved(col, row);
                                out.println("VALID " + row + col + player);
                                out.flush();
                                if(model.CheckforWinner(this, row, col)){
                                    out.println("WINNER " + player);
                                    out.flush();
                                    opponent.out.println("WINNER " + player);
                                    out.flush();
                                }
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}