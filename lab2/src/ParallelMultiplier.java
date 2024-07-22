public class ParallelMultiplier implements IMultiplier {
    private final int threads;

    public ParallelMultiplier(int threads) {
        this.threads = threads;
    }

    @Override
    public Result multiply(int[][] A, int[][] B) {
        int n = A.length;
        Result result = new Result(n, n);
        Thread[] threads = new Thread[this.threads];

        for (int i = 0; i < this.threads; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                int chunkSize = n / this.threads;
                int startRow = threadIndex * chunkSize;
                int endRow = (threadIndex == this.threads - 1) ? n : startRow + chunkSize;

                for (int row = startRow; row < endRow; row++) {
                    for (int col = 0; col < n; col++) {
                        int sum = 0;
                        for (int k = 0; k < n; k++) {
                            sum += A[row][k] * B[k][col];
                        }
                        result.setData(row, col, sum);
                    }
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
