package Task6;

public class Main {
    public static void main(String[] args) {

        Counter counter = new Counter();
        runThreads(counter);

        LockCounter lockCounter = new LockCounter();
        runThreads(lockCounter);

        SyncBlockCounter syncBlockCounter = new SyncBlockCounter();
        runThreads(syncBlockCounter);

        SyncMethodCounter syncMethodCounter = new SyncMethodCounter();
        runThreads(syncMethodCounter);
    }

    private static void runThreads(CounterBase value) {
        IncThread incrementThread = new IncThread(value);
        DecThread decrementThread = new DecThread(value);

        incrementThread.start();
        decrementThread.start();

        try {
            incrementThread.join();
            decrementThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(value.getClass().getSimpleName() + " = " + value.getValue());
    }

}