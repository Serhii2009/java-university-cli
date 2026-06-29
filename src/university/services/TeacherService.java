package university.services;

import university.entities.Teacher;
import university.enums.TeacherPosition;

public class TeacherService {
    private static final int MAX_SIZE = 100;
    private final Teacher[] teachers = new Teacher[MAX_SIZE];
    private int count = 0;

    public Teacher addTeacher(String name, String email, TeacherPosition position) {
        if (count >= MAX_SIZE) {
            throw new IllegalArgumentException("Storage full: cannot add more teachers (max " + MAX_SIZE + ")");
        }
        Teacher t = new Teacher(name, email, position);
        teachers[count++] = t;
        return t;
    }

    public void getAllTeachers() {
        if (count == 0) {
            System.out.println("No teachers found.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(teachers[i]);
        }
    }

    public Teacher findById(int id) {
        return teachers[findIndexById(id)];
    }

    public void updateTeacher(int id, String name, String email, TeacherPosition position) {
        Teacher t = findById(id);
        t.setName(name);
        t.setEmail(email);
        t.setPosition(position);
        System.out.println("Updated: " + t);
    }

    public void deleteTeacher(int id) {
        int idx = findIndexById(id);
        System.out.println("Deleted: " + teachers[idx]);
        for (int i = idx; i < count - 1; i++) {
            teachers[i] = teachers[i + 1];
        }
        teachers[--count] = null;
    }

    private int findIndexById(int id) {
        for (int i = 0; i < count; i++) {
            if (teachers[i].getId() == id) return i;
        }
        throw new IllegalArgumentException("Teacher not found with id: " + id);
    }
}
