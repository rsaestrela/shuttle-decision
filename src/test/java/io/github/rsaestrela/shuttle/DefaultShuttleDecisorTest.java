package io.github.rsaestrela.shuttle;


import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorInstantiationException;
import org.apache.commons.lang.RandomStringUtils;
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

    @Test
    public void shouldDecideForTheMajority1M() {
        Source<Integer> integerSource = new Source<>();
        Integer[] intArray = new Integer[666666];
        for (int i = 0; i < 666666; i++) {
            intArray[i] = i;
        }
        List<Integer> integers = integerSource.anValuedArrayListWithDuplicates(333334, 1000000, Integer.class, 1, intArray);
        ShuttleDecision<Integer> defaultShuttleDecision = new DefaultShuttleDecision<>(integers);
        try {
            Integer decision = defaultShuttleDecision.decide();
            assertEquals(decision, new Integer(1));
        } catch (ShuttleDecisorIndeterminateResultException e) {
            fail();
        }
    }

    @Test(enabled = false, description = "string is not the best heavy object btw")
    public void shouldDecideForTheMajority1MHeavyString() {
        Source<String> stringSource = new Source<>();
        String[] stringArray = new String[666666];
        for (int i = 0; i < 666666; i++) {
            stringArray[i] = RandomStringUtils.random(1000, true, true);
        }
        List<String> strings = stringSource.anValuedArrayListWithDuplicates(333334, 1000000, String.class, "shutter", stringArray);
        ShuttleDecision<String> defaultShuttleDecision = new DefaultShuttleDecision<>(strings);
        try {
            long started = System.nanoTime();
            String decision = defaultShuttleDecision.decide();
            assertEquals(decision, "shutter");
            System.out.println((System.nanoTime() - started));
        } catch (ShuttleDecisorIndeterminateResultException e) {
            fail();
        }
    }

    @SuppressWarnings("unchecked")
    private class Source<T> {

        public List<T> anValuedArrayListWithDuplicates(int nrOfDuplicates, int totalElements, Class<T> clazz, T sampleObj, T[] otherObjs) {
            return arrayListWithDuplicates(nrOfDuplicates, totalElements, clazz, sampleObj, otherObjs);
        }

        private List<T> arrayListWithDuplicates(int nrOfDuplicates, int totalElements, Class<T> clazz, T sampleObj, T[] otherObjs) {
            final boolean isValued = sampleObj != null && otherObjs.length > 0;

            if (isValued && otherObjs.length != (totalElements - nrOfDuplicates)) {
                fail();
            }

            List<T> list = new ArrayList<>();
            T sampleObject = null;
            try {
                if (isValued) {
                    sampleObject = sampleObj;
                } else {
                    sampleObject = clazz.newInstance();
                }
            } catch (InstantiationException | IllegalAccessException e) {
                fail();
            }
            for (int i = 0; i < nrOfDuplicates; i++) {
                list.add(sampleObject);
            }
            for (int j = 0; j < totalElements - nrOfDuplicates; j++) {
                try {
                    if (isValued) {
                        list.add(otherObjs[j]);
                    } else {
                        list.add(clazz.newInstance());
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    fail();
                }
            }
            return list;
        }
    }
}
