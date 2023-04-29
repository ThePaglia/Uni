package ex2015.a01b.e1;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * An interface modeling a small University program, with courses, teachers and
 * tutors. Each course is held every year, and every year it gets a professor
 * assigned. Every year, each course can have zero to three tutors assigned.
 *
 */
public interface CoursesManagement {

    /**
     * Maximum number of tutors allowed per course.
     */
    static final int TUTORING_SLOTS = 3;

    /**
     * Courses to manage
     */
    enum Course {
        OBJECT_ORIENTED_PROGRAMMING, OPERATING_SYSTEMS, FOUNDATIONS_OF_INFORMATICS, ANALYSIS, GEOMETRY, PHYSICS
    }

    /**
     * Assigns a professor to a particular course at a particular year.
     * 
     * @throws IllegalStateException
     *             if the course already have a professor for that year
     */
    void assignProfessor(Course c, int year, String professor);

    /**
     * Assigns a tutor to a particular course at a particular year.
     * 
     * @throws IllegalStateException
     *             if the course for the particular year already have three
     *             tutors
     */
    void assignTutor(Course c, int year, String professor);

    /**
     * @return the Set of courses that have not a professor, yet
     */
    Set<Course> getUnassignedCourses(int year);

    /**
     * @return a summary of the activity for this professor, as a set of pairs year-course
     */
    Set<Pair<Integer, Course>> getCoursesByProf(String prof);

    /**
     * @return a Map that, for each year, shows how many tutoring slots are left for each course,
     * 		   expressed as a set of pairs course-number of slots
     */
    Map<Integer, Set<Pair<Course, Integer>>> getRemainingTutoringSlots();

}