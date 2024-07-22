package Task1;

public class TransferThread extends Thread{

    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;
    private static final int REPS = 1000;
    private final int manageMethod;

    public TransferThread(Bank b, int from, int max, int manageMethod){
        bank = b;
        fromAccount = from;
        maxAmount = max;
        this.manageMethod = manageMethod;
    }

    @Override
    public void run(){
        while (true) {
            for (int i = 0; i < REPS; i++) {
                int toAccount = (int) (bank.size() * Math.random());
                int amount = (int) (maxAmount * Math.random()/REPS);

                // Check the chosen transfer method
                switch (manageMethod) {
                    case 1:
                        bank.transfer(fromAccount, toAccount, amount);
                        break;
                    case 2:
                        bank.transferLock(fromAccount, toAccount, amount);
                        break;
                    case 3:
                        bank.transferSyncB(fromAccount, toAccount, amount);
                        break;
                    case 4:
                        bank.transferSyncM(fromAccount, toAccount, amount);
                        break;
                    default:
                        bank.transfer(fromAccount, toAccount, amount); // Default method
                        break;
                }
            }
        }
    }
}
