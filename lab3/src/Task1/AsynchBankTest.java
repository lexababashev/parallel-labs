package Task1;
import java.util.Scanner;
/**
 author Cay Horstmann
 */

public class AsynchBankTest {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter 1 for Default, 2 for Lock, 3 for SyncBlock, 4 for SyncMethod: ");
        int chosenMethod = scanner.nextInt();

        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        int i;
        for (i = 0; i < NACCOUNTS; i++){
            TransferThread t = new TransferThread(b, i,
                    INITIAL_BALANCE,chosenMethod);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start () ;
        }
    }
}
