package io.github.rsaestrela.shuttle;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;

import java.util.Collection;

@SuppressWarnings("unchecked")
public abstract class AbstractShuttleDecision<I extends Collection, O, F> implements ShuttleDecision<O> {

    protected Collection<I> i;
    protected F f;

    protected AbstractShuttleDecision(Collection<I> i) {
        checkArgs(i);
        this.i = i;
        this.f = (F) i.toArray()[0];
    }

    public abstract O decide() throws ShuttleDecisorIndeterminateResultException;

    protected abstract void checkArgs(Collection<I> i);

}
