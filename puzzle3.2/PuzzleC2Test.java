import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * The test class PuzzleC2Test.
 *
 * @author  Camilo Fernandez y Andres Sepulveda
 * @version 1.0
 */
public class PuzzleC2Test{
    private Puzzle puzzle;
    private char [][] startingBoard;
    private Glue glue;
    /**
     * Default constructor for test class PuzzleC2Test
     */
    public PuzzleC2Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp(){
        startingBoard = new char[][]{
        {'.', '.', '.'},
        {'.', '.', '.'},
        {'.', '.', '.'}
        };
        puzzle = new Puzzle(startingBoard); // Inicializar el puzzle con el tablero
        glue = new Glue(startingBoard, new Rectangle()); // Rempalza rectangle con la implementacion adecuada
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
    
    
    /**
     * Test para revisar que no se permite agregar una baldosa en una posicion 
     * donde ya existe una.
     */
    @Test
    public void accordingFSShouldntAddATile(){
        // agregar una baldosa
        puzzle.agregarBaldosa(0,0,'r'); // Agregamos una baldosa roja en la posicion (0,0)
        
        // Guardar el estado original del tablero
        char[][] expectedBoard = {
        {'r', '.', '.'},
        {'.', '.', '.'},
        {'.', '.', '.'}
        };
        
        // Intentar agregar otra baldosa en la misma posicion.
        puzzle.agregarBaldosa(0,0,'b'); // Intentamos agregar baldosa de color azul en (0,0) de nuevo
        
        // Comprobar que le valor de la posicion en (0,0) no ha cambiado
        assertArrayEquals(expectedBoard, puzzle.getStartingBoard(), "No deberia agregar baldosa en una posicion ocupada");
    }
    
    /**
     * Test para revisar que no se permite reubicar una baldosa en una posicion 
     * donde ya existe una.
     */
    @Test
    public void accordingFSShouldntRelocateATile(){
        // agregar una baldosa
        puzzle.agregarBaldosa(0,0,'r'); // Agregamos una baldosa roja en la posicion (0,0)
        puzzle.agregarBaldosa(2,2,'b'); // Agregamos una baldosa azul en la posicion (2,2)
        // Guardar el estado original del tablero
        char[][] expectedBoard = {
        {'r', '.', '.'},
        {'.', '.', '.'},
        {'.', '.', 'b'}
        };
        
        // Intentar reubicar la baldosa (0,0) donde ya hay una (2,2)
        puzzle.reubicarBaldosa(0,0,2,2); 
        
        // Comprobar que le valor de la posicion en (0,0) no ha cambiado
        assertArrayEquals(expectedBoard, puzzle.getStartingBoard(), "No deberia reubicar baldosa en una posicion ocupada");
    }
    
    /**
     * Test para revisar que no se permite reubicar una baldosa que no existe
     */
    @Test
    public void accordingFSShouldntRelocateNullTile(){
        // Guardar el estado original del tablero
        char[][] expectedBoard = {
        {'.', '.', '.'},
        {'.', '.', '.'},
        {'.', '.', '.'}
        };
        
        // Intentar reubicar la baldosa inexistente (0,0) en la posicion (2,2)
        puzzle.reubicarBaldosa(0,0,2,2); 
        
        // Comprobar que le valor de la posicion en (0,0) no ha cambiado
        assertArrayEquals(expectedBoard, puzzle.getStartingBoard(), "No deberia reubicar baldosa en una posicion ocupada");
    }
    
    /**
     * Test para revisar que no se permite eliminar una baldosa que no existe
     */
    @Test
    public void accordingFSShouldntDeleteANullTile(){
        // Guardar el estado original del tablero
        char[][] expectedBoard = {
        {'.', '.', '.'},
        {'.', '.', '.'},
        {'.', '.', '.'}
        };
        
        // Intentar eliminar la baldosa inexistente(0,0) 
        puzzle.eliminarBaldosa(0,0); 
        
        // Comprobar que le valor de la posicion en (0,0) no ha cambiado
        assertArrayEquals(expectedBoard, puzzle.getStartingBoard(), "No deberia reubicar baldosa en una posicion ocupada");
    }
    
    /**
     * Test para revisar que no se permite agregar pegante en una posicion sin baldosa 
     */
     @Test
    public void accordingFSShouldntAddGlueInANullTile() {
        // Crear una instancia de Puzzle y configurarlo
        Puzzle puzzle = new Puzzle(startingBoard, startingBoard);
        puzzle.agregarBaldosa(0, 0, 'r'); // Agregar una baldosa en (0,0)
        
        // Redirigir la salida estándar
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    
        // Intentar agregar pegamento en una posición vacía
        puzzle.addGlue(1, 1); 
    
        // Restablecer la salida estándar
        System.setOut(originalOut);
    
        // Verificar que el mensaje correcto se imprimió
        String expectedOutput = "No se puede agregar pegante en una casilla vacía.";
        assertTrue(outContent.toString().contains(expectedOutput));
    }
    
    /**
     * Test para revisar que no se permite eliminar una baldosa si tiene pegante 
     */
     @Test
    public void accordingFSShouldntDeleteTileWhitGlue() {
        // Crear una instancia de Puzzle y configurarlo
        Puzzle puzzle = new Puzzle(startingBoard, startingBoard);
        puzzle.agregarBaldosa(0, 0, 'r'); // Agregar una baldosa en (0,0)
        
        // Redirigir la salida estándar
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    
        // Agregar pegamento en (0,0)
        puzzle.addGlue(0, 0); 
        
        // Intentar eliminar la baldosa (0,0) con pegamento
        puzzle.eliminarBaldosa(0,0);
        // Restablecer la salida estándar
        System.setOut(originalOut);
    
        // Verificar que el mensaje correcto se imprimió
        String expectedOutput = "No se puede eliminar la baldosa porque tiene pegante.";
        assertTrue(outContent.toString().contains(expectedOutput));
    }
    
}
