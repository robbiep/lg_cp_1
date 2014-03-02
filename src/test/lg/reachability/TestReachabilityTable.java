package test.lg.reachability;

import static org.junit.Assert.*;
import lg.abstract_board_game.AbstractBoard;
import lg.abstract_board_game.AbstractBoardGame;
import lg.data_objects.Color;
import lg.data_objects.Location;
import lg.data_objects.Piece;
import lg.reachability.ReachabilityRules;
import lg.reachability.ReachabilityRule;
import lg.reachability.ReachabilityTable;
import lg.reachability.Reachability;

import org.junit.Before;
import org.junit.Test;

import chess.ChessPieceFactory;
import test.MockData;

// TODO replace using MockDataFactory
public class TestReachabilityTable {
  
  AbstractBoardGame abg;
  Location central_location;
  ChessPieceFactory pieceFactory;
  
  @Before
  public void initialize(){
    abg = MockData.abstractBoardGame();
    central_location = MockData.centerLocation();
    pieceFactory = new ChessPieceFactory( abg.getAbstractBoard() );
  }

  @Test
  public void testReachabilityTableCase1(){
    
    ReachabilityRules reach = new ReachabilityRules();
    Piece piece = new Piece( "Test", Color.BLACK, 1, reach );
    
    
    ReachabilityTable r_table = Reachability.generateTable( abg, piece, central_location );
    
    for( int x = 0; x < abg.getDimX(); ++ x ){
      for( int y = 0; y < abg.getDimY(); ++ y ){
        for( int z = 0; z < abg.getDimZ(); ++ z ){
          if( x == MockData.CENTER && y == MockData.CENTER && z == MockData.CENTER ){
            assertTrue( "Fails at (" + x + ", " + y + ", " + z + ")",
                r_table.getTable()[x][y][z] == 0 );
          } else {
            assertTrue( "Fails at (" + x + ", " + y + ", " + z + ")",
                        r_table.getTable()[x][y][z] == 1 );       
          }
        }
      }
    }
  }
  
  @Test
  public void testReachabilityTableCase2(){
    ReachabilityRules reach = new ReachabilityRules();
    reach.addRule( new ReachabilityRule() {
      @Override
      public Boolean rule( Location x, Location y ){
        return( Math.abs(x.getX() - y.getX()) <= 1 &&  
                Math.abs(x.getY() - y.getY()) <= 1 &&  
                Math.abs(x.getZ() - y.getZ()) <= 1 );
      }
    });
    Piece piece = new Piece( "Test", Color.BLACK, 1, reach );
    
    ReachabilityTable r_table = Reachability.generateTable( abg, piece, central_location );
    
    for( int x = 0; x < abg.getDimX(); ++ x ){
      for( int y = 0; y < abg.getDimY(); ++ y ){
        for( int z = 0; z < abg.getDimZ(); ++ z ){
          if( x == MockData.CENTER && y == MockData.CENTER && z == MockData.CENTER ){
            assertTrue( "Fails at (" + x + ", " + y + ", " + z + ")",
                r_table.getTable()[x][y][z] == 0 );
          } else if(  Math.abs(MockData.CENTER - x) == 1 && 
                      Math.abs(MockData.CENTER - y) == 1 && 
                      Math.abs(MockData.CENTER - z) == 1 ){
            assertTrue( "Fails at (" + x + ", " + y + ", " + z + ")",
                        r_table.getTable()[x][y][z] == 1 );       
          } else if(  Math.abs(MockData.CENTER - x) == 2 && 
                      Math.abs(MockData.CENTER - y) == 2 && 
                      Math.abs(MockData.CENTER - z) == 2 ){
            assertTrue( "Fails at (" + x + ", " + y + ", " + z + ") ... " + 
                          r_table.getTable()[x][y][z],
                        r_table.getTable()[x][y][z] == 2 );       
          }
        }
      }
    }
  }
  
