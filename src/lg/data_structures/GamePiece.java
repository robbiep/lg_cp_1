package lg.data_structures;


import lg.data_objects.location.Location;
import lg.data_objects.piece.Piece;

public class GamePiece {
  
  public Piece    piece;
  public Location location;
  
  public GamePiece( Piece piece, Location location ) {
    this.piece = piece;
    this.location = location;
  }
  


}
