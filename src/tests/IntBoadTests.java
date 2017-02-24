package tests;

import experiment.BoardCell;
import experiment.IntBoard;
import static org.junit.Assert.*;
import java.util.Set;
import org.junit.*;

public class IntBoadTests {

	public IntBoard game;
	
	@Before
	public void setup(){
		game = new IntBoard();
	}
	
	@Test
	public void topLeft(){
		BoardCell cell = game.getCell(0, 0);
		Set<BoardCell> list = game.getAdjList(cell);
		assertTrue(list.contains(game.getCell(1, 0)));
		assertTrue(list.contains(game.getCell(0, 1)));
		assertEquals(2, list.size());
	}
	
	@Test
	public void bottomRight(){
		BoardCell cell = game.getCell(3, 3);
		Set<BoardCell> list = game.getAdjList(cell);
		assertTrue(list.contains(game.getCell(2, 3)));
		assertTrue(list.contains(game.getCell(3, 2)));
		assertEquals(2, list.size());
	}
	
	@Test
	public void rightEdge(){
		BoardCell cell = game.getCell(1, 3);
		Set<BoardCell> list = game.getAdjList(cell);
		assertTrue(list.contains(game.getCell(0, 3)));
		assertTrue(list.contains(game.getCell(2, 3)));
		assertTrue(list.contains(game.getCell(1, 2)));
		assertEquals(3, list.size());
	}
	
	@Test
	public void leftEdge(){
		BoardCell cell = game.getCell(2, 0);
		Set<BoardCell> list = game.getAdjList(cell);
		assertTrue(list.contains(game.getCell(1, 0)));
		assertTrue(list.contains(game.getCell(3, 0)));
		assertTrue(list.contains(game.getCell(2, 1)));
		assertEquals(3, list.size());
	}
	
	@Test
	public void centerOne(){
		BoardCell cell = game.getCell(1, 1);
		Set<BoardCell> list = game.getAdjList(cell);
		assertTrue(list.contains(game.getCell(1, 0)));
		assertTrue(list.contains(game.getCell(1, 2)));
		assertTrue(list.contains(game.getCell(0, 1)));
		assertTrue(list.contains(game.getCell(2, 1)));
		assertEquals(4, list.size());
	}
	

	@Test
	public void centerTwo(){
		BoardCell cell = game.getCell(2, 2);
		Set<BoardCell> list = game.getAdjList(cell);
		assertTrue(list.contains(game.getCell(2, 1)));
		assertTrue(list.contains(game.getCell(2, 3)));
		assertTrue(list.contains(game.getCell(1, 2)));
		assertTrue(list.contains(game.getCell(3, 2)));
		assertEquals(4, list.size());
	}
	
	//start in the topleft corner, move 1 space
	@Test
	public void testTargets0_1(){
		BoardCell cell = game.getCell(0, 0);
		game.calcTargets(cell, 1);
		Set targets = game.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(game.getCell(0, 1)));
		assertTrue(targets.contains(game.getCell(1, 0)));
	}
	
	//start on the top edge (space 1), move 1 space
	@Test
	public void testTargets1_1(){
		BoardCell cell = game.getCell(0, 1);
		game.calcTargets(cell, 1);
		Set targets = game.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(game.getCell(0, 0)));
		assertTrue(targets.contains(game.getCell(1, 1)));
		assertTrue(targets.contains(game.getCell(0, 2)));
	}
	
	//start in the middle of the board (space 5), move 1 space
	@Test
	public void testTargets5_1(){
		BoardCell cell = game.getCell(1, 1);
		game.calcTargets(cell, 1);
		Set targets = game.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(game.getCell(0, 1)));
		assertTrue(targets.contains(game.getCell(1, 0)));
		assertTrue(targets.contains(game.getCell(1, 2)));
		assertTrue(targets.contains(game.getCell(2, 1)));
	}
	
	//start in the topleft corner, move 2 spaces
	@Test
	public void testTargets0_2(){
		BoardCell cell = game.getCell(0, 0);
		game.calcTargets(cell, 2);
		Set targets = game.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(game.getCell(0,2)));
		assertTrue(targets.contains(game.getCell(2,0)));
		assertTrue(targets.contains(game.getCell(1,1)));
	}
	
	//start on the top edge (space 1), move 2 spaces
	@Test
	public void testTargets1_2(){
		BoardCell cell = game.getCell(0, 1);
		game.calcTargets(cell, 2);
		Set targets = game.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(game.getCell(1,0)));
		assertTrue(targets.contains(game.getCell(2,1)));
		assertTrue(targets.contains(game.getCell(1,2)));
		assertTrue(targets.contains(game.getCell(0,3)));
	}
	
	//start in the middle (space 5), move 2 spaces
	@Test
	public void testTargets5_2(){
		BoardCell cell = game.getCell(0, 1);
		game.calcTargets(cell, 2);
		Set targets = game.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(game.getCell(0,0)));
		assertTrue(targets.contains(game.getCell(0,2)));
		assertTrue(targets.contains(game.getCell(2,0)));
		assertTrue(targets.contains(game.getCell(2,2)));
		assertTrue(targets.contains(game.getCell(1,3)));
		assertTrue(targets.contains(game.getCell(3,1)));
	}
}
