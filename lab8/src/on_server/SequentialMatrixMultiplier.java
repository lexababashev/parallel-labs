package on_server;

public class SequentialMatrixMultiplier {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] result;

    public SequentialMatrixMultiplier(int[][] matrixA, int[][] matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    public int[][] multiplyMatrices() {
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;
        int colsA = matrixA[0].length;
        result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }
}

