package Task6;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockCounter extends CounterBase {
    private int value = 0;
    private final Lock lock = new ReentrantLock();
    @Override
    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }
    @Override
    public void decrement() {
        lock.lock();
        try {
            value--;
        } finally {
            lock.unlock();
        }
    }
    @Override
    public int getValue() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}