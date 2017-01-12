package io.github.rsaestrela.shuttle.decisor;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.function.Decisor5;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@SuppressWarnings("unchecked")
public class Decisor5Impl<I extends Collection<I>, O, F> implements Decisor5<I, O, F> {

    public O decide(Collection<I> i, F f) throws ShuttleDecisorIndeterminateResultException {
        if (!diffFound(i)) {
            return (O) f;
        }
        final Object[] iArray = i.toArray();
        final Optional<Object> decision = getDecision(getResultMap(iArray));
        if (!decision.isPresent()) {
            throw new ShuttleDecisorIndeterminateResultException("Cannot compute a decision");
        }
        return (O) decision.get();
    }

    private boolean diffFound(Collection<I> i) {
        final Object[] array = i.toArray();
        Object first = array[0];
        for (Object element : array) {
            if (!element.equals(first)) {
                return false;
            }
        }
        return true;
    }

    private Map<?, Integer> getResultMap(Object[] i) {
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

    private Optional<Object> getDecision(Map<?, Integer> resultMap) {
        for (Object key: resultMap.keySet()) {
            int value = resultMap.get(key);
            if (value >= MAJORITY) {
                return of(key);
            }
        }
        return empty();
    }
}
