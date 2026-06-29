package university.entities;

import university.enums.StudentStatus;

public class Student extends Person {
    private int yearOfStudy;
    private StudentStatus status;

    public Student(String name, String email, int yearOfStudy, StudentStatus status) {
        super(name, email);
        setYearOfStudy(yearOfStudy);
        this.status = status;
    }

    public int getYearOfStudy() { return yearOfStudy; }

    public void setYearOfStudy(int yearOfStudy) {
        if (yearOfStudy < 1 || yearOfStudy > 6) {
            throw new IllegalArgumentException("Year of study must be between 1 and 6, got: " + yearOfStudy);
        }
        this.yearOfStudy = yearOfStudy;
    }

    public StudentStatus getStatus() { return status; }

    public void setStatus(StudentStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Student[id=" + getId() + ", name=" + getName() + ", email=" + getEmail()
                + ", year=" + yearOfStudy + ", status=" + status + "]";
    }
}
