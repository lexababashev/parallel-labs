package Task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class TextReader {
    public static String readFile(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    public static List<Path> getAllTextFiles(String directoryPath) throws IOException {
        return Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .collect(Collectors.toList());
    }
}
