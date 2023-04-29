package ex2015.a01b.sol1;

import java.util.*;
import java.util.stream.*;

public class CoursesManagementImpl implements CoursesManagement {
    
    private final Map<Integer, Map<Course, Pair<Optional<String>, Set<String>>>> summary = new HashMap<>();
    
    public CoursesManagementImpl(){}
    
    private Map<Course, Pair<Optional<String>, Set<String>>> getOrInit(final int year) {
        final Map<Course, Pair<Optional<String>, Set<String>>> yMap = 
        		Optional.ofNullable(summary.get(year)).orElse(new EnumMap<>(Course.class));
        summary.put(year, yMap);
        return yMap;
    }

    @Override
    public void assignProfessor(final Course c, final int year, final String professor) {
        final Map<Course, Pair<Optional<String>, Set<String>>> yMap = getOrInit(year);
        final Pair<Optional<String>, Set<String>> profAndTutors = yMap.get(c);
        if (profAndTutors != null && profAndTutors.getX().isPresent()) {
            throw new IllegalStateException("This course already has a professor");
        }
        yMap.put(c, new Pair<>(Optional.of(professor), profAndTutors == null ? new HashSet<>() : profAndTutors.getY()));
    }

    @Override
    public void assignTutor(final Course c, final int year, final String tutor) {
        final Map<Course, Pair<Optional<String>, Set<String>>> yMap = getOrInit(year);
        final Pair<Optional<String>, Set<String>> profAndTutors = yMap.getOrDefault(c, new Pair<>(Optional.empty(), new HashSet<>()));
        final Set<String> tutors = profAndTutors.getY();
        if (tutors.size() >= TUTORING_SLOTS) {
            throw new IllegalStateException("This course already has three tutors");
        }
        tutors.add(tutor);
        yMap.putIfAbsent(c, profAndTutors);
    }

    @Override
    public Set<Course> getUnassignedCourses(final int year) {
        final Map<Course, Pair<Optional<String>, Set<String>>> target = summary.getOrDefault(year, Collections.emptyMap());
        return Arrays.stream(Course.values())
                .filter(c -> !(target.containsKey(c) && target.get(c).getX().isPresent()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Pair<Integer, Course>> getCoursesByProf(final String prof) {
        return summary.entrySet().stream()
                .flatMap(e -> e.getValue().entrySet().stream()
                        .map(entry -> new Pair<>(entry.getKey(), entry.getValue().getX()))
                        .filter(p -> p.getY().isPresent())
                        .filter(p -> prof.equals(p.getY().get()))
                        .map(p -> new Pair<>(e.getKey(), p.getX()))
                )
                .collect(Collectors.toSet());
    }

    @Override
    public Map<Integer, Set<Pair<Course, Integer>>> getRemainingTutoringSlots() {
        return summary.entrySet().stream()
            .map(entry -> new Pair<>(entry.getKey(), Arrays.stream(Course.values())
                    .map(c -> new Pair<>(c, TUTORING_SLOTS - Optional.ofNullable(entry.getValue().get(c))
                            .map(Pair::getY)
                            .map(Collection::size)
                            .orElse(0)))
                    .collect(Collectors.toSet()))
            )
            .collect(Collectors.toMap(Pair::getX, Pair::getY));
    }

    @Override
    public String toString() {
        return summary.toString();
    }


}
