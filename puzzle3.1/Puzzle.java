import java.util.Scanner;
import javax.swing.*;
/**
 * Clase que permite simular una situacion inspirada en el "Problem F" de la maraton
 * de programacion internacional 2023 "Tilting Tiles"
 * @author: Camilo Fernandez
 * @author: Andrés Sepúlveda
 * @version: 1.0
 */

public class Puzzle {
    private Rectangle startingTablero;
    private Rectangle endingTablero;
    private char[][] startingBoard;
    private char[][] endingBoard;
    private Glue glue;
    
    public Puzzle(){
        this.startingBoard = startingBoard;
        this.endingBoard = endingBoard;
        this.startingTablero = new Rectangle(); // Inicializar si es necesario
        this.endingTablero = new Rectangle();   // Inicializar si es necesario
        generateBoards();
        this.glue = new Glue(startingBoard, startingTablero);    // Inicializar el objeto Glue con el tablero inicial
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
        startingTablero.drawBoard();
        endingTablero.drawBoard();

        scanner.close();
    }    
    
    /** 
     * Elimina baldosas
     * @param Escoge la fila de la baldosa que quiera ser borrada
     * @param Escoge la columna de la baldosa que quiera ser borrada
     */
    public void eliminarBaldosa(int fila, int columna) {
        System.out.println("Intentando eliminar baldosa en (" + fila + ", " + columna + ")");
        if (fila >= 0 && fila < startingBoard.length && columna >= 0 && columna < startingBoard[0].length) {
            startingBoard[fila][columna] = '.'; // Representar baldosa eliminada con un punto (o cualquier otro símbolo)
            startingTablero.setBoard(startingBoard); // Actualizar el tablero en el objeto Rectangle
            System.out.println("Baldosa eliminada en (" + fila + ", " + columna + ")");
            startingTablero.drawBoard(); // Redibujar el tablero
            
            Canvas canvas = Canvas.getCanvas(); // Obtener canvas
            canvas.getCanvasPane().repaint(); // Forzar redibujado
        } else {
            System.out.println("Posición inválida para eliminar");
        }
    }
    
       /** 
     * Al elegir una baldosa la reubica en el lugar especificado de la matriz dada
     * @param Escoge la fila de la baldosa actual
     * @param Escoge la columna de la baldosa actual
     * @param Escoge la fila de la baldosa a la cual se quiera reubicar
     * @param Escoge la columna de la baldosa a la cual se quiera reubicar
     */
    public void reubicarBaldosa(int filaActual, int columnaActual, int nuevaFila, int nuevaColumna) {
        System.out.println("Intentando reubicar baldosa desde (" + filaActual + ", " + columnaActual + ") a (" + nuevaFila + ", " + nuevaColumna + ")");
            if (filaActual >= 0 && filaActual < startingBoard.length && columnaActual >= 0 && columnaActual < startingBoard[0].length
                && nuevaFila >= 0 && nuevaFila < startingBoard.length && nuevaColumna >= 0 && nuevaColumna < startingBoard[0].length) {
                char baldosa = startingBoard[filaActual][columnaActual];
                if (baldosa != '.') { // Verificar que la baldosa no esté vacía
                    startingBoard[filaActual][columnaActual] = '.'; // Dejar vacía la posición actual
                    startingBoard[nuevaFila][nuevaColumna] = baldosa; // Mover la baldosa a la nueva posición
                    startingTablero.setBoard(startingBoard); // Actualizar el tablero en el objeto Rectangle
                    System.out.println("Baldosa reubicada a (" + nuevaFila + ", " + nuevaColumna + ")");
                    startingTablero.drawBoard(); // Redibujar el tablero
                    
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
     * Agrega una baldosa en una posicion dada, no agrega vacios
     * @param escoger el color con ''
     * @param si es rojo poner la posicion en la cual se quiera poner y el color 'r'
     * @param si es azul poner la posicion en la cual se quiera poner y el color 'b'
     * @param si es amarillo poner la posicion en la cual se quiera poner y el color 'y'
     * @param si es verde poner la posicion en la cual se quiera poner y el color 'g'
     */
    public void agregarBaldosa(int fila, int columna, char color) {
        System.out.println("Intentando agregar baldosa en (" + fila + ", " + columna + ") con color '" + color + "'");
        if (fila >= 0 && fila < startingBoard.length && columna >= 0 && columna < startingBoard[0].length) {
            switch (color) {
                case 'r':
                     startingBoard[fila][columna] = color; // Agregar la baldosa con el color dado
                     startingTablero.setBoard(startingBoard); // Actualizar el tablero en el objeto Rectangle
                     System.out.println("Baldosa agregada en (" + fila + ", " + columna + ") con color '" + color + "'");
                     startingTablero.drawBoard(); // Redibujar el tablero
                     break;
                case 'g':
                     startingBoard[fila][columna] = color; 
                     startingTablero.setBoard(startingBoard); 
                     System.out.println("Baldosa agregada en (" + fila + ", " + columna + ") con color '" + color + "'");
                     startingTablero.drawBoard(); 
                     break;
                case 'b':
                     startingBoard[fila][columna] = color; 
                     startingTablero.setBoard(startingBoard); 
                     System.out.println("Baldosa agregada en (" + fila + ", " + columna + ") con color '" + color + "'");
                     startingTablero.drawBoard(); 
                     break;
                case 'y':
                     startingBoard[fila][columna] = color; 
                     startingTablero.setBoard(startingBoard); 
                     System.out.println("Baldosa agregada en (" + fila + ", " + columna + ") con color '" + color + "'");
                     startingTablero.drawBoard(); 
                     break;
            default:
                System.out.println("Color inválido. Use 'r' para rojo, 'g' para verde, 'b' para azul, 'y' para amarillo.");
                break;
            }
        Canvas canvas = Canvas.getCanvas(); // Obtener Canvas
        canvas.getCanvasPane().repaint(); // Forzar redibujado
        } 
        else {
        System.out.println("Posición inválida para agregar baldosa");
       }
    }
    
    /**
     * Hace visible el tablero con las baldosas
     */
    public void makeVisibleTable() {
        if (startingTablero != null && endingTablero != null) {
            startingTablero.makeVisibleTable();
            endingTablero.makeVisibleTable();
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
    
}