package a03b.sol1;

import java.util.*;

public class StudentImpl implements Student {

	private final int id;
	private final String name;
	private final Map<Integer,Integer> labEvaluations = new HashMap<>();
	private Optional<Integer> projectEvaluation = Optional.empty();
	private Optional<String> projectName = Optional.empty();

	public StudentImpl(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see a03b.sol1.Student#getId()
	 */
	@Override
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see a03b.sol1.Student#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see a03b.sol1.Student#addLabEvaluation(int, int)
	 */
	@Override
	public void addLabEvaluation(int exam, int evaluation) {
		this.labEvaluations.put(exam, evaluation);
	}
	
	/* (non-Javadoc)
	 * @see a03b.sol1.Student#labEvaluations()
	 */
	@Override
	public Map<Integer,Integer> labEvaluations(){
		return Collections.unmodifiableMap(labEvaluations);
	}
	
	/* (non-Javadoc)
	 * @see a03b.sol1.Student#setProject(java.lang.String, int)
	 */
	@Override
	public void setProject(String projectName, int evaluation) {
		this.projectEvaluation = Optional.of(evaluation);
		this.projectName = Optional.of(projectName);
	}
	
	/* (non-Javadoc)
	 * @see a03b.sol1.Student#getProjectName()
	 */
	@Override
	public Optional<String> getProjectName(){
		return this.projectName;
	}
	
	/* (non-Javadoc)
	 * @see a03b.sol1.Student#getProjectEvaluation()
	 */
	@Override
	public Optional<Integer> getProjectEvaluation(){
		return this.projectEvaluation;
	}
	
	/* (non-Javadoc)
	 * @see a03b.sol1.Student#lastLabEvaluation()
	 */
	@Override
	public Optional<Integer> lastLabEvaluation(){
		return this.labEvaluations.entrySet().stream().max((e1,e2)->e1.getKey()-e2.getKey()).map(e->e.getValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StudentImpl)) {
			return false;
		}
		StudentImpl other = (StudentImpl) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "StudentImpl [id=" + id + ", name=" + name + ", labEvaluations=" + labEvaluations + ", projectEvaluation="
				+ projectEvaluation + ", projectName=" + projectName + "]";
	}
	
	
}
