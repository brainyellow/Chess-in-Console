package chess;

import chess.Piece.Side;
import chess.Piece.Rank;

import java.util.Scanner;

/** Class for analyzing chessboard to see if a a king is under check
 * @author Tyler Latawiec
 * @author Brian Huang
 *
 */
public class Checkmate {

    /**
     * Method to determine if chess board is in a state of check
     * @param gb - accepts current state of chess board
     * @return 1 (valid check), -1 (invalid check e.g. player moves into check), 0 (no check)
     */
    public static int isCheck(GameBoard gb) {
        Side oppositeSide = !(gb.turnCount % 2 == 0) ? Side.BLACK : Side.WHITE;
        Side currentSide = (gb.turnCount % 2 == 0) ? Side.BLACK : Side.WHITE;
        for (int i = 0; i < gb.width; i++) {
            for (int j = 0; j < gb.height; j++) {
                Piece potentialKing = gb.getPiece(i, j);
                if (potentialKing != null && potentialKing.rank == Rank.KING) {
                    if (potentialKing.side == oppositeSide) {
                        for (int a = 0; a < gb.width; a++) {
                            for (int b = 0; b < gb.height; b++) {
                                Piece attacker = gb.getPiece(a, b);
                                if (attacker != null && attacker.side == currentSide) {
                                    if (MoveCheck.isValid(a, b, i, j, gb)) {
                                        return 1;
                                    }
                                }
                            }
                        }
                    } else if (potentialKing.side == currentSide) {
                        for (int a = 0; a < gb.width; a++) {
                            for (int b = 0; b < gb.height; b++) {
                                Piece attacker = gb.getPiece(a, b);
                                if (attacker != null && attacker.side == oppositeSide) {
                                    if (MoveCheck.isPossible(a, b, i, j, gb)) {
                                        return -1;
                                    }
                                }
                            }
                        }
                    }
                    ;
                }
            }
        }
        return 0;
    }

    /**
     * Method that checks if the state of the board is in checkmate
     * @param gb - Current state of chess board
     * @return true - if state of board is in checkmate, false if not
     */
    public static boolean isCheckmate(GameBoard gb){
        Side sideInCheck = gb.turnCount % 2 == 0 ? Side.BLACK:Side.WHITE;
        GameBoard checkmateBoard;
        if (isCheck(gb) == 0)
            return false;
        for (int i = 0; i < gb.height; i++){
            for (int j = 0; j < gb.width; j++){
                Piece sameSidePiece = gb.getPiece(i,j);
                if (sameSidePiece != null && sameSidePiece.side == sideInCheck) {
                    for (int a = 0; a < gb.height; a++) {
                        for (int b = 0; b < gb.width; b++) {
                            if (MoveCheck.isValid(i, j, a, b, gb)) {
                                checkmateBoard = GameBoard.cleanCopy(gb);
                                checkmateBoard.board[a][b] = checkmateBoard.board[i][j];
                                checkmateBoard.board[i][j] = null;
                                if (isCheck(checkmateBoard) == 0)
                                    return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method that checks if board is in a state of stalemate
     * @param gb current state of chess board
     * @return true or false whether the board is in stalemate or not
     */
    public static boolean isStalemate(GameBoard gb){
        Side currentSide = gb.turnCount % 2 == 0 ? Side.BLACK: Side.WHITE;

        GameBoard stalemateBoard;
        for (int i = 0; i < gb.height; i++){
            for (int j = 0; j < gb.width; j++){
                Piece tryMovePiece = gb.getPiece(i,j);
                if (tryMovePiece != null && tryMovePiece.side == currentSide){
                    for (int a = 0; a < gb.height; a++){
                        for (int b = 0; b < gb.width; b++){
                            if(MoveCheck.isValid(i,j,a,b, gb)){
                                stalemateBoard = GameBoard.cleanCopy(gb);
                                stalemateBoard.board[a][b] = stalemateBoard.board[i][j];
                                stalemateBoard.board[i][j] = null;
                                if (isCheck(stalemateBoard) == 0)
                                    return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}