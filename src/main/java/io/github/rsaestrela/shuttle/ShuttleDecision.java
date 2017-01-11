package io.github.rsaestrela.shuttle;


import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;

public interface ShuttleDecision<O> {
    O decide() throws ShuttleDecisorIndeterminateResultException;
}
