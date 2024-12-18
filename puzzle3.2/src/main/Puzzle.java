import java.util.*;
import javax.swing.*;
/**
 * Clase que permite simular una situacion inspirada en el "Problem F" de la maraton
 * de programacion internacional 2023 "Tilting Tiles"
 * @author: Camilo Fernandez
 * @author: Andrés Sepúlveda
 * @version: 1.0
 */

public class Puzzle {
    private int width;  // Ancho del tablero
    private int height; // Altura del tablero
    private Rectangle startingTablero; // Tablero inicial
    private Rectangle endingTablero; // Tablero final
    private char[][] startingBoard; // Representación del tablero inicial
    private char[][] endingBoard; // Representación del tablero final
    private Glue glue; // Pegamento para el tablero inicial
    private Glue endingGlue; // Pegamento para el tablero final
    private Tilt tilt; // Objeto para manejar inclinaciones
    private boolean tableVisible = false; // Indica si el tablero es visible
    private int depth; // Profundidad máxima para búsqueda de soluciones
    
    /**
     * Constructor que inicializa el Puzzle con dimensiones específicas.
     * 
     * @param h Altura del tablero.
     * @param w Ancho del tablero.
     */
    public Puzzle(int h, int w) {
        this.height = h;
        this.width = w;
        this.endingBoard = new char[height][width];
        this.startingBoard = new char[height][width]; // Inicializamos startingBoard vacío
        this.tilt = new Tilt(startingBoard, glue);  // Pasar el agujero a Tilt
        this.depth = 5;
        
        // Ajustamos las posiciones para que los tableros no se superpongan
        this.startingTablero = new Rectangle(width * 30, height * 30, 10, 20, "brown", true, false); // Tablero inicial
        this.endingTablero = new Rectangle(width * 30, height * 30, (width * 30) + 40, 20, "brown", true, false); // Tablero final
         this.glue = new Glue(startingBoard, startingTablero);// Inicializar el objeto Glue con el tablero inicial
        this.endingGlue = new Glue(endingBoard, endingTablero);// Inicializar el objeto Glue con el tablero inicial
        initializeBoard(startingBoard); // Tablero inicial vacío
    
        // Configurar los tableros en los objetos Rectangle
        startingTablero.setBoard(startingBoard);
        endingTablero.setBoard(endingBoard);
    
        // Obtener la instancia de Canvas y configurar el tamaño del panel
        Canvas canvas = Canvas.getCanvas();
        canvas.setVisible(true);
    
        // Configurar la ventana
        JFrame frame = new JFrame();
        frame.setSize((w * 30 * 2) + 80, h * 30 + 60); // Ajustar el tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas.getCanvasPane());
        frame.setVisible(true);
    
        // Dibujar solo el tablero inicial aquí
        startingTablero.drawBoard(glue);
        endingTablero.drawBoard(endingGlue);
    }
    
