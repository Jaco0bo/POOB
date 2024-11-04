import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas {
    // Singleton instance of the Canvas class.
    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     * 
     * @return the singleton instance of the Canvas.
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    // ----- instance part -----
    
    private JFrame frame; // Main frame that holds the canvas.
    private CanvasPane canvas; // Panel where shapes are drawn.
    private Graphics2D graphic; // Graphics context for drawing on the canvas.
    private Color backgroundColour; // Background color of the canvas.
    private Image canvasImage; // Image used for double buffering.
    private List<Object> objects; // List to keep track of objects' order on the canvas.
    private HashMap<Object, ShapeDescription> shapes; // Map storing shapes by their reference.

    /**
     * Create a Canvas.
     * 
     * @param title   Title to appear in the Canvas Frame.
     * @param width   The desired width for the canvas.
     * @param height  The desired height for the canvas.
     * @param bgColour The background color of the canvas.
     */
    private Canvas(String title, int width, int height, Color bgColour) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation.
        frame.setAlwaysOnTop(true); // Keep the frame always on top.
        frame.setResizable(false); // Disable resizing.
        frame.setFocusable(true); // Ensure the window can be focused.
        
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList<>();
        shapes = new HashMap<>();
    }

    /**
     * Set the canvas visibility and bring it to the front of the screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * 
     * @param visible Boolean value representing the desired visibility of
     *                the canvas (true or false).
     */
    public void setVisible(boolean visible) {
        if (graphic == null) {
            // First time: instantiate the offscreen image and fill it with
            // the background color.
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * 
     * @param referenceObject An object to define identity for this shape.
     * @param color           The color of the shape.
     * @param shape           The shape object to be drawn on the canvas.
     */
    public void draw(Object referenceObject, String color, Shape shape) {
        objects.remove(referenceObject);   // Remove if already present.
        objects.add(referenceObject);      // Add at the end.
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    /**
     * Erase a given shape from the screen.
     * 
     * @param referenceObject The shape object to be erased.
     */
    public void erase(Object referenceObject) {
        objects.remove(referenceObject);   // Remove if already present.
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground color of the Canvas.
     * 
     * @param colorString The new color for the foreground of the Canvas.
     */
    public void setForegroundColor(String colorString) {
        if (colorString.equals("red"))
            graphic.setColor(Color.red);
        else if (colorString.equals("black"))
            graphic.setColor(Color.black);
        else if (colorString.equals("blue"))
            graphic.setColor(Color.blue);
        else if (colorString.equals("yellow"))
            graphic.setColor(Color.yellow);
        else if (colorString.equals("green"))
            graphic.setColor(Color.green);
        else if (colorString.equals("magenta"))
            graphic.setColor(Color.magenta);
        else if (colorString.equals("white"))
            graphic.setColor(Color.white);
        else if (colorString.equals("brown"))
            graphic.setColor(new Color(130, 61, 19));
        else
            graphic.setColor(Color.black);
    }

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * 
     * @param milliseconds The number of milliseconds to wait.
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // Ignoring exception at the moment.
        }
    }

    /**
     * Redraw all shapes currently on the Canvas.
     */
    @SuppressWarnings("rawtypes")
    public void redraw() {
        erase();
        for (Iterator i = objects.iterator(); i.hasNext(); ) {
            shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
        frame.requestFocusInWindow();
    }

    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    /**
     * Get the CanvasPane of the Canvas.
     * 
     * @return The CanvasPane instance for direct access.
     */
    public CanvasPane getCanvasPane() {
        return canvas;
    }

    /**
     * Draws a filled rectangle at the specified location on the canvas.
     * 
     * @param x      The x-coordinate of the rectangle's top-left corner.
     * @param y      The y-coordinate of the rectangle's top-left corner.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public void drawRectangle(int x, int y, int width, int height) {
        if (graphic != null) {
            graphic.fillRect(x, y, width, height);
        }
    }

    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    @SuppressWarnings("serial")
    public class CanvasPane extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    /************************************************************************
     * Inner class ShapeDescription - represents a shape with its color.
     */
    private class ShapeDescription {
        private Shape shape; // The shape object to draw.
        private String colorString; // The color of the shape.

        public ShapeDescription(Shape shape, String color) {
            this.shape = shape;
            colorString = color;
        }

        /**
         * Draws the shape with its specified color.
         * 
         * @param graphic The graphics context to draw the shape.
         */
        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }
}