  @Test
  public void testReachabilityTableObstacle(){
    abg.addPiece( pieceFactory.createObstacle(), new Location( 7, 8, 7 ) );
    abg.getReachabilityTable( pieceFactory.createQueen( Color.WHITE ), central_location ).printReachabilityTable( 7 );
    abg.clearPieces();
    
    abg.addPiece( pieceFactory.createObstacle(), new Location( 8, 7, 7 ) );
    abg.getReachabilityTable( pieceFactory.createQueen( Color.WHITE ), central_location ).printReachabilityTable( 7 );
    abg.clearPieces();
    
    abg.addPiece( pieceFactory.createObstacle(), new Location( 7, 6, 7 ) );
    abg.getReachabilityTable( pieceFactory.createQueen( Color.WHITE ), central_location ).printReachabilityTable( 7 );
    abg.clearPieces();
    
    abg.addPiece( pieceFactory.createObstacle(), new Location( 6, 7, 7 ) );
    abg.getReachabilityTable( pieceFactory.createQueen( Color.WHITE ), central_location ).printReachabilityTable( 7 );
    abg.clearPieces();
    
    abg.addPiece( pieceFactory.createObstacle(), new Location( 6, 8, 7 ) );
    abg.getReachabilityTable( pieceFactory.createQueen( Color.WHITE ), central_location ).printReachabilityTable( 7 );
  }
  
  @Test
  public void testDistance(){
    assertTrue( 
      Reachability.getDistance( 
          abg, 
          MockData.pieceFactory().createQueen( Color.WHITE ), 
          MockData.centerLocation(), 
          new Location( 6, 2, 7) ) == 2 );
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createQueen( Color.WHITE ), 
            MockData.centerLocation(), 
            new Location( 6, 7, 7) ) == 1 );
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createPawn( Color.WHITE ), 
            MockData.centerLocation(), 
            new Location( 7, 2, 7) ) == 5 );
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createPawn( Color.BLACK ), 
            MockData.centerLocation(), 
            new Location( 7, 2, 7) ) == ReachabilityTable.INFINITY );
    
  }
  
  @Test
  public void testDistanceObstacles(){
    abg = MockData.flatAbstractBoardGame();
    Location center = new Location( 7, 7, 0 );
    
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createQueen( Color.WHITE ), 
            center,
            new Location( 6, 2, 0) ) == 2 );
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createQueen( Color.WHITE ), 
            center,
            new Location( 3, 7, 0) ) == 1 );
    
    abg.addPiece( pieceFactory.createObstacle(), new Location( 6, 7, 0 ) );
    
    assertTrue( 
      Reachability.getDistance( 
          abg, 
          MockData.pieceFactory().createQueen( Color.WHITE ), 
          center,
          new Location( 6, 2, 0) ) == 2 );
    
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createQueen( Color.WHITE ), 
            center, 
            new Location( 3, 7, 0) ) == 2 );
    
    abg.addPiece( pieceFactory.createObstacle(), new Location( 3, 7, 0 ) );
    Reachability.generateTable( 
        abg, 
        MockData.pieceFactory().createQueen( Color.WHITE ), 
        center ).printReachabilityTable();
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createQueen( Color.WHITE ), 
            center, 
            new Location( 3, 7, 0 ) ).equals(ReachabilityTable.INFINITY) );
    
    abg.addPiece( pieceFactory.createObstacle(), new Location( 3, 8, 0 ) );
    abg.addPiece( pieceFactory.createObstacle(), new Location( 3, 6, 0 ) );
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createQueen( Color.WHITE ), 
            center, 
            new Location( 2, 7, 0) ) == 2 );
    
    abg.addPiece( pieceFactory.createObstacle(), new Location( 2, 8, 0 ) );
    abg.addPiece( pieceFactory.createObstacle(), new Location( 2, 6, 0 ) );
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createQueen( Color.WHITE ), 
            center, 
            new Location( 2, 7, 0 )) == 3 );
    
    abg.addPiece( pieceFactory.createObstacle(), new Location( 1, 8, 0 ) );
    abg.addPiece( pieceFactory.createObstacle(), new Location( 1, 6, 0 ) );
    abg.addPiece( pieceFactory.createObstacle(), new Location( 0, 8, 0 ) );
    abg.addPiece( pieceFactory.createObstacle(), new Location( 0, 6, 0 ) );
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createQueen( Color.WHITE ), 
            center, 
            new Location( 2, 7, 0) ).equals(ReachabilityTable.INFINITY) );
    
    
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createPawn( Color.WHITE ), 
            center, 
            new Location( 7, 2, 0) ) == 5 );
    assertTrue( 
        Reachability.getDistance( 
            abg, 
            MockData.pieceFactory().createPawn( Color.BLACK ), 
            center, 
            new Location( 7, 2, 0) ) == ReachabilityTable.INFINITY );
    
  }
}