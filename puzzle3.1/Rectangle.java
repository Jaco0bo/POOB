import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 1.0  (15 July 2000)()
 */


 
public class Rectangle{

    public static int EDGES = 4;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    public boolean isVisible;
    private char[][] board;

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle(){
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
    }
    

    /**
     * Make this rectangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the rectangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the rectangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the rectangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the rectangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the rectangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the rectangle vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidth must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }

    /*
     * Draw the rectangle with current specifications on screen.
     */

    private void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, 
                                       width, height));
            canvas.wait(10);
        }
    }

    /*
     * Erase the rectangle on screen.
     */
    public void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Metodo para crear el tablero de la clase puzzle
     */
    public Rectangle(int h, int w, int xPosition, int yPosition, String color, boolean isVisible, boolean isBoard){
        this.height = h;
        this.width = w;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.color = color;
        this.isVisible = isVisible;
        this.board = new char[h / 30][w / 30];
    }
    
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public int getXPosition(){
        return xPosition;
    }
    
    public int getYPosition(){
        return yPosition;
    }
    
    public void setPosition(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
    }
    
    public void setBoard(char[][] board){
        this.board = board;
    }
    
    public void drawBoard() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    // Configura el color basado en el valor en la matriz
                    switch (board[i][j]) {
                        case 'r':
                            canvas.setForegroundColor("red");
                            break;
                        case 'g':
                            canvas.setForegroundColor("green");
                            break;
                        case 'b':
                            canvas.setForegroundColor("blue");
                            break;
                        case 'y':
                            canvas.setForegroundColor("yellow");
                            break;
                        case '.':
                            canvas.setForegroundColor("brown");
                    }
                    // Dibuja cada celda del tablero
                    canvas.drawRectangle(
                        xPosition + j * 30, // Ajustar según el tamaño de las celdas
                        yPosition + i * 30,
                        30, // Tamaño de las celdas
                        30
                    );
                }
            }
            canvas.wait(10); // Opcional: espera un momento para la visualización    
        }
    }
    
    public void makeVisibleTable(){
        if (!isVisible) {
            isVisible = true;
            drawBoard(); // Dibuja el tablero y las baldosas cuando se hace visible
        }
    }

    public void makeInvisibleTable(){
        if (isVisible) {
            erase(); // Borra el tablero del canvas
            isVisible = false;
        }
    }
    
    public void drawGlue() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas(); // Asegúrate de que sea el mismo Canvas
            // Dibuja el cuadrado de pegante encima de la baldosa
            canvas.setForegroundColor(color); // Cambia al color del pegante
            canvas.drawRectangle(xPosition, yPosition, width, height); // Dibujar el pegante
            canvas.wait(10); // Espera un momento para visualizar
        }
    }
    
    public static Rectangle createGlueSquare(int glueSize, int xPosition, int yPosition, String color) {
        return new Rectangle(glueSize, glueSize, xPosition, yPosition, color, true, false);
    }
    
}
