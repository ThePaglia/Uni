package a03a.sol1;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupingImpl<G,V> implements Grouping<G, V> {
	
	private final Map<G,Set<V>> map;
	
	public GroupingImpl(Map<G, Set<V>> map) {
		this.map = map;
	}

	@Override
	public Set<V> getValuesOfGroup(G group) {
		return this.map.get(group);
	}

	@Override
	public Set<G> getGroups() {
		return this.map.keySet();
	}

	@Override
	public Optional<G> getGroupOf(V data) {
		return this.map.entrySet().stream().filter(e -> e.getValue().contains(data)).map(e -> e.getKey()).findAny();
	}

	@Override
	public Map<G, Set<V>> asMap() {
		return Collections.unmodifiableMap(this.map);
	}

	@Override
	public Grouping<G, V> combineGroups(G initial1, G initial2, G result) {
		Map<G,Set<V>> map2 = new HashMap<>(this.map);
		var s = new HashSet<>(map2.get(initial1));
		s.addAll(map2.get(initial2));
		map2.put(result, s);
		map2.remove(initial1);
		map2.remove(initial2);
		return new GroupingImpl<>(map2);
	}
	
	

}
