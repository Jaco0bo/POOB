/**
 * Clase para las pruebas de aceptación (2)
 * @author Camilo Fernandez
 * @author Andrés Sepúlveda
 */

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;
import java.util.logging.Level;


/**
 * 
 */
class PuzzleATest {
	private Puzzle puzzle;
	private char [][] startingBoard;
	private char [][] endingBoard;
	private char [][] expectedBoard;
	private PuzzleContest puzzleContest;
	private static final Logger LOGGER = Logger.getLogger(PuzzleATest.class.getName());

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		// Initialize starting y ending board
		startingBoard = new char [][] {
		    {'.', '.', '.','.'},
	        {'.', '.', '.','.'},
	        {'.', '.', '.','.'},
	        {'.', '.', '.','.'}
		};
		
		endingBoard = new char[][] {
		    {'y', 'r', 'b','r'},
	        {'.', '.', 'y','r'},
	        {'.', '.', '.','g'},
	        {'.', '.', '.','b'}
		};
		
		puzzle = new Puzzle(startingBoard, endingBoard);
		puzzle.makeInvisibleTable();
	}
    /**
     * Escenario de Prueba de Aceptación
     * - Objetivo: Crear desde cero la configuracion del tablero inicial y llevarlo a la configuracion final.
     * - Condiciones iniciales: startingBoard vacio y endingBoard con la configuracion final.
     * - Pasos del escenario:
     *   1. Crear baldosas en las posiciones (0,1),(1,0),(1,1),(1,2),(1,3),(2,1),(3,1),(3,2),(3,3).
     *   	(Cada posición debe tener la baldosa con el color correcto según el ejemplo del problema).
     *   2. Eliminar la baldosa en (1,1) porque no tiene el color correcto.
     *   3. Verificar que la posicion (1,1) esta vacia despues de eliminar la baldosa.
     *   4. Mover baldosa de la posicion (3,3) a (1,1) para corregir el color.
     *   5. Verificar que la baldosa se reubico correctamente en (1,1) con el color esperado.
     *   6. Llamar a la clase puzzleContest con 'endingBoard' y el 'startingBoard' bien configurado.
     *   7. Verificar que el tablero 'startingBoard' alcanzo la configuracion final de 'endingBoard'.
     */	
	@Test
	void pruebaDeAceptacion1() throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		// Agregar baldosas
		puzzle.agregarBaldosa(0, 1, 'r'); // Agregamos una baldosa roja en la posicion (0,1).
        puzzle.agregarBaldosa(1, 0, 'r'); // Agregamos una baldosa roja en la posicion (1,0).
        puzzle.agregarBaldosa(1, 1, 'y'); // Agregamos una baldosa amarilla (el color correcto debe ser verde) en la posicion (1,1).
        puzzle.agregarBaldosa(1, 2, 'y'); // Agregamos una baldosa amarilla en la posicion (1,2).
        puzzle.agregarBaldosa(1, 3, 'b'); // Agregamos una baldosa azul en la posicion (1,3).
        puzzle.agregarBaldosa(2, 1, 'b'); // Agregamos una baldosa azul en la posicion (2,1).
        puzzle.agregarBaldosa(3, 1, 'y'); // Agregamos una baldosa amarilla en la posicion (3,1).
        puzzle.agregarBaldosa(3, 2, 'r'); // Agregamos una baldosa roja en la posicion (3,2).
        puzzle.agregarBaldosa(3, 3, 'g'); // Agregamos una baldosa verde en la posicion (3,3), (esta posicion debe estar vacia '.'). 
        Thread.sleep(200); // Esperar 200ms 
        
        // configuracion del tablero luego de agregar las baldosas
        expectedBoard = new char [][] {
		    {'.', 'r', '.','.'},
	        {'r', 'y', 'y','b'},
	        {'.', 'b', '.','.'},
	        {'.', 'y', 'r','g'}
        };
        
        // Comprobamos que startingBoard tenga las nuevas baldosas
        assertArrayEquals(expectedBoard, puzzle.getStartingBoard(), "Luego de crear las baldosas no quedo como expectedBoard");
        for (char[] fila : startingBoard) {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.info(Arrays.toString(fila));
            }
        }

        
	    // Preguntar al usuario si acepta el resultado luego de agregar las baldosas
        LOGGER.info("¿Aceptas el resultado tras crear las baldosas iniciales? (S/N)");
        String respuesta = scanner.nextLine();
        assertEquals("S", respuesta.toUpperCase(), "El usuario no aceptó el resultado tras crear baldosas.");
        
        
        // El usuario se ha equivocado con el color de la baldosa en (1,1), deberia ser 'g' y es 'y'
        // Procedemos a eliminar esta baldosa
        puzzle.eliminarBaldosa(1, 1);
        
        // Comprobamos que la posicion (1,1) no tiene baldosa
        assertEquals('.', startingBoard [1][1], "La baldosa no esta vacia");
        
        // El usuario se ha equivocado de posicion al colocar la baldosa
        // La posicion es (3,3) y deberia ser (1,1)
        // Procedemos a reubicarla
        puzzle.reubicarBaldosa(3, 3, 1, 1);
        
        // Comprobamos que la posicion (3,3) no tiene baldosa y (1,1) si.
        assertEquals('g', startingBoard [1][1], "la baldosa no es verde");
        assertEquals('.', startingBoard [3][3], "la baldosa no esta vacia");
        
        // Preguntar al usuario si acepta el resultado luego de agregar las baldosas
        LOGGER.info("¿Aceptas el resultado tras eliminar y reubicar baldosas? (S/N)");
        String respuesta2 = scanner.nextLine();
        assertEquals("S", respuesta2.toUpperCase(), "El usuario no aceptó el resultado tras eliminar y reubicar baldosas.");
        
        // Llamamos a puzzleContest para solucionar el tablero
        puzzleContest = new PuzzleContest();
        
        
        // Verificamos que startingBoard sea igual a endingBoard
        assertTrue(puzzleContest.solve(startingBoard, endingBoard));
        Thread.sleep(200); // Esperar 200ms 
        
     // Preguntar al usuario si acepta el resultado final
        LOGGER.info("¿Aceptas el resultado final luego de resolver el puzzle? (S/N)");
        String respuesta3 = scanner.nextLine();
        assertEquals("S", respuesta3.toUpperCase(), "El usuario no aceptó el resultado final, startingBoard no es igual a endingBoard.");
        scanner.close();
	}

}
