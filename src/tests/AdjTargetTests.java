package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AdjTargetTests {
	
	private static Board board;
	private static Set<BoardCell> testList;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException{
		board = Board.getInstance();
		board.setConfigFiles("map.csv", "legend.txt");
		board.initialize();
		testList = new HashSet<BoardCell>();
	}

	@Test
	public void testWalkwayAdj() {
		//has 4 adjacent walkways
		testList = board.getAdjList(15,6);
		assertEquals(testList.size(), 4);
		assertTrue(testList.contains(board.getCell(15,5)));
		assertTrue(testList.contains(board.getCell(15,7)));
		assertTrue(testList.contains(board.getCell(14,6)));
		assertTrue(testList.contains(board.getCell(16,6)));
		
		//has 3 adjacent walkways, plus a non-door room space
		testList = board.getAdjList(9,1);
		assertEquals(testList.size(), 3);
		assertTrue(testList.contains(board.getCell(8,1)));
		assertTrue(testList.contains(board.getCell(10,1)));
		assertTrue(testList.contains(board.getCell(9,0)));
		
		//test spaces at each board edge
		
		//top edge
		testList = board.getAdjList(0,7);
		assertEquals(testList.size(), 2);
		assertTrue(testList.contains(board.getCell(0,6)));
		assertTrue(testList.contains(board.getCell(1,7)));
		
		//left edge
		testList = board.getAdjList(14,0);
		assertEquals(testList.size(), 3);
		assertTrue(testList.contains(board.getCell(13,0)));
		assertTrue(testList.contains(board.getCell(15,0)));
		assertTrue(testList.contains(board.getCell(14,1)));
		
		//bottom edge - also has non-door room adjacent cells
		testList = board.getAdjList(23,8);
		assertEquals(testList.size(), 2);
		assertTrue(testList.contains(board.getCell(23,7)));
		assertTrue(testList.contains(board.getCell(23,9)));
		
		//right edge - also has non-door room adjacent cells
		testList = board.getAdjList(11,24);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCell(11,23)));
	}
	
	@Test
	public void testRoomAdj(){
		//these should both be empty sets
		testList = board.getAdjList(0,0);
		assertEquals(testList.size(), 0);
		
		testList = board.getAdjList(10,12);
		assertEquals(testList.size(), 0);
	}
	
	@Test
	public void testByDoorAdj(){
		//this door can only be accessed from the bottom
		int doorX = 13;
		int doorY = 8;
		testList = board.getAdjList(doorX-1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX+1, doorY);
		assertTrue(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY-1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY+1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		
		//this door can only be accessed from the top
		doorX = 6;
		doorY = 4;
		testList = board.getAdjList(doorX-1, doorY);
		assertTrue(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX+1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY-1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY+1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		
		//this door can only be accessed from the left
		doorX = 19;
		doorY = 21;
		testList = board.getAdjList(doorX-1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX+1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY-1);
		assertTrue(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY+1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		
		//this door can only be accessed from the right
		doorX = 18;
		doorY = 16;
		testList = board.getAdjList(doorX-1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX+1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY-1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY+1);
		assertTrue(testList.contains(board.getCell(doorX, doorY)));
	}
	
	@Test
	public void testAtDoorAdj(){
		//this door opens upward
		testList = board.getAdjList(16, 8);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCell(15,8)));
		
		//this door opens rightward
		testList = board.getAdjList(10,19);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCell(10,20)));
	}
	
	@Test
	public void testWalkwayTgts(){
		//middle of the board, one space
		board.calcTargets(16,19,1);
		testList = board.getTargets();
		assertEquals(testList.size(), 4);
		assertTrue(testList.contains(board.getCell(16, 18)));
		assertTrue(testList.contains(board.getCell(16, 20)));
		assertTrue(testList.contains(board.getCell(15, 19)));
		assertTrue(testList.contains(board.getCell(17, 19)));
		
		//middle of the board, 3 spaces
		board.calcTargets(16,19,3);
		testList = board.getTargets();
		assertEquals(testList.size(), 11);
		//"inner ring" of allowable targets
		assertTrue(testList.contains(board.getCell(16, 18)));
		assertTrue(testList.contains(board.getCell(16, 20)));
		assertTrue(testList.contains(board.getCell(15, 19)));
		assertTrue(testList.contains(board.getCell(17, 19)));
		//"outer ring" of allowable targets
		assertTrue(testList.contains(board.getCell(14, 20)));
		assertTrue(testList.contains(board.getCell(14, 18)));
		assertTrue(testList.contains(board.getCell(15, 17)));
		assertTrue(testList.contains(board.getCell(17, 17)));
		assertTrue(testList.contains(board.getCell(18, 18)));
		assertTrue(testList.contains(board.getCell(19, 19)));
		assertTrue(testList.contains(board.getCell(18, 20)));
		
		//in a narrow walkway, 2 spaces
		board.calcTargets(4,18,2);
		testList = board.getTargets();
		assertEquals(testList.size(), 4);
		assertTrue(testList.contains(board.getCell(4, 20)));
		assertTrue(testList.contains(board.getCell(4, 16)));
		assertTrue(testList.contains(board.getCell(5, 17)));
		assertTrue(testList.contains(board.getCell(5, 19)));
		
		//in a narrow walkway, 4 spaces
		board.calcTargets(4,18,4);
		testList = board.getTargets();
		assertEquals(testList.size(), 8);
		//"inner ring" of allowable targets
		assertTrue(testList.contains(board.getCell(4, 20)));
		assertTrue(testList.contains(board.getCell(4, 16)));
		assertTrue(testList.contains(board.getCell(5, 17)));
		assertTrue(testList.contains(board.getCell(5, 19)));
		//"outer ring" of allowable targets
		assertTrue(testList.contains(board.getCell(4, 14)));
		assertTrue(testList.contains(board.getCell(5, 21)));
		assertTrue(testList.contains(board.getCell(5, 15)));
		assertTrue(testList.contains(board.getCell(6, 20)));
	}
	
	@Test
	public void testRoomEntranceTgts(){
		//This space is within range of 2 doors if you move 3 spaces
		//One door requires 2 moves, one requires 3
		board.calcTargets(15,13,3);
		testList = board.getTargets();
		assertTrue(testList.contains(board.getCell(16, 14)));
		assertTrue(testList.contains(board.getCell(13, 12)));
		
		//this cell is next to a door, but in the wrong direction
		board.calcTargets(20,5,1);
		assertFalse(testList.contains(board.getCell(20, 4)));
		//now test with a roll of 3, it should be able to get in
		board.calcTargets(5,20,3);
		assertTrue(testList.contains(board.getCell(4,20)));
	}
	
	@Test
	public void testRoomExitTgts(){
		//Exit a left-facing door
		board.calcTargets(8,22, 2);
		testList = board.getTargets();
		for (BoardCell cell : testList){
			System.out.println(cell.getX() + " " + cell.getY());
		}
		assertEquals(testList.size(), 3);
		assertTrue(testList.contains(board.getCell(8, 20)));
		assertTrue(testList.contains(board.getCell(9, 21)));
		assertTrue(testList.contains(board.getCell(7, 21)));
		
		board.calcTargets(16,14,4);
		testList = board.getTargets();
		System.out.println(testList);
		assertEquals(testList.size(), 8);
		//"inner ring" of allowable targets
		assertTrue(testList.contains(board.getCell(14, 14)));
		assertTrue(testList.contains(board.getCell(15, 15)));
		assertTrue(testList.contains(board.getCell(15, 13)));
		//"outer ring" of allowable targets
		assertTrue(testList.contains(board.getCell(15, 11)));
		assertTrue(testList.contains(board.getCell(14, 12)));
		assertTrue(testList.contains(board.getCell(15, 17)));
		assertTrue(testList.contains(board.getCell(14, 16)));
		assertTrue(testList.contains(board.getCell(16, 12)));
	}

}
