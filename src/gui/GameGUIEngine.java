package gui;

import game.BoardManager;
import game.Coordinate;
import game.MoveParser;
import game.Square;
import game.Stockfish;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import pieces.PieceType;
import player.PlayerType;


/**
 * @author gnik
 *
 */
public class GameGUIEngine {
	private BoardManager boardManager;
	private JButton lastSelection = null;
	private JButton[][] allButtons = null;
	private Stockfish stockfish=new Stockfish();
	private PlayerType humanPlayer=PlayerType.WHITE;

	public static void main(String[] args) {
		new GameGUIEngine(PlayerType.WHITE);
	}

	public GameGUIEngine(PlayerType player) {
		humanPlayer=player;
		stockfish.setLevel(0);
		boardManager = new BoardManager();
		initialize();

	}
	public void moveAI(){
		if (boardManager.getCurrentPlayer()!=humanPlayer){
			String bestMove=stockfish.getBestMove(MoveParser.parse(boardManager.getMoveList()));
			
			if(!bestMove.equals("")){
			Coordinate initCoordinate=new Coordinate(bestMove.substring(0,2));
			Coordinate finalCoordinate=new Coordinate(bestMove.substring(2,4));
			boardManager.move(initCoordinate,finalCoordinate);
			if (boardManager.isValidPromotion(boardManager.getBoard()
					.getSquare(finalCoordinate))) {
				boardManager.promote(
						boardManager.getBoard().getSquare(
								finalCoordinate), PieceType.fromString(bestMove.substring(4,5)));}
			}
		}
	}	
	private void initialize() {
		JFrame guiFrame = new JFrame();
		guiFrame.setMinimumSize(new Dimension(700, 700));
		guiFrame.setTitle("Chess");
		guiFrame.setSize(800, 600);

		// make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);

		guiFrame.setLayout(new BorderLayout());
		ChessWindow window = new ChessWindow(boardManager.getBoard()
				.getSquares());

		allButtons = window.getButtons();
		updateBoard();
		guiFrame.add(window);
		guiFrame.pack();
		window.setVisible(true);
		guiFrame.setVisible(true);

	}
	
	public void highlightSquares(Square[] squares){
		for(Square c:squares){
			allButtons[7-c.getCoordinate().getY()][c.getCoordinate().getX()].setBackground(new Color(29, 114, 100));
		}
	}

