package lg.reachability;

import lg.data_objects.Location;
import lg.data_objects.Piece;

public class ReachabilityTable {
  
  private Piece           piece;
  private Integer[][][]   table;
  public  final static Integer  INFINITY = Integer.MAX_VALUE;
  
  public ReachabilityTable( Piece piece, Integer[][][] table ) {
    this.piece = piece;
    this.table = table;
  }
  
  public Integer[][][] getTable(){
    return table;
  }
  
  public Piece getPiece(){
    return piece;
  }
  
  /**
   * @return Value at the location
   */
  public Integer getValue( Location location ){
    return table[location.getX()][location.getY()][location.getZ()];
  }
  
  public void printReachabilityTable(){
    for( int z = 0; z < table[0][0].length; ++ z ){
      System.out.println( "Z dimension = " + z );
      printReachabilityTable( z );
      System.out.print( "\n\n" );
    }
  }
  
 /**
  * Prints the 2 dimensional table at a specific offset for dimension Z
  * @param z - offset in dimension z
  */
  public void printReachabilityTable( Integer z ){
    for( int y = 0; y < table[0].length; ++ y ){
      for( int x = 0; x < table.length; ++ x ){
        System.out.print( (table[x][y][z].equals( INFINITY )) ? "x " : table[x][y][z] + " " );
      }
      System.out.print( "\n" );
    }
    System.out.print( "\n" );
  }
  
  
}
