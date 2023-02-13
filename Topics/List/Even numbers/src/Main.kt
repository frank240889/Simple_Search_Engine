fun solution(numbers: List<Int>) {
    println(
        numbers.filter { it.mod(2) == 0 }.joinToString(separator = " ")
    )
}