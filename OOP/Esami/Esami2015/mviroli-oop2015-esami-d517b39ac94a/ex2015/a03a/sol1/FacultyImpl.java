package ex2015.a03a.sol1;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FacultyImpl implements Faculty {
    
    private final Map<Integer,Pair<String,Set<Integer>>> courses = new HashMap<>();
    private final Map<Integer,Pair<String,Set<Integer>>> students = new HashMap<>();
    
    private void check(boolean b, Supplier<RuntimeException> ex){
        if (b) {
            throw ex.get();
        }
    }

    @Override
    public void registerCourse(int id, String name) {
        check(this.courses.containsKey(id),()->new IllegalArgumentException("course already registered"));
        this.courses.put(id, new Pair<>(name,new HashSet<>()));
    }

    @Override
    public void registerStudent(int id, String name) {
        check(this.students.containsKey(id),()->new IllegalArgumentException("course already registered"));
        this.students.put(id, new Pair<>(name,new HashSet<>()));
    }

    @Override
    public void associate(int studentId, int courseId) {
        check(!this.students.containsKey(studentId),()->new IllegalArgumentException("student not registered"));
        check(!this.courses.containsKey(courseId),()->new IllegalArgumentException("course not registered"));
        check(this.courses.get(courseId).getY().contains(studentId),()->new IllegalStateException("course/student already registered"));
        this.courses.get(courseId).getY().add(studentId);
        this.students.get(studentId).getY().add(courseId);
    }

    @Override
    public String course(int id) {
        return this.courses.get(id).getX();
    }

    @Override
    public String student(int id) {
        return this.students.get(id).getX();
    }

    @Override
    public Set<Integer> coursesByStudent(int studentId){
        return Collections.unmodifiableSet(this.students.get(studentId).getY());
    }

    @Override
    public Set<Integer> studentsByCourse(int courseId) {
        return Collections.unmodifiableSet(this.courses.get(courseId).getY());
    }
    
    @Override
    public Map<String,Set<String>> mapCoursesStudents(){
        return this.courses.values()
                   .stream()
                   .collect(Collectors.toMap(e -> e.getX(),
                                             e -> e.getY().stream()
                                                          .map(i -> this.students.get(i).getX())
                                                          .collect(Collectors.toSet())));
    }

}
