import java.io.IOException;
import java.net.ServerSocket;


public class server {

    
    public static void main(String[] args){
        Model model;
        //BufferedReader p1In, p2In;
        //PrintWriter p1Out, p2Out;
        //int turn = 0;

        try {
            ServerSocket server = new ServerSocket(5000);
            model = new Model();
            /*
            Socket p1 = server.accept();
            Socket p2 = server.accept();
            
            p1In = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            p2In = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            
            p1Out = new PrintWriter(p1.getOutputStream(),  true);
            p2Out = new PrintWriter(p2.getOutputStream(),  true);
            
            p1Out.println("PLAYER 1");
            p1Out.flush();
            p1Out.flush();
            p2Out.println("PLAYER 2");
            p2Out.flush();
            p2Out.flush();
            
            if(turn % 2 == 0) {
                String input;
                try {
                    while((input = p1In.readLine()) != null){
                        System.out.println(input);
                        p1Out.print("VALID PLAYER 1");
                        p2Out.print("OPPONENT P2");
                    }
                } catch(IOException e) {}
            }
            */
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
