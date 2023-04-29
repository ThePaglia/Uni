package a01b.sol1;

public class EventImpl<X> implements Event<X> {
	
	final private int time;
	final private X value;
	
	public EventImpl(int time, X value) {
		super();
		this.time = time;
		this.value = value;
	}

	@Override
	public int getTime() {
		return this.time;
	}

	@Override
	public X getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time ^ (time >>> 32));
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventImpl other = (EventImpl) obj;
		if (time != other.time)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + time + ":" + value + "]";
	}
	
}