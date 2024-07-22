package Task1;

public class Statistics {
    private int totalWords;
    private int totalLength;

    public Statistics(int totalWords, int totalLength) {
        this.totalWords = totalWords;
        this.totalLength = totalLength;
    }

    public void add(Statistics other) {
        this.totalWords += other.totalWords;
        this.totalLength += other.totalLength;
    }

    public void print() {
        System.out.println("Total words: " + totalWords);
        System.out.println("Total length: " + totalLength);
        System.out.println("Average word length: " + (totalWords == 0 ? 0 : (double) totalLength / totalWords));
    }
}
