// You can experiment here, it won’t be checked

fun main(args: Array<String>) {
    val map = mapOf<String, Int>().toMutableMap()
    map["Robert"] = 1500
    map["Rob"] = 1000
    map.remove("Robert")
    map["Rob"] = 1
    map.clear()
    map += "Robert" to 1500
    println(map)
}
