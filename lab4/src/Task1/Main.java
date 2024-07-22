package Task1;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        try {
            String directoryPath = "D:\\kpi 6\\ТПО\\parallel-labs\\lab4\\books";
            Path path = Paths.get(directoryPath);

            // Послідовний аналіз
            long sequentialStartTime = System.currentTimeMillis();
            Statistics sequentialStatistics = new Statistics(0, 0);
            List<Path> files = TextReader.getAllTextFiles(directoryPath);
            for (Path file : files) {
                String text = TextReader.readFile(file);
                Statistics fileStatistics = SequentialWordLengthStatistics.analyze(text);
                sequentialStatistics.add(fileStatistics);
            }
            long sequentialEndTime = System.currentTimeMillis();
            long sequentialDuration = sequentialEndTime - sequentialStartTime;

            System.out.println("\nSequential Analysis:");
            sequentialStatistics.print();
            System.out.println("Sequential execution time: " + sequentialDuration + " ms");

            System.out.println("\n============================================\n");

            // Паралельний аналіз
            long parallelStartTime = System.currentTimeMillis();
            ForkJoinPool pool = new ForkJoinPool();
            DirectoryTask directoryTask = new DirectoryTask(path);
            Statistics parallelStatistics = pool.invoke(directoryTask);
            long parallelEndTime = System.currentTimeMillis();
            long parallelDuration = parallelEndTime - parallelStartTime;

            System.out.println("Parallel Analysis:");
            parallelStatistics.print();
            System.out.println("Parallel execution time: " + parallelDuration + " ms\n");

            double speedup = (double) sequentialDuration / parallelDuration;
            System.out.println("Speedup: " + speedup);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




