package io.github.rsaestrela.shuttle.decisor;

import io.github.rsaestrela.shuttle.decisor.function.Decisor5;

import java.util.Collection;

@SuppressWarnings("unchecked")
public class Decisor5Impl<I extends Collection, O, F> implements Decisor5<I, O, F> {

    public O decide(I i, F f) {
        if (i.stream().allMatch(element -> element.equals(f))) {
            return (O) f;
        }
        throw new IllegalArgumentException();
    }
}
