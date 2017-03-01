package tests;

import static org.junit.Assert.*;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class fileInitTest {

	private static Board board;
	private static final int LEGEND_SIZE = 11;
	private static final int NUM_ROWS = 24;
	private static final int NUM_COLUMNS = 25;

	@BeforeClass
	public static void setUp(){
		board = Board.getInstance();
		// config files will be hard-coded in unless specified otherwise
		board.initialize();
	}

	@Test
	public void testRooms() {
		
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Helstrom", legend.get('H'));
		assertEquals("Imperial City", legend.get('I'));
		assertEquals("Torval", legend.get('T'));
		assertEquals("Daggerfall", legend.get('D'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
		// two cells that are not a doorway.
		// These cells are white on the planning spreadsheet
		@Test
		public void FourDoorDirections() {
			BoardCell room = board.getCell(3, 3);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.DOWN, room.getDoorDirection());
			room = board.getCell(5, 9);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getCell(8, 9);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.LEFT, room.getDoorDirection());
			room = board.getCell(3, 6);
			assertTrue(room.isDoorway());
			assertEquals(DoorDirection.UP, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = board.getCell(0, 0);
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = board.getCell(6, 0);
			assertFalse(cell.isDoorway());
		}
		
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCell(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			assertEquals(24, numDoors);
		}

		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			// Test first cell in room
			assertEquals('D', board.getCell(0, 0).getInitial());
			assertEquals('S', board.getCell(8, 0).getInitial());
			assertEquals('B', board.getCell(16, 0).getInitial());
			// Test last cell in room
			assertEquals('D', board.getCell(5, 3).getInitial());
			assertEquals('I', board.getCell(19, 13).getInitial());
			// Test a walkway
			assertEquals('W', board.getCell(6, 12).getInitial());
			// Test the closet
			assertEquals('X', board.getCell(2, 16).getInitial());
		}
}
