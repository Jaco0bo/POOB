
/**
 * clase que representa el objeto hole.
 * @author Camilo Fernandez
 * @author An 
 * @version (a version number or a date)
 */
public class Hole {
    private Circle hole;
    private Rectangle boardRectangle; // Cambiado a Rectangle
    private int holeRow;
    private int holeColumn;
    private Glue glue;

    public Hole(Rectangle boardRectangle) { // Cambiar parámetro a Rectangle
        this.boardRectangle = boardRectangle;
        this.hole = new Circle(); // Inicializar el círculo sin parámetros
        this.holeRow = -1; // Inicializar fuera del tablero
        this.holeColumn = -1;
    }

    public void makeHole(int row, int column) {
        if (boardRectangle != null) {
            char[][] currentBoard = boardRectangle.getBoard(); // Obtén el estado actual del tablero
            if (row >= 0 && row < currentBoard.length && column >= 0 && column < currentBoard[0].length) {
                // Cambia el valor en el tablero a un símbolo que represente un agujero
                currentBoard[row][column] = 'O'; // O por hole
                boardRectangle.setBoard(currentBoard); // Actualiza el tablero
                boardRectangle.drawBoard(glue); // Redibuja el tablero
                holeRow = row; // Actualiza la posición del agujero
                holeColumn = column;
                drawHole(); // Dibuja el agujero
            }
        }
    }

    public boolean isHole(int row, int column) {
        return row == holeRow && column == holeColumn;
    }
    
    public void drawHole() {
        if (holeRow != -1 && holeColumn != -1) {
            int xPosition = 10 + holeColumn * 30;
            int yPosition = 20 + holeRow * 30;
            hole.createCircle(20, xPosition, yPosition, "blue");
        }
    }

    // Método para eliminar una ficha que cae en el agujero
    public void removeTileIfInHole(int row, int column) {
        if (isHole(row, column)) {
            boardRectangle.getBoard()[row][column] = '.';  // La ficha desaparece
            boardRectangle.drawBoard(glue); // Redibuja el tablero después de eliminar
        }
    }
}