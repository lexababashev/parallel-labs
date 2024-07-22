public class Result {
    private final int[][] data;

    public Result(int rows, int cols) {
        data = new int[rows][cols];
    }

    public synchronized void  setData(int row, int col, int value) {
        data[row][col] = value;
    }

    public synchronized int getData(int row, int col) {
        return data[row][col];
    }
//synchronized
    public int[][] getMatrix() {
        return data;
    }

    public void printMatrix() {
        for (int[] row : data) {
            for (int val : row) {
                System.out.print(val + "\t");
            }
            System.out.println();
        }
    }
}
