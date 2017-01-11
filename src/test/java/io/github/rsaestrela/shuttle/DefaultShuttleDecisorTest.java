package io.github.rsaestrela.shuttle;


import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

//// TODO: 1/11/17
public class DefaultShuttleDecisorTest {

    @Test
    public void defaultTest() {
        try {
            ShuttleDecision<String> defaultShuttleDecision = new DefaultShuttleDecision<>(new ArrayList<>());
            defaultShuttleDecision.decide();
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    public void defaultTest2() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        ShuttleDecision<String> defaultShuttleDecision = new DefaultShuttleDecision<>(new ArrayList<>(list));
        String decision = defaultShuttleDecision.decide();
        assertEquals(decision, "1");
    }
}
