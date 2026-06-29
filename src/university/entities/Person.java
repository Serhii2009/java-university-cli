package university.entities;

import java.util.Objects;

public abstract class Person {
    private static int idCounter = 0;

    private final int id;
    private String name;
    private String email;

    public Person(String name, String email) {
        this.id = ++idCounter;
        this.name = name;
        setEmail(email);
    }

    private static void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email '" + email + "': must contain '@'");
        }
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        return id == ((Person) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public abstract String toString();
}
