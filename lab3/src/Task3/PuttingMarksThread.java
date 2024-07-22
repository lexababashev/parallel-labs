package Task3;

import java.util.ArrayList;

public class PuttingMarksThread extends Thread {
    String teacherPost;
    ArrayList<Group> groups;
    private final Journal journal;

    public PuttingMarksThread(String teacherPost, Journal journal, ArrayList<Group> groups) {
        this.groups = groups;
        this.journal = journal;
        this.teacherPost = teacherPost;
    }

    public int generateMark() {
        return (int) (Math.random() * 101);
    }

    @Override
    public void run() {
        for (int week = 1; week <= journal.getWeeks(); week++) {

            for (int groupid = 0; groupid < groups.size(); groupid++) {

                int numStudents = journal.getGroups().get(groupid).getStudentsNumber();

                for (int studentid = 0; studentid < numStudents; studentid++) {

                    int mark = generateMark();

                    journal.addMark(mark, week, groupid, studentid);

                    System.out.printf("%-12s Тиждень %-7d Група %-7d Студент %-7d %-7d\n",
                            teacherPost, week, groupid+11, studentid, mark);

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}