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
    private Hole hole; // Referencia al agujero
    
    public Tilt(char[][] board, Hole hole){
        this.board = board; // Usar el tablero principal
        this.hole = hole;  // Pasar la referencia del agujero
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
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                hole.removeTileIfInHole(row, col);  // Eliminar fichas que caen en el agujero
            }
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
}
