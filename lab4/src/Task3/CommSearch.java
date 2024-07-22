package Task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class CommSearch {

    private static List<String> docReader(String directoryPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            return paths.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .map(CommSearch::fileReader)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private static String fileReader(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    static class CommonWordsTask extends RecursiveTask<Set<String>> {
        private static final int THRESHOLD = 1;
        private final List<String> documents;
        private final int startIndex;
        private final int endIndex;

        public CommonWordsTask(List<String> documents, int startIndex, int endIndex) {
            this.documents = documents;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        protected Set<String> compute() {
            if (endIndex - startIndex <= THRESHOLD) {
                return findCommonWordsInDocuments(documents.subList(startIndex, endIndex));

            } else {

                int midIndex = (startIndex + endIndex) / 2;

                CommonWordsTask task1 = new CommonWordsTask(documents, startIndex, midIndex);
                CommonWordsTask task2 = new CommonWordsTask(documents, midIndex, endIndex);
                invokeAll(task1, task2);

                Set<String> res1 = task1.join();
                Set<String> res2 = task2.join();

                res1.retainAll(res2);
                return res1;
            }
        }

        private Set<String> findCommonWordsInDocuments(List<String> docs) {
            if (docs.isEmpty()) {
                return new HashSet<>();
            }

            Set<String> commonWords = Arrays.stream(docs.get(0).split("\\W+"))
                    .collect(Collectors.toSet());

            for (int i = 1; i < docs.size(); i++) {
                Set<String> wordsInDoc = Arrays.stream(docs.get(i).split("\\W+"))
                        .collect(Collectors.toSet());
                commonWords.retainAll(wordsInDoc);
            }

            return commonWords;
        }
    }

    public static void main(String[] args) {
        // Читаємо документи з директорії
        List<String> documents = docReader("D:\\kpi 6\\ТПО\\parallel-labs\\lab4\\commonWords");

        ForkJoinPool pool = new ForkJoinPool();
        CommonWordsTask task = new CommonWordsTask(documents, 0, documents.size());
        Set<String> commonWords = pool.invoke(task);

        // Виведення результатів
        System.out.println("Кількість слів у кожному файлі:");
        for (int i = 0; i < documents.size(); i++) {
            int wordCount = Arrays.stream(documents.get(i).split("\\W+")).filter(word -> !word.isEmpty()).toArray().length;
            System.out.println("Документ " + (i + 1) + ": " + wordCount);
        }

        System.out.println("Кількість спільних слів: " + commonWords.size());
        System.out.println("Спільні слова: " + commonWords);
    }

}
