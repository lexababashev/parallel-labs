package Task2;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinMultiplier implements IMultiplier {

    private final int threads;

    public ForkJoinMultiplier(int threads) {
        this.threads = threads;
    }

    @Override
    public Result multiply(int[][] A, int[][] B) {
        int n = A.length;
        Result result = new Result(n, n);
        ForkJoinPool forkJoinPool = new ForkJoinPool(threads);

        // Create and invoke the ForkJoin task
        MultTask task = new MultTask(A, B, result, 0, n);
        forkJoinPool.invoke(task);
        return result;
    }
}
