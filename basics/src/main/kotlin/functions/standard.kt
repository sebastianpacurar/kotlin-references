package functions

/*
 Standard Library Functions:

  - contains: check if a Collection contains a specified element
  - containsAll: check if a Collection contains all elements of another Collection
  - indexOf: returns the index of the first specified element from an indexed Collection; if element not found it returns -1
  - lastIndexOf: returns the index of the last specified element from an indexed Collection; if element not found it returns -1
  - elementAt: returns the value at the specified index from an indexed Collection, or throws ArrayIndexOutOfBoundsException if not found
  - elementAtOrNull: returns the value at the specified index from an indexed Collection, or null if ArrayIndexOutOfBoundsException is reached
  - first: return the first element of an indexed collection, or throws NoSuchElementException if not found
  - firstOrNull: returns the first element of an indexed collection, or null if element not found
  - last: returns the last element of the indexed collection, or throws NoSuchElementException if not found
  - lastOrNull: returns the last element of the indexed collection, or null if element not found
  - take: returns a new Collection containing the first n elements from the original Collection
  - takeLast: returns a new Collection containing the last n elements from the original Collection
  - isEmpty: checks if a Collection is empty (this also applies to Strings) //TODO
  - isNotEmpty: checks if a Collection isn't empty (als applies to Strings) //TODO
  - isBlank: relevant only to Strings - returns True if contains only space chars, newlines and/or tabs //TODO
  - isNotBlank: relevant only to Strings - returns True if contains any visible character (letters, numbers, symbols) //TODO
  - distinct: returns unique results
  - flatten: flatten a Collection of nested Collection
  - zip: returns a Pair of entries between 2 Collections
  - unzip: //TODO
  - sort: sorts an existing mutable collection. does not return anything
  - sortDescending: similar to sort but in descending order
  - sorted: sorts and returns a new collection from the initial collection
  - sortedDescending: similar to sorted but in descending order
  - average: //TODO
  - max: //TODO
  - maxOrNull: //TODO
  - min: //TODO
  - minOrNull: //TODO

*/


fun main() {
    println("Contains example")
    containsFunc()

    println("\nContainsAll example")
    containsAllFunc()

    println("\nIndexOf and LastIndexOf examples")
    indexOfLastIndexOfFuncs()

    println("\nElementAt and ElementAtOrNull examples")
    elementAtElementAtOrNull()

    println("First and Last examples")
    firstLast()

    println("\nFirstOrNull and LastOrNull examples")
    firstOrNullLastOrNull()

    println("\nTake examples")
    takeFuncs()

    println("\nFlatten example")
    flattenFunc()

    println("\nDistinct example")
    distinctFunc()

    println("\nZip example")
    zipFunc()

    println("\nSort and SortDescending examples")
    sortFuncs()

    println("\nSorted and SortedDescending examples")
    sortedFuncs()
}


fun containsFunc() {
    val nums: List<Int> = listOf(1, 3, 5, 7)
    val mapOfChars: Map<Char, String> = mapOf(
        'a' to "apple",
        'b' to "brand",
        'c' to "city"
    )
    println("Initial data: \n list: $nums \n map: $mapOfChars")

    // check if 5 is in nums list
    val listHas5 = nums.contains(5)
    println("Is 5 in list: $listHas5")

    // check if there is a specific key in the map variable implicitly
    val mapHasKeyA = mapOfChars.contains('a')
    println("Is 'a' in map keys using implicit key check: $mapHasKeyA")

    // alternative way to look for an existing key in a Map
    val altMapHasKeyA = mapOfChars.keys.contains('a')
    println("Is 'a' in map keys using explicit key check: $altMapHasKeyA")

    // check if there is a specific value in the map variable
    val mapHasValueApple = mapOfChars.values.contains("apple")
    println("Is 'apple' in map values: $mapHasValueApple")
}


fun containsAllFunc() {
    val nums1: List<Int> = listOf(0, 1, 2, 3, 4, 5)
    val nums2: List<Int> = listOf(0, 2, 3, 4)
    val nums3: List<Int> = listOf(3, 2, 4, 0)
    val map1: Map<Char, Int> = mapOf('a' to 1, 'b' to 2, 'c' to 3)
    val map2: Map<Char, Int> = mapOf('b' to 2, 'c' to 3)
    println("Initial data: \n listOne: $nums1 \n listTwo: $nums2 \n listThree: $nums3 \n mapOne: $map1 \n mapTwo: $map2")

    val listOneContainsListTwo = nums1.containsAll(nums2)
    println("ListOne contains ListTwo: $listOneContainsListTwo")

    val listTwoContainsListThree = nums2.containsAll(nums3)
    println("ListTwo contains ListThree: $listTwoContainsListThree")

    val mapOneKeysContainsMapTwoKeys = map1.keys.containsAll(map2.keys)
    println("MapOne keys contains MapTwo keys: $mapOneKeysContainsMapTwoKeys")

    val mapOneValsContainsMapTwoVals = map1.values.containsAll(map2.values)
    println("MapOne values contains MapTwo values: $mapOneValsContainsMapTwoVals")
}


