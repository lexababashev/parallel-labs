package Task1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;

    private final Lock transLock = new ReentrantLock();

    public Bank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }
    public void transfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }

    //модифікація
    public void transferLock(int from, int to, int amount) {
        transLock.lock();
        try{
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        }finally {
            transLock.unlock();
        }
    }

    public void transferSyncB(int from, int to, int amount) {
        synchronized (this) {
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        }
    }

    public synchronized void transferSyncM(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }

    public void test(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);
    }
    public int size(){
        return accounts.length;
    }
}
