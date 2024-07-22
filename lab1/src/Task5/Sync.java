package Task5;

public class Sync {
    private volatile boolean permission;
    private volatile boolean stop;
    private int symbols;
    private int lines;

    public Sync() {
        permission = true;
        stop = false;
        symbols = 0;
        lines = 0;
    }

    public boolean isStop() {
        return stop;
    }

    public synchronized void printSymbol(char symbol, boolean control){
        while (this.permission != control && !isStop()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(isStop()) {
            notifyAll();
            return;
        }

        System.out.print(symbol);
        permission = !permission;
        symbols++;

        if (symbols == 100) {
            symbols = 0;
            System.out.println();
            lines++;
        }

        if (lines == 100) {
            stop = true;
        }

        notifyAll();
    }
}