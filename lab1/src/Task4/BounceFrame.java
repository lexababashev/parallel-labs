package Task4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private final BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    public static final int BALL_COUNT = 10;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programm");
        this.canvas = new BallCanvas();

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {

                    Ball[] balls = new Ball[BALL_COUNT];
                    BallThread[] ballThreads = new BallThread[BALL_COUNT];

                    for (int i = 0; i < BALL_COUNT; i++) {
                        int red = (int) (Math.random() * 256);
                        int green = (int) (Math.random() * 256);
                        int blue = (int) (Math.random() * 256);
                        Color color = new Color(red, green, blue);

                        Ball b = new Ball(canvas, color);
                        canvas.add(b);
                        balls[i] = b;
                    }

                    for (int i = 0; i < BALL_COUNT; i++) {
                        if (i > 0) {
                            try {
                                ballThreads[i - 1].join(); // Зачекати завершення попереднього потоку
                            } catch (InterruptedException ex) {
                                System.err.println(ex.getMessage());
                            }
                        }
                        ballThreads[i] = new BallThread(balls[i]);
                        ballThreads[i].start();
                    }

                }).start();
            }
        });

        buttonStop.addActionListener(e -> System.exit(0));

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}