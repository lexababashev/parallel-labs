package Task6;

class DecThread extends Thread {
    private final CounterBase value;

    public DecThread(CounterBase value) {
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            value.decrement();
        }
    }
}