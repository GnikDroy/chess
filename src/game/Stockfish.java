package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * @author gnik
 *
 */
public class Stockfish {
	/**
	 * This is the Stockfish process that runs in the background.
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

	
	private int stockfishLevel;

	/**
	 * This starts a new process for the stockfish engine(if available only).
	 * Also sets it in UCI_Chess960 mode with default options. Else it prints a stacktrace.
	 * @param level The difficulty level from 0-20.
	 */
	public Stockfish(int level) {
		stockfishLevel=level;
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
			stockfishInput.write("setoption name Skill Level value "+Integer.toString(stockfishLevel)+"\n");
			stockfishInput.flush();
			stockfishOutput.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts a new engine at max difficulty. Same as Engine(20)
	 */
	public Stockfish()
	{
		this(20);
	}
	
	
	/**
	 * Sets the level of difficulty of the engine
	 * @param level The level of difficulty (0-20)
	 */
	public void setLevel(int level ){
		stockfishLevel=level;
		try {
			stockfishInput.write("setoption name Skill Level value "+Integer.toString(stockfishLevel)+"\n");
			stockfishInput.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * This returns the best possible move 
	 * If there is no possible move(Checkmate) then it send a blank string.(BUGGY)
	 * @param position The current board position in UCI format
	 * @return String The best possible move in UCI format
	 */
	public String getBestMove(String position) {
		String output = "Stockfish api";
		try {
			stockfishInput.write("position startpos moves " + position + "\n");
			stockfishInput.write("go" + "\n");
			stockfishInput.flush();
			while (!output.substring(0, 8).equals("bestmove")) {
				output = stockfishOutput.readLine();
			}
			stockfishInput.write("stop" + "\n");
			if (output.equals("bestmove (none)")){return "";}
			output = output.split(" ")[1];

		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}
	
	
	/**
	 * Closes the to the Stockfish engine process gracefully. 
	 */
	public void quit(){
		try {
			stockfishInput.write("quit\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		stockfish.destroy();
	}

}
