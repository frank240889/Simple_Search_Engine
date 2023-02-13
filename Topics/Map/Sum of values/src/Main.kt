fun summator(map: Map<Int, Int>): Int {
    return map
        .filter {
            it.key.mod(2) == 0
        }
        .entries
        .sumOf {
            it.value
        }
}