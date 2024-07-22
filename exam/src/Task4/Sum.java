package Task4;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Sum {
    public static void main(String[] args) {

        // Створюємо масив з 1001 елементом і заповнюємо його значеннями 1
        double[] array = new double[1001];
        for (int i = 0; i < array.length; ++i) {
            array[i] = 1;
        }

        // Створюємо пул ForkJoin з 8 робочими потоками
        ForkJoinPool pool = new ForkJoinPool(8);

        // Викликаємо рекурсивну задачу Task, щоб обчислити суму масиву
        double sum = pool.invoke(new Task(array, 0, array.length));

        // Вимикаємо пул ForkJoin після виконання задачі
        pool.shutdown();

        // Виводимо загальну суму масиву
        System.out.println("Total sum: " + sum);
    }

    // Внутрішній клас, що реалізує RecursiveTask для обчислення суми частини масиву
    static class Task extends RecursiveTask<Double> {
        private final double[] array;
        private final int start;
        private final int end;
        private static final int THRESHOLD = 100;

        // Конструктор для ініціалізації полів задачі
        Task(double[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        // Метод compute(), який обчислює суму частини масиву
        @Override
        protected Double compute() {

            int length = end - start;

            // Якщо довжина частини масиву менша або дорівнює пороговому значенню THRESHOLD,
            // обчислюємо суму без використання рекурсії
            if (length <= THRESHOLD) {
                return Arrays.stream(array, start, end).sum();
            } else {
                // В іншому випадку ділимо задачу на дві підзадачі

                int mid = start + length / 2;

                // Створюємо ліву і праву підзадачі
                Task leftTask = new Task(array, start, mid);
                Task rightTask = new Task(array, mid, end);

                // Запускаємо ліву підзадачу асинхронно
                leftTask.fork();

                // Обчислюємо результат правої підзадачі синхронно
                double rightResult = rightTask.compute();

                // Очікуємо і отримуємо результат лівої підзадачі
                double leftResult = leftTask.join();

                // Повертаємо суму результатів лівої і правої підзадач
                return leftResult + rightResult;
            }
        }
    }
}
