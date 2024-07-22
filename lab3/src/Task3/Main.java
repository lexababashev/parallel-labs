package Task3;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Group> groups = generateGroups(3, 3);

        Journal journal = new Journal(groups, 3);

        PuttingMarksThread markThread1 = new PuttingMarksThread("Лектор", journal, groups);
        PuttingMarksThread markThread2 = new PuttingMarksThread("Асистент1", journal, groups);
        PuttingMarksThread markThread3 = new PuttingMarksThread("Асистент2", journal, groups);
        PuttingMarksThread markThread4 = new PuttingMarksThread("Асистент3", journal, groups);

        markThread1.start();
        markThread2.start();
        markThread3.start();
        markThread4.start();

            try {
                markThread1.join();
                markThread2.join();
                markThread3.join();
                markThread4.join();
            } catch (InterruptedException e) {
               //
            }

        journal.printMarks();

    }

    public static ArrayList<Student> generateStudents(int amountOfStudents) {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < amountOfStudents; i++) {
            students.add(new Student("Student " + i, i));
        }
        return students;
    }

    public static ArrayList<Group> generateGroups(int amountOfGroups, int amountOfStudents) {
        ArrayList<Group> groups = new ArrayList<>();
        for (int i = 0; i < amountOfGroups; i++) {
            groups.add(new Group(i, generateStudents(amountOfStudents)));
        }
        return groups;
    }

}