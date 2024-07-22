package Task3;

import mpi.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MPI_buckets {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args); // Ініціалізація MPI

        int rank = MPI.COMM_WORLD.Rank(); // Отримання номера поточного процесу
        int size = MPI.COMM_WORLD.Size(); // Отримання загальної кількості процесів

        int n = 100; // Розмір масиву для сортування
        int numBuckets = size - 1; // Кількість відр для бакет-сортування

        char[] array = new char[n]; // Основний масив для сортування
        char[] sortedArray = new char[n]; // Масив для зберігання відсортованих даних

        if (rank == 0) { // Логіка для головного процесу (розподілюючого)

            // Генерація випадкового масиву символів 'A' до 'Z'
            for (int i = 0; i < n; i++) {
                array[i] = (char) ('A' + (int) (Math.random() * 26));
            }

            // Знаходження мінімального і максимального значень у масиві
            char minValue = array[0];
            char maxValue = array[0];
            for (int i = 1; i < n; i++) {
                if (array[i] < minValue) {
                    minValue = array[i];
                }
                if (array[i] > maxValue) {
                    maxValue = array[i];
                }
            }

            // Визначення інтервалу для кожного відра
            double interval = (double) (maxValue - minValue + 1) / numBuckets;

            // Створення списку відер (бакетів)
            ArrayList<ArrayList<Character>> buckets = new ArrayList<>();
            for (int i = 0; i < numBuckets; i++) {
                buckets.add(new ArrayList<>());
            }

            // Розподілення елементів масиву по відрам
            for (int i = 0; i < n; i++) {
                int bucketIndex = (int) ((array[i] - minValue) / interval);
                if (bucketIndex >= numBuckets) bucketIndex = numBuckets - 1;
                buckets.get(bucketIndex).add(array[i]);
            }

            // Відправлення відсортованих відер процесам
            for (int i = 1; i < size; i++) {
                ArrayList<Character> bucket = buckets.get(i - 1);

                // Перетворення ArrayList у масив char для відправки
                char[] bucketArray = new char[bucket.size()];
                for (int j = 0; j < bucket.size(); j++) {
                    bucketArray[j] = bucket.get(j);
                }

                // Відправлення розміру та самого масиву
                int[] bucketSize = new int[]{bucketArray.length};
                // Відправлення розміру відра (bucketSize) до процесу і (параметр rank)
                MPI.COMM_WORLD.Send(bucketSize, 0, 1, mpi.MPI.INT, i, 0);
                MPI.COMM_WORLD.Send(bucketArray, 0, bucketArray.length, mpi.MPI.CHAR, i, 1);
            }

            // Отримання та збірка відсортованих даних від процесів
            int offset = 0;
            for (int i = 1; i < size; i++) {
                int[] bucketSize = new int[1];
                MPI.COMM_WORLD.Recv(bucketSize, 0, 1, mpi.MPI.INT, i, 0);

                char[] sortedBucket = new char[bucketSize[0]];
                MPI.COMM_WORLD.Recv(sortedBucket, 0, bucketSize[0], mpi.MPI.CHAR, i, 1);

                // Копіювання відсортованого відра в основний масив
                System.arraycopy(sortedBucket, 0, sortedArray, offset, bucketSize[0]);
                offset += bucketSize[0];
            }

            // Виведення відсортованого масиву
            System.out.println("Відсортований масив: " + Arrays.toString(sortedArray));
            System.out.println("Довжина масиву: " + sortedArray.length);

        } else { // Логіка для інших процесів (сортувальників)

            // Отримання розміру та самого відра для сортування
            int[] bucketSize = new int[1];
            MPI.COMM_WORLD.Recv(bucketSize, 0, 1, mpi.MPI.INT, 0, 0);

            char[] bucketArray = new char[bucketSize[0]];
            MPI.COMM_WORLD.Recv(bucketArray, 0, bucketSize[0], mpi.MPI.CHAR, 0, 1);

            // Сортування отриманого відра
            Arrays.sort(bucketArray);
            System.out.println("Процес " + rank + " відсортоване відро: " + Arrays.toString(bucketArray));

            // Відправлення відсортованого відра назад до головного процесу
            MPI.COMM_WORLD.Send(new int[]{bucketArray.length}, 0, 1, mpi.MPI.INT, 0, 0);
            MPI.COMM_WORLD.Send(bucketArray, 0, bucketArray.length, mpi.MPI.CHAR, 0, 1);
        }

        MPI.Finalize(); // Завершення роботи MPI
    }
}
