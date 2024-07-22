public class ParallelFoxMultiplier implements IMultiplier {
    private final int threads;

    public ParallelFoxMultiplier(int threads) {
        this.threads = threads;
    }

    @Override
    public Result multiply(int[][] A, int[][] B) {
        int n = A.length;
        Result result = new Result(n, n);
        int blockSize = (int) Math.ceil((double) n / threads);
        Thread[] threads = new Thread[this.threads];

        for (int threadId = 0; threadId < this.threads; threadId++) {
            int finalThreadId = threadId;
            threads[threadId] = new Thread(() -> {

                for (int block = 0; block < this.threads; block++) {
                    int startRow = finalThreadId * blockSize;
                    int endRow = Math.min((finalThreadId + 1) * blockSize, n);

                    for (int i = startRow; i < endRow; i++) {
                        for (int j = 0; j < n; j++) {
                            int sum = 0;
                            for (int k = block * blockSize; k < Math.min((block + 1) * blockSize, n); k++) {
                                sum += A[i][k] * B[k][j];
                            }
                            int currentValue = result.getData(i, j);
                            result.setData(i, j, currentValue + sum);
                        }
                    }
                }

            });
            threads[threadId].start();
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
