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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leer dimensiones del tablero
        int h = scanner.nextInt();
        int w = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        // Crear el tablero con dimensiones en píxeles
        Rectangle tablero = new Rectangle(w * 30, h * 30, 10, 20, "brown", true);

        // Leer el estado del tablero
        char[][] board = new char[h][w];
        for (int i = 0; i < h; i++) {
            String line = scanner.nextLine();
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
}
