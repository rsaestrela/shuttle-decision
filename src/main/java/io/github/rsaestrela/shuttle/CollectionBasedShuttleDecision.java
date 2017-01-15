package io.github.rsaestrela.shuttle;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorInstantiationException;
import io.github.rsaestrela.shuttle.decisor.function.CollectionBasedDecisor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class CollectionBasedShuttleDecision<I extends Collection<I>, O, H>
        extends AbstractCollectionBasedShuttleDecision<I, O, H>
        implements CollectableShuttleDecision<O> {

    private Set<CollectionBasedDecisor> decisors = new HashSet<>();

    public CollectionBasedShuttleDecision(Collection i) {
        super(i);
    }

    @Override
    public CollectionBasedShuttleDecision withDecisor(CollectionBasedDecisor decisor) {
        decisors.add(decisor);
        return this;
    }

    @Override
    public Set<O> decide()
            throws ShuttleDecisorIndeterminateResultException {
        Set<O> decisions = new HashSet<>();
        for (CollectionBasedDecisor decisor: decisors) {
            decisions.add((O) decisor.decide(i, head));
        }
        return decisions;
    }

    @Override
    protected void checkArgs(Collection<I> i) {
        if (i == null || i.isEmpty()) {
            throw new ShuttleDecisorInstantiationException("Null or empty input");
        }
    }
}
