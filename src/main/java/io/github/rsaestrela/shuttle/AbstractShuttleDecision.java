package io.github.rsaestrela.shuttle;

import java.util.Collection;

@SuppressWarnings("unchecked")
public abstract class AbstractShuttleDecision<I extends Collection, O, F> implements ShuttleDecision<O> {

    protected I i;
    protected F f;

    protected AbstractShuttleDecision(I i) {
        checkArgs(i);
        this.i = i;
        this.f = (F) i.toArray()[0];
    }

    public abstract O decide();

    private void checkArgs(I i) {
        if (i == null || i.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
