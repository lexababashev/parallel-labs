package Task6;

class IncThread extends Thread {
    private final CounterBase value;

    public IncThread(CounterBase value) {
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            value.increment();
        }
    }
}