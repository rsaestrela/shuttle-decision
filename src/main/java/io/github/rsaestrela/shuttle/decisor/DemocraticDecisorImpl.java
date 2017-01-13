package io.github.rsaestrela.shuttle.decisor;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.function.DemocraticDecisor;

import java.util.*;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * "Democratic decision making is when the leader gives up ownership and control
 * of a decision and allows the group to vote. Majority vote will decide the action.
 * Advantages include a fairly fast decision, and a certain amount of group participation.
 * The disadvantage of this style includes no responsibility."
 * todo: documentation
 */
@SuppressWarnings("unchecked")
public class DemocraticDecisorImpl<I extends Collection<I>, O, H> implements DemocraticDecisor<I, O, H> {

    private int majority = 0;

    public O decide(Collection<I> i, H head) throws ShuttleDecisorIndeterminateResultException {
        if (!diffFound(i, head)) {
            return (O) head;
        }
        final Object[] iArray = i.toArray();
        final Map<?, Integer> resultsMap = getResultsMap(iArray);

        getAndSetMajority(resultsMap);
        failOnRepeatedMajorities(resultsMap);

        final Optional<Object> decision = getDecision(resultsMap);
        if (!decision.isPresent()) {
            throw new ShuttleDecisorIndeterminateResultException("Cannot compute a decision");
        }
        return (O) decision.get();
    }

    private boolean diffFound(Collection<I> i, H head) {
        final Object[] array = i.toArray();
        for (Object element : array) {
            if (!element.equals(head)) {
                return true;
            }
        }
        return false;
    }

    private Map<?, Integer> getResultsMap(Object[] i) {
        Map<Object, Integer> resultsMap = new HashMap<>();
        for (Object curr : i) {
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

    private void failOnRepeatedMajorities(Map<?, Integer> resultsMap) {
        Set<Integer> majorities = new HashSet<>();
        for (Integer occurrence: resultsMap.values()) {
            if (occurrence == majority) {
                if (!majorities.add(occurrence)) {
                    majority = -1;
                    break;
                }
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
