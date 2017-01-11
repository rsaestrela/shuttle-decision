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
    public void shouldFailFastOnEmptyCollection(Collection<Object> collection) {
        try {
            new DefaultShuttleDecision<>(collection);
        } catch (ShuttleDecisorInstantiationException e) {
            return;
        }
        fail();
    }

    @Test
    public void shouldReturnFirstValueIfAllTheSame() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");

        ShuttleDecision<String> defaultShuttleDecision = new DefaultShuttleDecision<>(list);
        String decision = null;
        try {
            decision = defaultShuttleDecision.decide();
        } catch (ShuttleDecisorIndeterminateResultException e) {
            fail();
        }
        assertEquals(decision, "1");
    }

    @Test//TODO: unexpected behaviour
    public void shouldThrowIndeterminateException() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("2");

        ShuttleDecision<String> defaultShuttleDecision = new DefaultShuttleDecision<>(list);
        try {
            defaultShuttleDecision.decide();
        } catch (ShuttleDecisorIndeterminateResultException e) {
            return;
        }
        fail();
    }
}
