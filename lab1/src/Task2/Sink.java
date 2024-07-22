package Task2;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Sink {
    private int ballsInSink = 0;
    private static final int XSIZE = 40;
    private static final int YSIZE = 40;
    private final int width = 200;
    private final int height = 85;

    public Sink() {}
    public void incrementBallsInSink() {

        ballsInSink++;

    }

    public int getBallsInSink() {

        return ballsInSink;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fill(new Ellipse2D.Double(width, height, XSIZE, YSIZE));
        g2.setColor(Color.WHITE);
    }

    public boolean checkCollision(int x, int y) {
        return (x >= width && x <= width + XSIZE &&
                y >= height && y <= height + YSIZE);
    }
}
