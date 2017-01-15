package io.github.rsaestrela.shuttle.decisor.function;

import java.util.Collection;

public interface CollectionBasedDemocraticDecisor<I extends Collection<I>, O, F>
        extends CollectionBasedDecisor<I, O, F> {
    int majority();
}
