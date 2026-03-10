package com.mailson.pereira.tech.assessment.service.ecore

interface Hierarchy {
    /** The number of nodes in the hierarchy. */
    val size: Int

    /**
     * Returns the unique ID of the node identified by the hierarchy index. The depth for this node will be `depth(index)`.
     * @param index must be non-negative and less than [size]
     * */
    fun nodeId(index: Int): Int

    /**
     * Returns the depth of the node identified by the hierarchy index. The unique ID for this node will be `nodeId(index)`.
     * @param index must be non-negative and less than [size]
     * */
    fun depth(index: Int): Int

    fun formatString(): String {
        return (0 until size).joinToString(
            separator = ", ",
            prefix = "[",
            postfix = "]"
        ) { i -> "${nodeId(i)}:${depth(i)}" }
    }
}

