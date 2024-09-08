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
    private static char[][] board;
    private static Rectangle tablero;
    public boolean isVisibleTable;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leer dimensiones del tablero
        int h = scanner.nextInt();
        int w = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        // Crear el tablero con dimensiones en píxeles
        tablero = new Rectangle(w * 30, h * 30, 10, 20, "brown", true);

        // Leer el estado del tablero
        board = new char[h][w];
        for (int i = 0; i < h; i++) {
            String line = scanner.nextLine();
            if (line.length() != w) {
                System.out.println("Error: La línea ingresada tiene una longitud incorrecta.");
                return;
            }
            board[i] = line.toCharArray();
        }

        // Configurar el tablero en el objeto Rectangle
        tablero.setBoard(board);
        // Obtener la instancia de Canvas y configurar el tamaño del panel
        Canvas canvas = Canvas.getCanvas();
        canvas.setVisible(true);

        // Configurar la ventana
        JFrame frame = new JFrame();
        frame.setSize(w * 30 + 40, h * 30 + 60); // Ajustar el tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas.getCanvasPane());
        frame.setVisible(true);
        
        tablero.drawBoard();

        scanner.close();
    }
    
    /** 
     * Elimina baldosas
     * @param Escoge la fila de la baldosa que quiera ser borrada
     * @param Escoge la columna de la baldosa que quiera ser borrada
     */
    public static void eliminarBaldosa(int fila, int columna) {
        System.out.println("Intentando eliminar baldosa en (" + fila + ", " + columna + ")");
        if (fila >= 0 && fila < board.length && columna >= 0 && columna < board[0].length) {
            board[fila][columna] = '.'; // Representar baldosa eliminada con un punto (o cualquier otro símbolo)
            tablero.setBoard(board); // Actualizar el tablero en el objeto Rectangle
            System.out.println("Baldosa eliminada en (" + fila + ", " + columna + ")");
            tablero.drawBoard(); // Redibujar el tablero
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
    public static void reubicarBaldosa(int filaActual, int columnaActual, int nuevaFila, int nuevaColumna) {
        System.out.println("Intentando reubicar baldosa desde (" + filaActual + ", " + columnaActual + ") a (" + nuevaFila + ", " + nuevaColumna + ")");
            if (filaActual >= 0 && filaActual < board.length && columnaActual >= 0 && columnaActual < board[0].length
                && nuevaFila >= 0 && nuevaFila < board.length && nuevaColumna >= 0 && nuevaColumna < board[0].length) {
                char baldosa = board[filaActual][columnaActual];
                if (baldosa != '.') { // Verificar que la baldosa no esté vacía
                    board[filaActual][columnaActual] = '.'; // Dejar vacía la posición actual
                    board[nuevaFila][nuevaColumna] = baldosa; // Mover la baldosa a la nueva posición
                    tablero.setBoard(board); // Actualizar el tablero en el objeto Rectangle
                    System.out.println("Baldosa reubicada a (" + nuevaFila + ", " + nuevaColumna + ")");
                    tablero.drawBoard(); // Redibujar el tablero
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
    public static void agregarBaldosa(int fila, int columna, char color) {
        System.out.println("Intentando agregar baldosa en (" + fila + ", " + columna + ") con color '" + color + "'");
        if (fila >= 0 && fila < board.length && columna >= 0 && columna < board[0].length) {
            switch (color) {
                case 'r':
                     board[fila][columna] = color; // Agregar la baldosa con el color dado
                     tablero.setBoard(board); // Actualizar el tablero en el objeto Rectangle
                     System.out.println("Baldosa agregada en (" + fila + ", " + columna + ") con color '" + color + "'");
                     tablero.drawBoard(); // Redibujar el tablero
                     break;
                case 'g':
                     board[fila][columna] = color; 
                     tablero.setBoard(board); 
                     System.out.println("Baldosa agregada en (" + fila + ", " + columna + ") con color '" + color + "'");
                     tablero.drawBoard(); 
                     break;
                case 'b':
                     board[fila][columna] = color; 
                     tablero.setBoard(board); 
                     System.out.println("Baldosa agregada en (" + fila + ", " + columna + ") con color '" + color + "'");
                     tablero.drawBoard(); 
                     break;
                case 'y':
                     board[fila][columna] = color; 
                     tablero.setBoard(board); 
                     System.out.println("Baldosa agregada en (" + fila + ", " + columna + ") con color '" + color + "'");
                     tablero.drawBoard(); 
                     break;
            default:
                System.out.println("Color inválido. Use 'r' para rojo, 'g' para verde, 'b' para azul, 'y' para amarillo.");
                break;
            }
        } 
        else {
        System.out.println("Posición inválida para agregar baldosa");
       }
    }

    
    /**
     * Hace visible el tablero con las baldosas
     */
    public static void makeVisibleTable() {
        if (tablero != null) {
            tablero.makeVisibleTable();
            System.out.println("El tablero ahora es visible.");
        } else {
            System.out.println("El tablero no está inicializado.");
        }
    }

    /**
     * Hace invisible el tablero con las baldosas
     */
    public static void makeInvisibleTable() {
        if (tablero != null) {
            tablero.makeInvisibleTable();
            System.out.println("El tablero ahora es invisible.");
        } else {
            System.out.println("El tablero no está inicializado.");
        }
    }
    
    public static void rotarDerecha() {
        System.out.println("Rotando el tablero a la derecha...");
        int h = board.length;
        int w = board[0].length;
        char[][] nuevoTablero = new char[w][h]; 
    
        // La última fila se convierte en la primera columna
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                nuevoTablero[j][h - 1 - i] = board[i][j];
            }
        }
    
        board = nuevoTablero;
        tablero.setBoard(board);
        tablero.changeSize(w * 30, h * 30); 
        tablero.drawBoard(); 
    }

    public static void rotarIzquierda() {
        System.out.println("Rotando el tablero a la izquierda...");
        int h = board.length;
        int w = board[0].length;
        char[][] nuevoTablero = new char[w][h]; 
    
        //La primera columna se convierte en la última fila
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                nuevoTablero[w - 1 - j][i] = board[i][j];
            }
        }
    
        board = nuevoTablero;
        tablero.setBoard(board); 
        tablero.changeSize(w * 30, h * 30); 
        tablero.drawBoard(); 
    }

}
