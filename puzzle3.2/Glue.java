
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
    
    /**
     * Metodo de apoyo para adGlue que permite dibujar el pegante en las baldosas adyacentes
     */
    
    public void drawGlue(int filaAdj, int columnaAdj, int glueSize, int tileSize) {
        if (filaAdj >= 0 && filaAdj < board.length && columnaAdj >= 0 && columnaAdj < board[0].length) {
            int xPositionGlue = 10 + columnaAdj * tileSize + (tileSize - glueSize) / 2;  // Posición en x ajustada
            int yPositionGlue = 20 + filaAdj * tileSize + (tileSize - glueSize) / 2;     // Posición en y ajustada
    
            System.out.println("Dibujando pegante en (" + filaAdj + ", " + columnaAdj + "): x=" + xPositionGlue + ", y=" + yPositionGlue);
            Rectangle glueSquare = Rectangle.createGlueSquare(glueSize, xPositionGlue, yPositionGlue, "magenta");
            glueSquare.drawGlue(); // Dibujar el pegante sobre la baldosa
            glueApplied[filaAdj][columnaAdj] = true; // Marcar que el pegamento se ha aplicado en esta baldosa
        }
    }

    public void adGlue(int fila, int columna) {
        System.out.println("Intentando agregar pegante en (" + fila + ", " + columna + ")");
        
        // Primero, dibuja el tablero
        tablero.setBoard(board); // Actualizar el tablero en el objeto Rectangle
        tablero.drawBoard(); // Redibujar el tablero
    
        int glueSize = 10; // Tamaño del cuadrado de pegante
        int tileSize = 30; // Tamaño del cuadro de las baldosas
    
        // Dibuja el pegante en la baldosa original y en las adyacentes
        drawGlue(fila, columna, glueSize, tileSize);
        drawGlue(fila - 1, columna, glueSize, tileSize); // Baldosa arriba
        drawGlue(fila + 1, columna, glueSize, tileSize); // Baldosa abajo
        drawGlue(fila, columna - 1, glueSize, tileSize); // Baldosa a la izquierda
        drawGlue(fila, columna + 1, glueSize, tileSize); // Baldosa a la derecha
        System.out.println("Pegante agregado en (" + fila + ", " + columna + ") y en las adyacentes.");
    }
    
    /**
     * Metodo de apoyo para borrar el glue de las baldosas adyacentes
     */
    public void removeGlue(int filaAdj, int columnaAdj, int glueSize, int tileSize) {
        if (filaAdj >= 0 && filaAdj < board.length && columnaAdj >= 0 && columnaAdj < board[0].length) {
            if (glueApplied[filaAdj][columnaAdj]){ // Solo se elimina si hay glue en la baldosa
                int xPositionGlue = 10 + columnaAdj * tileSize + (tileSize - glueSize) / 2;  
                int yPositionGlue = 20 + filaAdj * tileSize + (tileSize - glueSize) / 2;     
                Rectangle glueSquare = Rectangle.createGlueSquare(glueSize, xPositionGlue, yPositionGlue,"white");
                glueSquare.drawGlue();
                glueApplied[filaAdj][columnaAdj] = false; // Marcar que ya no hay un pegamento en esta baldosa
            }
        }
    }
    
    public void adRemoveGlue(int fila, int columna) {
        System.out.println("Intentando eliminar pegante en (" + fila + ", " + columna + ")");
        if (glueApplied[fila][columna]){
            int glueSize = 10; // Tamaño del cuadrado de pegante
            int tileSize = 30; // Tamaño del cuadro de las baldosas
        
            // Elimina el pegante de la baldosa original y de las adyacentes
            removeGlue(fila, columna, glueSize, tileSize);
            removeGlue(fila - 1, columna, glueSize, tileSize); // Baldosa arriba
            removeGlue(fila + 1, columna, glueSize, tileSize); // Baldosa abajo
            removeGlue(fila, columna - 1, glueSize, tileSize); // Baldosa a la izquierda
            removeGlue(fila, columna + 1, glueSize, tileSize); // Baldosa a la derecha
        
            System.out.println("Pegante eliminado en (" + fila + ", " + columna + ") y en las adyacentes.");
        }else{
            System.out.println("No hay pegante en (" + fila + ", " + columna + ") para eliminar.");
        }
    }

    public boolean isGlued(int row, int col) {
        // Revisa si la baldosa en (row, col) está pegada a las adyacentes
        return glueApplied[row][col];
    }  
}
