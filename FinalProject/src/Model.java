import java.util.Arrays;

public class Model {
    private final int maxCol = 7;
    private final int maxRow = 6;
    private final Integer[][] grid = new Integer[maxRow][maxCol]; 
    private ClientHandler CurrentPlayer;
    
    public Model() {
        for(Integer[] row: grid) {
            Arrays.fill(row, 0);
        }
    }
    
    public ClientHandler getCurrentPlayer() {
        return CurrentPlayer;
    }
    
    public void setCurrentPlayer(ClientHandler ch) {
        this.CurrentPlayer = ch;
    }

    public synchronized Integer validMove(ClientHandler player, int col) {
        if(player == CurrentPlayer){
            for(int i = maxRow-1; i >= 0; i--) {
                if(grid[i][col] == 0){
                    grid[i][col] = Integer.parseInt(player.getPlayer());
                    CurrentPlayer = CurrentPlayer.getOpponent();
                    return i;
                }
            }
        }
        return -1;
    }
    
    public boolean CheckforWinner(ClientHandler player, int row, int col) {
        int count = 0;
        
        for(int i = 0; i < maxCol; i++){
            if(grid[row][i]==Integer.parseInt(player.getPlayer())){
                count++;
            } else { count = 0;}
            if(count >= 4){
                return true;
            }            
        }

        for(int i = 0; i < maxRow; i++){
            if(grid[i][col]==Integer.parseInt(player.getPlayer())){
                count++;
            } else { count = 0;}
            if(count >= 4){
                return true;
            }            
        }
        
        for (int tempRow = 0; tempRow < maxRow - 3; tempRow++){
            for (int tempCol = 0; tempCol < maxCol - 3; tempCol++){                
                if  (!grid[tempRow][tempCol].equals(0) && 
                     grid[tempRow][tempCol].equals(grid[tempRow+1][tempCol+1]) && 
                     grid[tempRow][tempCol].equals(grid[tempRow+2][tempCol+2]) &&
                     grid[tempRow][tempCol].equals(grid[tempRow+3][tempCol+3])) {
                    
                    return true;
                }
            }
        }
        
        for (int tempRow = 0; tempRow < maxRow - 3; tempRow++){
            for (int tempCol = 3; tempCol < maxCol; tempCol++){
                if  (!grid[tempRow][tempCol].equals(0) && 
                     grid[tempRow][tempCol].equals(grid[tempRow+1][tempCol-1]) && 
                     grid[tempRow][tempCol].equals(grid[tempRow+2][tempCol-2]) &&
                     grid[tempRow][tempCol].equals(grid[tempRow+3][tempCol-3])){
                    return true;
                }
            }
        }
        
        return false;
    }
}