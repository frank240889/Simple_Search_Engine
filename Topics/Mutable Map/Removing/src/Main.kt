fun removing(currentMap: MutableMap<Int, String>, value: String): MutableMap<Int, String> {
    val mutableMap = currentMap.toMutableMap()
    return mutableMap.filter {
        it.value != value
    }.toMutableMap()
}