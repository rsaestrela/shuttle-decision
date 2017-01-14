package io.github.rsaestrela.shuttle.decisor.function;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;

public interface DemocraticDecisor<I, O> {

    O decide(I i) throws ShuttleDecisorIndeterminateResultException;

}
