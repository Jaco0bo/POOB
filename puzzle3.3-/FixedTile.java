
/**
 * Write a description of class a here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FixedTile extends Tile {
    public FixedTile(String color) {
        super("fixed", color);  // Pasa el tipo "fixed"
    }

    @Override
    public boolean canBeRemoved() {
        return false;  // No se puede eliminar
    }

    @Override
    public boolean canBeMoved() {
        return false;  // No se puede reubicar
    }

    @Override
    public String getLetter() {
        return "F";  // Representa la baldosa con la letra "F"
    }
}


