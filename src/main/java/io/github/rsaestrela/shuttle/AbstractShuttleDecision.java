package io.github.rsaestrela.shuttle;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;

import java.util.Collection;

@SuppressWarnings("unchecked")
public abstract class AbstractShuttleDecision<I extends Collection, O, H> implements ShuttleDecision<O> {

    protected Collection<I> i;
    protected H head;

    protected AbstractShuttleDecision(Collection<I> i) {
        checkArgs(i);
        this.i = i;
        this.head = (H) i.toArray()[0];
    }

    public abstract O decide() throws ShuttleDecisorIndeterminateResultException;

    protected abstract void checkArgs(Collection<I> i);

}
