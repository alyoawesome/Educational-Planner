# Educational Planner 

## CPSC 210 – *Personal Project*

This application is an educational planner that allows students to keep track of their overall averages
in their courses using the grades they input from each of the course's assignments. 
    
This project is of interest to me because I'm aware that some schools may not offer software that allows their 
students to track their progress and evaluate their academic weak points. In today's educational environment, being
able to keep track of your grades and continuously improve academically is crucial to getting accepted
into a good university, which may eventually help in procuring a successful career. 

This application can be used by **any** student that wants to keep track of their academical progress,
but will mostly be targeted towards students that don't already have grade tracking software provided by their school
because their school's application may offer other features more specific to their students.

## User Stories:
- As a user, I want to be able to add any assignment to my courses
- As a user, I want to be able to input a grade and specific name 
(Eg: Project, Midterm, Homework) for each of my assignments
- As a user, I want to be able to delete any assignments that I've already inputted into my courses
- As a user, I want to be able to see the overall average for my course based on the grades of all of my assignments 
for that course, along with the grades of each of the assignments I put into the course.
- As a user, I want to be able to save all my courses, assignments, and the grades for those 
assignments to file
- As a user, I want to be able to load all my courses, assignments, and grades for those
assignments from file


# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by clicking on the 
"Add Assignment & Grade" Button
- You can generate the second required event related to adding Xs to a Y by clicking on a course 
in the course dropdown panel and seeing the assignments in that selected course, along with the course's overall grade
- You can locate my visual component by running the application and seeing that there is an image that serves as a 
loading screen before the user is shown the Educational Planner GUI
- You can save the state of my application by pressing on the "Save All Data" button
- You can reload the state of my application by pressing on the "Load All Data" button

# Phase 4: Task 2

Here is an example of a user adding a test assignment to their math course and to the "all assignments"
panel, adding a project assignment to their biology course and to the "all assignments" panel, and deleting a test 
assignment from their math course and from the "all assignments" panel. 

Wed Nov 30 21:14:05 PST 2022
Added Assignment: test to math

Wed Nov 30 21:14:05 PST 2022
Added Assignment: test to all assignments panel

Wed Nov 30 21:14:32 PST 2022
Added Assignment: project to Biology

Wed Nov 30 21:14:32 PST 2022
Added Assignment: project to all assignments panel

Wed Nov 30 21:14:52 PST 2022
Deleted Assignment: test from math

Wed Nov 30 21:14:52 PST 2022
Deleted Assignment: test from all assignments panel

# Phase 4: Task 3
If I had more time to work on this project, I
would try refactoring it by:

- To increase cohesion, the GUI class should be split into several different classes – a "CoursePanel" class, 
an "AssignmentPanel" class, and a "Paint" class. The CoursePanel class would be 
responsible for managing the complete functionality and display of adding courses into the 
EducationalPlanner, i.e., it would contain the code for the course scrollpane, the course name input, and 
add course button components. The "AssignmentPanel" class would be responsible for managing the complete 
functionality and display of adding and deleting assignments from any course, i.e., it would contain the code for the 
assignment's name and grade inputs and the add/delete assignment button components. The "Paint" class would be
responsible for managing the complete functionality and display of anything to do with repainting, i.e., it would 
contain the code for showing each assignment's name and grade for any selected course, along with the code for showing
all the assignments.


- To decrease duplication, I would try extracting the heavily duplicated code in the "paintAllAssignmentDetails" and 
"paintAssignmentDetails" methods into just one method, which would allow for lower coupling because I would just need
to change code in one place if I wanted to change the colour, font, or any other display configurations for showing
each assignment's name and grade for any selected course and for showing every inputted assignment


- For better clarity in naming, I would extract the for-loops in the "loadCourse" method into methods called 
"clearCourseList" for the first for-loop and "loadCourseListFromJson" for the second for-loop. This would allow
programmers to quickly understand this part of the program instead of having to spend time reading through 
considerable chunks of complex code to figure out what's going on
