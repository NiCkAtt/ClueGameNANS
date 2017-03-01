package clueGame;

import java.util.Map;
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
	
	public void initialize(){
		
	}
	
	public void loadRoomConfig(){
		
	}
	
	public void loadBoardConfig(){
		
	}
	
	public void calcAdjacencies(){
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength){
		
	}
	
	public Map<Character, String> getLegend(){
		return null;
	}

	public int getNumRows() {
		return 0;
	}

	public int getNumColumns() {
		return 0;
	}
	
	public BoardCell getCell(int x, int y){
		return null;
	}
	
}
