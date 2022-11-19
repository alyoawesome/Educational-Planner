package ui;

import model.Assignment;
import model.Course;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.awt.BorderLayout.CENTER;


// CITATION: Reused code from ListDemo and SpaceInvaders
//           Also learned about how to implement and configure scrollPanes using:
//           http://www.java2s.com/example/java-api/java/awt/borderlayout/page_end-3.html
//           Also learned about how to implement and use documentListeners for the addCourseListener using:
//           https://docs.oracle.com/javase/tutorial/uiswing/events/documentlistener.html

// This represents the Educational Planner's graphic user interface
public class GUI extends JPanel implements ListSelectionListener, ActionListener {

    private static final String JSON_STORE = "./data/courses.json";
    private Scanner input;
    private ArrayList<Course> courses = new ArrayList<>();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JList list;
    private DefaultListModel listModel;

    private static final String addCourse = "Add Course";
    private static final String addAssign = "Add Assignment & Grade";
    private static final String delAssign = "Delete Assignment";
    private static final String saveAll = "Save All Data";


    private static final String loadAll = "Load All Data";
    private JTextArea addA;
    private JTextArea delA;
    private JButton addCButton;
    private JTextField listObjName;
    private JLabel listLabel;

    int index = 0;
    String message = "";
    JScrollPane listScrollPane;

    // EFFECTS: runs the Educational Planner application's graphical user interface
    public GUI() {

        init();

        makeCourseScrollPane();

        makeAddCourseButton();

        courseScrollPaneConfigurations();

        makeAddAssignmentButtons();

        makeSaveButton();

        makeLoadButton();

    }

    // MODIFIES: this
    // EFFECTS: initializes the jFrame and adds a default course into the Course dropdown
    public void init() {
        JFrame jframe = new JFrame("Swing GUI DEMO");
        jframe.setSize(760, 450);
        jframe.getContentPane();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(this);
        jframe.setVisible(true);

        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        listModel = new DefaultListModel();
        listModel.addElement("Biology");
        addCourse("Biology");

    }

    // MODIFIES: this
    // EFFECTS: Makes a scrollPane with the default course and labels the scrollPane with the string "Courses: "
    public void makeCourseScrollPane() {

        listLabel = new JLabel("Courses: ");
        add(listLabel);
        listLabel.setBounds(5, 5, 5, 5);


        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(list);

    }

    // MODIFIES: this
    // EFFECTS: Creates a Text Area and an add course button for a course to be added
    public void makeAddCourseButton() {

        addCButton = new JButton(addCourse);
        AddCListener addCListener = new AddCListener(addCButton);
        addCButton.setActionCommand(addCourse);
        addCButton.addActionListener(addCListener);
        addCButton.setEnabled(false);

        listObjName = new JTextField(10);
        listObjName.addActionListener(addCListener);
        listObjName.getDocument().addDocumentListener(addCListener);
    }

