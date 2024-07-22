package Task2;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel{
    private final ArrayList<Ball> balls = new ArrayList<>();
    private Sink sink;
    private BounceFrame bounceFrame;

    public void add(Ball b){
        this.balls.add(b);
    }

    public void addSink(Sink sink) {
        this.sink = sink;
    }

    public void setBounceFrame(BounceFrame bounceFrame) {
        this.bounceFrame = bounceFrame;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for(int i=0; i<balls.size();i++){
            Ball b = balls.get(i);
            if (b != null) {
                b.draw(g2);
            }
        }
        g2.setColor(Color.BLACK);
        if (sink != null) {
            sink.draw(g2);
        }
    }
    public void updateSinkCounter() {
        int ballsInSink = sink.getBallsInSink();
        bounceFrame.updateSinkLabel(ballsInSink);
    }

    public synchronized void remove(Ball b) {
        balls.remove(b);
        repaint();
    }

    public boolean containsBall(Ball b) {
        return balls.contains(b);
    }
}