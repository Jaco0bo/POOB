
/**
 * Write a description of class a here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NormalTile extends Tile {
    public NormalTile(String letter, String color) {
        super("normal", color);  // Pasa el tipo como "normal" y el color
    }

    @Override
    public String getLetter() {
        return "N";  // La letra para una baldosa normal es "N"
    }
    
    @Override
    public boolean canBeRemoved() {
        return true;
    }

    @Override
    public boolean canBeMoved() {
        return true;
    }

    @Override
    public boolean canSlide() {
        return true;
    }

    @Override
    public boolean fallsInHole() {
        return true;
    }
}
