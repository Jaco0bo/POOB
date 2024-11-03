/**
 * Clase para una prueba de aceptación (3): tablero de 5x5 con tablero inicial vacío y baldosas desordenadas.
 * Utiliza dfsTilt para realizar inclinaciones inteligentes y resolver el tablero automáticamente.
 * 
 * @author Camilo Fernandez
 * @author Andrés Sepúlveda
 * @version 1.8
 */

import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PuzzleBtest {
    private Puzzle puzzle;
    private char[][] startingBoard;
    private char[][] endingBoard;
    private PuzzleContest puzzleContest;

    /**
     * Configuración inicial de los tableros de 5x5, con un tablero inicial vacío.
     */
    @BeforeEach
    void setUp() {
        // Tablero inicial vacío de 5x5
        startingBoard = new char[][] {
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'}
        };

        // Tablero final con baldosas en posiciones específicas
        endingBoard = new char[][] {
            {'r', 'b', 'g', 'y', '.'},
            {'b', 'g', 'y', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'b', 'r', '.', '.', '.'},
            {'g', 'y', '.', '.', '.'}
        };

        // Crear el objeto Puzzle con el tablero inicial vacío y el tablero final
        puzzle = new Puzzle(startingBoard, endingBoard);
    }

    /**
     * Prueba de Aceptación en Tablero de 5x5 con `startingBoard` Vacío y Resolución Automática:
     * - Objetivo: Agregar baldosas, desordenarlas y resolver el tablero mediante dfsTilt.
     * - Pasos del escenario:
     *   1. Agregar baldosas en las posiciones necesarias.
     *   2. Desordenar algunas baldosas intencionalmente.
     *   3. Usar dfsTilt para inclinar el tablero y resolverlo.
     *   4. Verificar que el tablero final coincide con la configuración deseada.
     */
    @Test
    public void PruebaDeAceptacion2() {
        Scanner scanner = new Scanner(System.in);

        // Imprimir el tablero inicial vacío
        System.out.println("Tablero inicial vacío:");
        printBoard(startingBoard);

        // Paso 1: Confirmación del estado inicial del tablero vacío
        System.out.println("¿Aceptas el estado inicial del tablero vacío? (S/N)");
        String respuesta = scanner.nextLine();
        assertEquals("S", respuesta.toUpperCase(), "El usuario no aceptó el estado inicial.");

        // Paso 2: Agregar baldosas en posiciones necesarias según el `endingBoard`
        System.out.println("Agregando baldosas en el tablero para acercarse a la configuración final...");
        
        puzzle.agregarBaldosa(0, 0, 'r'); // Agregar roja en (0,0)
        puzzle.agregarBaldosa(0, 1, 'y'); // Agregar amarilla en (0,1)
        puzzle.agregarBaldosa(0, 2, 'b'); // Agregar azul en (0,2)
        puzzle.agregarBaldosa(0, 3, 'g'); // Agregar verde en (0,3)
        puzzle.agregarBaldosa(0, 4, 'y'); // Agregar amarilla en (0,4)
        puzzle.agregarBaldosa(1, 0, 'y'); // Agregar amarilla en (1,0)
        puzzle.agregarBaldosa(1, 1, 'b'); // Agregar azul en (1,1)
        puzzle.agregarBaldosa(1, 2, 'g'); // Agregar verde en (1,2)
        puzzle.agregarBaldosa(2, 0, 'r'); // Agregar roja en (2,0)
        puzzle.agregarBaldosa(3, 0, 'b'); // Agregar azul en (3,0)
        puzzle.agregarBaldosa(4, 0, 'g'); // Agregar verde en (4,0)

        // Imprimir el tablero después de agregar las baldosas
        System.out.println("Tablero después de agregar las baldosas:");
        printBoard(puzzle.getStartingBoard());

        // Confirmación del usuario después de agregar las baldosas
        System.out.println("¿Aceptas el estado del tablero tras agregar las baldosas? (S/N)");
        respuesta = scanner.nextLine();
        assertEquals("S", respuesta.toUpperCase(), "El usuario no aceptó el estado tras agregar las baldosas.");

        // Paso 3: Desordenar algunas baldosas intencionalmente
        System.out.println("Desordenando baldosas para simular errores...");
        puzzle.reubicarBaldosa(0, 1, 1, 4); // Mover amarilla de (0,1) a (1,4)
        puzzle.reubicarBaldosa(1, 0, 4, 2); // Mover amarilla de (1,0) a (4,2)
        puzzle.reubicarBaldosa(2, 0, 3, 3); // Mover roja de (2,0) a (3,3)

        // Imprimir el tablero después de desordenar las baldosas
        System.out.println("Tablero después de desordenar las baldosas:");
        printBoard(puzzle.getStartingBoard());

        // Confirmación del usuario después de desordenar las baldosas
        System.out.println("¿Aceptas el estado del tablero tras desordenar las baldosas? (S/N)");
        respuesta = scanner.nextLine();
        assertEquals("S", respuesta.toUpperCase(), "El usuario no aceptó el estado tras desordenar las baldosas.");

        // Paso 4: Resolver el tablero usando dfsTilt para realizar inclinaciones inteligentes
        System.out.println("Resolviendo el tablero mediante inclinaciones inteligentes...");
        boolean solved = puzzle.findSolution(); // `findSolution` ejecutará `dfsTilt` para resolver el tablero

        // Verificar que el tablero se ha resuelto correctamente
        assertTrue(solved, "No se pudo resolver el tablero final mediante dfsTilt.");

        // Imprimir el tablero después de la solución automática
        System.out.println("Tablero después de aplicar dfsTilt:");
        printBoard(puzzle.getStartingBoard());

        // Confirmación final del usuario
        System.out.println("¿Aceptas el estado final del tablero? (S/N)");
        respuesta = scanner.nextLine();
        assertEquals("S", respuesta.toUpperCase(), "El usuario no aceptó el estado final del tablero.");

        scanner.close();
    }

    // Método auxiliar para imprimir el tablero
    private void printBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println(java.util.Arrays.toString(row));
        }
    }
}
