package Task5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Оберіть метод виводу: 1 - простий, 2 - синхр");
        int method = scanner.nextInt();

        switch (method) {
            case 1:
                new Thread(new PrintCharThread('-')).start();
                new Thread(new PrintCharThread('|')).start();
                break;
            case 2:
                Sync permission = new Sync();
                new Thread(new PrintCharThreadSync( '-',permission,true)).start();
                new Thread(new PrintCharThreadSync( '|',permission,false)).start();
                break;
            default:
                System.out.println("Невірний вибір. Завершення програми.");
        }
    }
}