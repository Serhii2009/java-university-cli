package university.services;

import university.entities.Course;
import university.entities.Teacher;

public class CourseService {
    private static final int MAX_SIZE = 100;
    private final Course[] courses = new Course[MAX_SIZE];
    private int count = 0;

    private final TeacherService teacherService;

    public CourseService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public Course addCourse(String name, int credits, int teacherId) {
        if (count >= MAX_SIZE) {
            throw new IllegalArgumentException("Storage full: cannot add more courses (max " + MAX_SIZE + ")");
        }
        Teacher teacher = teacherService.findById(teacherId);
        Course c = new Course(name, credits, teacher);
        courses[count++] = c;
        return c;
    }

    public void getAllCourses() {
        if (count == 0) {
            System.out.println("No courses found.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(courses[i]);
        }
    }

    public Course findById(int id) {
        return courses[findIndexById(id)];
    }

    public void updateCourse(int id, String name, int credits, int teacherId) {
        Course c = findById(id);
        Teacher teacher = teacherService.findById(teacherId);
        c.setName(name);
        c.setCredits(credits);
        c.setTeacher(teacher);
        System.out.println("Updated: " + c);
    }

    public void deleteCourse(int id) {
        int idx = findIndexById(id);
        System.out.println("Deleted: " + courses[idx]);
        for (int i = idx; i < count - 1; i++) {
            courses[i] = courses[i + 1];
        }
        courses[--count] = null;
    }

    public void filterByTeacher(int teacherId) {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (courses[i].getTeacher().getId() == teacherId) {
                System.out.println(courses[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No courses for teacher id: " + teacherId);
    }

    public void filterByCredits(int credits) {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (courses[i].getCredits() == credits) {
                System.out.println(courses[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No courses with " + credits + " credits.");
    }

    private int findIndexById(int id) {
        for (int i = 0; i < count; i++) {
            if (courses[i].getId() == id) return i;
        }
        throw new IllegalArgumentException("Course not found with id: " + id);
    }
}
