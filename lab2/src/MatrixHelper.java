public class MatrixHelper {
    public static boolean NotEqual(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return true;
        }
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}