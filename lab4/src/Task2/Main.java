package Task2;

import java.util.Random;

public class Main {
    private static final Random random = new Random();

    public static int[][] initMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt(11);
            }
        }
        return matrix;
    }

    public static double measureTimeOfPerformance(IMultiplier multiplier, int[][] A, int[][] B, Result[] outResult) {
        long totalTime = 0;
        long startTime = System.currentTimeMillis();
        outResult[0] = multiplier.multiply(A, B);
        long endTime = System.currentTimeMillis();
        totalTime += (endTime - startTime);
        return totalTime;
    }

    public static void main(String[] args){

        int size = 500;
        int threads = 4;
        int[][] A = initMatrix(size);
        int[][] B = initMatrix(size);

        IMultiplier parallelMultiplier = new ParallelMultiplier(threads);
        Result[] parallelResult = new Result[1];
        double parallelTime = measureTimeOfPerformance(parallelMultiplier, A, B, parallelResult);
        System.out.printf("Parallel: " + parallelTime + " ms\n");

        IMultiplier ForkJoinMultiplier = new ForkJoinMultiplier(threads);
        Result[] forkJoinResult = new Result[1];
        double forkJoinTime = measureTimeOfPerformance(ForkJoinMultiplier, A, B, forkJoinResult);
        System.out.printf("ForkJoin: " + forkJoinTime + " ms\n");

//        System.out.println();
//        MatrixHelper.PrintMatrix(parallelResult[0].getMatrix());
//        System.out.println();
//        MatrixHelper.PrintMatrix(forkJoinResult[0].getMatrix());
//        System.out.println();


        if (MatrixHelper.NotEqual(forkJoinResult[0].getMatrix(), parallelResult[0].getMatrix())) {
            System.err.println("\nresults NOT match!");
        } else {
            System.out.println("\nresults match!");
        }

        double speedup = parallelTime / forkJoinTime;
        System.out.printf("\nSpeedup: %.2f\n", speedup);

        System.out.println();
    }

}
