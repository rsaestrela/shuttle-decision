package io.github.rsaestrela.shuttle.decisor.function;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;

import java.util.Collection;

public interface Decisor5<I extends Collection, O, F> {

    int NUMBER_OF_ELEMENTS = 5;

    O decide(Collection<I> i, F f) throws ShuttleDecisorIndeterminateResultException;
}
