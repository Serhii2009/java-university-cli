package university.entities;

import university.enums.TeacherPosition;

public class Teacher extends Person {
    private TeacherPosition position;

    public Teacher(String name, String email, TeacherPosition position) {
        super(name, email);
        this.position = position;
    }

    public TeacherPosition getPosition() { return position; }

    public void setPosition(TeacherPosition position) { this.position = position; }

    @Override
    public String toString() {
        return "Teacher[id=" + getId() + ", name=" + getName() + ", email=" + getEmail()
                + ", position=" + position + "]";
    }
}
