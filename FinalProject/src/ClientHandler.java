
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private Socket conn;
    private PrintWriter out;
    private BufferedReader reader;
    private Model model;
    private ClientHandler opponent;
    private String player;
    private boolean opponentStatus = false;
    
    public ClientHandler(Socket conn, String player, Model model){
        this.conn = conn;
        this.player = player;
        this.model = model;
        
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = new PrintWriter(conn.getOutputStream(), true);
        } catch (IOException e){}
        
        out.println("MESS " + conn);
        out.flush();
        out.flush();
    }
    
    public void setOpponent(ClientHandler op){
        this.opponent = op;
    }
    
    public void setModel(Model model){
        this.model = model;
    }
    public ClientHandler getOpponent(){
        return this.opponent;
    }
    
    public String getPlayer(){
        return this.player;
    }
    
    public Socket getConn(){
        return this.conn;
    }
    
    public void OpponentMoved(int col, int row){
        out.println("OPPONENT " + row + col + opponent.player);
        out.flush();
    }
    
    @Override
    public void run() {
        if(opponent == null){
                out.println("MESS " + "Waiting for another player to connect..");
                out.flush();
        }
        
        while(opponent == null){
            Thread.yield();
        }
        
        out.println("BEGIN " + player);
        out.flush();
        opponentStatus  = true;
        messageConnection();
        bothConnected();
    }
    
    public void messageConnection(){
        out.println("MESS " + " ");
        out.println("MESS " + "You are player " + player + ".");
        out.println("MESS " + " ");
        out.println("MESS " + "Opponent has connected. Player ");
        out.println("MESS "+ model.getCurrentPlayer().getPlayer() + " is up first.");
        for(int i=0; i < 6; i++){
            out.flush();
        }
    }
    
    public void informTurn(){
        out.println("MESS " + " Your turn!");
        out.flush();
    }
    
    public void opponentQuit(){
        opponentStatus = false;
        out.println("MESS " + "Your opponent got scared and quit.");
        out.println("MESS " + "THEREFORE, YOU WIN");
        out.println("MESS " + "Quit and rejoin the server to play again!");
        out.println("EXITING " + "You will be disconnected in 5 seconds.");
        for(int i = 0; i < 4; i++){
            out.flush();
        }
    }
    
    public void Winner(){
        
    }
     
    public void bothConnected() {
        while(opponentStatus){
            Thread.yield();
            if(this == model.getCurrentPlayer()){
                informTurn();
                String pos;
                try {
                    if((pos = reader.readLine()) != null) {
                        System.out.println(pos);
                        if(pos.startsWith("MOVE")){
                            int col = Integer.parseInt(pos.substring(5));
                            System.out.println(col);
                            int row;
                            if((row = model.validMove(this, col)) != -1) {
                                model.getCurrentPlayer().OpponentMoved(col, row);
                                out.println("VALID " + row + col + player);
                                out.flush();

                                System.out.println("through");
                                Boolean win = model.CheckforWinner(this, row, col);
                                System.out.println(win);
                                if(win){
                                    out.println("WINNER " + player);
                                    out.println("EXITING " + "You will be disconnected in 5 seconds.");
                                    out.println("MESS " + "Quit and rejoin the server to play again!");
                                    out.flush();
                                    out.flush();
                                    out.flush();
                                    opponent.out.println("WINNER " + player);
                                    opponent.out.println("EXITING " + "You will be disconnected in 5 seconds.");
                                    opponent.out.println("MESS " + "Quit and rejoin the server to play again!");
                                    opponent.out.flush();
                                    opponent.out.flush();
                                    opponent.out.flush();
                                    
                                }
                            }
                        } else if(pos.startsWith("EXIT")){
                            break;
                        }
                    } else {break;}
                } catch (IOException ex) {
                    System.out.println("RUNNING FAILURE");
                    break;
                }
            }
        }
        System.out.println("Player " + player + " Exited.");
        opponent.opponentQuit();
    }
}
