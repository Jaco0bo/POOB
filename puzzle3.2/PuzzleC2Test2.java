

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class PuzzleC2Test2.
 *
 * @author  Camilo Fernandez y Andres Sepulveda
 * @version 1.0
 */
public class PuzzleC2Test2
{
    private Puzzle puzzle;
    private char[][] startingBoard;
    private char[][] endingBoard;
    private Glue glue;
    private Tilt tilt;
    /**
     * Default constructor for test class PuzzleC2Test2
     */
    public PuzzleC2Test2()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        // Inicializa los tableros con diferentes configuraciones
        startingBoard = new char[][]{
            {'.','r','.','.'},
            {'r','g','y','b'},
            {'.','b','.','.'},
            {'.','y','r','.'}
        };
        endingBoard = new char[][]{
            {'y','r','b','r'},
            {'.','.','y','r'},
            {'.','.','.','g'},
            {'.','.','.','b'}
        };

        puzzle = new Puzzle(startingBoard, endingBoard); // Puzzle con starting y ending boards
        glue = new Glue(startingBoard, new Rectangle()); // Glue para el tablero inicial
        tilt = new Tilt(startingBoard, glue);
        puzzle.makeInvisibleTable(); // Tablero invisible
    }
    
    @Test
    /**
     * Test que verifica que se devuelvan el numero correcto de baldosas inamovibles
     */
    public void accordingFSShouldCalculateTheNumberOfInamovibleTiles() {
        puzzle.makeInvisibleTable();

        // Como es inamovible en el metodo canMoveTile de la clase Tilt deberia ser false
        assertFalse(tilt.canMoveTile(1,1),"La baldosa en (1, 1) no debería poder moverse.");
    }
    
    @Test
    /**
     *  Test que verifica que el tablero se ladee correctamente hacia la izquierda
     */
    public void accordingFSShouldTiltLeft() {
        puzzle.makeInvisibleTable();
        // Llamar al método tiltBoards
        puzzle.tiltBoard("izquierda");
        
        // Verifica el estado del tablero después de la inclinación
        char[][] expectedBoard = {
            {'r','.','.','.'},
            {'r','g','y','b'},
            {'b','.','.','.'},
            {'y','r','.','.'}
        };
        
        // Obtener el tablero actual después de la inclinación
        char[][] currentBoard = puzzle.getStartingBoard();
        
        // Compara el tablero esperado con el actual
        assertArrayEquals(expectedBoard, currentBoard, "El tablero se inclinó correctamente a la izquierda.");
    }
    
    @Test
    /**
     *  Test que verifica que el tablero se ladee correctamente hacia la derecha
     */
    public void accordingFSShouldTiltRight() {
        puzzle.makeInvisibleTable();
        // Llamar al método tiltBoards
        puzzle.tiltBoard("derecha");
        
        // Verifica el estado del tablero después de la inclinación
        char[][] expectedBoard = {
            {'.','.','.','r'},
            {'r','g','y','b'},
            {'.','.','.','b'},
            {'.','.','y','r'}

        };
        
        // Obtener el tablero actual después de la inclinación
        char[][] currentBoard = puzzle.getStartingBoard();
        
         // Compara el tablero esperado con el actual
        assertArrayEquals(expectedBoard, currentBoard, "El tablero se inclinó correctamente a la izquierda.");
    }
    
    @Test
    /**
     *  Test que verifica que el tablero se ladee correctamente hacia arriba
     */
    public void accordingFSShouldTiltUp() {
        puzzle.makeInvisibleTable();
        // Llamar al método tiltBoards
        puzzle.tiltBoard("arriba");
        
        // Verifica el estado del tablero después de la inclinación
        char[][] expectedBoard = {
            {'r','r','y','b'},
            {'.','g','r','.'},
            {'.','b','.','.'},
            {'.','y','.','.'} 
        };
        
        // Obtener el tablero actual después de la inclinación
        char[][] currentBoard = puzzle.getStartingBoard();
        
         // Compara el tablero esperado con el actual
        assertArrayEquals(expectedBoard, currentBoard, "El tablero se inclinó correctamente a la izquierda.");
    }
    
    @Test
    /**
     *  Test que verifica que el tablero se ladee correctamente hacia abajo
     */
    public void accordingFSShouldTiltDown() {
        puzzle.makeInvisibleTable();
        // Llamar al método tiltBoards
        puzzle.tiltBoard("abajo");
        
        // Verifica el estado del tablero después de la inclinación
        char[][] expectedBoard = {
            {'.','r','.','.'},
            {'.','g','.','.'},
            {'.','b','y','.'},
            {'r','y','r','b'}
        };
        
        // Obtener el tablero actual después de la inclinación
        char[][] currentBoard = puzzle.getStartingBoard();
        
         // Compara el tablero esperado con el actual
        assertArrayEquals(expectedBoard, currentBoard, "El tablero se inclinó correctamente a la izquierda.");
    }
    
    @Test
    public void accordinfFSShouldBlinkTile() throws InterruptedException {
        // Hacer parpadear la baldosa en (1, 1)
        puzzle.parpadearBaldosa(1, 1);

        // Esperar un tiempo suficiente para que se realice al menos un parpadeo
        Thread.sleep(150); // Ajusta el tiempo según sea necesario

        // Verifica solamente la posicion [1][1] en vez de todo el tablero 
        assertEquals('w', puzzle.getStartingBoard()[1][1], "La baldosa debería ser blanca después de parpadear.");

        // Esperar un poco más para ver el color original
        Thread.sleep(50); 

        // Verificar que la baldosa ha vuelto a su color original
        assertEquals('g', puzzle.getStartingBoard()[1][1], "La baldosa debería haber vuelto a su color original (verde).");
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {   
    }
}
