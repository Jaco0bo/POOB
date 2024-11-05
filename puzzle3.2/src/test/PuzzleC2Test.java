import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Nested;


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
        glue = new Glue(startingBoard, new Rectangle()); 
        puzzle.makeInvisibleTable(); // Tablero invisible
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
        puzzle.makeInvisibleTable(); // Tablero invisible
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
    
    /**
     * Test para revisar que no se permite eliminar una baldosa si tiene pegante 
     */
     @Test
    public void accordingFSShouldExchangeTBoards() {
        // Crear una instancia de Puzzle y configurarlo
        Puzzle puzzle = new Puzzle(startingBoard, startingBoard);
        puzzle.makeInvisibleTable(); // Tablero invisible
        puzzle.agregarBaldosa(0, 0, 'r'); // Agregar una baldosa en (0,0)
        
        // Redirigir la salida estándar
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    
        // Agregar pegamento en (0,0)
        puzzle.makeInvisibleTable(); // Tablero invisible
        puzzle.addGlue(0, 0); 
        puzzle.makeInvisibleTable(); // Tablero invisible
        // Intentar eliminar la baldosa (0,0) con pegamento
        puzzle.makeInvisibleTable(); // Tablero invisible
        puzzle.eliminarBaldosa(0,0);
        // Restablecer la salida estándar
        System.setOut(originalOut);
    
        // Verificar que el mensaje correcto se imprimió
        String expectedOutput = "No se puede eliminar la baldosa porque tiene pegante.";
        assertTrue(outContent.toString().contains(expectedOutput));
    }  
    
     /**
      * Test para revisar que se elimina correctamente el pegante de una baldosa
      */
     @Test
     public void accordingFSShouldDeleteAGlueFromTile() {
         // Crear una instancia de Puzzle y configurarlo
         Puzzle puzzle = new Puzzle(startingBoard, startingBoard);
         puzzle.makeInvisibleTable(); // Tablero invisible
         puzzle.agregarBaldosa(0, 0, 'r'); // Agregar una baldosa en (0,0)
         
         // Agregar pegamento en (0,0)
         puzzle.addGlue(0, 0);
         
         // Verificar que el pegamento efectivamente esta puesto
         // assertTrue(glue.isGlued(0, 0));
         
         // Intentar eliminar el pegamento de la baldosa
         puzzle.removeGlue(0,0);
         
         // Verificar que el pegamento efectivamente esta borrado
         assertFalse(glue.isGlued(0, 0));
     } 
         
@Nested
class ExchangeMethodTests {

    private Puzzle puzzle;
    private char[][] startingBoard;
    private char[][] endingBoard;

    @BeforeEach
    public void setUpForExchange() {
        // Inicializa los tableros con diferentes configuraciones
        startingBoard = new char[][]{
            {'.', '.', '.'},
            {'.', '.', '.'},
            {'.', '.', '.'}
        };
        endingBoard = new char[][]{
            {'r', 'b', 'g'},
            {'y', '.', '.'},
            {'.', '.', '.'}
        };
        
        puzzle = new Puzzle(startingBoard, endingBoard); // Puzzle con starting y ending boards
        new Glue(startingBoard, new Rectangle());
        puzzle.makeInvisibleTable(); // Tablero invisible
    }

    @Test
    public void accordingFSShouldExchangeBoardsSuccessfully() {
        // Configurar el estado inicial de los tableros y pegamento
        puzzle.agregarBaldosa(0, 0, 'r');
        puzzle.addGlue(0, 0);

        // Intercambiar los tableros
        puzzle.exchange();

        // Verificar que los tableros y las posiciones fueron intercambiados
        assertArrayEquals(endingBoard, puzzle.getStartingBoard(), "El tablero inicial debería haberse intercambiado.");
        assertArrayEquals(startingBoard, puzzle.getEndingBoard(), "El tablero final debería haberse intercambiado.");
    }
    
    @Test
    public void accordingFSShouldCalculatedMisplacedTilesWithValidEndingBoard() {
        // Inicializa el tablero de inicio y el de final
        startingBoard = new char[][]{
            {'r', 'b', 'g'},
            {'.', 'y', '.'},
            {'.', '.', '.'}
        };
        endingBoard = new char[][]{
            {'r', 'g', 'b'},
            {'.', 'y', '.'},
            {'.', '.', '.'}
        };
        
        // Crea la instancia de Puzzle con ambos tableros
        puzzle = new Puzzle(startingBoard, endingBoard);
        
        // Llama al método y verifica que el conteo de baldosas mal colocadas es correcto
        int misplaced = puzzle.misplacedTiles();
        
        // (b y g en las posiciones incorrectas)
        assertEquals(2, misplaced);
    }
    
    /**
     * Test para revisar que makeVisible funciona correctamente 
     */
     @Test
    public void accordingFSShouldSetVisibleBoards() {
        // Crear una instancia de Puzzle y configurarlo
        Puzzle puzzle = new Puzzle(startingBoard, startingBoard);
        puzzle.makeVisibleTable(); // Tablero visible
        puzzle.agregarBaldosa(0,0,'r');
        assertTrue(puzzle.isTableVisible());
    }   
    
    /**
     * Test para revisar que makeInvisible funciona correctamente 
     */
     @Test
    public void accordingFSShouldSetInvisibleBoards() {
        // Crear una instancia de Puzzle y configurarlo
        Puzzle puzzle = new Puzzle(startingBoard, startingBoard);
        puzzle.makeVisibleTable(); // Tablero visible
        puzzle.agregarBaldosa(0,0,'r');
        puzzle.makeInvisibleTable(); // Tablero invisible
        assertFalse(puzzle.isTableVisible());
    }
    
    /**
     * Test para revisar que se estan reubicando correctamente las baldosas con pegantes 
     */
    @Test
    public void accordingFSShouldReubicateTileWhitGlue() {
        startingBoard = new char[][]{
            {'r', 'b', 'g'},
            {'.', 'y', '.'},
            {'.', '.', '.'}
        };
        // Crear una instancia de Puzzle y configurarlo
        Puzzle puzzle = new Puzzle(startingBoard, startingBoard);
        // Agregamos pegante en la posicion (1,1)
        puzzle.addGlue(1,1);
        
        //reubicamos la baldosa (1,1) a la posicion (2,2)
        puzzle.reubicarBaldosa(1,1,2,2);
            
        //La baldosa se movió correctamente a la nueva posición
        assertEquals('y', puzzle.getStartingBoard()[2][2], "La baldosa no se movió a la nueva posición.");
        
        //La posición original (1,1) está vacía
        assertEquals('.', puzzle.getStartingBoard()[1][1], "La posición original no está vacía después de mover la baldosa.");
        
        //El pegamento fue eliminado de la posición original
        assertFalse(glue.isGlued(1, 1), "El pegamento no se eliminó de la posición original.");
        
        // El pegamento fue añadido a la nueva posición de la baldosa
        assertFalse(glue.isGlued(2, 2), "El pegamento no fue añadido a la nueva posición de la baldosa.");
    }
    

    
}
}
