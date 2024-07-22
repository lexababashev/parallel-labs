package Task6;

class SyncMethodCounter extends CounterBase {
    private int value = 0;
    @Override
    public synchronized void increment() {
        value++;
    }
    @Override
    public synchronized void decrement() {
        value--;
    }
    @Override
    public synchronized int getValue() {
        return value;
    }
}