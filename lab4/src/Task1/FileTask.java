package Task1;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.RecursiveTask;

public class FileTask extends RecursiveTask<Statistics> {
    private Path file;

    public FileTask(Path file) {
        this.file = file;
    }

    @Override
    protected Statistics compute() {
        try {
            String text = TextReader.readFile(file);
            return SequentialWordLengthStatistics.analyze(text);
        } catch (IOException e) {
            e.printStackTrace();
            return new Statistics(0, 0);
        }
    }
}


