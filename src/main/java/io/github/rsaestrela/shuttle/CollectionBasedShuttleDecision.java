package io.github.rsaestrela.shuttle;

import io.github.rsaestrela.shuttle.decisor.DemocraticDecisorImpl;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorInstantiationException;
import io.github.rsaestrela.shuttle.decisor.function.CollectionBasedDemocraticDecisor;

import java.util.Collection;

@SuppressWarnings("unchecked")
public final class CollectionBasedShuttleDecision<I extends Collection<I>, O, H>
        extends AbstractCollectionBasedShuttleDecision<I, O, H> {

    private CollectionBasedDemocraticDecisor<I, O, H> decisor = new DemocraticDecisorImpl<>();

    public CollectionBasedShuttleDecision(Collection i) {
        super(i);
    }

    @Override
    public int majority() {
        return decisor.majority(i);
    }

    @Override
    public O decide() throws ShuttleDecisorIndeterminateResultException {
        return decisor.decide(i, head);
    }

    @Override
    protected void checkArgs(Collection<I> i) {
        if (i == null || i.isEmpty()) {
            throw new ShuttleDecisorInstantiationException("Null or empty input");
        }
    }
}
