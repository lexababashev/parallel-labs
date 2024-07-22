package Task6;

class SyncBlockCounter extends CounterBase {
    private int value = 0;

    @Override
    public void increment() {
        synchronized(this) {
            value++;
        }
    }
    @Override
    public void decrement() {
        synchronized(this) {
            value--;
        }
    }
    @Override
    public int getValue() {
        synchronized(this) {
            return value;
        }
    }
}