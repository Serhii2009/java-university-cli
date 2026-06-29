package university;

import university.enums.Grade;
import university.enums.StudentStatus;
import university.enums.TeacherPosition;
import university.services.CourseService;
import university.services.EnrollmentService;
import university.services.StudentService;
import university.services.TeacherService;
import university.util.GPAUtils;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        TeacherService teacherService = new TeacherService();
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService(teacherService);
        EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("=== University Management System ===");
            System.out.println("1. Students");
            System.out.println("2. Teachers");
            System.out.println("3. Courses");
            System.out.println("4. Enrollments");
            System.out.println("5. Reports / Search");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int choice = readInt(scanner);

            switch (choice) {
                case 1 -> studentMenu(scanner, studentService);
                case 2 -> teacherMenu(scanner, teacherService);
                case 3 -> courseMenu(scanner, courseService);
                case 4 -> enrollmentMenu(scanner, enrollmentService);
                case 5 -> reportsMenu(scanner, studentService, enrollmentService, courseService);
                case 0 -> { System.out.println("Goodbye!"); scanner.close(); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void studentMenu(Scanner sc, StudentService ss) {
        while (true) {
            System.out.println();
            System.out.println("--- Students ---");
            System.out.println("1. Add student");
            System.out.println("2. List all students");
            System.out.println("3. Update student");
            System.out.println("4. Delete student");
            System.out.println("5. Change status");
            System.out.println("6. Filter by status");
            System.out.println("7. Filter by year");
            System.out.println("8. Sort by name");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int choice = readInt(sc);

            switch (choice) {
                case 1 -> addStudentFlow(sc, ss);
                case 2 -> ss.getAllStudents();
                case 3 -> updateStudentFlow(sc, ss);
                case 4 -> deleteStudentFlow(sc, ss);
                case 5 -> changeStatusFlow(sc, ss);
                case 6 -> filterByStatusFlow(sc, ss);
                case 7 -> filterByYearFlow(sc, ss);
                case 8 -> ss.sortByNameBubble();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void addStudentFlow(Scanner sc, StudentService ss) {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Year of study (1-6): ");
        int year = readInt(sc);
        System.out.print("Status (ACTIVE / ON_LEAVE / EXPELLED / GRADUATED): ");
        String statusStr = sc.nextLine().trim().toUpperCase();
        try {
            StudentStatus status = StudentStatus.valueOf(statusStr);
            System.out.println("Added: " + ss.addStudent(name, email, year, status));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudentFlow(Scanner sc, StudentService ss) {
        System.out.print("Student ID: ");
        int id = readInt(sc);
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Year of study (1-6): ");
        int year = readInt(sc);
        System.out.print("Status (ACTIVE / ON_LEAVE / EXPELLED / GRADUATED): ");
        String statusStr = sc.nextLine().trim().toUpperCase();
        try {
            StudentStatus status = StudentStatus.valueOf(statusStr);
            ss.updateStudent(id, name, email, year, status);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteStudentFlow(Scanner sc, StudentService ss) {
        System.out.print("Student ID: ");
        int id = readInt(sc);
        try {
            ss.deleteStudent(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void changeStatusFlow(Scanner sc, StudentService ss) {
        System.out.print("Student ID: ");
        int id = readInt(sc);
        System.out.print("New status (ACTIVE / ON_LEAVE / EXPELLED / GRADUATED): ");
        String statusStr = sc.nextLine().trim().toUpperCase();
        try {
            StudentStatus status = StudentStatus.valueOf(statusStr);
            ss.changeStatus(id, status);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void filterByStatusFlow(Scanner sc, StudentService ss) {
        System.out.print("Status (ACTIVE / ON_LEAVE / EXPELLED / GRADUATED): ");
        String statusStr = sc.nextLine().trim().toUpperCase();
        try {
            StudentStatus status = StudentStatus.valueOf(statusStr);
            ss.filterByStatus(status);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void filterByYearFlow(Scanner sc, StudentService ss) {
        System.out.print("Year of study (1-6): ");
        int year = readInt(sc);
        ss.filterByYear(year);
    }

    private static void teacherMenu(Scanner sc, TeacherService ts) {
        while (true) {
            System.out.println();
            System.out.println("--- Teachers ---");
            System.out.println("1. Add teacher");
            System.out.println("2. List all teachers");
            System.out.println("3. Update teacher");
            System.out.println("4. Delete teacher");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int choice = readInt(sc);

            switch (choice) {
                case 1 -> addTeacherFlow(sc, ts);
                case 2 -> ts.getAllTeachers();
                case 3 -> updateTeacherFlow(sc, ts);
                case 4 -> deleteTeacherFlow(sc, ts);
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void addTeacherFlow(Scanner sc, TeacherService ts) {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Position (ASSISTANT / LECTURER / PROFESSOR): ");
        String posStr = sc.nextLine().trim().toUpperCase();
        try {
            TeacherPosition position = TeacherPosition.valueOf(posStr);
            System.out.println("Added: " + ts.addTeacher(name, email, position));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateTeacherFlow(Scanner sc, TeacherService ts) {
        System.out.print("Teacher ID: ");
        int id = readInt(sc);
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Position (ASSISTANT / LECTURER / PROFESSOR): ");
        String posStr = sc.nextLine().trim().toUpperCase();
        try {
            TeacherPosition position = TeacherPosition.valueOf(posStr);
            ts.updateTeacher(id, name, email, position);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteTeacherFlow(Scanner sc, TeacherService ts) {
        System.out.print("Teacher ID: ");
        int id = readInt(sc);
        try {
            ts.deleteTeacher(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void courseMenu(Scanner sc, CourseService cs) {
        while (true) {
            System.out.println();
            System.out.println("--- Courses ---");
            System.out.println("1. Add course");
            System.out.println("2. List all courses");
            System.out.println("3. Update course");
            System.out.println("4. Delete course");
            System.out.println("5. Filter by teacher");
            System.out.println("6. Filter by credits");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int choice = readInt(sc);

            switch (choice) {
                case 1 -> addCourseFlow(sc, cs);
                case 2 -> cs.getAllCourses();
                case 3 -> updateCourseFlow(sc, cs);
                case 4 -> deleteCourseFlow(sc, cs);
                case 5 -> filterCourseByTeacherFlow(sc, cs);
                case 6 -> filterCourseByCreditsFlow(sc, cs);
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void addCourseFlow(Scanner sc, CourseService cs) {
        System.out.print("Course name: ");
        String name = sc.nextLine();
        System.out.print("Credits (1-10): ");
        int credits = readInt(sc);
        System.out.print("Teacher ID: ");
        int teacherId = readInt(sc);
        try {
            System.out.println("Added: " + cs.addCourse(name, credits, teacherId));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateCourseFlow(Scanner sc, CourseService cs) {
        System.out.print("Course ID: ");
        int id = readInt(sc);
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Credits (1-10): ");
        int credits = readInt(sc);
        System.out.print("Teacher ID: ");
        int teacherId = readInt(sc);
        try {
            cs.updateCourse(id, name, credits, teacherId);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteCourseFlow(Scanner sc, CourseService cs) {
        System.out.print("Course ID: ");
        int id = readInt(sc);
        try {
            cs.deleteCourse(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void filterCourseByTeacherFlow(Scanner sc, CourseService cs) {
        System.out.print("Teacher ID: ");
        int teacherId = readInt(sc);
        cs.filterByTeacher(teacherId);
    }

    private static void filterCourseByCreditsFlow(Scanner sc, CourseService cs) {
        System.out.print("Credits: ");
        int credits = readInt(sc);
        cs.filterByCredits(credits);
    }

    private static void enrollmentMenu(Scanner sc, EnrollmentService es) {
        while (true) {
            System.out.println();
            System.out.println("--- Enrollments ---");
            System.out.println("1. Enroll student in course");
            System.out.println("2. List all enrollments");
            System.out.println("3. Set grade");
            System.out.println("4. Mark as paid");
            System.out.println("5. View student enrollments + GPA");
            System.out.println("6. Print transcript");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int choice = readInt(sc);

            switch (choice) {
                case 1 -> enrollFlow(sc, es);
                case 2 -> es.getAllEnrollments();
                case 3 -> setGradeFlow(sc, es);
                case 4 -> markPaidFlow(sc, es);
                case 5 -> studentEnrollmentsFlow(sc, es);
                case 6 -> printTranscriptFlow(sc, es);
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void enrollFlow(Scanner sc, EnrollmentService es) {
        System.out.print("Student ID: ");
        int studentId = readInt(sc);
        System.out.print("Course ID: ");
        int courseId = readInt(sc);
        System.out.print("Semester (e.g. 2024-Fall): ");
        String semester = sc.nextLine();
        try {
            System.out.println("Enrolled: " + es.enroll(studentId, courseId, semester));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void setGradeFlow(Scanner sc, EnrollmentService es) {
        System.out.print("Enrollment ID: ");
        int id = readInt(sc);
        System.out.print("Grade (A / B / C / D / F / NA): ");
        String gradeStr = sc.nextLine().trim().toUpperCase();
        try {
            Grade grade = Grade.valueOf(gradeStr);
            es.setGrade(id, grade);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void markPaidFlow(Scanner sc, EnrollmentService es) {
        System.out.print("Enrollment ID: ");
        int id = readInt(sc);
        try {
            es.markPaid(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void studentEnrollmentsFlow(Scanner sc, EnrollmentService es) {
        System.out.print("Student ID: ");
        int id = readInt(sc);
        try {
            es.getStudentEnrollments(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printTranscriptFlow(Scanner sc, EnrollmentService es) {
        System.out.print("Student ID: ");
        int id = readInt(sc);
        try {
            es.printTranscript(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void reportsMenu(Scanner sc, StudentService ss,
                                    EnrollmentService es, CourseService cs) {
        while (true) {
            System.out.println();
            System.out.println("--- Reports / Search ---");
            System.out.println("1. Search student by name or email");
            System.out.println("2. Students with unpaid enrollments");
            System.out.println("3. Average GPA for a course");
            System.out.println("4. Top-N students by GPA");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int choice = readInt(sc);

            switch (choice) {
                case 1 -> {
                    System.out.print("Search query: ");
                    String query = sc.nextLine();
                    ss.findByNameOrEmail(query);
                }
                case 2 -> es.getUnpaidEnrollments();
                case 3 -> {
                    System.out.print("Course ID: ");
                    int courseId = readInt(sc);
                    try {
                        double avg = es.getAverageGPA(courseId);
                        System.out.printf("Average GPA for course %d: %.2f%n", courseId, avg);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.print("Number of top students: ");
                    int n = readInt(sc);
                    GPAUtils.printTopNByGPA(ss, es, n);
                }
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (true) {
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            }
            sc.nextLine();
            System.out.print("Please enter a valid number: ");
        }
    }
}
