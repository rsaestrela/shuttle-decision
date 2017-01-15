package io.github.rsaestrela.shuttle;


import io.github.rsaestrela.shuttle.decisor.DemocraticDecisorImpl;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorIndeterminateResultException;
import io.github.rsaestrela.shuttle.decisor.exception.ShuttleDecisorInstantiationException;
import io.github.rsaestrela.shuttle.decisor.function.CollectionBasedDemocraticDecisor;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.testng.Assert.*;

public class CollectionBasedShuttleDecisorTest {

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
            new CollectionBasedShuttleDecision<>(collection);
        } catch (ShuttleDecisorInstantiationException e) {
            return;
        }
        fail();
    }

    @Test
    public void shouldReturnFirstValueIfAllTheSame() {

        CollectableShuttleDecision<Set<String>> collectionBasedShuttleDecision =
                new CollectionBasedShuttleDecision<>(Arrays.asList("2", "2", "2", "2", "2"));

        try {

            CollectionBasedDemocraticDecisor democraticDecisor = new DemocraticDecisorImpl();
            Set decisions = collectionBasedShuttleDecision
                    .withDecisor(democraticDecisor)
                    .decide();

            assertTrue(decisions.contains("2"));
            assertEquals(democraticDecisor.majority(), -1);
        } catch (ShuttleDecisorIndeterminateResultException e) {
            fail();
        }
    }

    @Test
    public void shouldDecideForTheMajority() {
        CollectableShuttleDecision<String> collectionBasedShuttleDecision =
                new CollectionBasedShuttleDecision<>(Arrays.asList("1", "1", "1", "2", "3"));
        try {
            Set decisions = collectionBasedShuttleDecision
                    .withDecisor(new DemocraticDecisorImpl<>())
                    .decide();
            assertTrue(decisions.contains("1"));
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
        CollectableShuttleDecision<Integer> collectionBasedShuttleDecision =
                new CollectionBasedShuttleDecision<>(integers);
        try {
            Set decisions = collectionBasedShuttleDecision
                    .withDecisor(new DemocraticDecisorImpl<>())
                    .decide();
            assertTrue(decisions.contains(1));
        } catch (ShuttleDecisorIndeterminateResultException e) {
            fail();
        }
    }

    @Test(description = "string is not the best heavy object btw")
    public void shouldDecideForTheMajority1MHeavyString() {

        Source<String> stringSource = new Source<>();
        String[] stringArray = new String[99998];
        for (int i = 0; i < 99998; i++) {
            stringArray[i] = RandomStringUtils.random(100, true, true);
        }
        List<String> strings = stringSource.anValuedArrayListWithDuplicates(2, 100000, String.class, "shutter", stringArray);
        CollectableShuttleDecision<String> collectionBasedShuttleDecision =
                new CollectionBasedShuttleDecision<>(strings);
        try {
            Set decisions = collectionBasedShuttleDecision
                    .withDecisor(new DemocraticDecisorImpl<>())
                    .decide();
            assertTrue(decisions.contains("shutter"));
        } catch (ShuttleDecisorIndeterminateResultException e) {
            fail();
        }
    }

    private class TestObject {

        private Integer boxedIntField;
        private String stringField;

        public TestObject(Integer boxedIntField, String stringField) {
            this.boxedIntField = boxedIntField;
            this.stringField = stringField;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestObject that = (TestObject) o;
            return Objects.equals(boxedIntField, that.boxedIntField) &&
                    Objects.equals(stringField, that.stringField);
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
