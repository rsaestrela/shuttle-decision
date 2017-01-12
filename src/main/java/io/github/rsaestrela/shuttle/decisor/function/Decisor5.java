package io.github.rsaestrela.shuttle.decisor.function;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;

import java.util.Collection;

public interface Decisor5<I extends Collection<I>, O, F> {

    int NUMBER_OF_ELEMENTS = 5;
    int MAJORITY = 3;

    O decide(Collection<I> i, F f) throws ShuttleDecisorIndeterminateResultException;
}
