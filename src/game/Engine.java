package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import pieces.PieceType;

/**
 * @author gnik
 *
 */
public class Engine {
	/**
	 * This is the stockfish process that runs in the background.
	 */
	private Process stockfish;
	
	/**
	 * This is the input BufferedWriter object to give commands to Engine.
	 */
	private BufferedWriter stockfishInput;
	
	/**
	 * This is the output object to recive output from Engine.
	 */
	private BufferedReader stockfishOutput;

	/**
	 * This starts a new process (if it can) engine.
	 */
	public Engine() {
		try {
			stockfish = Runtime.getRuntime().exec("stockfish");
		} catch (IOException e) {
			e.printStackTrace();
		}
		stockfishInput = new BufferedWriter(new OutputStreamWriter(
				stockfish.getOutputStream()));
		stockfishOutput = new BufferedReader(new InputStreamReader(
				stockfish.getInputStream()));
		try {
			stockfishInput.write("setoption name UCI_Chess960 value true\n");
			stockfishInput.flush();
			stockfishOutput.readLine();
		} catch (Exception e) {

		}
	}

	/**
	 * This returns the best possible move 
	 * @param position The current board position in UCI format
	 * @return String The best possible move in UCI format
	 */
	public String getBestMove(String position) {
		System.out.println(position);
		String output = "Stockfish api";
		try {
			stockfishInput.write("position startpos moves " + position + "\n");
			stockfishInput.write("go" + "\n");
			stockfishInput.flush();
			while (!output.substring(0, 8).equals("bestmove")) {
				output = stockfishOutput.readLine();
			}
			stockfishInput.write("stop" + "\n");
			output = output.split(" ")[1];

		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}
	public void quit(){
		try {
			stockfishInput.write("quit\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		stockfish.destroy();
	}
	public static void main(String args[])
	{
		System.out.println(PieceType.valueOf("q"));
	}
}
