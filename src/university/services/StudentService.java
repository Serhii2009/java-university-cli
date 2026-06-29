package university.services;

import university.entities.Student;
import university.enums.StudentStatus;

public class StudentService {
    private static final int MAX_SIZE = 100;
    private final Student[] students = new Student[MAX_SIZE];
    private int count = 0;

    public Student addStudent(String name, String email, int yearOfStudy, StudentStatus status) {
        if (count >= MAX_SIZE) {
            throw new IllegalArgumentException("Storage full: cannot add more students (max " + MAX_SIZE + ")");
        }
        Student s = new Student(name, email, yearOfStudy, status);
        students[count++] = s;
        return s;
    }

    public void getAllStudents() {
        if (count == 0) {
            System.out.println("No students found.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(students[i]);
        }
    }

    public Student findById(int id) {
        return students[findIndexById(id)];
    }

    public void updateStudent(int id, String name, String email, int yearOfStudy, StudentStatus status) {
        Student s = findById(id);
        s.setName(name);
        s.setEmail(email);
        s.setYearOfStudy(yearOfStudy);
        s.setStatus(status);
        System.out.println("Updated: " + s);
    }

    public void deleteStudent(int id) {
        int idx = findIndexById(id);
        System.out.println("Deleted: " + students[idx]);
        for (int i = idx; i < count - 1; i++) {
            students[i] = students[i + 1];
        }
        students[--count] = null;
    }

    public void changeStatus(int id, StudentStatus newStatus) {
        Student s = findById(id);
        s.setStatus(newStatus);
        System.out.println("Status updated: " + s);
    }

    public void filterByStatus(StudentStatus status) {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (students[i].getStatus() == status) {
                System.out.println(students[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No students with status: " + status);
    }

    public void filterByYear(int year) {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (students[i].getYearOfStudy() == year) {
                System.out.println(students[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No students in year: " + year);
    }

    public void sortByNameBubble() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - 1 - i; j++) {
                if (students[j].getName().compareTo(students[j + 1].getName()) > 0) {
                    Student tmp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = tmp;
                }
            }
        }
        System.out.println("Students sorted by name:");
        getAllStudents();
    }

    public void findByNameOrEmail(String query) {
        String q = query.toLowerCase();
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (students[i].getName().toLowerCase().contains(q)
                    || students[i].getEmail().toLowerCase().contains(q)) {
                System.out.println(students[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No students matching: " + query);
    }

    public Student[] getStudentsArray() {
        Student[] copy = new Student[count];
        for (int i = 0; i < count; i++) copy[i] = students[i];
        return copy;
    }

    public int getCount() { return count; }

    private int findIndexById(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getId() == id) return i;
        }
        throw new IllegalArgumentException("Student not found with id: " + id);
    }
}
