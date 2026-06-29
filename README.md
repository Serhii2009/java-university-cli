# University Management System

A console-based university management system written in plain Java. Manages students, teachers, courses, and enrollments entirely in memory using arrays — no frameworks, no databases.

---

## Prerequisites

Java 11 or later. Verify with:

```
java -version
```

---

## Compile & Run

### Git Bash / macOS / Linux

```bash
mkdir -p out
javac -d out src/university/**/*.java src/university/*.java
java -cp out university.Main
```

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out src/university/enums/*.java src/university/interfaces/*.java src/university/entities/*.java src/university/util/*.java src/university/services/*.java src/university/Main.java
java -cp out university.Main
```

---

## Project Structure

```
src/
 └── university/
      ├── Main.java                   # Entry point, all menus
      ├── entities/
      │    ├── Person.java            # Abstract base (id, name, email)
      │    ├── Student.java           # yearOfStudy, StudentStatus
      │    ├── Teacher.java           # TeacherPosition
      │    ├── Course.java            # name, credits, Teacher
      │    └── Enrollment.java        # Student + Course + grade + paid
      ├── enums/
      │    ├── Grade.java             # A/B/C/D/F/NA with GPA values
      │    ├── StudentStatus.java     # ACTIVE/ON_LEAVE/EXPELLED/GRADUATED
      │    └── TeacherPosition.java   # ASSISTANT/LECTURER/PROFESSOR
      ├── interfaces/
      │    └── Payable.java           # markAsPaid() / isPaid()
      ├── services/
      │    ├── StudentService.java    # CRUD + filters + bubble sort
      │    ├── TeacherService.java    # CRUD
      │    ├── CourseService.java     # CRUD + filters by teacher/credits
      │    └── EnrollmentService.java # Enroll, grades, payments, transcripts
      └── util/
           └── GPAUtils.java          # GPA calculation + top-N ranking
README.md
```

---

## Features

**Students**
- Add, list, update, delete
- Change enrollment status (ACTIVE / ON_LEAVE / EXPELLED / GRADUATED)
- Filter by status or year of study
- Sort alphabetically by name (bubble sort)

**Teachers**
- Full CRUD

**Courses**
- Full CRUD
- Filter by teacher or number of credits

**Enrollments**
- Enroll a student in a course for a semester
- Set grade (A / B / C / D / F / NA)
- Mark as paid
- View all enrollments for a student with computed GPA
- Print a formatted academic transcript

**Reports**
- Search students by name or email (substring, case-insensitive)
- List all unpaid enrollments
- Average GPA for a specific course
- Top-N students ranked by GPA (selection sort)

---

## Example Session

```
=== University Management System ===
1. Students
2. Teachers
3. Courses
4. Enrollments
5. Reports / Search
0. Exit
> 2

--- Teachers ---
1. Add teacher
...
> 1
Name: Alice Brown
Email: alice@uni.edu
Position (ASSISTANT / LECTURER / PROFESSOR): PROFESSOR
Added: Teacher[id=1, name=Alice Brown, email=alice@uni.edu, position=PROFESSOR]

> 3
--- Courses ---
> 1
Course name: Algorithms
Credits (1-10): 3
Teacher ID: 1
Added: Course[id=1, name=Algorithms, credits=3, teacher=Alice Brown]

> 1
--- Students ---
> 1
Name: Bob Smith
Email: bob@uni.edu
Year of study (1-6): 2
Status (ACTIVE / ON_LEAVE / EXPELLED / GRADUATED): ACTIVE
Added: Student[id=2, name=Bob Smith, email=bob@uni.edu, year=2, status=ACTIVE]

> 4
--- Enrollments ---
> 1
Student ID: 2
Course ID: 1
Semester (e.g. 2024-Fall): 2024-Fall
Enrolled: Enrollment[id=1, student=Bob Smith, course=Algorithms, semester=2024-Fall, grade=NA, paid=false]

> 3
Enrollment ID: 1
Grade (A / B / C / D / F / NA): A
Grade set: Enrollment[id=1, student=Bob Smith, course=Algorithms, semester=2024-Fall, grade=A, paid=false]

> 6
Student ID: 2
=== TRANSCRIPT ===
Student : Bob Smith
Year    : 2  |  Status: ACTIVE
-----------------------------------------
Semester        | Course               | Grade | Paid
2024-Fall       | Algorithms           | A     | No
-----------------------------------------
GPA: 4.00
```

---

## Known Limitations

- Each entity type supports a maximum of 100 records (fixed-size array).
- Deleted entity IDs are not reused; searching by a deleted ID returns an error.
- Deleting a teacher does not cascade to courses that reference them.
- All data is in-memory and lost when the program exits.
