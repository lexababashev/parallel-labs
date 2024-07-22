package Task4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class DocSearch {

    static class DocSearchTask extends RecursiveAction {
        private final Path directory;

        public DocSearchTask(Path directory) {
            this.directory = directory;
        }

        @Override
        protected void compute() {
            if (Files.isDirectory(directory)) {
                try {
                    List<DocSearchTask> subTasks = new ArrayList<>();

                    Files.list(directory).forEach(path -> {
                        subTasks.add(new DocSearchTask(path));
                    });
                    invokeAll(subTasks);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            } else {
                fileSearcher(directory);
            }
        }

        private static final Set<String> KEYWORDS = Set.of("machine learning", "deep learning", "natural language processing",
                "mobile development", "Android", "iOS", "cross-platform development", "native apps", "hybrid apps",
                "game development", "game engines", "graphics", "animation", "artificial intelligence", "virtual reality",
                "software","cloud", "network", "algorithm", "architecture", "database", "web", "security", "server");

        private void fileSearcher(Path filePath) {
            try {

                Set<String> foundKeywords = new HashSet<>();
                String content = Files.readString(filePath);
                String[] words = content.toLowerCase().split("\\W+");
                for (String word : words) {
                    if (KEYWORDS.contains(word)) {
                        foundKeywords.add(word);
                    }
                }
                if (!foundKeywords.isEmpty()) {
                    System.out.println("\nФайл на задану тематику: " + filePath.getFileName());
                    System.out.println("Знайдені слова: " + foundKeywords + "\n");

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        DocSearchTask task = new DocSearchTask(Paths.get("D:\\kpi 6\\ТПО\\parallel-labs\\lab4\\books"));
        pool.invoke(task);
        pool.shutdown();
    }
}