    /**
     * Inicializa un tablero vacío con el carácter '.'.
     * 
     * @param board Tablero a inicializar.
     */
    private void initializeBoard(char[][] board) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = '.';
            }
        }
    }
    
    /**
     * Constructor que inicializa el Puzzle con un tablero final proporcionado.
     * 
     * @param endingBoard Tablero final a utilizar en el Puzzle.
     */
    public Puzzle(char[][] endingBoard) {       
        // Llama al primer constructor con las dimensiones del tablero final
        this(endingBoard.length, endingBoard[0].length);
        
        // Reemplaza el tablero final generado por el proporcionado
        this.endingBoard = endingBoard;
        
        // Vuelve a configurar el tablero final en el objeto Rectangle
        endingTablero.setBoard(endingBoard);
            
        this.endingGlue = new Glue(endingBoard, endingTablero);
    
        endingTablero.drawBoard(endingGlue); // Solo dibuja el tablero final
        
    }
    
    // Constructor 3: Recibe el tablero inicial y final
    /**
     * Constructor que inicializa el Puzzle con tableros inicial y final proporcionados.
     * 
     * @param startingBoard Tablero inicial.
     * @param endingBoard   Tablero final.
     */
    public Puzzle(char[][] startingBoard, char[][] endingBoard) {
        // Llamamos al segundo constructor para inicializar el tablero final
        this(endingBoard);
        
        // Inicializamos el tablero inicial
        this.startingBoard = startingBoard;
    
        // Inicializar el tablero inicial con las dimensiones y color base
        this.startingTablero = new Rectangle(width * 30, height * 30, 10, 20, "brown", true, false);
    
        // Configura las baldosas en el tablero inicial según el array startingBoard
        startingTablero.setBoard(startingBoard);
        
        // Inicializacion de glue
        this.glue = new Glue(startingBoard, startingTablero);
    
        // Dibujamos el tablero inicial
        startingTablero.drawBoard(glue);
    
        // Asegúrate de que no se dibuje más de una vez
        Canvas canvas = Canvas.getCanvas();
        canvas.wait(10);
    }
    
    /** 
     * Elimina baldosas
     * @param fila Escoge la fila de la baldosa que quiere ser borrada
     * @param columna Escoge la columna de la baldosa que quiere ser borrada
     */
    public void eliminarBaldosa(int fila, int columna) {
        
        // Verificar si la posición es válida
        if (fila >= 0 && fila < startingBoard.length && columna >= 0 && columna < startingBoard[0].length) {
            // Verificar si la baldosa tiene pegante
            if (glue.isGlued(fila, columna)) {
                System.out.println("No se puede eliminar la baldosa porque tiene pegante.");
                return; // Salir del método si la baldosa tiene pegante
            }
    
            // Proceder a eliminar la baldosa
            startingBoard[fila][columna] = '.'; // Representar baldosa eliminada con un punto (o cualquier otro símbolo)
            startingTablero.setBoard(startingBoard); // Actualizar el tablero en el objeto Rectangle
    
            // Redibujar el tablero
            startingTablero.drawBoard(glue); 
    
            Canvas canvas = Canvas.getCanvas(); // Obtener canvas
            canvas.getCanvasPane().repaint(); // Forzar redibujado
        } else {
            System.out.println("Posición inválida para eliminar");
        }
    }
    
    /**
     * Reubica una baldosa desde una posición actual a una nueva posición.
     * 
     * @param filaActual     Fila de la baldosa actual.
     * @param columnaActual  Columna de la baldosa actual.
     * @param nuevaFila      Nueva fila para la baldosa.
     * @param nuevaColumna   Nueva columna para la baldosa.
     */
    public void reubicarBaldosa(int filaActual, int columnaActual, int nuevaFila, int nuevaColumna) {
        System.out.println("Intentando reubicar baldosa desde (" + filaActual + ", " + columnaActual + ") a (" + nuevaFila + ", " + nuevaColumna + ")");
    
        // Verificar límites
        if (filaActual >= 0 && filaActual < startingBoard.length && columnaActual >= 0 && columnaActual < startingBoard[0].length
                && nuevaFila >= 0 && nuevaFila < startingBoard.length && nuevaColumna >= 0 && nuevaColumna < startingBoard[0].length) {
            if (startingBoard[nuevaFila][nuevaColumna] != '.') {
                System.out.println("No se puede reubicar la baldosa. La posición (" + nuevaFila + ", " + nuevaColumna + ") ya está ocupada.");
                return; // No hacer nada si la posición está ocupada
            }
            char baldosa = startingBoard[filaActual][columnaActual];
            if (baldosa != '.') { // Verificar que la baldosa no esté vacía
                System.out.println("Antes de mover la baldosa:");
                for (char[] fila : startingBoard) {
                    System.out.println(Arrays.toString(fila));
                }
                // Almacenar las posiciones de los pegantes adyacentes
                List<int[]> posicionesPegantes = new ArrayList<>();
                agregarPegantesAdyacentes(filaActual, columnaActual, posicionesPegantes);
    
                // **NUEVO**: Eliminar pegante de la posición original y de adyacentes
                glue.removeGlue(filaActual, columnaActual, glue.getGlueSize(), glue.getTileSize());
                for (int[] pos : posicionesPegantes) {
                    glue.removeGlue(pos[0], pos[1], glue.getGlueSize(), glue.getTileSize());
                }
    
                // Mover la baldosa a la nueva posición
                startingBoard[filaActual][columnaActual] = '.'; // Dejar vacía la posición actual
                startingBoard[nuevaFila][nuevaColumna] = baldosa; // Mover la baldosa a la nueva posición
    
                // Mover pegamentos, sin crear nuevos
                if (!posicionesPegantes.isEmpty()) {
                    for (int[] pos : posicionesPegantes) {
                        int peganteFila = pos[0];
                        int peganteColumna = pos[1];
    
                        // Calcular nuevas posiciones para los pegantes
                        int nuevaPosFila = nuevaFila + (peganteFila - filaActual);
                        int nuevaPosColumna = nuevaColumna + (peganteColumna - columnaActual);
    
                        // Asegurarse de que la nueva posición esté dentro de los límites
                        if (nuevaPosFila >= 0 && nuevaPosFila < startingBoard.length && 
                            nuevaPosColumna >= 0 && nuevaPosColumna < startingBoard[0].length) {
                            
                            // Mover baldosa pegada y mantener su color original
                            startingBoard[nuevaPosFila][nuevaPosColumna] = startingBoard[peganteFila][peganteColumna];
                            startingBoard[peganteFila][peganteColumna] = '.'; // Vaciar la posición original
                            
                            // Volver a agregar el pegante en las nuevas posiciones sin afectar adyacentes
                            glue.addGlueWithoutAdjacent(nuevaPosFila, nuevaPosColumna);
                        }
                    }
    
                    // También añadir pegante en la nueva posición de la baldosa original
                    glue.addGlueWithoutAdjacent(nuevaFila, nuevaColumna);
                }
    
                // Actualizar el tablero en el objeto Rectangle
                startingTablero.setBoard(startingBoard);
                System.out.println("Baldosa reubicada a (" + nuevaFila + ", " + nuevaColumna + ")");
    
                // Redibujar el tablero
                System.out.println("Estado de startingBoard después de mover la baldosa:");
                for (char[] fila : startingBoard) {
                    System.out.println(Arrays.toString(fila));
                }

                startingTablero.drawBoard(glue);
                Canvas canvas = Canvas.getCanvas(); // Obtener Canvas
                canvas.getCanvasPane().repaint(); // Forzar redibujado
            } else {
                System.out.println("No hay baldosa para mover en (" + filaActual + ", " + columnaActual + ")");
            }
        } else {
            System.out.println("Posición inválida para reubicar");
        }
    }

    /**
     * Agrega las posiciones de los pegantes adyacentes a la lista.
     * @param fila Escoge la fila de la baldosa actual
     * @param columna Escoge la columna de la baldosa actual
     * @param posicionesPegantes Lista donde se agregarán las posiciones de los pegantes
     */
    private void agregarPegantesAdyacentes(int fila, int columna, List<int[]> posicionesPegantes) {
        // Verificar arriba
        if (fila - 1 >= 0 && glue.isGlued(fila - 1, columna)) { // Arriba
            posicionesPegantes.add(new int[]{fila - 1, columna});
        }
        // Verificar abajo
        if (fila + 1 < startingBoard.length && glue.isGlued(fila + 1, columna)) { // Abajo
            posicionesPegantes.add(new int[]{fila + 1, columna});
        }
        // Verificar izquierda
        if (columna - 1 >= 0 && glue.isGlued(fila, columna - 1)) { // Izquierda
            posicionesPegantes.add(new int[]{fila, columna - 1});
        }
        // Verificar derecha
        if (columna + 1 < startingBoard[0].length && glue.isGlued(fila, columna + 1)) { // Derecha
            posicionesPegantes.add(new int[]{fila, columna + 1});
        }
    }
    
    /**
     * Agrega una baldosa de un color específico en la posición dada.
     * 
     * @param fila    Fila de la nueva baldosa.
     * @param columna Columna de la nueva baldosa.
     * @param color   Color de la baldosa a agregar.
     */
    public void agregarBaldosa(int fila, int columna, char color) {
        if (fila >= 0 && fila < startingBoard.length && columna >= 0 && columna < startingBoard[0].length) {
            if (startingBoard[fila][columna] != '.') {
                System.out.println("No se puede agregar la baldosa. La posición (" + fila + ", " + columna + ") ya está ocupada.");
                return; // No hacer nada si la posición está ocupada
            }
            switch (color) {
                case 'r':
                case 'g':
                case 'b':
                case 'y':
                case 'w':
                    startingBoard[fila][columna] = color; // Agregar la baldosa con el color dado
                    startingTablero.setBoard(startingBoard); // Actualizar el tablero en el objeto Rectangle
                    
                    // Redibujar el tablero
                    startingTablero.drawBoard(glue); 
    
                    // Redibujar el pegamento en las posiciones donde fue aplicado previamente
                    for (int i = 0; i < startingBoard.length; i++) {
                        for (int j = 0; j < startingBoard[0].length; j++) {
                            if (glue.isGlued(i, j)) { // Verificar si hay pegamento en la posición
                                glue.drawGlue(i, j, glue.getGlueSize(), glue.getTileSize()); // Solo dibujar el pegamento existente
                            }
                        }
                    }
    
                    Canvas canvas = Canvas.getCanvas(); // Obtener Canvas
                    canvas.getCanvasPane().repaint(); // Forzar redibujado
                    break;
                default:
                    System.out.println("Color inválido. Use 'r' para rojo, 'g' para verde, 'b' para azul, 'y' para amarillo.");
                    break;
            }
        } else {
            System.out.println("Posición inválida para agregar baldosa");
        }
    }
    
    /**
     * Hace visible el tablero con las baldosas
     */
    public void makeVisibleTable() {
        if (startingTablero != null && endingTablero != null) {
            startingTablero.makeVisibleTable(glue);
            endingTablero.makeVisibleTable(glue);
            Canvas canvas = Canvas.getCanvas(); // Obtener Canvas
            canvas.getCanvasPane().repaint(); // Forzar redibujado
            tableVisible = true; // Tablero visible
            System.out.println("Los tableros ahora son visibles.");
        } else {
            System.out.println("Los tableros no estan inicializados.");
        }
    }

    /**
     * Hace invisible el tablero con las baldosas
     */
    public void makeInvisibleTable() {
        if (startingTablero != null && endingTablero != null) {
            startingTablero.makeInvisibleTable();
            endingTablero.makeInvisibleTable();
            tableVisible = false; // Tablero invisible;
            System.out.println("Los tableros ahora son invisibles.");
        } else {
            System.out.println("Los tableros no estan inicializados.");
        }
    }
    
    /**
     * Añade pegamento en una posición específica.
     * 
     * @param fila    Fila donde se agregará el pegamento.
     * @param columna Columna donde se agregará el pegamento.
     */
    public void addGlue(int fila, int columna){
        glue.adGlue(fila, columna);
        Canvas canvas = Canvas.getCanvas(); // Obtener Canvas
        canvas.getCanvasPane().repaint(); // Forzar redibujado
    }
    
    /**
     * Elimina el pegamento de una posición específica.
     * 
     * @param fila    Fila de la que se eliminará el pegamento.
     * @param columna Columna de la que se eliminará el pegamento.
     */
    public void removeGlue(int fila, int columna){
        glue.adRemoveGlue(fila, columna);
        Canvas canvas = Canvas.getCanvas(); // Obtener Canvas
        canvas.getCanvasPane().repaint(); // Forzar redibujado
    }
    
    /**
     * Intercambia los contenidos y posiciones de los tableros inicial y final.
     */
    public void exchange() {
        // Intercambiar referencias de los tableros
        Rectangle tempTablero = startingTablero; // Variable temporal
        startingTablero = endingTablero;
        endingTablero = tempTablero;
    
        // Intercambiar las posiciones de los tableros
        int tempX = startingTablero.getXPosition();
        int tempY = startingTablero.getYPosition();
        startingTablero.setPosition(endingTablero.getXPosition(), endingTablero.getYPosition());
        endingTablero.setPosition(tempX, tempY);
    
        // Intercambiar los contenidos de los tableros
        char[][] tempBoard = startingBoard;
        startingBoard = endingBoard;
        endingBoard = tempBoard;
    
        // Clonar los estados del pegamento
        boolean[][] glueStateEnding = glue.cloneGlueState(); // Estado antes de intercambiar
    
        // Actualizar el estado del pegamento
        glue.updateGlueState(glueStateEnding); // Aquí puedes decidir qué estado asignar a cada tablero
    
        // Actualizar los tableros con los nuevos contenidos
        startingTablero.setBoard(startingBoard);
        endingTablero.setBoard(endingBoard);
    
        // Redibujar los tableros
        Canvas canvas = Canvas.getCanvas();
        canvas.setVisible(true);
        startingTablero.drawBoard(glue);
        endingTablero.drawBoard(glue);
        canvas.getCanvasPane().repaint(); // Forzar redibujado
    }

    @SuppressWarnings("unused")
    private void printGlueState(boolean[][] glueState) {
        for (boolean[] row : glueState) {
            System.out.println(Arrays.toString(row));
        }
    }

    /**
     * Inclina el tablero en la dirección dada.
     * 
     * @param direction Dirección en la que se inclinará el tablero.
     */
    public void tiltBoard(String direction) {
        // Establecer el tablero actual en el objeto Tilt
        tilt.setBoard(startingBoard);
        
        // Crear una copia del estado actual del pegamento para comparaciones posteriores
        boolean[][] originalGlue = glue.getGlueState(startingBoard);
    
        // Inclinar el tablero y mover las baldosas
        tilt.tilt(direction);
    
        // Crear una nueva matriz temporal para los pegamentos movidos
        boolean[][] newGlueApplied = new boolean[startingBoard.length][startingBoard[0].length];
    
        // Recorrer el tablero para mover el pegamento junto con las baldosas
        for (int fila = 0; fila < startingBoard.length; fila++) {
            for (int columna = 0; columna < startingBoard[0].length; columna++) {
                if (startingBoard[fila][columna] != '.') { // Si hay una baldosa en la posición actual
                    
                    // Si hay pegamento en la posición original
                    if (originalGlue[fila][columna]) {
                        int nuevaFila = fila;
                        int nuevaColumna = columna;
    
                        // Dependiendo de la dirección, calcular la nueva posición
                        switch (direction.toLowerCase()) {
                            case "arriba":
                                nuevaFila = tilt.getNewRow(fila, columna, "arriba");
                                break;
                            case "abajo":
                                nuevaFila = tilt.getNewRow(fila, columna, "abajo");
                                break;
                            case "izquierda":
                                nuevaColumna = tilt.getNewCol(fila, columna, "izquierda");
                                break;
                            case "derecha":
                                nuevaColumna = tilt.getNewCol(fila, columna, "derecha");
                                break;
                        }
    
                        // Si la baldosa no se mueve porque otra la bloquea
                        if (startingBoard[nuevaFila][nuevaColumna] != '.' && nuevaFila != fila && nuevaColumna != columna) {
                            // Mantener el pegamento en su posición original
                            newGlueApplied[fila][columna] = true;
                        } else {
                            // Mover el pegamento de la posición original a la nueva
                            newGlueApplied[nuevaFila][nuevaColumna] = true;
                        }
                    }
                }
            }
        }
    
        // Manejar casos donde dos baldosas con pegamento se mueven a la misma posición
        for (int fila = 0; fila < newGlueApplied.length; fila++) {
            for (int columna = 0; columna < newGlueApplied[0].length; columna++) {
                if (newGlueApplied[fila][columna]) {
                    // Si la baldosa en esta posición tenía pegamento antes y otra baldosa se movió aquí
                    if (originalGlue[fila][columna]) {
                        newGlueApplied[fila][columna] = true; // Mantener el pegamento
                    }
                }
            }
        }
    
        // Actualizar el estado de glueApplied con los nuevos movimientos
        glue.updateGlueState(newGlueApplied);
    
        // Redibujar el tablero y los pegamentos
        startingTablero.setBoard(startingBoard);
        startingTablero.drawBoard(glue);
    
        // Forzar el repintado del canvas para reflejar los cambios
        Canvas canvas = Canvas.getCanvas();
        canvas.getCanvasPane().repaint(); 
        canvas.wait(10);
    
        System.out.println("Tablero inclinado hacia " + direction); 
        System.out.println(Arrays.deepToString(startingBoard));
    }


    /**
     * Finish the game and close the program
     */
    public void finish() {
        System.out.println("Finalizando el juego...");
        System.exit(0); 
    }
    
    /**
     * Obtiene el número de baldosas que están mal colocadas.
     * 
     * @return Número de baldosas fuera de lugar.
     */
    public int misplacedTiles() {
        if (endingBoard == null) {
            return 0; // O lanzar una excepción
        }
        int count = 0;

        for (int i = 0; i < startingBoard.length; i++) {
            for (int j = 0; j < startingBoard[i].length; j++) {
                // Contar las baldosas que no coinciden con el tablero final
                if (startingBoard[i][j] != '.' && startingBoard[i][j] != endingBoard[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Devuelve las posiciones de las baldosas que están fijadas en su lugar.
     * 
     * @return Array de posiciones de las baldosas fijas.
     */
    public int[][] fixedTiles() {
        List<int[]> fixedTilesList = new ArrayList<>();
        makeVisibleTable();
        
        // Sincronizar el tablero de Tilt con el de Puzzle
        tilt.setBoard(startingBoard);  
        
        for (int i = 0; i < startingBoard.length; i++) {
            for (int j = 0; j < startingBoard[i].length; j++) {
                if (startingBoard[i][j] != '.' && !tilt.canMoveTile(i, j)) {
                    fixedTilesList.add(new int[]{i, j});
    
                    // Hacer que la baldosa parpadee si el tablero está visible
                    System.out.println(tableVisible);
                    if (tableVisible) {
                        System.out.println("Parpadeando baldosa en fila: " + i + ", columna: " + j);
                        parpadearBaldosa(i,j);
                    }
                }
            }
        }
        System.out.println("puzzle");
        System.out.println(Arrays.deepToString(startingBoard));
        return fixedTilesList.toArray(new int[fixedTilesList.size()][]);
    }
    
    /**
     * Hace parpadear una baldosa en una posición específica.
     * 
     * @param fila    Fila de la baldosa.
     * @param columna Columna de la baldosa.
     */
    public void parpadearBaldosa(int fila, int columna) {
        // Crear un hilo para manejar el parpadeo de la baldosa
        new Thread(() -> {
            char colorOriginal = startingBoard[fila][columna];
            if (colorOriginal != '.') {
                for (int k = 0; k < 21; k++) {  // Número de veces que queremos que parpadee
                    eliminarBaldosa(fila, columna);
                    agregarBaldosa(fila, columna, 'w'); // 'w' para blanco
    
                    // Pausa para simular el parpadeo
                    try {
                        Thread.sleep(50); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
    
                    eliminarBaldosa(fila, columna);
                    agregarBaldosa(fila, columna, colorOriginal);
    
                    // Pausa antes de la siguiente iteración del parpadeo
                    try {
                        Thread.sleep(50); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();  // Iniciar el hilo
    }
    
    /**
     * Inclina el tablero según la profundidad establecida.
     */
    public void tilt() {
        dfsTilt(startingBoard, depth);
    }
    
    /**
     * Realiza un movimiento de inclinación en el tablero.
     * 
     * @param board     Tablero a inclinar.
     * @param direction Dirección en la que se inclinará el tablero.
     */
    private void tilt(char[][] board, String direction) {
        // Establecer el tablero proporcionado en el objeto Tilt
        tilt.setBoard(board);
    
        // Inclinar el tablero y mover las baldosas
        tilt.tilt(direction);
    
        // Redibujar el tablero 
        startingTablero.setBoard(board);
        startingTablero.drawBoard(glue);  
    
        // Forzar el repintado del canvas para reflejar los cambios
        Canvas canvas = Canvas.getCanvas();
        canvas.getCanvasPane().repaint(); 
        canvas.wait(10);
    }
  
    /**
     * Método auxiliar para contar las baldosas mal colocadas en un tablero dado.
     * 
     * @param board Tablero a evaluar.
     * @return Número de baldosas fuera de lugar.
     */
    @SuppressWarnings("unused")
    private int misplacedTiles(char[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.' && board[i][j] != endingBoard[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Ejecuta un algoritmo DFS para inclinar el tablero.
     * 
     * @param board Tablero a evaluar.
     * @param depth Profundidad máxima de la búsqueda.
     * @return true si se encuentra una solución, false en caso contrario.
     */
    protected boolean dfsTilt(char[][] board, int depth) {
        // Condición de parada: si el tablero es igual al tablero final
        if (Arrays.deepEquals(board, endingBoard)) {
            System.out.println("Solución Encontrada");
            System.out.println(Arrays.deepToString(board)); // Mostrar el tablero final
            
            // Actualizar startingBoard con el tablero final
            for (int i = 0; i < board.length; i++) {
                System.arraycopy(board[i], 0, startingBoard[i], 0, board[i].length);
            }
            return true;  // Solución encontrada
        }
    
        // Si el límite de profundidad se alcanza, detener la búsqueda
        if (depth == 0) {
            return false;
        }
    
        // Conjunto para guardar configuraciones ya visitadas
        Set<String> visitedStates = new HashSet<>();
        
        // Convertir el tablero actual en una cadena para almacenar en el conjunto
        String currentState = boardToString(board);
        if (visitedStates.contains(currentState)) {
            return false; 
        }
        
        // Marcar el estado actual como visitado
        visitedStates.add(currentState);
    
        // Intentar los cuatro posibles movimientos
        String[] directions = {"arriba", "abajo", "izquierda", "derecha"};
        
        for (String direction : directions) {
            // Crear una copia del tablero actual para inclinar
            char[][] newBoard = copyBoard(board);
            
            // Inclinar el nuevo tablero
            tilt(newBoard, direction);
            
            // Volver a llamar a DFS con el tablero inclinado y profundizar
            if (dfsTilt(newBoard, depth - 1)) {
                System.out.println("Tablero final encontrado después de inclinar hacia " + direction);
                return true;  // Solución encontrada en esta rama recursiva
            }
        }
        
        return false;  // No se encontró solución en esta rama
    }

    /** Convierte el tablero a una cadena para facilitar su comparación en DFS.
     * 
     * @param board Tablero a convertir.
     * @return Cadena que representa el estado del tablero.
     */
    private String boardToString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }
    
    /**
     * Crea una copia del tablero dado.
     * 
     * @param board Tablero a copiar.
     * @return Copia del tablero.
     */
    private char[][] copyBoard(char[][] board) {
        char[][] newBoard = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
        }
        return newBoard;
    }
    
    /**
     * Obtiene la profundidad de búsqueda.
     * 
     * @return Profundidad actual de búsqueda.
     */
    public int getDepth() {
        return depth;
    }
    
    /**
     * Obtiene el tablero inicial.
     * 
     * @return Tablero inicial.
     */
    public char[][] getStartingBoard() {
        return startingBoard;
    }
    
    /**
     * Obtiene el tablero final.
     * 
     * @return Tablero final.
     */
    public char[][] getEndingBoard() {
        return endingBoard;
    } 
    
    /**
     * Busca una solución para el puzzle.
     * 
     * @return true si se encuentra una solución, false en caso contrario.
     */
    public boolean findSolution() {
        return dfsTilt(startingBoard, depth);
    }
    
    /**
     * Verifica si el tablero es visible.
     * 
     * @return true si el tablero es visible, false en caso contrario.
     */
    public boolean isTableVisible() {
        return tableVisible;
    }
}