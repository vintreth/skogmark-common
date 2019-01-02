package ru.skogmark.common.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link WeightedRandom}
 */
public class WeightedRandomTest {
    private WeightedRandom weightedRandom = new WeightedRandom();

    @Test
    public void shouldPickTheMostWeightedSorted() throws Exception {
        // given
        float[] weights = {1.0f, 0.7f, 0.4f, 0.3f, 0.1f, 0.05f, 0.02f, 0.01f};

        // when
        Map<Integer, Integer> results = doRandomPicking(weights);

        // then
        assertTrue("value of results[0] should be more then value of results[1]", results.get(0) > results.get(1));
        assertTrue("value of results[1] should be more then value of results[2]", results.get(1) > results.get(2));
        assertTrue("value of results[2] should be more then value of results[3]", results.get(2) > results.get(3));
        assertTrue("value of results[3] should be more then value of results[4]", results.get(3) > results.get(4));
        assertTrue("value of results[4] should be more then value of results[5]", results.get(4) > results.get(5));
        assertTrue("value of results[5] should be more then value of results[6]", results.get(5) > results.get(6));
        assertTrue("value of results[6] should be more then value of results[7]", results.get(6) > results.get(7));
    }

    @Test
    public void shouldPickTheMostWeightedUnsorted() throws Exception {
        // given
        float[] weights = {0.3f, 0.5f, 0.9f, 0.7f};

        // when
        Map<Integer, Integer> results = doRandomPicking(weights);

        // then
        assertTrue("value of results[2] should be more then value of results[3]", results.get(2) > results.get(3));
        assertTrue("value of results[3] should be more then value of results[1]", results.get(3) > results.get(1));
        assertTrue("value of results[1] should be more then value of results[0]", results.get(1) > results.get(0));
    }

    @Test
    public void shouldPickTheMostWeightedBigNumbers() throws Exception {
        // given
        float[] weights = {1.0f, 5.0f, 4.3f, 0.5f};

        // when
        Map<Integer, Integer> results = doRandomPicking(weights);

        // then
        assertTrue("value of results[1] should be more then value of results[2]", results.get(1) > results.get(2));
        assertTrue("value of results[2] should be more then value of results[0]", results.get(2) > results.get(0));
        assertTrue("value of results[0] should be more then value of results[3]", results.get(0) > results.get(3));
    }

    private Map<Integer, Integer> doRandomPicking(float[] weights) {
        Map<Integer, Integer> results = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            Integer index = weightedRandom.pick(weights);
            if (!results.containsKey(index)) {
                results.put(index, 1);
            } else {
                results.put(index, results.get(index) + 1);
            }
        }
        return results;
    }
}