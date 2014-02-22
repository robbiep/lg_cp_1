package test.lg;

import static org.junit.Assert.*;

import java.util.ArrayList;

import lg.AbstractBoard;
import lg.data_objects.Location;

import org.junit.Test;

public class test_board {

  @Test
  public void testBoard() {
    AbstractBoard board;
    
    board = new AbstractBoard(2,2,2);
    assertEquals(new Integer(2), board.getX());
    assertEquals(new Integer(2), board.getY());
    assertEquals(new Integer(2), board.getZ());
   
  }
  
  @Test
  public void testBoardValidLocation(){
    AbstractBoard board;
    
    board = new AbstractBoard(2,2,2);
    assertTrue(board.validLocation(new Location(0,0,0)));
    assertTrue(board.validLocation(new Location(1,1,1)));
    assertTrue(board.validLocation(new Location(2,2,2)));
    assertFalse(board.validLocation(new Location(3,3,3)));
  }

}
