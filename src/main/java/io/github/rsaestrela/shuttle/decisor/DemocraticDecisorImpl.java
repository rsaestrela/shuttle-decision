package io.github.rsaestrela.shuttle.decisor;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.function.CollectionBasedDemocraticDecisor;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Collection based implementation of
 * {@link CollectionBasedDemocraticDecisor}.
 *
 * <i>Democratic decision making is when the leader gives up
 * ownership and control of a decision and allows the group
 * to vote. Majority vote will decide the action. Advantages
 * include a fairly fast decision, and a certain amount of
 * group participation. The disadvantage of this style includes
 * no responsibility."</i>
 *
 */
@SuppressWarnings("unchecked")
public class DemocraticDecisorImpl<I extends Collection<I>, O, H>
        implements CollectionBasedDemocraticDecisor<I, O, H> {

    private int majority = -1;

    /**
     * Aims to choose an element from the supplied collection, based on
     * democratic rules. Given a non empty collection of <i>n</i> elements
     * it will decide to return the most repeated element found. It's an
     * atomic operation in the sense that can only return a valid element,
     * otherwise throws an exception.
     *
     * Object comparison is based in {@link HashMap::hash} so you don't
     * have to override your objects hashcode nor equals.
     *
     * @return O the chosen element
     * @throws ShuttleDecisorIndeterminateResultException if no decision
     * could be computed
     */
    public O decide(Collection<I> collection, H head)
            throws ShuttleDecisorIndeterminateResultException {

        final Map<?, Integer> entriesMap = mapEntries(collection);

        if (sameElements(entriesMap)) {
            return (O) head;
        }

        getAndSetMajority(entriesMap.values());
        noMajorityOnRepeated(entriesMap.values());

        final Optional<Object> decision = getDecision(entriesMap);
        if (!decision.isPresent()) {
            throw new ShuttleDecisorIndeterminateResultException("Cannot compute a decision");
        }
        return (O) decision.get();
    }

    private Map<?, Integer> mapEntries(Collection<I> i) {
        Map<Object, Integer> entriesMap = new HashMap<>();
        for (Object curr : i.toArray()) {
            if (entriesMap.containsKey(curr)) {
                entriesMap.put(curr, entriesMap.get(curr) + 1);
            } else {
                entriesMap.put(curr, 1);
            }
        }
        return entriesMap;
    }

    private boolean sameElements(Map<?, Integer> entriesMap) {
        return entriesMap.size() == 1;
    }

    private void getAndSetMajority(Collection<Integer> allMappedValues) {
        for (Integer occurrence: allMappedValues) {
            if (occurrence > majority) {
                majority = occurrence;
            }
        }
    }

    private void noMajorityOnRepeated(Collection<Integer> allMajorities) {
        Set<Integer> majorities = new HashSet<>();
        for (Integer occurrence: allMajorities) {
            if (occurrence == majority && !majorities.add(occurrence)) {
                majority = -1;
                break;
            }
        }
    }

    private Optional<Object> getDecision(Map<?, Integer> entriesMap) {
        for (Object key: entriesMap.keySet()) {
            int value = entriesMap.get(key);
            if (value == majority) {
                return of(key);
            }
        }
        return empty();
    }

    @Override
    public int majority() {
        return majority;
    }

}
