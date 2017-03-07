package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import clueGame.BoardCell;

//As a forward note to avoid confusion, when working with the coordinates of a cell,
//with board[][] first bracket is the rows (y) and the second is the column (x)
public class Board {
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 256;
	
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board theInstance = new Board();
	
	
	// methods
	private Board() {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
	}
	
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
		
		//adjacencies will be precalculated here
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		this.calcAdjacencies();
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
		Set<BoardCell> adjacencies;
		//first the board cell at location will be tested for three different types:
		//room, doorway, and walkway
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numColumns; j++){
				adjacencies = new HashSet<BoardCell>(); // will need a new hashset for every cell

				if (board[i][j].isRoom() | board[i][j].getInitial() == 'X'){ //test to see if current cell is a room or closet
					adjMatrix.put(board[i][j], adjacencies);
					continue;
				}
				
				if (board[i][j].isDoorway()){ //test to see if it's a doorway
					if (board[i][j].getDoorDirection() == DoorDirection.UP)
						adjacencies.add(board[i-1][j]);
					if (board[i][j].getDoorDirection() == DoorDirection.DOWN)
						adjacencies.add(board[i+1][j]);
					if (board[i][j].getDoorDirection() == DoorDirection.LEFT)
						adjacencies.add(board[i][j-1]);
					if (board[i][j].getDoorDirection() == DoorDirection.RIGHT)
						adjacencies.add(board[i][j+1]);
					
					//assuming doorways can move along adjacent doorways of any type,
					//this will add any cells that are adjacent doorways
					/*if (i>0 && board[i-1][j].isDoorway()) adjacencies.add(board[i-1][j]);
					if (i < board.length-1 && board[i+1][j].isDoorway()) adjacencies.add(board[i+1][j]);
					if (j>0 && board[i][j-1].isDoorway()) adjacencies.add(board[i][j-1]);
					if (j<board[i].length-1 && board[i][j+1].isDoorway()) adjacencies.add(board[i][j+1]);*/
						
					adjMatrix.put(board[i][j], adjacencies);
					continue;
				}

				//this will test for walkway now
				if(i > 0){ //then there's a space above us
					//each if will test to see if adjacent cell is a walkway or a valid doorway
					if (board[i-1][j].isWalkway())
						adjacencies.add(board[i-1][j]);
					else if (board[i-1][j].isDoorway() && board[i-1][j].getDoorDirection() == DoorDirection.DOWN)
						adjacencies.add(board[i-1][j]);
				}
				if(i < board.length-1){ //then there's a space below us
					if (board[i+1][j].isWalkway())
						adjacencies.add(board[i+1][j]);
					else if (board[i+1][j].isDoorway() && board[i+1][j].getDoorDirection() == DoorDirection.UP)
						adjacencies.add(board[i+1][j]);
				}
				if(j > 0){ //then there's a space to the left us
					if (board[i][j-1].isWalkway())
						adjacencies.add(board[i][j-1]);
					else if (board[i][j-1].isDoorway() && board[i][j-1].getDoorDirection() == DoorDirection.RIGHT)
						adjacencies.add(board[i][j-1]);
				}
				if(j < board[i].length-1){ //then there's a space to the right us
					if (board[i][j+1].isWalkway())
						adjacencies.add(board[i][j+1]);
					else if (board[i][j+1].isDoorway() && board[i][j+1].getDoorDirection() == DoorDirection.LEFT)
						adjacencies.add(board[i][j+1]);
				}
				adjMatrix.put(board[i][j], adjacencies);
			}
		}
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
		return board[y][x];
	}

	//Exact same as above, but Rader called it something different in her tests
	public BoardCell getCellAt(int x, int y){
		return board[y][x];
	}

	//will return set based on key value of cell associated with x and y
	public Set<BoardCell> getAdjList(int x, int y) {
		return adjMatrix.get(this.getCell(x, y));
	}
	
	public void calcTargets(int col, int row, int pathLength){
		calcTargets(board[row][col], pathLength);
	}

	public void calcTargets(BoardCell startCell, int pathLength){
		targets.clear();
		visited.clear();
		
		visited.add(startCell);
		
		findAllTgts(startCell, pathLength);
	}
	
	//the recursive bit of the target finding algorithm
	private void findAllTgts(BoardCell start, int dist){
		for(BoardCell cell : adjMatrix.get(start)){ //for each cell next to us
			if(!visited.contains(cell)){ //only do this if we haven't seen this cell yet
				visited.add(cell);
				
				if(dist == 1){ //if we can only move 1 more space, add it to targets
					targets.add(cell);
				}else if (cell.isDoorway()){ //if we're not done moving, but it's a door, it's a target
					targets.add(cell);
				}else if(dist > 1){ //otherwise, move to that space and keep looking
					findAllTgts(cell, dist-1);
				}
				
				visited.remove(cell);
			}
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

}
