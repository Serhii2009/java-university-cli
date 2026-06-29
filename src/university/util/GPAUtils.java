package university.util;

import university.entities.Enrollment;
import university.entities.Student;
import university.enums.Grade;
import university.services.EnrollmentService;
import university.services.StudentService;

public class GPAUtils {

    public static double calculateGPA(Enrollment[] enrollments, int count) {
        double total = 0.0;
        int validCount = 0;
        for (int i = 0; i < count; i++) {
            if (enrollments[i].getGrade() != Grade.NA) {
                total += enrollments[i].getGrade().getGpaValue();
                validCount++;
            }
        }
        return validCount == 0 ? 0.0 : total / validCount;
    }

    public static void printTopNByGPA(StudentService ss, EnrollmentService es, int n) {
        Student[] students = ss.getStudentsArray();
        int total = ss.getCount();

        if (total == 0) {
            System.out.println("No students in the system.");
            return;
        }

        double[] gpas = new double[total];
        for (int i = 0; i < total; i++) {
            gpas[i] = es.calculateStudentGPA(students[i].getId());
        }

        for (int i = 0; i < total - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < total; j++) {
                if (gpas[j] > gpas[maxIdx]) maxIdx = j;
            }
            double tg = gpas[i]; gpas[i] = gpas[maxIdx]; gpas[maxIdx] = tg;
            Student ts = students[i]; students[i] = students[maxIdx]; students[maxIdx] = ts;
        }

        int limit = Math.min(n, total);
        System.out.println("=== Top " + limit + " Students by GPA ===");
        for (int i = 0; i < limit; i++) {
            System.out.printf("%-3d %-25s GPA: %.2f%n", i + 1, students[i].getName(), gpas[i]);
        }
    }
}
