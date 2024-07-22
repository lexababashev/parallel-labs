package on_client;
import java.io.*;
import java.net.*;


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

                // Отримання матриць від клієнта
                int[][] matrixA = (int[][]) in.readObject();
                int[][] matrixB = (int[][]) in.readObject();

;
                // Паралельне множення матриць
                int numThreads = 16;
                int[][] parallelResult = multiplyMatricesParallel(matrixA, matrixB, numThreads);

                out.writeObject(parallelResult);

                // Закриття з'єднання з клієнтом
                clientSocket.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Метод для паралельного множення матриць за допомогою стрічкового алгоритму з вказаною кількістю потоків
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