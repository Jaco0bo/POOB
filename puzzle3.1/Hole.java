
/**
 * clase que representa el objeto hole.
 * @author Camilo Fernandez
 * @author An 
 * @version (a version number or a date)
 */
public class Hole {
    private Circle hole;
    private char[][] board;

    public Hole(char[][] board) {
        this.board = board;
        this.hole = new Circle(); // Inicializar el círculo sin parámetros
    }

    public void makeHole(int row, int column) {
        if (board[row][column] == '.') {  // Solo crear agujero en espacios vacíos
            int xPosition = 10 + column * 30;
            int yPosition = 20 + row * 30;
            hole.createCircle(20, xPosition, yPosition, "blue");
            board[row][column] = 'O';  // Marcar el agujero en el tablero
        } else {
            System.out.println("No se puede crear un agujero en esta posición.");
        }
    }
}

