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
    private int width;
    private int height;
    private Rectangle startingTablero;
    private Rectangle endingTablero;
    private char[][] startingBoard;
    private char[][] endingBoard;
    private Glue glue;
    private Tilt tilt;
    private Hole hole;   
    
    public Puzzle(){
        this.startingBoard = startingBoard;
        this.endingBoard = endingBoard;
        this.startingTablero = new Rectangle(); // Inicializar si es necesario
        this.endingTablero = new Rectangle();   // Inicializar si es necesario
        generateBoards();
        this.glue = new Glue(startingBoard, startingTablero);// Inicializar el objeto Glue con el tablero inicial
        this.hole = new Hole(startingTablero); // Cambiado para inicializar con el tablero
        this.tilt = new Tilt(startingBoard, hole);  // Pasar el agujero a Tilt
    }
    
    public Puzzle(int h, int w) {
        this.height = h;
        this.width = w;
        this.endingBoard = new char[height][width];
        this.startingBoard = new char[height][width]; // Inicializamos startingBoard vacío
        this.glue = new Glue(startingBoard, startingTablero);// Inicializar el objeto Glue con el tablero inicial
        this.hole = new Hole(startingTablero); // Cambiado para inicializar con el tablero
        this.tilt = new Tilt(startingBoard, hole);  // Pasar el agujero a Tilt
        
        // Ajustamos las posiciones para que los tableros no se superpongan
        this.startingTablero = new Rectangle(width * 30, height * 30, 10, 20, "brown", true, false); // Tablero inicial
        this.endingTablero = new Rectangle(width * 30, height * 30, (width * 30) + 40, 20, "brown", true, false); // Tablero final
    
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
        endingTablero.drawBoard(glue);
    }

    private void initializeBoard(char[][] board) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = '.';
            }
        }
    }
    
    public Puzzle(char[][] endingBoard) {
        // Llama al primer constructor con las dimensiones del tablero final
        this(endingBoard.length, endingBoard[0].length);
    
        // Reemplaza el tablero final generado por el proporcionado
        this.endingBoard = endingBoard;
    
        // Vuelve a configurar el tablero final en el objeto Rectangle
        endingTablero.setBoard(endingBoard);

        endingTablero.drawBoard(glue); // Solo dibuja el tablero final
    }
    
    // Constructor 3: Recibe el tablero inicial y final
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

    // Inicializa el tablero final (endingBoard) con baldosas de colores
    private void initializeEndingBoard(char[][] board) {
        Canvas canvas = Canvas.getCanvas();
    
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Configura el color basado en el valor de la matriz
                switch (board[i][j]) {
                    case 'r':
                        canvas.setForegroundColor("red");
                        break;
                    case 'g':
                        canvas.setForegroundColor("green");
                        break;
                    case 'b':
                        canvas.setForegroundColor("blue");
                        break;
                    case 'y':
                        canvas.setForegroundColor("yellow");
                        break;
                    case '.':
                        canvas.setForegroundColor("brown"); // Baldosa vacía o base
                        break;
                    default:
                        canvas.setForegroundColor("gray"); // Para caracteres no especificados
                        break;
                }
                // Dibuja cada baldosa en el tablero final
                canvas.drawRectangle(
                    (width * 30) + j * 30, // Posición en x
                    20 + i * 30,           // Posición en y
                    28,                    // Ancho de la baldosa
                    28                     // Altura de la baldosa
                );
            }
        }
        canvas.wait(10); // Opcional para un pequeño retraso en la visualización
    }
    
    private void generateBoards() {
        Scanner scanner = new Scanner(System.in);

        // Leer dimensiones del tablero
        System.out.println("Ingrese las dimensiones del tablero (h w): ");
        int h = scanner.nextInt();
        int w = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        // Crear los tableros inicial y final con las dimensiones especificadas
        startingTablero = new Rectangle(w * 30, h * 30, 10, 20, "brown", true, false);
        endingTablero = new Rectangle(w * 30, h * 30, (w * 30) + 40, 20, "blue", true, false); 

        // Leer el estado inicial del tablero
        startingBoard = new char[h][w];
        System.out.println("Ingrese la configuración inicial del tablero: ");
        for (int i = 0; i < h; i++) {
            String line = scanner.nextLine();
            if (line.length() != w) {
                System.out.println("Error: La línea ingresada tiene una longitud incorrecta.");
                return;
            }
            startingBoard[i] = line.toCharArray();
        }

        // Leer el estado final del tablero
        endingBoard = new char[h][w];
        System.out.println("Ingrese la configuración final del tablero: ");
        for (int i = 0; i < h; i++) {
            String line = scanner.nextLine();
            if (line.length() != w) {
                System.out.println("Error: La línea ingresada tiene una longitud incorrecta.");
                return;
            }
            endingBoard[i] = line.toCharArray();
        }

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

        // Dibujar ambos tableros (inicial y final)
        startingTablero.drawBoard(glue);
        endingTablero.drawBoard(glue);

        scanner.close();
    }    
    
    /** 
     * Elimina baldosas
     * @param fila Escoge la fila de la baldosa que quiere ser borrada
     * @param columna Escoge la columna de la baldosa que quiere ser borrada
     */
    public void eliminarBaldosa(int fila, int columna) {
        System.out.println("Intentando eliminar baldosa en (" + fila + ", " + columna + ")");
        
        // Verificar si la posición es válida
        if (fila >= 0 && fila < startingBoard.length && columna >= 0 && columna < startingBoard[0].length) {
            // Verificar si la baldosa tiene pegante
            if (glue.isGlued(fila, columna)) {
                System.out.println("No se puede eliminar la baldosa en (" + fila + ", " + columna + ") porque tiene pegante.");
                return; // Salir del método si la baldosa tiene pegante
            }
    
            // Proceder a eliminar la baldosa
            startingBoard[fila][columna] = '.'; // Representar baldosa eliminada con un punto (o cualquier otro símbolo)
            startingTablero.setBoard(startingBoard); // Actualizar el tablero en el objeto Rectangle
            System.out.println("Baldosa eliminada en (" + fila + ", " + columna + ")");
    
            // Redibujar el tablero
            startingTablero.drawBoard(glue); 
    
            Canvas canvas = Canvas.getCanvas(); // Obtener canvas
            canvas.getCanvasPane().repaint(); // Forzar redibujado
        } else {
            System.out.println("Posición inválida para eliminar");
        }
    }
    
    public void reubicarBaldosa(int filaActual, int columnaActual, int nuevaFila, int nuevaColumna) {
        System.out.println("Intentando reubicar baldosa desde (" + filaActual + ", " + columnaActual + ") a (" + nuevaFila + ", " + nuevaColumna + ")");
    
        // Verificar límites
        if (filaActual >= 0 && filaActual < startingBoard.length && columnaActual >= 0 && columnaActual < startingBoard[0].length
                && nuevaFila >= 0 && nuevaFila < startingBoard.length && nuevaColumna >= 0 && nuevaColumna < startingBoard[0].length) {
    
            char baldosa = startingBoard[filaActual][columnaActual];
            if (baldosa != '.') { // Verificar que la baldosa no esté vacía
                
                // Almacenar las posiciones de los pegantes adyacentes
                List<int[]> posicionesPegantes = new ArrayList<>();
                agregarPegantesAdyacentes(filaActual, columnaActual, posicionesPegantes);
    
                // Eliminar pegantes de la posición original
                glue.removeGlue(filaActual, columnaActual, glue.getGlueSize(), glue.getTileSize());
    
                // Eliminar pegantes de las posiciones adyacentes
                for (int[] pos : posicionesPegantes) {
                    glue.removeGlue(pos[0], pos[1], glue.getGlueSize(), glue.getTileSize());
                }
    
                // Mover la baldosa a la nueva posición
                startingBoard[filaActual][columnaActual] = '.'; // Dejar vacía la posición actual
                startingBoard[nuevaFila][nuevaColumna] = baldosa; // Mover la baldosa a la nueva posición
    
                // Comprobar si la baldosa original tenía pegamento
                if (!posicionesPegantes.isEmpty()) {
                    // Si había pegamento, mover las baldosas adyacentes pegadas
                    for (int[] pos : posicionesPegantes) {
                        int peganteFila = pos[0];
                        int peganteColumna = pos[1];
    
                        // Calcular nuevas posiciones para los pegantes
                        int nuevaPosFila = nuevaFila + (peganteFila - filaActual);
                        int nuevaPosColumna = nuevaColumna + (peganteColumna - columnaActual);
    
                        // Asegurarse de que la nueva posición esté dentro de los límites
                        if (nuevaPosFila >= 0 && nuevaPosFila < startingBoard.length && 
                            nuevaPosColumna >= 0 && nuevaPosColumna < startingBoard[0].length) {
                            startingBoard[nuevaPosFila][nuevaPosColumna] = startingBoard[peganteFila][peganteColumna]; // Mover la baldosa pegante
                            startingBoard[peganteFila][peganteColumna] = '.'; // Dejar vacía la posición original
                            glue.adGlue(nuevaPosFila, nuevaPosColumna); // Agregar pegamento a la nueva posición
                        }
                    }
    
                    // También añadir pegante en la nueva posición de la baldosa original
                    glue.adGlue(nuevaFila, nuevaColumna);
                }
    
                // Actualizar el tablero en el objeto Rectangle
                startingTablero.setBoard(startingBoard);
                System.out.println("Baldosa reubicada a (" + nuevaFila + ", " + nuevaColumna + ")");
    
                // Redibujar el tablero
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

    public void agregarBaldosa(int fila, int columna, char color) {
        System.out.println("Intentando agregar baldosa en (" + fila + ", " + columna + ") con color '" + color + "'");
        if (fila >= 0 && fila < startingBoard.length && columna >= 0 && columna < startingBoard[0].length) {
            switch (color) {
                case 'r':
                case 'g':
                case 'b':
                case 'y':
                    startingBoard[fila][columna] = color; // Agregar la baldosa con el color dado
                    startingTablero.setBoard(startingBoard); // Actualizar el tablero en el objeto Rectangle
                    System.out.println("Baldosa agregada en (" + fila + ", " + columna + ") con color '" + color + "'");
                    
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
            System.out.println("Los tableros ahora son invisibles.");
        } else {
            System.out.println("Los tableros no estan inicializados.");
        }
    }
    
    public void addGlue(int fila, int columna){
        glue.adGlue(fila, columna);
        Canvas canvas = Canvas.getCanvas(); // Obtener Canvas
        canvas.getCanvasPane().repaint(); // Forzar redibujado
    }
    
    public void removeGlue(int fila, int columna){
        glue.adRemoveGlue(fila, columna);
        Canvas canvas = Canvas.getCanvas(); // Obtener Canvas
        canvas.getCanvasPane().repaint(); // Forzar redibujado
    }
    
    public void exchange(){
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
    
    public void makeHole(int row, int column) {
        hole.makeHole(row, column);  // Crear el agujero
        startingTablero.setBoard(startingBoard); // Actualizar el tablero en el objeto Rectangle
        startingTablero.drawBoard(glue); // Redibujar el tablero completo
        hole.drawHole(); // Dibujar el agujero
        Canvas canvas = Canvas.getCanvas(); // Obtener Canvas
        canvas.getCanvasPane().repaint(); // Forzar redibujado
    }

    
    /**
     * 
     */
    public void tiltBoard(String direction) {
        tilt.tilt(direction); 
        startingTablero.setBoard(startingBoard); // Actualizar el tablero
        startingTablero.drawBoard(glue); // Redibujar el tablero
        Canvas canvas = Canvas.getCanvas(); 
        canvas.getCanvasPane().repaint(); // Forzar redibujado
    }

    /**
     * Finish the game and close the program
     */
    public void finish() {
        System.out.println("Finalizando el juego...");
        System.exit(0); 
    }
}

