
/**
 * Write a description of class Tile here.
 * 
 * @author Camilo Andres Fernandez Diaz
 * @author Andres Jacobo Sepulveda
 * @version (a version number or a date)
 */
public class Tile {
    protected String type;  // Tipo de baldosa
    protected String color; // Color de la baldosa

    // Constructor básico
    public Tile(String type, String color) {
        this.type = type;
        this.color = color;
    }
    
    // Cambiar el tipo de baldosa
    public void setType(String type) {
        this.type = type;
    }

    // Obtener el tipo de baldosa
    public String getType() {
        return type;
    }

    // Obtener el color de la baldosa
    public String getColor() {
        return color;
    }

    // Obtener la letra que representa el tipo de baldosa
    public String getLetter() {
        switch (type) {
            case "fixed":
                return "F";
            case "rough":
                return "R";
            case "freelance":
                return "L";
            case "flying":
                return "Y";
            default:
                return "N";  // Para baldosas normales
        }
    }

    // Verificar si una baldosa puede ser eliminada
    public boolean canBeRemoved() {
        return true;  // Por defecto, todas las baldosas pueden ser eliminadas
    }

    // Verificar si una baldosa puede ser movida
    public boolean canBeMoved() {
        return true;  // Por defecto, todas las baldosas pueden moverse
    }

    // Verificar si una baldosa puede deslizarse
    public boolean canSlide() {
        return true;  // Por defecto, todas las baldosas pueden deslizarse
    }

    // Verificar si una baldosa cae en un agujero
    public boolean fallsInHole() {
        return true;  // Por defecto, todas las baldosas caen en agujeros
    }
}


