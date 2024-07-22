package Task5;

public class PrintCharThreadSync implements Runnable {
    private final char symbol;

    private final Sync permission;
    private final boolean control;

    public PrintCharThreadSync(char symbol, Sync permission, boolean control) {
        this.symbol = symbol;
        this.permission = permission;
        this.control = control;
    }

    @Override
    public void run() {
            while (!permission.isStop()) {
                permission.printSymbol(symbol, control);
            }
    }
}