	public void updateBoard() {

		Square[][] squares = boardManager.getBoard().getSquares();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				JButton button;
				Square square;
				if (boardManager.getCurrentPlayer() == PlayerType.BLACK) {
					button = allButtons[col][row];
					square = squares[row][col];
				} else {
					button = allButtons[col][row];
					square = squares[row][7 - col];
				}

				// This is necessary to set the background of the buttons.
				int offset = 1;
				if (boardManager.getCurrentPlayer() == PlayerType.WHITE) {
					offset = 0;
				}
				if ((row + col + offset) % 2 == 0) {
					button.setBackground(java.awt.Color.white);
				} else {
					button.setBackground(new Color(29, 114, 46));
				}
				// Places peices if there the square are occupied.
				if (square.isOccupied()) {
					String playerString;
					String pieceString;
					if (square.getPiece().getPlayer() == PlayerType.WHITE) {
						playerString = "white";
					} else {
						playerString = "black";
					}
					if (square.getPiece().getType() == PieceType.KING) {
						pieceString = "King";
					} else if (square.getPiece().getType() == PieceType.PAWN) {
						pieceString = "Pawn";
					} else if (square.getPiece().getType() == PieceType.ROOK) {
						pieceString = "Rook";
					} else if (square.getPiece().getType() == PieceType.KNIGHT) {
						pieceString = "Knight";
					} else if (square.getPiece().getType() == PieceType.BISHOP) {
						pieceString = "Bishop";
					} else {
						pieceString = "Queen";
					}

					Image image = null;
					try {
						image = ImageIO.read(getClass().getResource(
								"/" + playerString + pieceString + ".png"));
					} catch (IOException e) {
					}
					button.setIcon(new ImageIcon(image));

				} else {
					button.setIcon(null);
				}
			}
		}}
	
	class MyActionListener implements ActionListener {



		

		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			Point rv = new Point();
			int selectionX = button.getLocation(rv).x / 80;
			int selectionY = button.getLocation(rv).y / 80;
			if (boardManager.getCurrentPlayer() == PlayerType.WHITE) {
				selectionY = 7 - selectionY;
			}
			Coordinate currentCoordinate = new Coordinate(selectionX,
					selectionY);
			
			boolean moved = false;
			if (lastSelection != null) {
				selectionX = lastSelection.getLocation(rv).x / 80;
				selectionY = lastSelection.getLocation(rv).y / 80;
				if (boardManager.getCurrentPlayer() == PlayerType.WHITE) {
					selectionY = 7 - selectionY;
				}
				Coordinate lastCoordinate = new Coordinate(selectionX,
						selectionY);
				moved = boardManager.move(lastCoordinate, currentCoordinate);
				if (boardManager.isGameOver()){boardManager.resetBoard();updateBoard();return;}
				if (moved) {
					if (boardManager.isValidPromotion(boardManager.getBoard()
							.getSquare(currentCoordinate))) {
						boardManager.promote(
								boardManager.getBoard().getSquare(
										currentCoordinate), PieceType.QUEEN);
					}
					lastSelection = null;

					moveAI();
					updateBoard();

				}
				
				
				
			}
			
			if (!moved) {

				updateBoard();
				highlightSquares(boardManager.getValidMoves(currentCoordinate));
				if(boardManager.isGameOver()){
					boardManager.resetBoard();
				}		

				lastSelection = button;
			}

		}
	}

	public class ChessWindow extends JPanel {
		private static final long serialVersionUID = 1L;

		private JButton[][] allButtons = new JButton[8][8];

		public JButton[][] getButtons() {
			return allButtons;
		}

		public ChessWindow(Square[][] squares) {
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					gbc.gridx = row;
					gbc.gridy = col;
					JButton button = new JButton();
					allButtons[col][row] = button;

					button.setBorderPainted(false);
					button.setPreferredSize(new Dimension(80, 80));

					Square square;
					if (boardManager.getCurrentPlayer() == PlayerType.BLACK) {
						square = squares[row][col];
					} else {
						square = squares[row][7 - col];
					}

					if (square.isOccupied()) {
						String playerString;
						String pieceString;
						if (square.getPiece().getPlayer() == PlayerType.WHITE) {
							playerString = "white";
						} else {
							playerString = "black";
						}
						if (square.getPiece().getType() == PieceType.KING) {
							pieceString = "King";
						} else if (square.getPiece().getType() == PieceType.PAWN) {
							pieceString = "Pawn";
						} else if (square.getPiece().getType() == PieceType.ROOK) {
							pieceString = "Rook";
						} else if (square.getPiece().getType() == PieceType.KNIGHT) {
							pieceString = "Knight";
						} else if (square.getPiece().getType() == PieceType.BISHOP) {
							pieceString = "Bishop";
						} else {
							pieceString = "Queen";
						}

						Image image = null;
						try {
							image = ImageIO.read(getClass().getResource(
									"/" + playerString + pieceString + ".png"));
						} catch (IOException e) {
						}
						button.setIcon(new ImageIcon(image));

					} else {
						button.setIcon(null);
					}

					if ((row + col) % 2 == 0) {
						button.setBackground(java.awt.Color.white);
					} else {
						button.setBackground(new Color(29, 114, 46));
					}
					MyActionListener actionListener = new MyActionListener();
					button.addActionListener(actionListener);
					add(button, gbc);

				}
			}
		}
	}

}
