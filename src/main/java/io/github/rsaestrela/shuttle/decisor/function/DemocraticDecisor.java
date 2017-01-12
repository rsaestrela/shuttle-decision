package io.github.rsaestrela.shuttle.decisor.function;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;

import java.util.Collection;

public interface DemocraticDecisor<I extends Collection<I>, O, F> {

    double majority(Collection<I> i);

    O decide(Collection<I> i, F f) throws ShuttleDecisorIndeterminateResultException;

}
