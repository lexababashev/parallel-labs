package Task2;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {

        int size = 100;
        int[] importantInfo = new int[size];
        for (int i = 0; i < size; i++)
            importantInfo[i] = i+1;


        Random random = new Random();

        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {}
        }
        drop.put(-1);
    }
}
