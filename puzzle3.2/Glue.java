
/**
 * Clase que permite simular el comportamiento de glue con las baldosas de puzzle.
 * @author Camilo Fernandez
 * @author Andrés Sepúlveda 
 * @version (a version number or a date)
 */
public class Glue{
    private Rectangle tablero;
    private char[][] board;
    private boolean[][] glueApplied; 
    
    /**
     * Constructor for objects of class Glue
     */
    public Glue(char[][] board, Rectangle tablero){
        this.board = board;
        this.tablero = tablero;
        this.glueApplied = new boolean[board.length][board[0].length]; // Inicializar matriz de pegante
    }
    
    public void adGlue(int fila, int columna) {
        System.out.println("Intentando agregar pegante en (" + fila + ", " + columna + ")");
    
        // Verificar que el tablero y el canvas no sean nulos
        if (tablero == null || board == null || board.length == 0 || board[0].length == 0) {
            System.out.println("Error: El tablero no ha sido inicializado correctamente.");
            return;
        }
    
        // Verificar si la casilla donde se quiere aplicar pegante no está vacía
        if (board[fila][columna] == '.') {
            System.out.println("No se puede agregar pegante en una casilla vacía.");
            return;
        }
    
        // Verificar las casillas adyacentes y aplicar pegante solo si no están vacías
        int glueSize = 10;  // Tamaño del pegante
        int tileSize = 30;  // Tamaño de las baldosas
    
        // Aplicar pegante en la baldosa original
        drawGlue(fila, columna, glueSize, tileSize);
    
        // Aplicar pegante solo en las casillas adyacentes que no estén vacías
        if (fila - 1 >= 0 && board[fila - 1][columna] != '.') {
            drawGlue(fila - 1, columna, glueSize, tileSize); // Baldosa arriba
        }
        if (fila + 1 < board.length && board[fila + 1][columna] != '.') {
            drawGlue(fila + 1, columna, glueSize, tileSize); // Baldosa abajo
        }
        if (columna - 1 >= 0 && board[fila][columna - 1] != '.') {
            drawGlue(fila, columna - 1, glueSize, tileSize); // Baldosa a la izquierda
        }
        if (columna + 1 < board[0].length && board[fila][columna + 1] != '.') {
            drawGlue(fila, columna + 1, glueSize, tileSize); // Baldosa a la derecha
        }
    
        System.out.println("Pegante agregado en (" + fila + ", " + columna + ") y en las adyacentes que no están vacías.");
    
        // Forzar el redibujado del canvas
        Canvas canvas = Canvas.getCanvas();
        canvas.getCanvasPane().repaint();
    }

    private void drawGlue(int filaAdj, int columnaAdj, int glueSize, int tileSize) {
        if (filaAdj >= 0 && filaAdj < board.length && columnaAdj >= 0 && columnaAdj < board[0].length) {
            int xPositionGlue = 10 + columnaAdj * tileSize + (tileSize - glueSize) / 2;
            int yPositionGlue = 20 + filaAdj * tileSize + (tileSize - glueSize) / 2;
    
            System.out.println("Dibujando pegante en (" + filaAdj + ", " + columnaAdj + "): x=" + xPositionGlue + ", y=" + yPositionGlue);
            Rectangle glueSquare = Rectangle.createGlueSquare(glueSize, xPositionGlue, yPositionGlue, "magenta");
            glueSquare.drawGlue();
    
            // Inicializa glueApplied si es necesario
            if (glueApplied == null) {
                glueApplied = new boolean[board.length][board[0].length];
            }
    
            glueApplied[filaAdj][columnaAdj] = true; // Marcar que el pegamento se ha aplicado
        }
    }

    public void removeGlue(int filaAdj, int columnaAdj, int glueSize, int tileSize) {
        if (filaAdj >= 0 && filaAdj < board.length && columnaAdj >= 0 && columnaAdj < board[0].length) {
            if (glueApplied[filaAdj][columnaAdj]) { // Solo se elimina si hay glue en la baldosa
                // Determinar el color original de la baldosa
                String tileColor;
                if (board[filaAdj][columnaAdj] == '.') {
                    tileColor = "brown"; // Vacías
                } else {
                    switch (board[filaAdj][columnaAdj]) {
                        case 'y': tileColor = "yellow"; break;
                        case 'b': tileColor = "blue"; break;
                        case 'g': tileColor = "green"; break;
                        case 'r': tileColor = "red"; break;
                        default: tileColor = "gray"; // Cualquier otro caso
                    }
                }
                
                int xPositionGlue = 10 + columnaAdj * tileSize + (tileSize - glueSize) / 2;
                int yPositionGlue = 20 + filaAdj * tileSize + (tileSize - glueSize) / 2;
                Rectangle glueSquare = Rectangle.createGlueSquare(glueSize, xPositionGlue, yPositionGlue, tileColor);
                glueSquare.drawGlue(); // Dibuja el cuadrado con el color de la baldosa
                glueApplied[filaAdj][columnaAdj] = false; // Marcar que el pegamento ha sido eliminado
            }
        }
    }

    public void adRemoveGlue(int fila, int columna) {
        System.out.println("Intentando eliminar pegante en (" + fila + ", " + columna + ")");
        
        if (glueApplied[fila][columna]) {
            int glueSize = 10; // Tamaño del cuadrado de pegante
            int tileSize = 30; // Tamaño del cuadro de las baldosas
        
            // Elimina el pegante de la baldosa original y de las adyacentes
            removeGlue(fila, columna, glueSize, tileSize);
            removeGlue(fila - 1, columna, glueSize, tileSize); // Baldosa arriba
            removeGlue(fila + 1, columna, glueSize, tileSize); // Baldosa abajo
            removeGlue(fila, columna - 1, glueSize, tileSize); // Baldosa a la izquierda
            removeGlue(fila, columna + 1, glueSize, tileSize); // Baldosa a la derecha
        
            System.out.println("Pegante eliminado en (" + fila + ", " + columna + ") y en las adyacentes.");
        } else {
            System.out.println("No hay pegante en (" + fila + ", " + columna + ") para eliminar.");
        }
    }

    public boolean isGlued(int row, int col) {
        // Revisa si la baldosa en (row, col) está pegada a las adyacentes
        return glueApplied[row][col];
    }  
}
