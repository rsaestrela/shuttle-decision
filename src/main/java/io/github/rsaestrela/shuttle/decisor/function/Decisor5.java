package io.github.rsaestrela.shuttle.decisor.function;

import java.util.Collection;

public interface Decisor5<I extends Collection, O, F> {
    O decide(I i, F f);
}
