package chess;

import chess.Piece.Rank;
import chess.Piece.Side;
import java.util.Scanner;

/**
 * Class for checking if move is valid/possible
 * @author Tyler Latawiec
 * @author Brian Huang
 */

public class MoveCheck {
    /**
     * Method accepts the starting position of a piece and the position it is trying to move to, checks if another side is trying to move the opposing sides' pieces.
     * Afterwards, sends position parameters to isPossible method, which determines if the move can be executed
     * @param startX - initial x position of piece to be moved
     * @param startY - initial y position ...
     * @param endX - x position after piece is moved
     * @param endY - y position ...
     * @param gb - current state of chess board
     * @return true if valid move, false if invalid
     */
    public static boolean isValid(int startX, int startY, int endX, int endY, GameBoard gb) {
        Piece piece = gb.getPiece(startX, startY);
        if (piece == null)
            return false; // no piece to move

        if (gb.turnCount % 2 == 0) {
            if (piece.side == Side.WHITE)
                return false; // black attempting to move white
        } else if (gb.turnCount % 2 == 1){
            if (piece.side == Side.BLACK)
                return false;  //white attempting to move black
        }
        if (isPossible(startX, startY, endX, endY, gb))
            return true;
        else
            return false;
    }

    /**
     * Method accepts the starting and ending position of a move and sees if it is possible or not. Considers all special cases such as En Passant, Castling, etc...
     * @param startX - initial x position of piece to be moved
     * @param startY - initial y position ...
     * @param endX - x position after piece is moved
     * @param endY - y position ...
     * @param gb - current state of chess board
     * @return true if possible move, false if impossible
     */
    public static boolean isPossible(int startX, int startY, int endX, int endY, GameBoard gb){
        int mod;
        int initPosY;

        Piece piece = gb.getPiece(startX, startY);
        Piece victim = gb.getPiece(endX, endY);
        if (piece.side == Side.BLACK) {
            mod = -1;
            initPosY = 6;
        } else {
            mod = 1;
            initPosY = 1;
        }

        if (piece.rank == Rank.PAWN) {
            if (Math.abs(startX-endX) == 1 && (endY-startY) == mod) {
                Piece enPassantVictim = gb.getPiece(endX, endY - mod);
                if (enPassantVictim != null && enPassantVictim.side != piece.side && enPassantVictim.enPassantTurn > 0 && gb.turnCount-1 == enPassantVictim.enPassantTurn) {
                    gb.board[endX][endY - mod] = null;
                    return true;
                }
                else if (victim != null && victim.side != piece.side)
                    return true;
                else
                    return false;
            }
            if (startX - endX != 0) {   //asserts it is moving >1 tile
                if ((Math.abs(startX - endX) == 1) && endY - startY == (mod)) {                   // if trying to move diagonal
                    if (gb.getPiece(endX, endY) != null) {                                              // if destination is occupied
                        if (gb.getPiece(endX, endY).side != piece.side) {                                   // if occupied spot belongs to enemy
                            return true;                                                                        //allow
                        } else                                                                              // else, no
                            return false;
                    } else                                                                              // else, no
                        return false;
                }
                return false; // attempting to move sideways (pawn not acceptable)
            }

            if (startY + (2 * mod) == endY) { // if attempting to move 2 spaces
                if ((gb.getPiece(startX, startY + mod) == null) && (gb.getPiece(startX, startY + (2 * mod)) == null) && startY == initPosY) // if no obstruction                                                                     // moved
                    return true;
                else
                    return false;
            } else if (startY + mod == endY) { // if attempting to move 1 space
                if (gb.getPiece(startX, startY + mod) == null) // if no obstructing
                    return true;
                else
                    return false;
            } else
                return false;
        }

        if (piece.rank == Rank.ROOK) {
            if (startX - endX == 0 ^ startY - endY == 0) {
                if (endX - startX > 0 ^ endY - startY > 0)
                    mod = 1;
                else
                    mod = -1;

                if (Math.abs(endX - startX) > 0) {
                    // moving horizontal
                    for (int i = 1; i < Math.abs(startX - endX); i++)
                        if (gb.getPiece(startX + (i * mod), startY) != null)
                            return false;
                } else {
                    // moving vertical
                    for (int i = 1; i < Math.abs(startY - endY); i++)
                        if (gb.getPiece(startX, (startY + (i * mod))) != null)
                            return false;
                }

                if (gb.getPiece(endX, endY) != null
                        && (gb.getPiece(startX, startY).side != gb.getPiece(endX, endY).side))
                    return true;
                else if (gb.getPiece(endX, endY) == null)
                    return true;
                else
                    return false;
            } else return false;
        }

        if (piece.rank == Rank.KNIGHT) {
            if (startX + 2 == endX || startX - 2 == endX || startY + 2 == endY || startY - 2 == endY) {
                if (startY + 1 == endY || startY - 1 == endY || startX + 1 == endX || startX - 1 == endX) {
                    if (gb.getPiece(endX, endY) != null) {
                        if (gb.getPiece(startX, startY).side != gb.getPiece(endX, endY).side)
                            return true;
                        else
                            return false;
                    } else
                        return true;
                } else
                    return false;
            } else
                return false;
        }

        if (piece.rank == Rank.BISHOP) {
            if (Math.abs(startX - endX) == Math.abs(startY - endY) && startX - endX != 0) {
                int right;
                int up;

                if (endX - startX > 0)
                    right = 1;
                else
                    right = -1;
                if (endY - startY > 0)
                    up = 1;
                else
                    up = -1;

                for (int i = 1; i < (Math.abs(startX - endX)); i++) {
                    if (gb.getPiece(startX + (i * right), startY + (i * up)) != null) {
                        return false;
                    }
                }
                if (gb.getPiece(endX, endY) != null)
                    if (gb.getPiece(endX, endY).side != piece.side)
                        return true;
                    else
                        return false;
            } else return false;
        }

        if (piece.rank == Rank.QUEEN){
            if (startX - endX == 0 ^ startY - endY == 0) {
                if (endX - startX > 0 ^ endY - startY > 0)
                    mod = 1;
                else
                    mod = -1;

                if (Math.abs(endX - startX) > 0) {
                    // moving horizontal
                    for (int i = 1; i < Math.abs(startX - endX); i++)
                        if (gb.getPiece(startX + (i * mod), startY) != null)
                            return false;
                } else {
                    // moving vertical
                    for (int i = 1; i < Math.abs(startY - endY); i++)
                        if (gb.getPiece(startX, (startY + (i * mod))) != null)
                            return false;
                }

                if (gb.getPiece(endX, endY) != null
                        && (gb.getPiece(startX, startY).side != gb.getPiece(endX, endY).side))
                    return true;
                else if (gb.getPiece(endX, endY) == null)
                    return true;
                else
                    return false;
            }
            else if (Math.abs(startX - endX) == Math.abs(startY - endY) && startX - endX != 0) {
                int right;
                int up;

                if (endX - startX > 0)
                    right = 1;
                else
                    right = -1;
                if (endY - startY > 0)
                    up = 1;
                else
                    up = -1;

                for (int i = 1; i < (Math.abs(startX - endX)); i++) {
                    if (gb.getPiece(startX + (i * right), startY + (i * up)) != null) {
                        return false;
                    }
                }
                if (gb.getPiece(endX, endY) != null)
                    if (gb.getPiece(endX, endY).side != piece.side)
                        return true;
                    else
                        return false;
            }
            else return false;

        }
        if (piece.rank == Rank.KING) {
            //castling
            if (!piece.moved && Math.abs(startX-endX) == 2 && startY-endY == 0 && Checkmate.isCheck(gb) == 0){
                int direction = (startX - endX < 0) ? 1:-1;
                GameBoard copy = GameBoard.cleanCopy(gb);
                //moving left
                if (gb.getPiece(startX + direction, endY) != null)
                    return false;
                else {
                    copy.board[startX + direction][endY] = copy.board[startX][startY];
                    copy.board[startX][startY] = null;
                    if (Checkmate.isCheck(copy) != 0)
                        return false;
                    copy.board[startX + 2*direction][endY] = copy.board[startX+direction][endY];
                    copy.board[startX+direction][endY] = null;
                    if (Checkmate.isCheck(copy) == 1)
                        return false;
                }

                int rookXPos = (startX - endX < 0) ? 7:0;
                int rookYPos = (piece.side == Side.WHITE) ? 0:7;
                Piece rook = gb.getPiece(rookXPos, rookYPos);
                if (rook != null && rook.rank == Rank.ROOK && !rook.moved && rook.side == piece.side){
                    for (int i = 1; i < Math.abs(startX - rookXPos); i++){
                        if (gb.getPiece(startX + i*direction, endY) != null)
                            return false;
                    }
                    gb.board[startX + direction][endY] = gb.board[rookXPos][rookYPos];
                    gb.board[rookXPos][rookYPos] = null;
                    return true;
                }
                else return false;
            }
            if ((startX + 1 == endX || startX - 1 == endX || startX - endX == 0)
                    && (startY + 1 == endY || startY - 1 == endY || startY - endY == 0)) {
                if (startY - endY == 0 && startX - endX == 0) {
                    return false;
                }
                else {
                    if (gb.getPiece(endX, endY) != null){
                        if (piece.side != gb.getPiece(endX, endY).side)
                            return true;
                        else
                            return false;
                    }
                    return true;
                }
            }
            else return false;
        }
        return true;
    }
}