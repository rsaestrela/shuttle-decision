package io.github.rsaestrela.shuttle.decisor.function;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;

import java.util.Collection;

public interface CollectionBasedDecisor<I, O, H> {
    O decide(Collection<I> i, H h) throws ShuttleDecisorIndeterminateResultException;
}
