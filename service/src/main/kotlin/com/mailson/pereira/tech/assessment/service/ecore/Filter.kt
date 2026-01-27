package com.mailson.pereira.tech.assessment.service.ecore

fun Hierarchy.filter(nodeIdPredicate: (Int) -> Boolean): Hierarchy {
    val filteredNodeIds = mutableListOf<Int>()
    val filteredDepths = mutableListOf<Int>()

    val previousValidArray = BooleanArray(size + 1) { true }

    for (i in 0 until size) {
        val id = nodeId(i)
        val depth = depth(i)

        val isPreviousValid = previousValidArray.getOrElse(depth - 1) { true } && nodeIdPredicate(id)
        previousValidArray[depth] = isPreviousValid

        if (isPreviousValid) {
            filteredNodeIds.add(id)
            filteredDepths.add(depth)
        }
    }

    return ArrayBasedHierarchy(filteredNodeIds.toIntArray(), filteredDepths.toIntArray())
}