package ex2015.a01a.sol1;

import java.util.*;

/**
 * An interface modelling the weekly calendar of a University programme
 *
 */
public interface CoursesCalendar {
	
	
	/**
	 * Working days of the week
	 */
	enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY }
	
	/**
	 * Available rooms
	 */
	enum Room { A, B, C, D, E, MAGNA, VELA, C_ALDO_MORO }
	
	/**
	 * Returns the hours in a day in which rooms can be possibly booked
	 */
	List<Integer> possibleSlots();
	
	/**
	 * Books a lesson
	 * @param d: day
	 * @param r: room
	 * @param start: initial hour
	 * @param duration: number of consecutive hours
	 * @param course: course name
	 * @throws IllegalStateException if some hours are already booked, in which case no change to booking is done
	 */
	void bookRoom(Day d, Room r, int start, int duration, String course);
	
	/**
	 * Yields all booking in a given day and room
	 * @param d: day
	 * @param r: room
	 * @return a set of pairs of initial hour and course name
	 */
	Set<Pair<Integer,String>> dayRoomSlots(Day d, Room r);
	
	/**
	 * Yields all booking of a course
	 * @param course: the course to inspect
	 * @param r: room
	 * @return a map from pairs of day and room, to sets of initial hours
	 */
	Map<Pair<Day,Room>,Set<Integer>> courseSlots(String course);

}