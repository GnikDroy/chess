public class Main{

public static void main(String[] args) {
    Player player=new Player(PlayerType.WHITE);
    Square position=new Square(2,3);
    
    Bishop neT=new Bishop(player,PieceType.BISHOP,position);

}

}