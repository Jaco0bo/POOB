import java.util.*;
/**
 * Esta clase Tilt inclina el tablero en diferentes direcciones y mueve las baldosas en esa dirección.
 * Funciona con un tablero representado como una matriz de caracteres donde las baldosas se representan
 * con cualquier carácter que no sea '.' (que se usa para representar espacios vacíos).
 * Ahora incluye lógica de pegamento para mover baldosas adyacentes pegadas.
 * 
 * @author Camilo Andres Fernandez Diaz 
 * @author Andres Jacobo Sepulveda
 * @version 2.0
*/

public class Tilt {
    private char[][] board; // Referencia al tablero principal
    private Glue glue;
    
    public Tilt(char[][] board, Glue glue){
        this.board = board; // Usar el tablero principal
        this.glue = glue; 
    }
    
    public void tilt(String direction) {
        switch (direction.toLowerCase()) {
            case "arriba":
                tiltUp();
                break;
            case "abajo":
                tiltDown();
                break;
            case "izquierda":
                tiltLeft();
                break;
            case "derecha":
                tiltRight();
                break;
            default:
                System.out.println("Dirección inválida. Use 'arriba', 'abajo', 'izquierda' o 'derecha'.");
            
        }
    }

    // Método para inclinar hacia arriba
    private void tiltUp() {
        for (int col = 0; col < board[0].length; col++) {
            int writeIndex = 0;
            for (int row = 0; row < board.length; row++) {
                if (board[row][col] != '.') {
                    board[writeIndex][col] = board[row][col];
                    
                    if (writeIndex != row) {
                        board[row][col] = '.';
                    }
                    writeIndex++;
                }
            }
        }
    }

    // Método para inclinar hacia abajo
    private void tiltDown() {
        for (int col = 0; col < board[0].length; col++) {
            int writeIndex = board.length - 1;
            for (int row = board.length - 1; row >= 0; row--) {
                if (board[row][col] != '.') {
                    board[writeIndex][col] = board[row][col];
                    if (writeIndex != row) {
                        board[row][col] = '.';
                    }
                    writeIndex--;
                }
            }
        }
    }

    // Método para inclinar hacia la izquierda
    private void tiltLeft() {
        for (int row = 0; row < board.length; row++) {
            int writeIndex = 0;
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] != '.') {
                    board[row][writeIndex] = board[row][col];
                    if (writeIndex != col) {
                        board[row][col] = '.';
                    }
                    writeIndex++;
                }
            }
        }
    }

    // Método para inclinar hacia la derecha
    private void tiltRight() {
        for (int row = 0; row < board.length; row++) {
            int writeIndex = board[0].length - 1;
            for (int col = board[0].length - 1; col >= 0; col--) {
                if (board[row][col] != '.') {
                    board[row][writeIndex] = board[row][col];
                    if (writeIndex != col) {
                        board[row][col] = '.';
                    }
                    writeIndex--;
                }
            }
        }
    }
    
    public void setBoard(char[][] board) {
        this.board = board;
    }
    
    public int getNewRow(int fila, int columna, String direction) {
        switch (direction) {
            case "arriba":
                return Math.max(0, fila - 1);
            case "abajo":
                return Math.min(board.length - 1, fila + 1);
            default:
                return fila;
        }
    }

    public int getNewCol(int fila, int columna, String direction) {
        switch (direction) {
            case "izquierda":
                return Math.max(0, columna - 1);
            case "derecha":
                return Math.min(board[0].length - 1, columna + 1);
            default:
                return columna;
        }
    }
    
    // Método para determinar si una baldosa puede moverse
    public boolean canMoveTile(int row, int col) {
        System.out.println("tilt"); 
        System.out.println(Arrays.deepToString(board));
    
        // Verifica si la baldosa no es un espacio vacío
        if (board[row][col] == '.') {
            return false;
        }
    
        // Verificar si hay espacio vacío en la misma fila
        for (int i = 0; i < board[row].length; i++) {
            if (board[row][i] == '.') {
                return true;
            }
        }
    
        // Verificar si hay espacio vacío en la misma columna
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == '.') {
                return true;
            }
        }
    
        // Si no hay espacios vacíos en la misma fila o columna, la baldosa no se puede mover
        return false;
    }
    
    public char[][] getBoard(){
        return board;
    }
}
