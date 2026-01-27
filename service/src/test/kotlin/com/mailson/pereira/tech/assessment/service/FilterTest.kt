package com.mailson.pereira.tech.assessment.service

import com.mailson.pereira.tech.assessment.service.ecore.ArrayBasedHierarchy
import com.mailson.pereira.tech.assessment.service.ecore.Hierarchy
import com.mailson.pereira.tech.assessment.service.ecore.filter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FilterTest {
    @Test
    fun testFilter() {
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
            intArrayOf(0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2))
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 3 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 5, 8, 10, 11),
            intArrayOf(0, 1, 1, 0, 1, 2))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    // implemented tests
    @Test
    fun testFilter_excludesNodeWithInvalidParent_evenIfNodeIsValid() {
        val hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6),
            intArrayOf(0, 1, 2, 3, 1, 0)
        )
        val filtered = hierarchy.filter { it % 2 == 0 }
        val expected = ArrayBasedHierarchy(
            intArrayOf(6),
            intArrayOf(0)
        )
        assertEquals(expected.formatString(), filtered.formatString())
    }


    @Test
    fun testFilterShouldIncludesAllNodesWhenPredicateAlwaysTrue() {
        val hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3),
            intArrayOf(0, 1, 2)
        )
        val filtered = hierarchy.filter { true }
        assertEquals(hierarchy.formatString(), filtered.formatString())
    }

    @Test
    fun testFilterShouldExcludesAllNodesWhenPredicateAlwaysFalse() {
        val hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3),
            intArrayOf(0, 1, 2)
        )
        val filtered = hierarchy.filter { false }
        val expected = ArrayBasedHierarchy(intArrayOf(), intArrayOf())
        assertEquals(expected.formatString(), filtered.formatString())
    }

    @Test
    fun testFilterShouldExcludesChildWhenIntermediateAncestorFails() {
        val hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4),
            intArrayOf(0, 1, 2, 3)
        )
        val filtered = hierarchy.filter { it != 2 }
        val expected = ArrayBasedHierarchy(
            intArrayOf(1),
            intArrayOf(0)
        )
        assertEquals(expected.formatString(), filtered.formatString())
    }

    @Test
    fun testFilterShouldPreservesMultipleRootsWhenValid() {
        val hierarchy = ArrayBasedHierarchy(
            intArrayOf(10, 11, 20, 21),
            intArrayOf(0, 1, 0, 1)
        )

        val filtered = hierarchy.filter { it > 0 }
        assertEquals(hierarchy.formatString(), filtered.formatString())
    }

    @Test
    fun testFilterShoudExcludesSubtreeWhenRootFails() {
        val hierarchy = ArrayBasedHierarchy(
            intArrayOf(100, 101, 102),
            intArrayOf(0, 1, 2)
        )

        val filtered = hierarchy.filter { it != 100 }
        val expected = ArrayBasedHierarchy(intArrayOf(), intArrayOf())
        assertEquals(expected.formatString(), filtered.formatString())
    }

}