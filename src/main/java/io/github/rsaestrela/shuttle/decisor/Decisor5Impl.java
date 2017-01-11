package io.github.rsaestrela.shuttle.decisor;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.function.Decisor5;

import java.util.Collection;

@SuppressWarnings("unchecked")
public class Decisor5Impl<I extends Collection, O, F> implements Decisor5<I, O, F> {

    public O decide(Collection<I> i, F f) throws ShuttleDecisorIndeterminateResultException {
        if (equalElements(i)) {
            return (O) f;
        }
        //todo
        throw new ShuttleDecisorIndeterminateResultException("Cannot compute a decision");
    }

    private boolean equalElements(Collection<I> i) {
        return !diffFound(i);
    }

    private boolean diffFound(Collection<I> i) {
        boolean diffFound = false;
        final Object[] iArray = i.toArray();
        for (int e = 0; e < i.size(); e++) {
            if (e + 1 == iArray.length) {
                diffFound = !iArray[e].equals(iArray[0]);
            } else {
                diffFound = !iArray[e].equals(iArray[e + 1]);
            }
        }
        return diffFound;
    }
}
