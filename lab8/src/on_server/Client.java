package on_server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 12345);
            System.out.println("\nПідключено до сервера: " + socket + "\n");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            long startTime = System.nanoTime(); // Початок вимірювання часу
            // Отримання результатів від сервера
            int[][] parallelResult = (int[][]) in.readObject();

            // Кінець вимірювання часу
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000; // в мілісекунди

            // Виведення результатів
            System.out.println("Паралельний результат:");
            printMatrix(parallelResult);

            System.out.println("Час виконання запиту: " + duration + " мс");

            // Закриття з'єднання
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
