package ex2015.a03b.sol1;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ThesesManagementImpl implements ThesesManagement {
    
    private final Map<Integer,Pair<String,Optional<Thesis>>> students = new HashMap<>();
    private final Map<Integer,Integer> theses = new HashMap<>();
    
    private static class Thesis {
        private String title;
        private Status status = Status.TO_BE_APPROVED;
        private Optional<Integer> score = Optional.empty();
        public Thesis(String title) {
            super();
            this.title = title;
        }
        public Status getStatus(){
            return this.status;
        }
        public String getTitle(){
            return this.title;
        }
        public Optional<Integer> getScore(){
            return this.score;
        }
        public void approved(){
            this.status = Status.APPROVED;
        }
        public void submitted(String finalTitle){
            this.status = Status.SUBMITTED;
            this.title = finalTitle;
        }
        public void concluded(int score){
            this.status = Status.CONCLUDED;
            this.score = Optional.of(score);
        }
    }
    
    private void check(boolean b, Supplier<RuntimeException> ex){
        if (b) {
            throw ex.get();
        }
    }

    @Override
    public void registerStudent(int studentId, String name) {
        check(this.students.containsKey(studentId),()->new IllegalArgumentException("student already registered"));
        this.students.put(studentId, new Pair<>(name,Optional.empty()));
    }

    @Override
    public void registerThesis(int thesisId, String title, int studentId) {
        check(this.theses.containsKey(thesisId),()->new IllegalArgumentException("thesis already existing"));
        check(!this.students.containsKey(studentId),()->new IllegalArgumentException("student should exist"));
        final Pair<String,Optional<Thesis>> info = this.students.get(studentId);
        check(info.getY().isPresent(),()->new IllegalArgumentException("student already has a thesis"));
        this.theses.put(thesisId, studentId);
        info.setY(Optional.of(new Thesis(title)));
    }
    
    private Thesis thesisById(int id){
        return this.students.get(this.theses.get(id)).getY().get();
    }

    @Override
    public void thesisApproved(int thesisId) {
        check(this.thesisById(thesisId).getStatus()!=Status.TO_BE_APPROVED,()->new IllegalArgumentException("not to be approved"));
        this.thesisById(thesisId).approved();
    }

    @Override
    public void thesisSubmitted(int thesisId, String finalTitle) {
        check(this.thesisById(thesisId).getStatus()!=Status.APPROVED,()->new IllegalArgumentException("not to be submitted"));
        this.thesisById(thesisId).submitted(finalTitle);
    }

    @Override
    public void thesisConcluded(int thesisId, int score) {
        check(this.thesisById(thesisId).getStatus()!=Status.SUBMITTED,()->new IllegalArgumentException("not to be concluded"));
        this.thesisById(thesisId).concluded(score);
    }

    @Override
    public Map<String, Status> statusByStudent() {
        return this.students
                   .values()
                   .stream()
                   .filter(e -> e.getY().isPresent())
                   .map(e -> new Pair<>(e.getX(),e.getY().get()))
                   .collect(Collectors.toMap(e -> e.getX(), e -> e.getY().getStatus()));
    }

    
    @Override
    public Pair<String, Status> thesis(int thesisId) {
        return new Pair<String,Status>(this.thesisById(thesisId).getTitle(),this.thesisById(thesisId).getStatus());
    }

    @Override
    public double averageThesisScore() {
        return this.students
                   .values()
                   .stream()
                   .map(e->e.getY())
                   .filter(Optional::isPresent)
                   .filter(e->e.get().getScore().isPresent())
                   .mapToInt(e->e.get().getScore().get())
                   .average().getAsDouble();
    }

    
}
