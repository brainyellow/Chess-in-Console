package chess;

/** Class for moving piece
 * @author Brian Huang
 * @author Tyler Latawiec
 */
import java.util.Scanner;
import chess.Piece.Side;
import chess.Piece.Rank;
public class Mover {


    /**
     * Method that accepts a move from user and tries to execute said move if move is valid
     * @param input - Accepts users input which should be a chess move e.g. "h2 h4"
     * @param gb    - Accepts current state of chess board
     * @return true if move is successful, false if not
     */
    public static boolean move(String input, GameBoard gb) {
        int startX, startY, endX, endY;
        String moves[] = input.split("\\s");

        String startPos = moves[0];
        String endPos = moves[1];

        startX = (int) startPos.charAt(0) - 97;
        startY = Character.getNumericValue(startPos.charAt(1)) - 1;

        endX = (int) endPos.charAt(0) - 97;
        endY = Character.getNumericValue(endPos.charAt(1)) - 1;

        if (MoveCheck.isValid(startX, startY, endX, endY, gb)) {

            GameBoard boardCopy = GameBoard.cleanCopy(gb);

            Piece toMove = boardCopy.getPiece(startX, startY);
            if (toMove.rank == Piece.Rank.PAWN && Math.abs(endY - startY) == 2 && !toMove.moved) {
                toMove.enPassantTurn = gb.turnCount;
            } else if (toMove.rank == Rank.PAWN) {
                toMove.enPassantTurn = -1;
            }
            if (!toMove.moved)
                toMove.moved = true;
            if (toMove.rank == Rank.PAWN && (toMove.side == Side.WHITE && endY == 7) ^ (toMove.side == Side.BLACK && endY == 0)) {
                if (moves.length > 2) {
                    String promotion = moves[2];
                    if (promotion.equalsIgnoreCase("Q")) {
                        toMove.rank = Rank.QUEEN;
                    } else if (promotion.equalsIgnoreCase("N")) {
                        toMove.rank = Rank.KNIGHT;
                    } else if (promotion.equalsIgnoreCase("B")) {
                        toMove.rank = Rank.BISHOP;
                    } else if (promotion.equalsIgnoreCase("R")) {
                        toMove.rank = Rank.ROOK;
                    } else
                        toMove.rank = Rank.QUEEN;
                } else
                    toMove.rank = Rank.QUEEN;
            }

            boardCopy.board[startX][startY] = null;
            boardCopy.board[endX][endY] = toMove;

            if (Checkmate.isCheck(boardCopy) == -1) {
                System.out.println("Illegal move, try again");
                return false;
            } else if (Checkmate.isCheck(boardCopy) == 1) {
                System.out.println("Check");
            }

            gb.board = boardCopy.board;
            gb.turnCount++;
            if (Checkmate.isCheckmate(gb)) {
                System.out.println("Checkmate");
                boardCopy.printBoard();
                String winner = gb.turnCount % 2 == 1 ? "White Wins!" : "Black Wins!";
                System.out.println(winner);
                System.exit(0);
            }
            if (Checkmate.isStalemate(gb)){
                System.out.println("Stalemate");
                boardCopy.printBoard();
                System.exit(0);
            }
            return true;
        }
        System.out.println("Illegal move, try again");
        return false;
    }

    /**
     * Method that checks in initial input from user e.g. "a2 a4" or if the user is offering to draw
     * @param chessMove - chess move input from user. Correct format: "{a-h}{1-8} {a-h}{1-8} {Promotion Piece or draw?}"
     * @return true if in correct format, false if in incorrect format
     */
    public static boolean isChessMove(String chessMove) {
        String[] moves = chessMove.split("\\s");
        if (moves.length == 3) {
            if (moves[2].equalsIgnoreCase("draw?") || moves[2].equalsIgnoreCase("draw")) {
                Scanner kb = new Scanner(System.in);
                if (kb.nextLine().equalsIgnoreCase("draw")) {
                    System.exit(0);
                }
            }
        }
        if (moves.length > 3)
            return false;
        else if (moves[0].length() != 2)
            return false;
        else if (moves[1].length() != 2)
            return false;
        if (moves.length == 3) {
            String appended = moves[2];
            if (!appended.equalsIgnoreCase("Q") && !appended.equalsIgnoreCase("N") && !appended.equalsIgnoreCase("B") && !appended.equalsIgnoreCase("R"))
                return false;

            for (int i = 0; i < 2; i++) {
                if ((int) (moves[i].charAt(0)) < 97 || (int) (moves[i].charAt(0)) > 104)
                    return false;
                if (Character.getNumericValue(moves[i].charAt(1)) < 1 || Character.getNumericValue(moves[i].charAt(1)) > 8)
                    return false;
            }
        }
        return true;
    }
}
