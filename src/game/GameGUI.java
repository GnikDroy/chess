package game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class GameGUI {
	private BoardManager boardManager;
	private JButton lastSelection = null;
	private JButton[][] allButtons=null;
	private PlayerType perspective=PlayerType.BLACK;
	
	public static void main(String[] args) {
		new GameGUI();
	}

	public GameGUI() {
		boardManager = new BoardManager();
		JFrame guiFrame = new JFrame();
		// make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setMinimumSize(new Dimension(700,700));
		guiFrame.setTitle("Chess");
		guiFrame.setSize(800, 600);
		// This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		// Options for the JComboBox
		guiFrame.setLayout(new BorderLayout());
		ChessWindow window = new ChessWindow();
		allButtons=window.getButtons();
		guiFrame.add(window);
		guiFrame.pack();
		window.setVisible(true);
		guiFrame.setVisible(true);
	}

	class MyActionListener implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		
		public void updateBoard(){
			Square[][] squares=boardManager.getBoard().getSquares();
			for (int row=0;row<8;row++)
			{
				for (int col=0;col<8;col++){
					JButton button=allButtons[col][row];
					Square square=squares[row][col];
					if (square.isOccupied())
					{
						String playerString;
						String pieceString;
						if (square.getPiece().getPlayer()==PlayerType.WHITE){playerString="white";}
						else{playerString="black";}
						if (square.getPiece().getType()==PieceType.KING){pieceString="King";}
						else if(square.getPiece().getType()==PieceType.PAWN){pieceString="Pawn";}
						else if(square.getPiece().getType()==PieceType.ROOK){pieceString="Rook";}
						else if(square.getPiece().getType()==PieceType.KNIGHT){pieceString="Knight";}
						else if(square.getPiece().getType()==PieceType.BISHOP){pieceString="Bishop";}
						else {pieceString="Queen";}
						
						Image image = null;
						try {
							image = ImageIO.read(getClass().getResource(
									"/"+playerString+pieceString+".png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(image));
						
					}
					else{button.setIcon(null);}
				}
			}
			
		}
		public void actionPerformed(ActionEvent e) {
			
			JButton button = (JButton) e.getSource();
			Point rv = new Point();
			int selectionX = button.getLocation(rv).x / 80;
			int selectionY = button.getLocation(rv).y / 80;
			Coordinate currentCoordinate = new Coordinate(selectionX,
					selectionY);
			boolean moved=false;
			if (lastSelection != null) {
				selectionX = lastSelection.getLocation(rv).x / 80;
				selectionY = lastSelection.getLocation(rv).y / 80;
				Coordinate lastCoordinate = new Coordinate(selectionX,
						selectionY);
				
				moved = boardManager.move(lastCoordinate,
						currentCoordinate);
				if (moved) {
					updateBoard();
				}
			}

			if (!moved){lastSelection = button;}

		}
	}

	public class ChessWindow extends JPanel {
		private JButton[][] allButtons=new JButton[8][8];
		public JButton[][] getButtons(){
			return allButtons;
		}
		public ChessWindow() {
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					gbc.gridx = col;
					gbc.gridy = row;
					Border border = null;
					if (row < 7) {
						if (col < 7) {
							border = new MatteBorder(1, 1, 0, 0,
									java.awt.Color.GRAY);
						} else {
							border = new MatteBorder(1, 1, 0, 1,
									java.awt.Color.GRAY);
						}
					} else {
						if (col < 7) {
							border = new MatteBorder(1, 1, 1, 0,
									java.awt.Color.GRAY);
						} else {
							border = new MatteBorder(1, 1, 1, 1,
									java.awt.Color.GRAY);
						}
					}
					JButton button = new JButton();

					if (row == 6) {
						Image blackPawn = null;
						try {
							blackPawn = ImageIO.read(getClass().getResource(
									"/blackPawn.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(blackPawn));
					}

					if (row == 1) {
						Image whitePawn = null;
						try {
							whitePawn = ImageIO.read(getClass().getResource(
									"/whitePawn.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(whitePawn));
					}

					if (row == 7 && (col == 0 || col == 7)) {
						Image blackRook = null;
						try {
							blackRook = ImageIO.read(getClass().getResource(
									"/blackRook.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(blackRook));
					}

					if (row == 0 && (col == 0 || col == 7)) {
						Image whiteRook = null;
						try {
							whiteRook = ImageIO.read(getClass().getResource(
									"/whiteRook.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(whiteRook));
					}

					if (row == 7 && (col == 1 || col == 6)) {
						Image blackKnight = null;
						try {
							blackKnight = ImageIO.read(getClass().getResource(
									"/blackKnight.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(blackKnight));
					}

					if (row == 0 && (col == 1 || col == 6)) {
						Image whiteKnight = null;
						try {
							whiteKnight = ImageIO.read(getClass().getResource(
									"/whiteKnight.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(whiteKnight));
					}

					if (row == 7 && (col == 2 || col == 5)) {
						Image blackBishop = null;
						try {
							blackBishop = ImageIO.read(getClass().getResource(
									"/blackBishop.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(blackBishop));
					}

					if (row == 0 && (col == 2 || col == 5)) {
						Image whiteBishop = null;
						try {
							whiteBishop = ImageIO.read(getClass().getResource(
									"/whiteBishop.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(whiteBishop));
					}

					if (row == 7 && col == 3) {
						Image blackQueen = null;
						try {
							blackQueen = ImageIO.read(getClass().getResource(
									"/blackQueen.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(blackQueen));
					}

					if (row == 0 && col == 3) {
						Image whiteQueen = null;
						try {
							whiteQueen = ImageIO.read(getClass().getResource(
									"/whiteQueen.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(whiteQueen));
					}

					if (row == 7 && col == 4) {
						Image blackKing = null;
						try {
							blackKing = ImageIO.read(getClass().getResource(
									"/blackKing.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(blackKing));
					}

					if (row == 0 && col == 4) {
						Image whiteKing = null;
						try {
							whiteKing = ImageIO.read(getClass().getResource(
									"/whiteKing.png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(whiteKing));
					}

					button.setBorderPainted(false);
					allButtons[row][col]=button;
					button.setPreferredSize(new Dimension(80, 80));
					if ((row+col)%2!=0)
					{
					button.setBackground(java.awt.Color.white);
					}
					else
					{
						button.setBackground(new Color(29,114,46));
					}
					MyActionListener mal = new MyActionListener();
					button.addActionListener(mal);
					add(button, gbc);

				}
			}
		}
	}

}
