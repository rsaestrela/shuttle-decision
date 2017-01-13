package io.github.rsaestrela.shuttle.decisor;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.function.DemocraticDecisor;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Collection based implementation of {@link DemocraticDecisor}.
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
        implements DemocraticDecisor<I, O, H> {

    private int majority = -1;

    /**
     * Aims to choose an element from the supplied collection, based on
     * democratic rules. Given a non empty collection of <i>n</i> elements
     * it will decide to return the most repeated element found.
     *
     * If all elements are equal returns the parameter {@param head}.
     * If all the elements exist in the same quantity in the provided
     * collection or the most contained elements exist repeatedly
     * it will throw a checked  {@link ShuttleDecisorIndeterminateResultException}
     * exception.
     */
    public O decide(Collection<I> collection, H head) throws ShuttleDecisorIndeterminateResultException {

        final Map<?, Integer> resultsMap = getResultsMap(collection);

        if (sameElements(resultsMap)) {
            return (O) head;
        }

        getAndSetMajority(resultsMap);
        noMajorityOnRepeated(resultsMap);

        final Optional<Object> decision = getDecision(resultsMap);
        if (!decision.isPresent()) {
            throw new ShuttleDecisorIndeterminateResultException("Cannot compute a decision");
        }
        return (O) decision.get();
    }

    private boolean sameElements(Map<?, Integer> resultsMap) {
        return resultsMap.size() == 1;
    }

    private Map<?, Integer> getResultsMap(Collection<I> i) {
        Map<Object, Integer> resultsMap = new HashMap<>();
        for (Object curr : i.toArray()) {
            if (resultsMap.containsKey(curr)) {
                resultsMap.put(curr, resultsMap.get(curr) + 1);
            } else {
                resultsMap.put(curr, 1);
            }
        }
        return resultsMap;
    }

    private void getAndSetMajority(Map<?, Integer> resultsMap) {
        for (Integer occurrence: resultsMap.values()) {
            if (occurrence > majority) {
                majority = occurrence;
            }
        }
    }

    private void noMajorityOnRepeated(Map<?, Integer> resultsMap) {
        Set<Integer> majorities = new HashSet<>();
        for (Integer occurrence: resultsMap.values()) {
            if (occurrence == majority && !majorities.add(occurrence)) {
                majority = -1;
                break;
            }
        }
    }

    private Optional<Object> getDecision(Map<?, Integer> resultMap) {
        for (Object key: resultMap.keySet()) {
            int value = resultMap.get(key);
            if (value == majority) {
                return of(key);
            }
        }
        return empty();
    }

    @Override
    public int majority(Collection<I> i) {
        return majority;
    }
}
