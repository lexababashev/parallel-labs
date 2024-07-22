package on_client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("\nПідключено до сервера: " + socket + "\n");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            long startTime = System.nanoTime();
            int SIZE = 500;
            int[][] matrixA = generateMatrix(SIZE, SIZE);
            int[][] matrixB = generateMatrix(SIZE, SIZE);

            // Надсилання матриць на сервер
            out.writeObject(matrixA);
            out.writeObject(matrixB);

            // Отримання результатів від сервера
            int[][] parallelResult = (int[][]) in.readObject();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000; // Конвертація часу в секунди

            // Обчислення послідовного результату на клієнті
            SequentialMatrixMultiplier sequentialMultiplier = new SequentialMatrixMultiplier(matrixA, matrixB);
            int[][] sequentialResult = sequentialMultiplier.multiplyMatrices();

            // Порівняння результатів
            boolean isEqual = isEqual(sequentialResult, parallelResult);

            System.out.println("Паралельний результат:");
            printMatrix(parallelResult);
            //System.out.println("Послідовний результат:");
            //printMatrix(sequentialResult);
            System.out.println("Результати корерктні: " + isEqual);
            System.out.println("Час виконання запиту: " + duration + " мс");

            // Закриття з'єднання
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Random random = new Random();
    public static int[][] generateMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(2);
            }
        }
        return matrix;
    }

    // Метод для порівняння двох матриць
    public static boolean isEqual(int[][] matrixA, int[][] matrixB) {
        if (matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) {
            return false;
        }

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
