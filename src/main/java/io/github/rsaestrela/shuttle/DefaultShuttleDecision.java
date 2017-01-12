package io.github.rsaestrela.shuttle;

import io.github.rsaestrela.shuttle.decisor.Decisor5Impl;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorInstantiationException;
import io.github.rsaestrela.shuttle.decisor.function.Decisor5;

import java.util.Collection;

@SuppressWarnings("unchecked")
public final class DefaultShuttleDecision<I extends Collection<I>, O, F> extends AbstractShuttleDecision<I, O, F> {

    private final Decisor5<I, O, F> decisor = new Decisor5Impl<>();

    public DefaultShuttleDecision(Collection i) {
        super(i);
    }

    @Override
    public O decide() throws ShuttleDecisorIndeterminateResultException {
        return decisor.decide(i, f);
    }

    @Override
    protected void checkArgs(Collection<I> i) {
        if (i == null || i.isEmpty()) {
            throw new ShuttleDecisorInstantiationException("Null or empty input");
        }
        if (i.size() != Decisor5.NUMBER_OF_ELEMENTS) {
            throw new ShuttleDecisorInstantiationException("Wrong number of input elements");
        }
    }
}
