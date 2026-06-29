package university.enums;

public enum Grade {
    A(4.0), B(3.0), C(2.0), D(1.0), F(0.0), NA(0.0);

    private final double gpaValue;

    Grade(double gpaValue) {
        this.gpaValue = gpaValue;
    }

    public double getGpaValue() {
        return gpaValue;
    }
}
