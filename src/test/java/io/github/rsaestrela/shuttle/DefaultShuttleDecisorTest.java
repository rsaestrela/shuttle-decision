package io.github.rsaestrela.shuttle;


import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorInstantiationException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class DefaultShuttleDecisorTest {

    @DataProvider(name = "emptyOrNullStructuresProvider")
    public Object[][] emptyOrNullStructuresProvider() {
        return new Object[][]{
                {new ArrayList<>()},
                {new CopyOnWriteArrayList<>()},
                {new Vector<>()},
                {new HashSet<>()},
                {new ConcurrentSkipListSet<>()},
                {new LinkedHashSet<>()},
                {new TreeSet<>()},
                {null}
        };
    }

    @Test(dataProvider = "emptyOrNullStructuresProvider")
    public void shouldFailFastOnEmptyCollection(Collection<?> collection) {
        try {
            new DefaultShuttleDecision<>(collection);
        } catch (ShuttleDecisorInstantiationException e) {
            return;
        }
        fail();
    }

    @Test
    public void shouldReturnFirstValueIfAllTheSame() {
        ShuttleDecision<String> defaultShuttleDecision = new DefaultShuttleDecision<>(Arrays.asList("2", "2", "2", "2", "2"));

        try {
            String decision = defaultShuttleDecision.decide();
            assertEquals(decision, "2");
        } catch (ShuttleDecisorIndeterminateResultException e) {
            fail();
        }
    }

    @Test
    public void shouldDecideForTheMajority() {
        ShuttleDecision<String> defaultShuttleDecision = new DefaultShuttleDecision<>(Arrays.asList("1", "1", "1", "2", "3"));
        try {
            String decision = defaultShuttleDecision.decide();
            assertEquals(decision, "1");
        } catch (ShuttleDecisorIndeterminateResultException e) {
            fail();
        }
    }

}
