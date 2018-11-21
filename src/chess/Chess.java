package chess;

import java.util.Scanner;

import static chess.Mover.move;

/** Main class for running two player chess
 * @author Tyler Latawiec
 * @author Brian Huang
 */

public class Chess {
    public static void main(String[] args){

		String input = "";
		Scanner kb = new Scanner(System.in);
		GameBoard board = new GameBoard();
		
		board.initBoard();
		board.printBoard();

		boolean isChessMove;
		boolean moveHappens;
		while (!input.equalsIgnoreCase("resign")){
			if (board.turnCount % 2 == 0)
				System.out.print("Black's move: ");
			else
				System.out.print("White's move: ");

			input = kb.nextLine();
			isChessMove = Mover.isChessMove(input);
			if (!input.equalsIgnoreCase("resign") && isChessMove) {
				moveHappens = move(input, board);
				if (moveHappens)
					board.printBoard();
			}

			if (input.equalsIgnoreCase("resign")) {
				if (board.turnCount % 2 == 0) {
					System.out.print("White Wins!");
				}
				else if (board.turnCount % 2 == 1) {
					System.out.print("Black Wins!");
				}
				break;
			}
			if (!isChessMove)
				System.out.println("Illegal move, try again");

		}
		kb.close();
    }
}
