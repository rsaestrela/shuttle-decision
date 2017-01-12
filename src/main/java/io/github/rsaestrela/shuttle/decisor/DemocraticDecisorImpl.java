package io.github.rsaestrela.shuttle.decisor;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.function.DemocraticDecisor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public int majority(Collection<I> i) {
        return i.size() / 2 + 1;
    }

    public O decide(Collection<I> i, H head) throws ShuttleDecisorIndeterminateResultException {
        if (!diffFound(i, head)) {
            return (O) head;
        }
        final Object[] iArray = i.toArray();
        final Optional<Object> decision = getDecision(toResultMap(iArray), majority(i));
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

    private Map<?, Integer> toResultMap(Object[] i) {
        Map<Object, Integer> resultMap = new HashMap<>();
        for (Object curr : i) {
            if (resultMap.containsKey(curr)) {
                resultMap.put(curr, resultMap.get(curr) + 1);
            } else {
                resultMap.put(curr, 1);
            }
        }
        return resultMap;
    }

    private Optional<Object> getDecision(Map<?, Integer> resultMap, int majority) {
        for (Object key: resultMap.keySet()) {
            int value = resultMap.get(key);
            if (value >= majority) {
                return of(key);
            }
        }
        return empty();
    }
}
