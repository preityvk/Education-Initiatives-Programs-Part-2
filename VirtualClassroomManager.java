import java.util.*; // Import necessary classes from the java.util package

public class VirtualClassroomManager {

    // Inner class representing a Classroom
    static class Classroom {
        private String name; // Name of the classroom
        private Set<String> students; // Set to hold enrolled students
        private List<String> assignments; // List to hold assignments
        private Map<String, Set<String>> submissions; // Map to track student submissions

        // Constructor to initialize the classroom with a name
        public Classroom(String name) {
            this.name = name; // Set the classroom name
            this.students = new HashSet<>(); // Initialize the student set
            this.assignments = new ArrayList<>(); // Initialize the assignments list
            this.submissions = new HashMap<>(); // Initialize the submission tracking map
        }

        // Method to enroll a student in the classroom
        public void addStudent(String studentId) {
            if (students.add(studentId)) {
                submissions.put(studentId, new HashSet<>()); // Initialize submission set for the student
                System.out.println("Student " + studentId + " has been enrolled in " + name + ".");
            } else {
                System.out.println("Student " + studentId + " is already enrolled in " + name + ".");
            }
        }

        // Method to schedule an assignment for the classroom
        public void scheduleAssignment(String assignmentDetails) {
            assignments.add(assignmentDetails); // Add assignment to the list
            System.out.println("Assignment for " + name + " has been scheduled: " + assignmentDetails);
        }

        // Method to submit an assignment for a student
        public void submitAssignment(String studentId, String assignmentDetails) {
            if (students.contains(studentId)) {
                submissions.get(studentId).add(assignmentDetails); // Record the student's submission
                System.out.println("Assignment submitted by Student " + studentId + " in " + name + ": " + assignmentDetails);
            } else {
                System.out.println("Submission failed: Student " + studentId + " is not enrolled in " + name + ".");
            }
        }

        // Method to return the set of enrolled students
        public Set<String> getStudents() {
            return students; // Return the set of students
        }

        // Method to return the submissions of a student
        public Map<String, Set<String>> getSubmissions() {
            return submissions; // Return the submissions map
        }
    }

    // Map to hold classrooms by their names
    private Map<String, Classroom> classrooms;

    // Constructor to initialize the manager
    public VirtualClassroomManager() {
        classrooms = new HashMap<>(); // Initialize the classrooms map
    }

    // Method to add a new classroom
    public void addClassroom(String className) {
        if (!classrooms.containsKey(className)) {
            classrooms.put(className, new Classroom(className)); // Create new classroom
            System.out.println("Classroom " + className + " has been created.");
        } else {
            System.out.println("Classroom " + className + " already exists.");
        }
    }

    // Method to enroll a student in a specific classroom
    public void addStudent(String studentId, String className) {
        Classroom classroom = classrooms.get(className); // Get the classroom by name
        if (classroom != null) { // Check if the classroom exists
            classroom.addStudent(studentId); // Enroll the student
        } else {
            System.out.println("Classroom " + className + " does not exist.");
        }
    }

    // Method to schedule an assignment
    public void scheduleAssignment(String className, String assignmentDetails) {
        Classroom classroom = classrooms.get(className); // Get the classroom by name
        if (classroom != null) { // Check if the classroom exists
            classroom.scheduleAssignment(assignmentDetails); // Schedule the assignment
        } else {
            System.out.println("Classroom " + className + " does not exist.");
        }
    }

    // Method to submit an assignment
    public void submitAssignment(String studentId, String className, String assignmentDetails) {
        Classroom classroom = classrooms.get(className); // Get the classroom by name
        if (classroom != null) { // Check if the classroom exists
            classroom.submitAssignment(studentId, assignmentDetails); // Submit the assignment
        } else {
            System.out.println("Classroom " + className + " does not exist.");
        }
    }

    // Method to list students, their assignments, and submissions in a specific classroom
    public void listStudentsInClass(String className) {
        Classroom classroom = classrooms.get(className); // Get the classroom by name
        if (classroom != null) { // Check if the classroom exists
            System.out.println("Students in " + className + ": " + classroom.getStudents());
            System.out.println("Assignments scheduled for " + className + ": " + classroom.assignments);
            
            // Display student submissions for each assignment
            for (String student : classroom.getStudents()) {
                Set<String> submittedAssignments = classroom.getSubmissions().get(student);
                System.out.println("Student " + student + " has submitted: " + submittedAssignments);
            }
        } else {
            // Inform the user if the classroom does not exist
            System.out.println("Classroom " + className + " does not exist.");
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a scanner for user input
        VirtualClassroomManager manager = new VirtualClassroomManager(); // Create manager instance

        // Infinite loop to keep accepting commands
        while (true) {
            System.out.print("Enter command: "); // Prompt user for input
            String input = scanner.nextLine(); // Read user input
            String[] commandParts = input.split(" "); // Split input into parts

            if (commandParts.length == 0) continue; // Skip empty input

            String action = commandParts[0].toLowerCase(); // Get the action (first part)

            // Handle different commands using a switch statement
            switch (action) {
                case "add_classroom":
                    if (commandParts.length > 1) {
                        manager.addClassroom(commandParts[1]); // Add classroom
                    } else {
                        System.out.println("Please provide a classroom name."); // Prompt if no name provided
                    }
                    break;
                case "add_student":
                    if (commandParts.length > 2) {
                        manager.addStudent(commandParts[1], commandParts[2]); // Add student to classroom
                    } else {
                        System.out.println("Please provide a student ID and classroom name."); // Prompt if arguments are missing
                    }
                    break;
                case "schedule_assignment":
                    if (commandParts.length > 2) {
                        String assignmentDetails = String.join(" ", Arrays.copyOfRange(commandParts, 2, commandParts.length)); // Join the rest of the command as assignment details
                        manager.scheduleAssignment(commandParts[1], assignmentDetails); // Schedule assignment
                    } else {
                        System.out.println("Please provide a classroom name and assignment details."); // Prompt if arguments are missing
                    }
                    break;
                case "submit_assignment":
                    if (commandParts.length > 3) {
                        String assignmentDetails = String.join(" ", Arrays.copyOfRange(commandParts, 3, commandParts.length)); // Join the rest of the command as assignment details
                        manager.submitAssignment(commandParts[1], commandParts[2], assignmentDetails); // Submit assignment
                    } else {
                        System.out.println("Please provide a student ID, classroom name, and assignment details."); // Prompt if arguments are missing
                    }
                    break;
                case "list_students":
                    if (commandParts.length > 1) {
                        manager.listStudentsInClass(commandParts[1]); // List students in classroom
                    } else {
                        System.out.println("Please provide a classroom name."); // Prompt if no name provided
                    }
                    break;
                case "exit":
                    scanner.close(); // Close the scanner and exit the program
                    return;
                default:
                    System.out.println("Unknown command."); // Handle unrecognized commands
            }
        }
    }
}
