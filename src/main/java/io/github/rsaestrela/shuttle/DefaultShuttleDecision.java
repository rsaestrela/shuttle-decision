package io.github.rsaestrela.shuttle;

import io.github.rsaestrela.shuttle.decisor.Decisor5Impl;
import io.github.rsaestrela.shuttle.decisor.function.Decisor5;

import java.util.Collection;

@SuppressWarnings("unchecked")
public class DefaultShuttleDecision<I extends Collection, O, F> extends AbstractShuttleDecision<I, O, F> {

    private final Decisor5<I, O, F> decisor = new Decisor5Impl<>();

    public DefaultShuttleDecision(I i) {
        super(i);
    }

    @Override
    public O decide() {
        return decisor.decide(i, f);
    }
}
