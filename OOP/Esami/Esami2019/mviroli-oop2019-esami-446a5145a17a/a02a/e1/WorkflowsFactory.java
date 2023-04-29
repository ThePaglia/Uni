package a02a.e1;

import java.util.List;
import java.util.Set;

public interface WorkflowsFactory {
	
	/**
	 * @param <T>
	 * @param task
	 * @return a workflow made of a single 'task'
	 */
	<T> Workflow<T> singleTask(T task);
	
	/**
	 * @param <T>
	 * @param tasks
	 * @return a workflow made of first element of 'tasks', then second, then third, and so on
	 */
	<T> Workflow<T> tasksSequence(List<T> tasks);
	
	/**
	 * @param <T>
	 * @param tasks
	 * @return a workflow made of 'initialTasks' (any order), and then all are executed, of 'finalTask'  
	 */
	<T> Workflow<T> tasksJoin(Set<T> initialTasks, T finalTask);
	
	/**
	 * @param <T>
	 * @param initialTask
	 * @param finalTasks
	 * @return a workflow made of initialTasks, and then (in any order) all tasks in 'finalTask'   
	 */
	<T> Workflow<T> tasksFork(T initialTask, Set<T> finalTasks);
	
	/**
	 * >>> This method is optional in this exam!
	 * @param <T>
	 * @param first
	 * @param second
	 * @return a workflow where we execute entire workflow 'first', and then entire workflow 'second'
	 */
	<T> Workflow<T> concat(Workflow<T> first, Workflow<T> second);
	
}
