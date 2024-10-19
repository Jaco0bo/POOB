import java.util.*;
/**
 * Write a description of class PuzzleContest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PuzzleContest {

    private char[][] startingBoard;
    private char[][] endingBoard;
    private int depth;
    private Puzzle puzzle;

    // Constructor de PuzzleContest
    public PuzzleContest(char[][] startingBoard, char[][] endingBoard, int depth) {
        this.startingBoard = startingBoard;
        this.endingBoard = endingBoard;
        this.depth = depth;
        this.puzzle = puzzle;
    }

    // Método para resolver el puzzle, retorna true si se encuentra una solución, false si no
    public boolean solve() {
        return dfsSolve(copyBoard(startingBoard), depth);
    }

    // Método para simular el proceso y mostrar los pasos
    public void simulate() {
        dfsTilt(copyBoard(startingBoard), depth);
    }

    // Método DFS para resolver sin imprimir los pasos
    private boolean dfsSolve(char[][] board, int depth) {
        if (Arrays.deepEquals(board, endingBoard)) {
            return true;
        }

        if (depth == 0) {
            return false;
        }

        Set<String> visitedStates = new HashSet<>();
        String currentState = boardToString(board);

        if (visitedStates.contains(currentState)) {
            return false;
        }

        visitedStates.add(currentState);

        String[] directions = {"arriba", "abajo", "izquierda", "derecha"};
        for (String direction : directions) {
            char[][] newBoard = copyBoard(board);
            tilt(newBoard, direction);

            if (dfsSolve(newBoard, depth - 1)) {
                return true;  // Si se encuentra la solución, se retorna true
            }
        }

        return false;  // No se encontró solución en este camino
    }

    // Método DFS para simular e imprimir los pasos
    private void dfsTilt(char[][] board, int depth) {
        if (Arrays.deepEquals(board, endingBoard)) {
            System.out.println("Solucion Encontrada");
            System.out.println(Arrays.deepToString(board));
            return;
        }

        if (depth == 0) {
            return;
        }

        Set<String> visitedStates = new HashSet<>();
        String currentState = boardToString(board);

        if (visitedStates.contains(currentState)) {
            return;
        }

        visitedStates.add(currentState);

        String[] directions = {"arriba", "abajo", "izquierda", "derecha"};
        for (String direction : directions) {
            char[][] newBoard = copyBoard(board);
            tilt(newBoard, direction);

            System.out.println("Inclinando hacia " + direction);
            System.out.println(Arrays.deepToString(newBoard));

            dfsTilt(newBoard, depth - 1);

            if (Arrays.deepEquals(newBoard, endingBoard)) {
                System.out.println("Tablero final encontrado después de inclinar hacia " + direction);
                return;
            }
        }
    }

    // Método auxiliar para convertir el tablero a un String para evitar ciclos
    private String boardToString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }

    // Método auxiliar para copiar un tablero
    private char[][] copyBoard(char[][] board) {
        char[][] newBoard = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
        }
        return newBoard;
    }

    // Método para inclinar el tablero (este puede venir de la clase Puzzle)
    private void tilt(char[][] board, String direction) {
        puzzle.tilt();
    }
}
