package com.ocado.basket;

import com.ocado.basket.splitter.BasketSolution;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class BasketSolutionTests {
    private final static int REFERENCE_SOLUTION_NR_OF_GROUPS = 3;
    private final static int REFERENCE_SOLUTION_GROUPS_SIZE = 3;
    private static BasketSolution referenceSolution;
    private static BasketSolution emptySolution;

    @BeforeAll
    static void beforeAllSetup() {
        emptySolution = new BasketSolution(new HashMap<>(), 0);
    }
    @BeforeEach
    void setup() {
        Map<String, List<String>> referenceMap = getDummyMapWithSize(REFERENCE_SOLUTION_NR_OF_GROUPS, REFERENCE_SOLUTION_GROUPS_SIZE);
        referenceSolution = new BasketSolution(referenceMap, REFERENCE_SOLUTION_GROUPS_SIZE);
    }

    static int[] getNrOfGroupsRange() {
        return IntStream.iterate(-1 * REFERENCE_SOLUTION_NR_OF_GROUPS, i -> i <= REFERENCE_SOLUTION_NR_OF_GROUPS, i -> i + 1).toArray();
    }

    static int[] getNonNegativeNrOfGroupsRange() {
        return IntStream.iterate(0, i -> i <= REFERENCE_SOLUTION_NR_OF_GROUPS, i -> i + 1).toArray();
    }

    private static Map<String, List<String>> getDummyMapWithSize(int size, int valueListSize) {
        Map<String, List<String>> result = new HashMap<>();
        for (int i = 0; i < size; i++) {
            result.put("delivery " + i, IntStream.range(1, valueListSize).mapToObj(j -> "product " + j).toList());
        }
        return result;
    }

    @Test
    void solutionIsNotBetterThanItself() {
        assertFalse(referenceSolution.isBetterThan(referenceSolution));
        assertFalse(emptySolution.isBetterThan(emptySolution));
    }

    @ParameterizedTest
    @MethodSource("getNonNegativeNrOfGroupsRange")
    void solutionsWithSameParametersAreNotBetter(int number) {
        var firstSolution = new BasketSolution(getDummyMapWithSize(number, number), number);
        var secondSolution = new BasketSolution(getDummyMapWithSize(number, number), number);
        assertFalse(firstSolution.isBetterThan(secondSolution));
    }

    @ParameterizedTest
    @MethodSource("getNrOfGroupsRange")
    void solutionWithLessGroupsIsBetterNoMatterTheMaxGroupSize(int deviation) {
        var mapWithLowerGroupSize = getDummyMapWithSize(
                referenceSolution.deliveries().size() - Math.abs(deviation) - (deviation == 0 ? 1 : 0),
                referenceSolution.maxGroupSize() - deviation
        );
        var betterSolution = new BasketSolution(mapWithLowerGroupSize, referenceSolution.maxGroupSize());
        assertTrue(betterSolution.isBetterThan(referenceSolution));
    }

    @ParameterizedTest
    @MethodSource("getNrOfGroupsRange")
    void solutionWithSameNrOfGroupsIsBetterIfMaxGroupSizeIsHigher(int deviation) {
        var mapWithSameNrOfGroups = getDummyMapWithSize(
                referenceSolution.deliveries().size(),
                referenceSolution.maxGroupSize() + deviation
        );
        var anotherSolution = new BasketSolution(mapWithSameNrOfGroups, referenceSolution.maxGroupSize() + deviation);
        assertEquals(anotherSolution.isBetterThan(referenceSolution), deviation > 0);
    }

    @Test
    void appendingEmptyGroupShouldNotChangePreviousSolution() {
        var appended = BasketSolution.getSolutionWithAdditionalGroup("delivery 1", new ArrayList<>(), referenceSolution);
        assertEquals(appended.maxGroupSize(), referenceSolution.maxGroupSize());
        assertEquals(appended.deliveries().size(), referenceSolution.deliveries().size());
    }

    @Test
    void appendingSmallGroupShouldIncreaseGroupsSizeButNotMaxGroupSize() {
        assumeTrue(referenceSolution.maxGroupSize() > 1);
        var appended = BasketSolution.getSolutionWithAdditionalGroup("unique delivery", new ArrayList<>(List.of("product 1")), referenceSolution);
        assertEquals(REFERENCE_SOLUTION_GROUPS_SIZE, appended.maxGroupSize());
        assertEquals(REFERENCE_SOLUTION_NR_OF_GROUPS + 1, referenceSolution.deliveries().size());
    }

    @Test
    void appendingBigGroupShouldIncreaseGroupsSizeAndMaxGroupSize() {
        var productsGroup = IntStream.range(0, REFERENCE_SOLUTION_GROUPS_SIZE + 1).mapToObj(i -> "product " + i).toList();
        var appended = BasketSolution.getSolutionWithAdditionalGroup("unique delivery", new ArrayList<>(productsGroup), referenceSolution);
        assertTrue(REFERENCE_SOLUTION_GROUPS_SIZE < appended.maxGroupSize());
        assertEquals(REFERENCE_SOLUTION_NR_OF_GROUPS + 1, referenceSolution.deliveries().size());
    }

}
