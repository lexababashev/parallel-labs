package Task1;

public class SequentialWordLengthStatistics {
    public static Statistics analyze(String text) {
        // Розбиваємо текст на слова, використовуючи пробіли як роздільники
        String[] words = text.split("\\s+");
        int totalWords = words.length;
        int totalLength = 0;
        for (String word : words) {
            totalLength += word.length();
        }
        return new Statistics(totalWords, totalLength);
    }
}
