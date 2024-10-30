
/**
 * Clase que permite simular el comportamiento de glue con las baldosas de puzzle.
 * @author Camilo Fernandez
 * @author Andrés Sepúlveda 
 * @version (a version number or a date)
 */
public class Glue{
    private Rectangle tablero;
    private Rectangle endingTablero;
    private char[][] board;
    private char[][] endingBoard;
    private boolean[][] glueApplied;
    private boolean [][] glueAppliedEnding;
    private boolean[][] newGlueApplied;
    private int glueSize = 10;  
    private int tileSize = 30;   
    /**
     * Constructor for objects of class Glue
     */
    public Glue(char[][] board, Rectangle tablero){
        this.board = board;
        this.tablero = tablero;
        this.glueApplied = new boolean[board.length][board[0].length]; // Inicializar matriz de pegante
    }
    
    public Glue(char[][] board, Rectangle tablero, char[][] endingBoard, Rectangle endingTablero){
        this.board = board;
        this.tablero = tablero;
        this.endingBoard = endingBoard;
        this.endingTablero = endingTablero;
    
        // Inicializar las matrices de pegamento para ambos tableros
        this.glueApplied = new boolean[board.length][board[0].length];
        this.glueAppliedEnding = new boolean[endingBoard.length][endingBoard[0].length];
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
    
        // Solo se aplica pegamento en la baldosa original si no está ya pegada
        if (!isGlued(fila, columna)) {
            drawGlue(fila, columna, glueSize, tileSize); // Dibuja el pegante
            glueApplied[fila][columna] = true; // Marca como pegado
        }
    
        // Aplicar pegamento solo a las baldosas adyacentes que no están pegadas
        applyGlueIfNotGlued(fila - 1, columna); // Baldosa arriba
        applyGlueIfNotGlued(fila + 1, columna); // Baldosa abajo
        applyGlueIfNotGlued(fila, columna - 1); // Baldosa a la izquierda
        applyGlueIfNotGlued(fila, columna + 1); // Baldosa a la derecha
    
        System.out.println("Pegante agregado en (" + fila + ", " + columna + ") y en las adyacentes que no están vacías.");
    
        // Forzar el redibujado del canvas
        Canvas canvas = Canvas.getCanvas();
        canvas.getCanvasPane().repaint();
    }

    private void applyGlueIfNotGlued(int row, int col) {
        if (row >= 0 && row < board.length && col >= 0 && col < board[0].length) {
            // Verifica que la casilla no esté vacía y que no tenga pegante
            if (board[row][col] != '.' && !isGlued(row, col)) {
                drawGlue(row, col, glueSize, tileSize); // Dibuja el pegante
                glueApplied[row][col] = true; // Marca como pegado
            }
        }
    }

    public void drawGlue(int filaAdj, int columnaAdj, int glueSize, int tileSize) {
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
    
    
    public boolean isGluedInEndingBoard(int row, int col) {
        return glueAppliedEnding[row][col];
    }
    
    public int getGlueSize(){
        return glueSize;
    }
    
    public int getTileSize(){
        return tileSize;
    }
    
    public void addGlueWithoutAdjacent(int fila, int columna) {
        System.out.println("Intentando agregar pegante en (" + fila + ", " + columna + ") sin adyacentes");
        
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
    
        // Solo se aplica pegamento en la baldosa original si no está ya pegada
        if (!isGlued(fila, columna)) {
            drawGlue(fila, columna, glueSize, tileSize); // Dibuja el pegante
            glueApplied[fila][columna] = true; // Marca como pegado
        }
    
        System.out.println("Pegante agregado en (" + fila + ", " + columna + ") sin afectar adyacentes.");
    
        // Forzar el redibujado del canvas
        Canvas canvas = Canvas.getCanvas();
        canvas.getCanvasPane().repaint();
    }
    
    // Método para mover el pegamento de una posición a otra
    public void moveGlue(int fromRow, int fromCol, int toRow, int toCol) {
        if (glueApplied[fromRow][fromCol]) {
            glueApplied[toRow][toCol] = true;  // Mover pegamento a la nueva posición
            glueApplied[fromRow][fromCol] = false; // Quitar pegamento de la posición anterior
        }
    }
    
    public void updateGlueState(boolean[][] newGlueApplied) {
        this.glueApplied = newGlueApplied;
        }
    
    public boolean[][] getGlueState(char[][] board) {
        // Crear una nueva matriz para almacenar el estado del pegamento
        boolean[][] glueState = new boolean[board.length][board[0].length];
    
        // Revisa cada posición en el tablero y asigna el estado del pegamento
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                glueState[row][col] = isGlued(row, col); // Verifica si la baldosa está pegada
            }
        }
        
        return glueState; // Retorna el estado del pegamento para el tablero dado
    }

    // Método para clonar el estado del pegamento
    public boolean[][] cloneGlueState() {
        boolean[][] clonedState = new boolean[glueApplied.length][];
        for (int i = 0; i < glueApplied.length; i++) {
            clonedState[i] = glueApplied[i].clone();
        }
        return clonedState;
    }
    
    // Método para obtener el estado del pegamento para un tablero específico
    public boolean[][] getGlueStateForBoard(char[][] board) {
        boolean[][] state = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                state[i][j] = glueApplied[i][j]; 
            }
        }
        return state;
    }

}