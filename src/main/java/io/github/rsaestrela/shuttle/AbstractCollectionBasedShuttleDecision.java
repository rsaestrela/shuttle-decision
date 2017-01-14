package io.github.rsaestrela.shuttle;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;

import java.util.Collection;

@SuppressWarnings("unchecked")
public abstract class AbstractCollectionBasedShuttleDecision<I extends Collection, O, H>
        implements CollectableShuttleDecision<O> {

    protected Collection<I> i;
    protected H head;

    protected AbstractCollectionBasedShuttleDecision(Collection<I> i) {
        checkArgs(i);
        this.i = i;
        this.head = (H) i.toArray()[0];
    }

    public abstract int majority();

    public abstract O decide()
            throws ShuttleDecisorIndeterminateResultException;

    protected abstract void checkArgs(Collection<I> i);

}
