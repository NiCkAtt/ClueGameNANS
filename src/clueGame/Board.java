package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 256;
	
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();
	
	// methods
	private Board() {}
	
	public static Board getInstance(){
		return theInstance;
	}
	
	public void setConfigFiles(String boardFile, String legendFile) throws FileNotFoundException{
		boardConfigFile = boardFile;
		roomConfigFile = legendFile;
		
		FileReader reader = new FileReader(boardConfigFile);
		Scanner readIn = new Scanner(reader);
		String boardLine;
		String[] boardSpaces;

		int columns = 0;
		int rows = 0;
		
		//this loop is ONLY to find the dimensions of the board
		while(readIn.hasNextLine()){
			boardLine = readIn.nextLine();
			
			if(rows == 0){
				boardSpaces = boardLine.split(",");
				for(String s:boardSpaces){
					columns++;
				}
			}
			
			rows++;
		}

		//In the initial clue layout assignment, you said we HAD to have a row/column of numbers
		//Your provided clueboard does not have these.
		//This is necessary when the numbers are present, otherwise, leave them commented.
		//rows--;
		//columns--;

		numRows = rows;
		numColumns = columns;
	}
	
	public void initialize() throws FileNotFoundException{
		String boardLine;
		String[] boardSpaces;
		FileReader reader = new FileReader(boardConfigFile);
		Scanner readIn = new Scanner(reader);
		
		board = new BoardCell[numRows][numColumns];
		for(int i=0; i < numRows; i++){
			boardLine = readIn.nextLine();
			boardSpaces = boardLine.split(",");
			
			for(int j=0; j < numColumns; j++){
				//regardless, the first char in this string will be the boardcell initial
				board[i][j] = new BoardCell(j,i,boardSpaces[j].charAt(0));
				//if there's a second char, then that's the door direction
				if(boardSpaces[j].length() > 1){
					board[i][j].makeDoor(boardSpaces[j].charAt(1));
				}
			}
		}
		reader = new FileReader(roomConfigFile);
		readIn = new Scanner(reader);
		legend = new HashMap<Character, String>();
		while(readIn.hasNextLine()){
			//the names here are confusing, but didn't want to create new temp variables
			//If you like, you can pretend these are called "legend line" and "legend space"
			boardLine = readIn.nextLine();
			boardSpaces = boardLine.split(", ");
			legend.put(boardSpaces[0].charAt(0), boardSpaces[1]);
		}
		
		readIn.close();
	}
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		String legendLine;
		String[] legendSpaces;
		FileReader reader = new FileReader(roomConfigFile);
		Scanner readIn = new Scanner(reader);
		
		legend = new HashMap<Character, String>();
		while(readIn.hasNextLine()){
			//the names here are confusing, but didn't want to create new temp variables
			//If you like, you can pretend these are called "legend line" and "legend space"
			legendLine = readIn.nextLine();
			legendSpaces = legendLine.split(", ");
			if(!(legendSpaces[2].equalsIgnoreCase("Card") || legendSpaces[2].equalsIgnoreCase("other"))){
				throw new BadConfigFormatException(roomConfigFile);
			}
			legend.put(legendSpaces[0].charAt(0), legendSpaces[1]);
		}
		readIn.close();
	}
	
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(boardConfigFile);
		Scanner readIn = new Scanner(reader);
		String boardLine;
		String[] boardSpaces;
		
		board = new BoardCell[numRows][numColumns];
		for(int i=0; i < numRows; i++){
			boardLine = readIn.nextLine();
			boardSpaces = boardLine.split(",");
			
			if(boardSpaces.length != numColumns)
				throw new BadConfigFormatException(boardConfigFile);
			
			for(int j=0; j < numColumns; j++){
				if(!legend.containsKey(boardSpaces[j].charAt(0)))
					throw new BadConfigFormatException(boardConfigFile);
					
				//regardless, the first char in this string will be the boardcell initial
				board[i][j] = new BoardCell(j,i,boardSpaces[j].charAt(0));
				//if there's a second char, then that's the door direction
				if(boardSpaces[j].length() > 1){
					board[i][j].makeDoor(boardSpaces[j].charAt(1));
				}
			}
		}
		readIn.close();
	}
	
	public void calcAdjacencies(){
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength){
		
	}
	
	public Map<Character, String> getLegend(){
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCell(int x, int y){
		return board[x][y];
	}
	
	//Exact same as above, but Rader called it something different in her tests
	public BoardCell getCellAt(int x, int y){
		return board[x][y];
	}
	
}
