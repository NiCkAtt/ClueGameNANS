package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell[][] spaces;
	int width = 4;
	int height = 4;
	public Map<BoardCell, Set<BoardCell>> adj; //adjacency list
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	public IntBoard(){
		spaces = new BoardCell[4][4];
		for (int i=0; i < spaces.length; i++){
			for(int j=0; j < spaces[i].length; j++){
				spaces[i][j] = new BoardCell(j,i);
			}
		}
		adj = calcAdjacencies();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
	}
	
	private Map<BoardCell, Set<BoardCell>> calcAdjacencies(){
		Map<BoardCell, Set<BoardCell>> retval = new HashMap<BoardCell, Set<BoardCell>>(); //return value
		Set<BoardCell> adjacent;;
		
		for(int i=0; i<spaces.length; i++){ //scan through rows
			for(int j=0; j<spaces[i].length; j++){ //scan through columns
				adjacent = new HashSet<BoardCell>(); //make sure we start with a blank set for each space
				
				if(i > 0){ //then there's a space above us	
					adjacent.add(spaces[i-1][j]);
				}
				if(i < spaces.length-1){ //then there's a space below us
					adjacent.add(spaces[i+1][j]); //add the lower space
				}
				if(j > 0){ //then there's a space to the left us
					adjacent.add(spaces[i][j-1]); //add the left space
				}
				if(j < spaces[i].length-1){ //then there's a space to the right us
					adjacent.add(spaces[i][j+1]); //add the right space
				}
				
				retval.put(spaces[i][j],adjacent); //add the adjacent cells we found
			}
		}
		
		return retval;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength){
		targets.clear();
		visited.clear();
		visited.add(startCell);
		
		findAllTgts(startCell, pathLength);
	}
	
	public void findAllTgts(BoardCell start, int dist){
		for(BoardCell cell : adj.get(start)){ //for each cell next to us
			if(!visited.contains(cell)){ //only do this if we haven't seen this cell yet
				visited.add(cell);
				
				if(dist == 1){ //if we can only move 1 more space, add it to targets
					targets.add(cell);
				} else if(dist > 1){ //otherwise, move to that space and keep looking
					findAllTgts(cell, dist-1);
				}
				
				visited.remove(cell);
			}
		}
	}
	
	public Set<BoardCell> getTargets(){
		return this.targets;
	}
	/*
	 * public Set<> targets(BoardCell start, int distance){
	 * 		Set targets = new Set<>();
	 * 		Set visited = new Set<>();
	 * 
	 * 		targets = recursiveFn()
	 * 
	 * 		return targets;
	 * }
	 * 
	 */
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		
		return adj.get(cell);
	}
	
	public BoardCell getCell(int x, int y){
		return spaces[y][x];
	}
}
