package on_server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Сервер запущено. Очікування на з'єднаня...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клієнт підключився: " + clientSocket);

                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                int SIZE = 500;
                int[][] matrixA = generateMatrix(SIZE, SIZE);
                int[][] matrixB = generateMatrix(SIZE, SIZE);

                // Паралельне множення матриць
                int numThreads = 16;
                int[][] parallelResult = multiplyMatricesParallel(matrixA, matrixB, numThreads);


                // Відправка результатів клієнту
                out.writeObject(parallelResult);


                // Закриття з'єднання з клієнтом
                clientSocket.close();

            }
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

    // Метод для паралельного множення матриць за допомогою стрічкового алгоритму
    public static int[][] multiplyMatricesParallel(int[][] matrixA, int[][] matrixB, int numThreads) {
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;
        int[][] result = new int[rowsA][colsB];

        // Обчислення кількості рядків матриці matrixA для кожного потоку
        int rowsPerThread = (int) Math.ceil((double) rowsA / numThreads);

        // Створення та запуск потоків
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int startRow = i * rowsPerThread;
            int endRow = Math.min((i + 1) * rowsPerThread, rowsA);
            threads[i] = new Thread(new MatrixMultiplier(matrixA, matrixB, result, startRow, endRow));
            threads[i].start();
        }

        // Очікування завершення усіх потоків
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}