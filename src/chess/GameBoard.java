package chess;

import chess.Piece.Rank;
import chess.Piece.Side;

/**Class for creating chess board
 * @author Brian Huang
 * @author Tyler Latawiec
 */

public class GameBoard{
	
	int turnCount = 1;
	Piece[][] board;
	final int width = 8;
	final int height = 8;

	/**
	 * Constructor for chess board
	 */
	public GameBoard(){
    	board = new Piece[width][height];
	}

	/**
	 * Method for initializing chess board
	 */
	public void initBoard(){

		//Pawns
		for(int i = 0; i < width; i++){
			board[i][6] = new Piece(Side.BLACK, Rank.PAWN);
			board[i][1] = new Piece(Side.WHITE, Rank.PAWN);
		}

		//Rooks
		board[0][0] = new Piece(Side.WHITE, Rank.ROOK);
		board[7][0] = new Piece(Side.WHITE, Rank.ROOK);
		board[0][7] = new Piece(Side.BLACK, Rank.ROOK);
		board[7][7] = new Piece(Side.BLACK, Rank.ROOK);

		//Knights
		board[1][0] = new Piece(Side.WHITE, Rank.KNIGHT);
		board[6][0] = new Piece(Side.WHITE, Rank.KNIGHT);
		board[1][7] = new Piece(Side.BLACK, Rank.KNIGHT);
		board[6][7] = new Piece(Side.BLACK, Rank.KNIGHT);

		//Bishops
		board[2][0] = new Piece(Side.WHITE, Rank.BISHOP);
		board[5][0] = new Piece(Side.WHITE, Rank.BISHOP);
		board[2][7] = new Piece(Side.BLACK, Rank.BISHOP);
		board[5][7] = new Piece(Side.BLACK, Rank.BISHOP);

		//Queens
		board[3][0] = new Piece(Side.WHITE, Rank.QUEEN);
		board[3][7] = new Piece(Side.BLACK, Rank.QUEEN);

		//Kings
		board[4][0] = new Piece(Side.WHITE, Rank.KING);
		board[4][7] = new Piece(Side.BLACK, Rank.KING);
	}

	/**
	 * Method for printing the chess board
	 */
    public void printBoard() {
    	for(int i = height-1; i >= 0; i--)
    	{
    		for(int j = 0; j < width; j++)
    		{
				if (board[j][i] != null)
					System.out.print(board[j][i].toString() + " ");
				else{
					if ((i+j) % 2 == 0)
						System.out.print("## ");
					else{
						System.out.print("   ");
					}
					
				}
    		}
    		System.out.println(i+1);
    	}
    	for(char col = 'a'; col <= 'h'; col++)
    	{
    		System.out.print(" " + col + " ");
		}
		
		System.out.println("\n");
    	
	}

	/**
	 * Method for returning piece at position specified
	 * @param x - x position on board
	 * @param y - y position on board
	 * @return board[x][y] - current piece on specified position
	 */
	public Piece getPiece(int x, int y)
	{
		return board[x][y];
	}

    /**
     * Method used when cloning chess board (Deep Copy)
     * @param piece - Accepts a chess piece
     * @return clean - Returns a new piece with the same value as piece but different address in memory
     */
    public static Piece cleanPiece(Piece piece){
        StringBuffer sb = new StringBuffer(piece.toString());
        char charSide = sb.charAt(0);
        char charRank = sb.charAt(1);
        Side side = charSide == 'b' ? Side.BLACK:Side.WHITE;
        Rank rank;
        if (charRank == 'p') {
            rank = Rank.PAWN;
        }
        else if (charRank == 'R')
            rank = Rank.ROOK;
        else if (charRank == 'N')
            rank = Rank.KNIGHT;
        else if (charRank == 'B')
            rank = Rank.BISHOP;
        else if (charRank == 'Q')
            rank = Rank.QUEEN;
        else
            rank = Rank.KING;
        Piece clean = new Piece(side, rank);
        clean.moved = piece.moved;
        if (clean.rank == Rank.PAWN)
            clean.enPassantTurn= piece.enPassantTurn;

        return clean;
    }
    /**
     * Creates a deep copy of a chess board
     * @param gb - chess board to copy
     * @return boardCopy- returns clean chess board copy
     */
    public static GameBoard cleanCopy(GameBoard gb){
        GameBoard boardCopy = new GameBoard();
        boardCopy.turnCount = gb.turnCount;
        for (int i = 0; i < boardCopy.height; i++)
            for(int j = 0; j < boardCopy.width; j++)
                if (gb.board[i][j] != null)
                    boardCopy.board[i][j] = cleanPiece(gb.board[i][j]);
        return boardCopy;
    }
}