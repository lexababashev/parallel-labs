package Task3;

import java.util.ArrayList;

public record Group(int id, ArrayList<Student> students) {
    public Student getStudent(int id) {
        return students.get(id);
    }
    public int getStudentsNumber() {
        return students.size();
    }

    public int getGroupNumber(){
        return id+11;
    }
    public int getgroupid(){
        return id;
    }
}