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

    public static double measureTimeOfPerformance(IMultiplier multiplier, int[][] A, int[][] B, int iterations, Result[] outResult) {
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();
            outResult[0] = multiplier.multiply(A, B);
            long endTime = System.currentTimeMillis();
            totalTime += (endTime - startTime);
        }
        return totalTime / (iterations * 1000.0);
    }

    public static void main(String[] args){
        int[] sizes = {1000};
        int[] threadsArray = {4};
        int iterations = 1;

        for (int size : sizes) {
            int[][] A = initMatrix(size);
            int[][] B = initMatrix(size);

            System.out.println("Size: " + size);

            IMultiplier sequentialMultiplier = new SequentialMultiplier();
            Result[] sequentialResult = new Result[1];
            double sequentialTime = measureTimeOfPerformance(sequentialMultiplier, A, B, iterations, sequentialResult);

            System.out.printf("Sequential: " + sequentialTime + "\n");

            //sequentialResult[0].printMatrix();
            //System.out.println();

            for (int threads : threadsArray) {
                IMultiplier parallelMultiplier = new ParallelMultiplier(threads);
                Result[] parallelResult = new Result[1];
                double parallelTime = measureTimeOfPerformance(parallelMultiplier, A, B, iterations, parallelResult);

                IMultiplier parallelFoxMultiplier = new ParallelFoxMultiplier(threads);
                Result[] parallelFoxResult = new Result[1];
                double foxTime = measureTimeOfPerformance(parallelFoxMultiplier, A, B, iterations, parallelFoxResult);

                System.out.printf("Threads: " + threads + " ");
                System.out.printf("Parallel: " + parallelTime + " ");
                System.out.printf("Parallel Fox: " + foxTime + "\n");

                if (MatrixHelper.NotEqual(sequentialResult[0].getMatrix(), parallelResult[0].getMatrix())) {
                    System.err.println("Sequential and Parallel results do not match for " + threads + " threads!");
                }

                if (MatrixHelper.NotEqual(sequentialResult[0].getMatrix(), parallelFoxResult[0].getMatrix())) {
                    System.err.println("Sequential and Parallel Fox results do not match for " + threads + " threads!");
                }
            }
            System.out.println();
        }
    }
}
