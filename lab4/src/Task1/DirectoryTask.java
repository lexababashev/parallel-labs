package Task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class DirectoryTask extends RecursiveTask<Statistics> {
    private Path directory;

    public DirectoryTask(Path directory) {
        this.directory = directory;
    }

    @Override
    protected Statistics compute() {
        List<RecursiveTask<Statistics>> tasks = new ArrayList<>();

        try {
            Files.list(directory).forEach(path -> {
                if (Files.isDirectory(path)) {
                    DirectoryTask directoryTask = new DirectoryTask(path);
                    directoryTask.fork();
                    tasks.add(directoryTask);
                } else if (path.toString().endsWith(".txt")) {
                    FileTask fileTask = new FileTask(path);
                    fileTask.fork();
                    tasks.add(fileTask);
                }
            });

            Statistics result = new Statistics(0, 0);
            for (RecursiveTask<Statistics> task : tasks) {
                result.add(task.join());
            }
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return new Statistics(0, 0);
        }
    }
}







