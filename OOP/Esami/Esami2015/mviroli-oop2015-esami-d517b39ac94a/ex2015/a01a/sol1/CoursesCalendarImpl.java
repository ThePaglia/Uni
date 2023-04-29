package ex2015.a01a.sol1;

import java.util.*;
import java.util.stream.*;

public class CoursesCalendarImpl implements CoursesCalendar {
	
	private static final List<Integer> AVAILABLE_SLOTS = Arrays.asList(9,10,11,12,14,15,16,17);
	private final Map<Pair<Day, Room>, Map<Integer,String>> slots = new HashMap<>();
	
	public CoursesCalendarImpl(){}
	
	@Override
	public List<Integer> possibleSlots(){
		return AVAILABLE_SLOTS;
	}
	
	private Map<Integer,String> getOrPrepareDayRoom(Day d, Room r){
		return slots.merge(new Pair<>(d,r), new HashMap<>(), (x,y)->x);
	}

	@Override
	public void bookRoom(Day d, Room r, int start, int duration, String course) {
		final Map<Integer,String> map = this.getOrPrepareDayRoom(d,r);
		int begin = AVAILABLE_SLOTS.indexOf(start);
		for (int i = begin; i < begin + duration; i++){
			if (map.containsKey(AVAILABLE_SLOTS.get(i))){
				throw new IllegalStateException();
			}
		}
		for (int i = begin; i < begin + duration; i++){
			map.put(AVAILABLE_SLOTS.get(i), course);
		}
	}

	@Override
	public Set<Pair<Integer, String>> dayRoomSlots(Day d, Room r) {
		final Map<Integer,String> map = this.getOrPrepareDayRoom(d,r);
		return map.entrySet().stream().map(e -> new Pair<>(e.getKey(),e.getValue())).collect(Collectors.toSet());
	}
	
	@Override
	public Map<Pair<Day, Room>, Set<Integer>> courseSlots(String course) {
		return this.slots
				   .entrySet()
				   .stream()
				   .collect(Collectors.toMap(k->k.getKey(),
						                     v-> v.getValue()
						                          .entrySet()
						                          .stream()
						                          .filter(e->e.getValue().equals(course)).map(e->e.getKey())
						                          .collect(Collectors.toSet())));
	}
}
