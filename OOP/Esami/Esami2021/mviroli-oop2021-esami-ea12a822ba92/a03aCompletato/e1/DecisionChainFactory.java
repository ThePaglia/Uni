package a03a.e1;

import java.util.List;
import java.util.function.Predicate;

/**
 * An interface to model several factories of DecisionChain, with many opportunities of reuse
 *
 */
public interface DecisionChainFactory {
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param b the output to provide
	 * @return a simple decider that directly returns 'b' whatever input receives
	 */
	<A,B> DecisionChain<A,B> oneResult(B b);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param predicate
	 * @param positive
	 * @param negative
	 * @return a decider that if predicate is passed by the input then it delegates to
	 * a decider always returning 'positive', otherwise it delegates to a decider always 
	 * returning 'negative'  
	 */
	<A,B> DecisionChain<A,B> simpleTwoWay(Predicate<A> predicate, B positive, B negative);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param mapList, e.g. list (a1,b1),...,(an,bn)
	 * @param defaultReply is returned if the input is neither a1, nor a2, ..., nor an
	 * @return a chain of deciders, the first returns 'b1' if it receives 'a1', otherwise 
	 * it delegates to one that returns 'b2' if it receives 'a2', and so on. If latter
	 * decider does not receive 'an', then 'defaultReply' is returned.
	 */
	<A,B> DecisionChain<A,B> enumerationLike(List<Pair<A,B>> mapList, B defaultReply);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param predicate
	 * @param positive
	 * @param negative
	 * @return a decider that, if predicate is passed by the input, it delegates to 'positive' decider, 
	 * otherwise to 'negative' decider 
	 */
	<A,B> DecisionChain<A,B> twoWay(Predicate<A> predicate, DecisionChain<A,B> positive, DecisionChain<A,B> negative);
	
	/**
	 * @param <A>
	 * @param <B>
	 * @param cases, e.g. list (p1,b1),...,(pn,bn)
	 * @param defaultReply
	 * @return a chain of deciders, the first returns 'b1' if its input passes predicate 'p1', otherwise 
	 * it delegates to one that returns 'b2' if its input passes 'p2', and so on. If latter
	 * decider does not receive an input that passes 'pn', then 'deafultReply' is returned.
	 */
	<A,B> DecisionChain<A,B> switchChain(List<Pair<Predicate<A>,B>> cases, B defaultReply);

}