fun indexOfLastIndexOfFuncs() {
    val chars: List<Char> = listOf('a', 'a', 'b', 'c', 'b', 'd', 'd', 'e')
    println("Initial data: $chars")

    val positionOfFirstLetterB: Int = chars.indexOf('b')
    println("Position of first letter 'b' is: $positionOfFirstLetterB")

    val positionOfLastLetterB: Int = chars.lastIndexOf('b')
    println("Position of last letter 'b' is: $positionOfLastLetterB")

    // this returns -1 since there is no letter 'g' in the list
    val positionOfFirstLetterG: Int = chars.indexOf('g')
    println("Position of first letter 'g' is: $positionOfFirstLetterG")

    // this returns -1 since there is no letter 'g' in the list
    val positionOfLastLetterG: Int = chars.lastIndexOf('g')
    println("Position of last letter 'g' is: $positionOfLastLetterG")
}


fun elementAtElementAtOrNull() {
    val chars: List<Char> = listOf('a', 'b', 'c', 'd', 'e')
    println("Initial data: $chars")

    val elementAtPos2: Char = chars.elementAt(2)
    println("Element at index 2 is $elementAtPos2")

    try {
        val elementAtPos10: Char = chars.elementAt(10) // exception
        println("$elementAtPos10 unreached")
    } catch (e: ArrayIndexOutOfBoundsException) {
        println("Element at index 10 throws ${e.javaClass.simpleName}")
    }

    val elementAtPos10OrNull: Char? = chars.elementAtOrNull(10)
    println("Element at index 10 is $elementAtPos10OrNull")
}


fun firstLast() {
    val nums: List<Int> = listOf(1, 2, 3)
    val emptyList: List<Int> = emptyList()
    println("Initial data: \n List with items: $nums \n Empty List: $emptyList")

    val firstNum = nums.first()
    println("First number from list with items: $firstNum")

    val lastNum = nums.last()
    println("Last number from list with items: $lastNum")

    try {
        val firstNumErr = emptyList.first() // exception
        println("$firstNumErr unreached")
    } catch (e: NoSuchElementException) {
        println("Retrieving first number from empty list using 'first' throws: ${e.javaClass.simpleName}")
    }

    try {
        val lastNumErr = emptyList.last() // exception
        println("$lastNumErr unreached")
    } catch (e: NoSuchElementException) {
        println("Retrieving last number from empty list using 'last' throws: ${e.javaClass.simpleName}")
    }
}


fun firstOrNullLastOrNull() {
    val nums: List<Int> = listOf(1, 2, 3)
    val emptyList: List<Int> = emptyList()
    println("Initial data: \n List with items: $nums \n Empty List: $emptyList")

    val firstValidNum = nums.firstOrNull()
    println("First number from list with items: $firstValidNum")

    val lastValidNum = nums.lastOrNull()
    println("Last number from list with items: $lastValidNum")

    val firstInvalidNum = emptyList.firstOrNull()
    println("Last number from empty list using 'firstOrNull': $firstInvalidNum")

    val lastInvalidNum = emptyList.lastOrNull()
    println("Last number from empty list using 'lastOrNull': $lastInvalidNum")
}


fun takeFuncs() {
    val nums: List<Int> = listOf(0, 1, 2, 3, 4, 5)
    println("Initial data: $nums")

    val firstThree: List<Int> = nums.take(3)
    println("first three using take: $firstThree")

    val lastThree: List<Int> = nums.takeLast(3)
    println("last three using takeLast: $lastThree")

    val lastThreeReversed: List<Int> = nums.takeLast(3).reversed()
    println("last three using takeLast but reversed: $lastThreeReversed")
}


fun flattenFunc() {
    val nestedList: List<List<Int>> = listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6))
    println("Initial data: $nestedList")

    val flattened: List<Int> = nestedList.flatten()
    println("Flattened list using 'flatten': $flattened")
}


fun distinctFunc() {
    val nums: List<Int> = listOf(1, 2, 2, 3, 3, 4, 5, 5)
    println("Initial data: $nums")

    val uniqueNums: List<Int> = nums.distinct()
    println("Distinct values using 'distinct': $uniqueNums")
}


fun zipFunc() {
    val firstList: List<Int> = listOf(1, 2, 3, 4, 5)
    val secondList: List<Char> = listOf('a', 'b', 'c', 'd', 'e')
    println("Initial data: \n nums: $firstList \n chars: $secondList")

    val pairs: List<Pair<Int, Char>> = firstList.zip(secondList)
    println("Zipping numbers with chars using 'zip': $pairs")
}


fun sortFuncs() {
    val nums: MutableList<Int> = mutableListOf(2, 1, 5, 7, 3, 0, 4, 6) // needs to be mutable to work
    println("Initial nums data: $nums")

    nums.sort()
    println("Nums after 'sort': $nums")

    nums.sortDescending()
    println("Nums after 'sortDescending': $nums")
}


fun sortedFuncs() {
    val nums: List<Int> = listOf(2, 1, 5, 7, 3, 0, 4, 6) // needs to be immutable to work
    println("Initial nums data: $nums")

    val sortedAsc: List<Int> = nums.sorted()
    println("Nums after 'sorted' and saved to new variable: $sortedAsc")

    val sortedDesc: List<Int> = nums.sortedDescending()
    println("Nums after 'sortedDescending' and saved to new variable: $sortedDesc")
}
