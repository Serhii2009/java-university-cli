package university.entities;

public class Course {
    private static int idCounter = 0;

    private final int id;
    private String name;
    private int credits;
    private Teacher teacher;

    public Course(String name, int credits, Teacher teacher) {
        this.id = ++idCounter;
        this.name = name;
        setCredits(credits);
        this.teacher = teacher;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getCredits() { return credits; }

    public void setCredits(int credits) {
        if (credits < 1 || credits > 10) {
            throw new IllegalArgumentException("Credits must be between 1 and 10, got: " + credits);
        }
        this.credits = credits;
    }

    public Teacher getTeacher() { return teacher; }

    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    @Override
    public String toString() {
        return "Course[id=" + id + ", name=" + name + ", credits=" + credits
                + ", teacher=" + teacher.getName() + "]";
    }
}
