package chess;

/**Class for chess piece
 * @author Brian Huang
 * @author Tyler Latawiec
 */

public class Piece{

    public enum Side{
        BLACK, WHITE
    }
    public enum Rank{
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }

    public Side side;
    public Rank rank;
    public boolean moved = false;
    public int enPassantTurn = 0;

    /**
     * Piece constructor
     * @param side - Color of piece (Black or White)
     * @param rank - Rank of piece (Pawn, Rook, Knight, Queen, Bishop, or King)
     */
    public Piece(Side side, Rank rank){

        this.side = side;
        this.rank = rank;
    }

    /**
     * Method to convert Piece object to String value
     * @return output - String representation of Piece object
     */
    public String toString(){

        String output = "";

        if (side == Side.BLACK)
            output = "b";
        else
            output = "w";


        if (rank == Rank.PAWN) {
            output += "p";
        }
        else if (rank == Rank.ROOK)
            output += "R";
        else if (rank == Rank.KNIGHT)
            output += "N";
        else if (rank == Rank.BISHOP)
            output += "B";
        else if (rank == Rank.QUEEN)
            output += "Q";
        else
            output += "K";

        return output;
    }
}

