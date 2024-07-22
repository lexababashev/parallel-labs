package Task3;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Journal {
    private final ArrayList<Group> groups;
    private final int weeks;
    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> marks = new ConcurrentHashMap<>();

    public Journal(ArrayList<Group> groups, int weeks) {
        this.groups = groups;
        this.weeks = weeks;
    }

    public int getWeeks() {
        return weeks;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void addMark(int mark, int week, int groupId, int studentId) {
        Group group = groups.get(groupId);
        Student student = group.getStudent(studentId);

        String key = student.getId() + "-" + group.id();

        ConcurrentHashMap<Integer, Integer> weekMarks = marks.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
        weekMarks.putIfAbsent(week, mark);
    }

    public int getMark(int week, int groupId, int studentId) {
        Group group = groups.get(groupId);
        Student student = group.getStudent(studentId);

        String key = student.getId() + "-" + group.id();
        ConcurrentHashMap<Integer, Integer> weekMark = marks.get(key);

        return weekMark.getOrDefault(week, -1);
    }

    public void printMarks() {

        System.out.println("\n----------------------------------\n");

        for (Group group : groups) {
            System.out.println("ІП-" + group.getGroupNumber());
            for (int week = 1; week <= weeks; week++) {
                if (week == 1) {
                    System.out.printf("%-12s", " ");
                }
                System.out.printf("%-10d", week);
            }

            System.out.println();
            System.out.println();

            for (Student student : group.students()) {
                System.out.print("Студент " + student.getId() + ":  ");
                for (int week = 1; week <= weeks; week++) {
                    int mark = getMark(week, group.id(), student.getId());
                    System.out.printf("%-10d", mark);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}