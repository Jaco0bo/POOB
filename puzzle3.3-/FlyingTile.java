
/**
 * Write a description of class a here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FlyingTile extends Tile {
    public FlyingTile(String color) {
        super("flying", color);  // Pasa el tipo "flying"
    }

    @Override
    public boolean fallsInHole() {
        return false;  // No cae en los agujeros
    }

    @Override
    public String getLetter() {
        return "Y";  // Representa la baldosa con la letra "Y"
    }
}

