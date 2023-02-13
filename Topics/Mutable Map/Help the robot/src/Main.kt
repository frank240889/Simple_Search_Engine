fun helpingTheRobot(purchases: Map<String, Int>, addition: Map<String, Int>) : MutableMap<String, Int> {
    val mutablePurchases = purchases.toMutableMap()
    val mutableAddition = addition.toMutableMap()
    mutablePurchases.forEach { (k, v) ->
        if (addition.containsKey(k)) {
            val value = mutableAddition.remove(k) ?: 0
            mutablePurchases[k] = v + value
        }
    }
    mutablePurchases.putAll(mutableAddition)
    return mutablePurchases
}