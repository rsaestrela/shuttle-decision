package io.github.rsaestrela.shuttle.decisor;

import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.function.CollectionBasedConsensusDecisor;

import java.util.Collection;

/**
 * Collection based implementation of
 * {@link CollectionBasedConsensusDecisor}.
 *
 * <i>Consensus decision making is when the leader gives up total
 * control of the decision. The complete group is totally involved
 * in the decision. The leader is not individually responsible for
 * the outcome. The complete organization or group is now responsible
 * for the outcome. This is not a democratic style because everyone
 * must agree and "buy in" on the decision. If total commitment and
 * agreement by everyone is not obtained the decision becomes democratic.
 * The advantages include group commitment and responsibility for the
 * outcome. Teamwork and good security is also created because everyone
 * has a stake in the success of the decision. A more accurate decision
 * is usually made, with a higher probability of success, because so
 * many ideas, perspectives, skills and "brains" were involved in the
 * creation. The disadvantages include a very slow and extremely time
 * consuming decision. It is also a lot of work getting everyone in
 * the organization involved. It takes skill and practice for a group
 * to learn how to work together.</i>
 *
 */
@SuppressWarnings("unchecked")
public class ConsensusDecisorImpl<I extends Collection<I>, O, H>
        implements CollectionBasedConsensusDecisor<I, O, H> {

    /**
     * Aims to choose an element from the supplied collection, based on
     * consensus rule. Given a non empty collection of <i>n</i> elements
     * it return the first element if all elements are logically equals.
     * It's an atomic operation in the sense that can only return a valid
     * element, otherwise throws an exception.
     *
     * Object comparison is based in {@link Object::equals} so in order
     * to compute a logical conversion input objects should have this
     * method overridden.
     *
     *
     * @return O the head element
     * @throws ShuttleDecisorIndeterminateResultException if no decision
     * could be computed
     */
    public O decide(Collection<I> collection, H head)
            throws ShuttleDecisorIndeterminateResultException {
        if (!diffFound(collection, head)) {
            return (O) head;
        } else {
           throw new ShuttleDecisorIndeterminateResultException("Cannot compute a decision");
        }
    }

    private boolean diffFound(Collection<I> i, H head) {
        final Object[] array = i.toArray();
        for (Object element : array) {
            if (!element.equals(head)) {
                return true;
            }
        }
        return false;
    }

}
