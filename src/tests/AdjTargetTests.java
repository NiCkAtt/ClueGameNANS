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
		testList = board.getAdjList(6,15);
		assertEquals(testList.size(), 4);
		assertTrue(testList.contains(board.getCell(5,15)));
		assertTrue(testList.contains(board.getCell(7,15)));
		assertTrue(testList.contains(board.getCell(6,14)));
		assertTrue(testList.contains(board.getCell(6,16)));
		
		//has 3 adjacent walkways, plus a non-door room space
		testList = board.getAdjList(1,9);
		assertEquals(testList.size(), 3);
		assertTrue(testList.contains(board.getCell(1,8)));
		assertTrue(testList.contains(board.getCell(1,10)));
		assertTrue(testList.contains(board.getCell(0,9)));
		
		//test spaces at each board edge
		
		//top edge
		testList = board.getAdjList(7,0);
		assertEquals(testList.size(), 2);
		assertTrue(testList.contains(board.getCell(6,0)));
		assertTrue(testList.contains(board.getCell(7,1)));
		
		//left edge
		testList = board.getAdjList(0,14);
		assertEquals(testList.size(), 3);
		assertTrue(testList.contains(board.getCell(0,13)));
		assertTrue(testList.contains(board.getCell(0,15)));
		assertTrue(testList.contains(board.getCell(1,14)));
		
		//bottom edge - also has non-door room adjacent cells
		testList = board.getAdjList(8,23);
		assertEquals(testList.size(), 2);
		assertTrue(testList.contains(board.getCell(7,23)));
		assertTrue(testList.contains(board.getCell(9,23)));
		
		//right edge - also has non-door room adjacent cells
		testList = board.getAdjList(24,11);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCell(23,11)));
	}
	
	@Test
	public void testRoomAdj(){
		//these should both be empty sets
		testList = board.getAdjList(0,0);
		assertEquals(testList.size(), 0);
		
		testList = board.getAdjList(12,10);
		assertEquals(testList.size(), 0);
	}
	
	@Test
	public void testByDoorAdj(){
		//this door can only be accessed from the bottom
		int doorX = 8;
		int doorY = 13;
		testList = board.getAdjList(doorX-1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX+1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY-1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY+1);
//		for (BoardCell cell : testList){
//			System.out.println(cell.getX() + " " + cell.getY() + " " + cell.getInitial());
//		}
		assertTrue(testList.contains(board.getCell(doorX, doorY)));
		
		//this door can only be accessed from the top
		doorX = 4;
		doorY = 6;
		testList = board.getAdjList(doorX-1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX+1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY-1);
		assertTrue(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY+1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		
		//this door can only be accessed from the left
		doorX = 21;
		doorY = 19;
		testList = board.getAdjList(doorX-1, doorY);
		assertTrue(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX+1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY-1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY+1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		
		//this door can only be accessed from the right
		doorX = 16;
		doorY = 18;
		testList = board.getAdjList(doorX-1, doorY);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX+1, doorY);
		assertTrue(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY-1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
		testList = board.getAdjList(doorX, doorY+1);
		assertFalse(testList.contains(board.getCell(doorX, doorY)));
	}
	
	@Test
	public void testAtDoorAdj(){
		//this door opens upward
		testList = board.getAdjList(8, 16);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCell(8, 15)));
		
		//this door opens rightward
		testList = board.getAdjList(19,10);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCell(20,10)));
	}
	
	@Test
	public void testWalkwayTgts(){
		//middle of the board, one space
		board.calcTargets(19,16,1);
		testList = board.getTargets();
		assertEquals(testList.size(), 4);
		assertTrue(testList.contains(board.getCell(18, 16)));
		assertTrue(testList.contains(board.getCell(20, 16)));
		assertTrue(testList.contains(board.getCell(19, 15)));
		assertTrue(testList.contains(board.getCell(19, 17)));
		
		//middle of the board, 3 spaces
		board.calcTargets(19,16,3);
		testList = board.getTargets();
		assertEquals(testList.size(), 11);
		//"inner ring" of allowable targets
		assertTrue(testList.contains(board.getCell(18, 16)));
		assertTrue(testList.contains(board.getCell(20, 16)));
		assertTrue(testList.contains(board.getCell(19, 15)));
		assertTrue(testList.contains(board.getCell(19, 17)));
		//"outer ring" of allowable targets
		assertTrue(testList.contains(board.getCell(20, 14)));
		assertTrue(testList.contains(board.getCell(18, 14)));
		assertTrue(testList.contains(board.getCell(17, 15)));
		assertTrue(testList.contains(board.getCell(17, 17)));
		assertTrue(testList.contains(board.getCell(18, 18)));
		assertTrue(testList.contains(board.getCell(19, 19)));
		assertTrue(testList.contains(board.getCell(20, 18)));
		
		//in a narrow walkway, 2 spaces
		board.calcTargets(18,4,2);
		testList = board.getTargets();
		assertEquals(testList.size(), 4);
		assertTrue(testList.contains(board.getCell(20, 4)));
		assertTrue(testList.contains(board.getCell(16, 4)));
		assertTrue(testList.contains(board.getCell(17, 5)));
		assertTrue(testList.contains(board.getCell(19, 5)));
		
		//in a narrow walkway, 4 spaces
		board.calcTargets(18,4,4);
		testList = board.getTargets();
		assertEquals(testList.size(), 8);
		//"inner ring" of allowable targets
		assertTrue(testList.contains(board.getCell(20, 4)));
		assertTrue(testList.contains(board.getCell(16, 4)));
		assertTrue(testList.contains(board.getCell(17, 5)));
		assertTrue(testList.contains(board.getCell(19, 5)));
		//"outer ring" of allowable targets
		assertTrue(testList.contains(board.getCell(14, 4)));
		assertTrue(testList.contains(board.getCell(21, 5)));
		assertTrue(testList.contains(board.getCell(15, 5)));
		assertTrue(testList.contains(board.getCell(20, 6)));
	}
	
	@Test
	public void testRoomEntranceTgts(){
		//This space is within range of 2 doors if you move 3 spaces
		//One door requires 2 moves, one requires 3
		board.calcTargets(13,15,3);
		testList = board.getTargets();
		assertTrue(testList.contains(board.getCell(14, 16)));
		assertTrue(testList.contains(board.getCell(12, 13)));
		
		//this cell is next to a door, but in the wrong direction
		board.calcTargets(5,20,1);
		assertFalse(testList.contains(board.getCell(4, 20)));
		//now test with a roll of 3, it should be able to get in
		board.calcTargets(5,20,3);
		assertTrue(testList.contains(board.getCell(4, 20)));
	}
	
	@Test
	public void testRoomExitTgts(){
		//Exit a left-facing door
		board.calcTargets(22, 8, 2);
		testList = board.getTargets();
		assertEquals(testList.size(), 3);
		assertTrue(testList.contains(board.getCell(20, 8)));
		assertTrue(testList.contains(board.getCell(21, 9)));
		assertTrue(testList.contains(board.getCell(21, 7)));
		
		board.calcTargets(14,16,4);
		testList = board.getTargets();
		System.out.println(testList);
		assertEquals(testList.size(), 8);
		//"inner ring" of allowable targets
		assertTrue(testList.contains(board.getCell(14, 14)));
		assertTrue(testList.contains(board.getCell(15, 15)));
		assertTrue(testList.contains(board.getCell(13, 15)));
		//"outer ring" of allowable targets
		assertTrue(testList.contains(board.getCell(11, 15)));
		assertTrue(testList.contains(board.getCell(12, 14)));
		assertTrue(testList.contains(board.getCell(17, 15)));
		assertTrue(testList.contains(board.getCell(16, 14)));
		assertTrue(testList.contains(board.getCell(12, 16)));
	}

}
