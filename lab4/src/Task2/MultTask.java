package Task2;

import java.util.concurrent.RecursiveTask;

public class MultTask extends RecursiveTask<Void> {

    private static final int THRESHOLD = 4;

    private final int[][] A;
    private final int[][] B;
    private final Result result;
    private final int startRow;
    private final int endRow;

    public MultTask(int[][] A, int[][] B, Result result, int startRow, int endRow) {
        this.A = A;
        this.B = B;
        this.result = result;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    protected Void compute() {
        int n = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;

        if (endRow - startRow <= THRESHOLD) {
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < colsB; j++) {
                    int sum = 0;
                    for (int k = 0; k < colsA; k++) {
                        sum += A[i][k] * B[k][j];
                    }
                    result.setData(i, j, sum);
                }
            }
        } else {
            int midRow = (startRow + endRow) / 2;

            MultTask task1 = new MultTask(A, B, result, startRow, midRow);
            MultTask task2 = new MultTask(A, B, result, midRow, endRow);
            invokeAll(task1, task2);
        }

        return null;
    }
}
