package Task3;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;


class Ball {
    private Component canvas;
    private static final int XSIZE = 10;
    private static final int YSIZE = 10;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;

    private final Color color;


    public Ball(Component c, Color color, int x, int y){
        this.canvas = c;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Ball(Component c, Color color){
        this.canvas = c;
        this.color = color;
        if(Math.random() < 0.5){
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }



    public void draw (Graphics2D g2){
        g2.setColor(color);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public void move(){
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }
}
