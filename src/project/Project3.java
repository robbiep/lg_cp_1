package project;

import lg.abstract_board_game.AbstractBoardGame;
import lg.data_objects.Color;
import lg.data_objects.location.Location;
import lg.data_objects.piece.Piece;
import lg.data_objects.zone.ZoneBundle;
import lg.data_objects.zone.ZoneTarget;
import lg.grammar.GZ;
import chess.ChessPieceFactory;

public class Project3 {
  public static void run(){
    System.out.println( 
        "\nLG Project 3\n" +
        "------------\n" +
        "Generates zones for two different ABG configurations.\n");
    
    ChessPieceFactory factory = new ChessPieceFactory();
    AbstractBoardGame abg = new AbstractBoardGame( 8, 8, 1, factory );
    Piece p0 = factory.createBishop( Color.WHITE );
    Piece p1 = factory.createKing( Color.WHITE );
    Piece q  = factory.createPawn( Color.BLACK );
    Piece q1  = factory.createPawn( Color.BLACK );
    Location location_x = new Location( 2, 0, 0 );
    Location location_x1 = new Location( 2, 3, 0 );
    Location location_y = new Location( 2, 2, 0 );
    Location location_z = new Location( 2, 1, 0 );
    abg.addPiece( p0, location_x );
    abg.addPiece( q, location_y );
    abg.addPiece( q1, location_z );
    abg.addPiece( p1, location_x1 );
    GZ gz = new GZ( abg, p0, q );
    ZoneBundle zones = gz.S( new ZoneTarget( location_x, location_y, 2 ) );
    System.out.println( zones.toString() );
  }
}
