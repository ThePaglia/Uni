package a02b.sol1;

import java.util.*;
import java.util.stream.*;
import static a02b.sol1.ExamsFactory.SimpleExamActivities.*;
import static a02b.sol1.ExamsFactory.OOPExamActivities.*;

public class ExamsFactoryImpl implements ExamsFactory {
	
	// the map associates to an activity those it needs to be done before (see a02a.sol1)
	private <A> CourseExam<A> fromGraph(Map<A,Set<A>> map) {
		return new CourseExam<>() {
			
			private Set<A> done = new HashSet<>();
			
			@Override
			public Set<A> activities() {
				return map.keySet();
			}

			@Override
			public Set<A> getPendingActivities() {
				return this.examOver() ? Set.of() : this.activities().stream().filter(this::canBeDone).collect(Collectors.toSet());
			}
			
			private boolean canBeDone(A activity) {
				return !this.done.contains(activity) && this.done.containsAll(map.get(activity));
			}

			@Override
			public void completed(A activity) {
				if (!this.getPendingActivities().contains(activity)) {
					throw new IllegalStateException();
				}
				this.done.add(activity);
			}

			@Override
			public boolean examOver() {
				return this.done.equals(this.activities());
			}
			
		};
	}
	
	@Override
	public CourseExam<SimpleExamActivities> simpleExam() {
		return fromGraph(Map.of(
				WRITTEN, Set.of(),
				ORAL, Set.of(WRITTEN),
				REGISTER, Set.of(ORAL)
			));
	}

	@Override
	public CourseExam<OOPExamActivities> simpleOopExam() {
		return fromGraph(Map.of(
			LAB_REGISTER, Set.of(),
			LAB_EXAM, Set.of(LAB_REGISTER),
			PROJ_PROPOSE, Set.of(),
			PROJ_SUBMIT, Set.of(PROJ_PROPOSE),
			PROJ_EXAM, Set.of(PROJ_SUBMIT),
			FINAL_EVALUATION, Set.of(PROJ_EXAM,LAB_EXAM)
		));
	}
	
	@Override
	public CourseExam<OOPExamActivities> fullOopExam() {
		return fromGraph(Map.of(
			STUDY, Set.of(),
			LAB_REGISTER, Set.of(STUDY),
			LAB_EXAM, Set.of(LAB_REGISTER),
			PROJ_PROPOSE, Set.of(STUDY),
			PROJ_SUBMIT, Set.of(PROJ_PROPOSE),
			PROJ_EXAM, Set.of(PROJ_SUBMIT),
			CSHARP_TASK, Set.of(PROJ_SUBMIT),
			FINAL_EVALUATION, Set.of(CSHARP_TASK,PROJ_EXAM,LAB_EXAM)
		));
	}

}
