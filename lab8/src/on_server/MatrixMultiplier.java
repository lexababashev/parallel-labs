package on_server;

public class MatrixMultiplier implements Runnable {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] result;
    private int startRow;
    private int endRow;

    public MatrixMultiplier(int[][] matrixA, int[][] matrixB, int[][] result, int startRow, int endRow) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = result;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    public void run() {
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }
}