    // MODIFIES: this
    // EFFECTS: Sets the scrollPane's visible size and border size
    public void courseScrollPaneConfigurations() {

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(listObjName);
        buttonPane.add(addCButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(listScrollPane, CENTER);
        add(buttonPane, CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Create Text Areas and buttons for Assignment name and grade to be added and deleted
    public void makeAddAssignmentButtons() {

        addA = new JTextArea(1, 10);

        JButton addAButton = new JButton(addAssign);
        addAButton.setActionCommand(addAssign);
        addAButton.addActionListener(this);
        add(addA);
        add(addAButton);

        delA = new JTextArea(1, 10);

        JButton delAButton = new JButton(delAssign);
        delAButton.setActionCommand(delAssign);
        delAButton.addActionListener(this);
        add(delA);
        add(delAButton);
    }

    // MODIFIES: this
    //EFFECTS: Makes Button for Saving all data
    public void makeSaveButton() {

        JButton saveButton = new JButton(saveAll);
        saveButton.setActionCommand(saveAll);
        saveButton.addActionListener(this);
        add(saveButton);

    }

    // MODIFIES: this
    //EFFECTS: Makes button for loading back all data
    public void makeLoadButton() {

        JButton loadButton = new JButton(loadAll);
        loadButton.setActionCommand(loadAll);
        loadButton.addActionListener(this);
        add(loadButton);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(addAssign)) {
            String text = addA.getText();
            String[] words = text.split("&");
            if (text.length() > 0 && text.contains("&")) {
                addAssignment(words[0], Double.parseDouble(words[1]));
            }
            repaint();
        } else if (e.getActionCommand().equals(delAssign)) {
            String textA = delA.getText();
            if (textA.length() > 0 && !courses.get(index).getAssignments().isEmpty()) {
                deleteAssignment(textA);
            }
            repaint();
        } else if (e.getActionCommand().equals(loadAll)) {
            loadCourse();
            repaint();
        } else if (e.getActionCommand().equals(saveAll)) {
            saveCourse();
            repaint();
        }
    }


    // Represents the addCourseListener
    class AddCListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;


        //EFFECTS: assigns the course button to the courseListener
        public AddCListener(JButton button) {
            this.button = button;
        }


        public void actionPerformed(ActionEvent e) {
            String name = listObjName.getText();


            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                listObjName.requestFocusInWindow();
                listObjName.selectAll();
                return;
            }

            index = courses.size();
            listModel.addElement(listObjName.getText());
            addCourse(listObjName.getText());


            listObjName.requestFocusInWindow();
            listObjName.setText("");


            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }


        //EFFECTS: returns true if the scrollPanel already contains the inputted course, else returns false
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }


        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }


        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }


        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        //EFFECTS: return true if the addCourse's textField has no text, else return false
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }


    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() == -1) {
                repaint();
            } else {
                index = list.getSelectedIndex();
                repaint();
            }
        }
    }

    // REQUIRES: the course's name needs to be a String (no numbers) and has length > 0;
    // MODIFIES: this
    // EFFECTS: makes a new course
    private Course makeCourse(String name) {
        ArrayList<Assignment> assignments = new ArrayList<>();
        Course course = new Course(name, assignments);
        return course;
    }

    // MODIFIES: this
    // EFFECTS: makes a course with the inputted name
    private void addCourse(String name) {
        message = "Add Course " + name + " ( Course No." + index + " )";
        courses.add(makeCourse(name));
    }

    // REQUIRES: The number of course selected must be >=0 and <= courses.size()
    // EFFECTS: shows the user the overall average for a course of their choosing
    private double calculateOverallAverage(int index) {
        Course selectedCourse = courses.get(index);
        double overallAverage = selectedCourse.calculateOverallAverage();
        return overallAverage;
    }

    // MODIFIES: this
    // EFFECTS: adds an assignment to a course of user's choosing
    private void addAssignment(String assign, double grade) {
        Assignment assignment = new Assignment(assign, grade);
        message = "Add Assignment :" + assign + " for Course no." + index;
        courses.get(index).addAssignment(assignment);
    }

    // MODIFIES: this
    // EFFECTS: deletes an assignment in a course of user's choosing
    private void deleteAssignment(String textA) {
        String status = courses.get(index).deleteAssignment(textA);
        message = "Delete Assignment :" + textA + " " + status;
    }

    // EFFECTS: saves the Courses to file
    private void saveCourse() {
        try {
            jsonWriter.open();
            jsonWriter.write(courses);
            jsonWriter.close();
            message = "Data saved to " + JSON_STORE;
        } catch (FileNotFoundException e) {
            message = "Problem in saving data to " + JSON_STORE;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads course from file
    private void loadCourse() {
        try {
            this.courses = jsonReader.read();
            int size = listModel.size();

            for (int i = size - 1; i >= 0; i--) {
                String name = listModel.get(i).toString();
                listModel.removeElement(name);
            }

            if (courses != null && !courses.isEmpty()) {
                for (int i = 0; i < courses.size(); i++) {
                    listModel.addElement(courses.get(i).getCourseName());
                    index = i;
                }
            }
            message = "Data loaded from file: " + JSON_STORE;
            validate();
            repaint();
        } catch (IOException e) {
            message = "Unable to read from file: " + JSON_STORE;
        }
    }

    // MODIFIES: this
    //EFFECTS: makes the assignment pane that shows a course's assignments and a course's overall average
    // for any selected course
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);

        g2d.setFont(new Font("default", Font.PLAIN, 14));

        String courseName = courses.get(index).getCourseName();

        paintAssignmentDetails(g);
        g2d.drawString("Course Selected: " + courseName, 80, 190);
        g2d.drawString("Course Number: " + String.valueOf(index), 350, 190);

        g2d.drawString("Message: " + message, 80, 350);
    }

    // MODIFIES: this
    //EFFECTS: Writes the assignment's name, grade, and the selected course's overall average using blue font
    public void paintAssignmentDetails(Graphics g) {
        Double avg = calculateOverallAverage(index);
        String avg1 = "";
        if (avg > 0.0) {
            avg1 = String.valueOf(avg);
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);
        ArrayList<Assignment> assignList = courses.get(index).getAssignments();
        g2d.drawString("No.      Assignments: ", 80, 220);
        for (int i = 0; i < assignList.size(); i++) {
            g2d.drawString(String.valueOf(i), 85, 240 + i * 20);
            g2d.drawString(assignList.get(i).getName(),
                    140,
                    240 + i * 20);
        }
        g2d.drawString("Grade: ", 250, 220);
        for (int i = 0; i < assignList.size(); i++) {
            g2d.drawString(assignList.get(i).getGrade() + "",
                    260,
                    240 + i * 20);
        }
        g2d.drawString("Average: ", 350, 220);
        g2d.drawString(avg1, 360, 240);

    }


}
