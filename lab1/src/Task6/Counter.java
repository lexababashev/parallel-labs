package Task6;

public class Counter extends CounterBase {
    private int value = 0 ;

    @Override
    public void increment() {
        value++;
    }

    @Override
    public void decrement() {
        value--;
    }

    @Override
    public int getValue() {
        return value;
    }
}
