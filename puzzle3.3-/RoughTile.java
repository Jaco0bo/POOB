
/**
 * Write a description of class a here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RoughTile extends Tile {
    public RoughTile(String color) {
        super("rough", color);  // Pasa el tipo "rough"
    }

    @Override
    public boolean canSlide() {
        return false;  // No se puede deslizar
    }

    @Override
    public String getLetter() {
        return "R";  // Representa la baldosa con la letra "R"
    }
}

