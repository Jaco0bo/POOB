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

    public static void reubicarBaldosa(int filaActual, int columnaActual, int nuevaFila, int nuevaColumna) {
        // Imprimir información para depuración
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
}
