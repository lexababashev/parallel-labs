package Task2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private final BallCanvas canvas;
    private final Sink sink;
    private final JLabel sinkLabel = new JLabel("0");
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    public static final int BALL_COUNT = 100;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");
        this.canvas = new BallCanvas();

        this.sink = new Sink();
        canvas.addSink(sink);

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);


        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");


        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 1; i <= BALL_COUNT; i++) {
                    Ball b = new Ball(canvas, sink);
                    canvas.add(b);

                    BallThread thread = new BallThread(b, sink, canvas);
                    thread.start();
                }
            }
        });
        buttonStop.addActionListener(e -> System.exit(0));

        // buttonStop.addActionListener(new ActionListener() {
        //   @Override
        //  public void actionPerformed(ActionEvent e) {System.exit(0);
        // }
        //});

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);

        JPanel labelPanel = new JPanel();
        labelPanel.add(sinkLabel);
        labelPanel.setBackground(Color.lightGray);
        content.add(labelPanel, BorderLayout.NORTH);

        canvas.setBounceFrame(this);
    }
    public void updateSinkLabel(int ballsInSink) {
        sinkLabel.setText(String.valueOf(ballsInSink));
    }
}