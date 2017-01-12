package io.github.rsaestrela.shuttle;

import io.github.rsaestrela.shuttle.decisor.DemocraticDecisorImpl;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorInstantiationException;
import io.github.rsaestrela.shuttle.decisor.function.DemocraticDecisor;

import java.util.Collection;

@SuppressWarnings("unchecked")
public final class DefaultShuttleDecision<I extends Collection<I>, O, H> extends AbstractShuttleDecision<I, O, H> {

    private DemocraticDecisor<I, O, H> decisor = new DemocraticDecisorImpl<>();

    public DefaultShuttleDecision(Collection i) {
        super(i);
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
