package lab7;

//import java.util.Random;

public class SequentialMultiplier {
    static final int A_ROWS = 500;
    static final int A_COLS = 500;
    static final int B_COLS = 500;


    public static void multiply(int[][] a, int[][] b, int[][] c) {
        for (int i = 0; i < A_ROWS; i++) {
            for (int j = 0; j < B_COLS; j++) {
                for (int k = 0; k < A_COLS; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] A = new int[A_ROWS][A_COLS];
        int[][] B = new int[A_COLS][B_COLS];
        int[][] C = new int[A_ROWS][B_COLS];

        //Random random = new Random();
        for (int i = 0; i < A_ROWS; i++) {
            for (int j = 0; j < A_COLS; j++) {
                A[i][j] = 10;
                //A[i][j] = random.nextInt(11);
            }
        }

        for (int i = 0; i < A_COLS; i++) {
            for (int j = 0; j < B_COLS; j++) {
                B[i][j] = 10;
                //B[i][j] = random.nextInt(11);
            }
        }

        long start = System.currentTimeMillis();
        multiply(A, B, C);
        long end = System.currentTimeMillis();

        System.out.println("\nMatrix A:\n");
        MatrixHelper.printMatrix(A);

        System.out.println("\nMatrix B:\n");
        MatrixHelper.printMatrix(B);

        System.out.println("\nMatrix C:\n");
        MatrixHelper.printMatrix(C);

        System.out.println("\nSequential time: " + (end - start));
    }

}
