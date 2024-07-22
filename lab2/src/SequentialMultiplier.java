public class SequentialMultiplier implements IMultiplier {
    @Override
    public Result multiply(int[][] A, int[][] B) {
        int n = A.length;
        Result result = new Result(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += A[i][k] * B[k][j];
                }
                result.setData(i, j, sum);
            }
        }
        return result;
    }
}