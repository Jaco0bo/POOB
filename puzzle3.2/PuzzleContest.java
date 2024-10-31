/**
 * Write a description of class PuzzleContest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PuzzleContest {
    int depth = 5;
    public boolean solve(char[][] startingBoard, char[][] endingBoard) {
        // Crear la instancia de Puzzle con los tableros dados
        Puzzle puzzle = new Puzzle(startingBoard, endingBoard);
        
        // Llamar a findSolution para ejecutar la búsqueda y devolver el resultado
        return puzzle.findSolution();
    }
}

