package search
import java.io.File
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

lateinit var invertedIndex: Map<String, List<Int>>
val people = ArrayList<String>()
fun main(args: Array<String>) {
    readPeople(args[1])
    showMenu()
}

fun readPeople(filename: String) {
    val peopleData = File(filename)
    peopleData.forEachLine {
        people.add(it)
    }
    invertedIndex = invertedIndex(people)
}

private fun invertedIndex(people: List<String>): Map<String, List<Int>> {
    val invertedIndex = mutableMapOf<String, MutableList<Int>>()
    people.forEachIndexed { index, line ->
        line.split(" ").forEach { word ->
            invertedIndex.getOrPut(word.trim().lowercase()) { mutableListOf() }.add(index)
        }
    }
    return invertedIndex
}

fun menu() {
   println("=== Menu ===")
   println("1. Find a person")
   println("2. Print all people")
   println("0. Exit")
}

fun showMenu() {
    do {
        menu()
        val option = readln().toInt()
        if (option != 1 && option != 2 && option != 0) {
            println("Incorrect option! Try again.")
        } else {
            evaluateOption(option)
        }
    } while(option != 1 && option != 2 && option != 0)
}

fun evaluateOption(option: Int) {
    when (option) {
        1 -> {
            showSelectStrategy()
            showMenu()
        }
        2 -> {
            list()
            showMenu()
        }
        0 -> {
            println("Bye!")
            exitProcess(0)
        }
    }
}

fun showSelectStrategy() {
    var strategy: SearchStrategy? = null
    do {
        strategy = getStrategy()
    } while(strategy == null)
    search(strategy)
}

fun getStrategy(): SearchStrategy? {
    println("Select a matching strategy: ALL, ANY, NONE")
    return try {
        SearchStrategy.valueOf(readln())
    } catch (e: IllegalArgumentException) {
        null
    }
}

fun search(strategy: SearchStrategy) {
    println("Enter a name or email to search all suitable people.")
    val input = readln().lowercase()
    val queries = getQueries(input)

    val result = mutableMapOf<String, List<Int>>()
    if (strategy == SearchStrategy.NONE) {
        val temp = mutableMapOf<String, List<Int>>().apply {
            putAll(invertedIndex)
        }
        queries.forEach { key ->
            temp.remove(key)
        }
        result.putAll(temp)
    } else {
        queries.forEach { key ->
            invertedIndex[key]?.let {
                result[key] = it
            }
        }
    }
    val found = mutableListOf<String>()
    val indexes = mutableListOf<Int>().apply {
        result.flatMap { it.value }.forEach {
            if (!this.contains(it))
                add(it)
        }
    }

    when (strategy) {
        SearchStrategy.ALL -> {
            for (index in indexes) {
                val line = people[index].lowercase().split(" ")
                if (line.containsAll(queries)) {
                    found.add(people[index])
                }
            }
        }
        SearchStrategy.ANY -> {
            for (index in indexes) {
                found.add(people[index])
            }
        }
        else -> {
            MainFor@ for (index in indexes) {
                val line = people[index].lowercase()
                var contains: Boolean
                if (queries.size == 1) {
                    contains = queries[0] in line
                } else {
                    contains = queries[0] in line
                    for (q in 1 until queries.size) {
                        val word = queries[q]
                        if (word in line) {
                            contains = contains && true
                            continue@MainFor
                        }
                    }
                }

                if (!contains) {
                    found.add(people[index])
                }
            }
        }
    }
    if (people.isEmpty()) {
        println("No matching people found.")
    } else {
        println("${found.size} persons found:")
        for (people in found) {
            println(people)
        }
    }
}

fun list() {
    println("=== List of people ===")
    for (j in people.indices) {
        println(people[j])
    }
}

fun getQueries(input: String): List<String> {
    return input.lowercase().split(" ")
}

enum class SearchStrategy {
    ALL, ANY, NONE
}