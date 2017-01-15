package io.github.rsaestrela.shuttle;


import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.function.CollectionBasedDecisor;

import java.util.Set;

public interface CollectableShuttleDecision<O> extends ShuttleDecision {
    CollectionBasedShuttleDecision withDecisor(CollectionBasedDecisor decisor);
    Set<O> decide() throws ShuttleDecisorIndeterminateResultException;
}
