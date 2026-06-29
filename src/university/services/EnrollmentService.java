package university.services;

import university.entities.Course;
import university.entities.Enrollment;
import university.entities.Student;
import university.enums.Grade;
import university.util.GPAUtils;

public class EnrollmentService {
    private static final int MAX_SIZE = 100;
    private final Enrollment[] enrollments = new Enrollment[MAX_SIZE];
    private int count = 0;

    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enroll(int studentId, int courseId, String semester) {
        if (count >= MAX_SIZE) {
            throw new IllegalArgumentException("Storage full: cannot add more enrollments (max " + MAX_SIZE + ")");
        }
        Student student = studentService.findById(studentId);
        Course course = courseService.findById(courseId);
        Enrollment e = new Enrollment(student, course, semester);
        enrollments[count++] = e;
        return e;
    }

    public void getAllEnrollments() {
        if (count == 0) {
            System.out.println("No enrollments found.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(enrollments[i]);
        }
    }

    public void setGrade(int enrollmentId, Grade grade) {
        Enrollment e = findById(enrollmentId);
        e.setGrade(grade);
        System.out.println("Grade set: " + e);
    }

    public void markPaid(int enrollmentId) {
        Enrollment e = findById(enrollmentId);
        e.markAsPaid();
        System.out.println("Marked as paid: " + e);
    }

    public void getStudentEnrollments(int studentId) {
        studentService.findById(studentId);
        boolean found = false;
        Enrollment[] buf = new Enrollment[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudent().getId() == studentId) {
                System.out.println(enrollments[i]);
                buf[n++] = enrollments[i];
                found = true;
            }
        }
        if (!found) {
            System.out.println("No enrollments for student id: " + studentId);
        } else {
            double gpa = GPAUtils.calculateGPA(buf, n);
            if (n == 0 || allNA(buf, n)) {
                System.out.printf("GPA: %.2f (no graded courses)%n", gpa);
            } else {
                System.out.printf("GPA: %.2f%n", gpa);
            }
        }
    }

    public void printTranscript(int studentId) {
        Student student = studentService.findById(studentId);
        Enrollment[] buf = new Enrollment[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudent().getId() == studentId) {
                buf[n++] = enrollments[i];
            }
        }

        System.out.println("=== TRANSCRIPT ===");
        System.out.printf("Student : %s%n", student.getName());
        System.out.printf("Year    : %d  |  Status: %s%n", student.getYearOfStudy(), student.getStatus());
        System.out.println("-----------------------------------------");
        System.out.printf("%-15s | %-20s | %-5s | %s%n", "Semester", "Course", "Grade", "Paid");
        System.out.println("-----------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-15s | %-20s | %-5s | %s%n",
                    buf[i].getSemester(),
                    buf[i].getCourse().getName(),
                    buf[i].getGrade(),
                    buf[i].isPaid() ? "Yes" : "No");
        }
        System.out.println("-----------------------------------------");
        double gpa = GPAUtils.calculateGPA(buf, n);
        if (n == 0 || allNA(buf, n)) {
            System.out.printf("GPA: %.2f (no graded courses)%n", gpa);
        } else {
            System.out.printf("GPA: %.2f%n", gpa);
        }
    }

    public void getUnpaidEnrollments() {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (!enrollments[i].isPaid()) {
                System.out.println(enrollments[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No unpaid enrollments.");
    }

    public double getAverageGPA(int courseId) {
        courseService.findById(courseId);
        Enrollment[] buf = new Enrollment[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getCourse().getId() == courseId) {
                buf[n++] = enrollments[i];
            }
        }
        return GPAUtils.calculateGPA(buf, n);
    }

    public double calculateStudentGPA(int studentId) {
        Enrollment[] buf = new Enrollment[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getStudent().getId() == studentId) {
                buf[n++] = enrollments[i];
            }
        }
        return GPAUtils.calculateGPA(buf, n);
    }

    private Enrollment findById(int id) {
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getId() == id) return enrollments[i];
        }
        throw new IllegalArgumentException("Enrollment not found with id: " + id);
    }

    private boolean allNA(Enrollment[] arr, int n) {
        for (int i = 0; i < n; i++) {
            if (arr[i].getGrade() != Grade.NA) return false;
        }
        return true;
    }
}
