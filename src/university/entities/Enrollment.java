package university.entities;

import university.enums.Grade;
import university.interfaces.Payable;

public class Enrollment implements Payable {
    private static int idCounter = 0;

    private final int id;
    private final Student student;
    private final Course course;
    private final String semester;
    private Grade grade = Grade.NA;
    private boolean paid = false;

    public Enrollment(Student student, Course course, String semester) {
        this.id = ++idCounter;
        this.student = student;
        this.course = course;
        this.semester = semester;
    }

    public int getId() { return id; }

    public Student getStudent() { return student; }

    public Course getCourse() { return course; }

    public String getSemester() { return semester; }

    public Grade getGrade() { return grade; }

    public void setGrade(Grade grade) { this.grade = grade; }

    @Override
    public void markAsPaid() { this.paid = true; }

    @Override
    public boolean isPaid() { return paid; }

    @Override
    public String toString() {
        return "Enrollment[id=" + id + ", student=" + student.getName()
                + ", course=" + course.getName() + ", semester=" + semester
                + ", grade=" + grade + ", paid=" + paid + "]";
    }
}
