package com.example.kotlinbasics

import kotlin.math.floor

// Kotlin is a strongly typed language

// Primary Constructor must have atleast one parameter
data class User(val id: Long, var name: String)
// A data class is a class that only contains state and does not perform any operation.

fun main() {
    // function that is starting point of our application
    var varName = "Saumya" // type inference: Finds out the type from context
    varName = "Android"
    // var variable can be overridden (mutable)
    val valName = "Web"
    // val variable cannot be overridden, it can only be set once (immutable)
    println("Hello " + varName)

    val myByte: Byte = 13 // explicitly mentioning the data type
    val myShort: Short = 125
    val myInt: Int = 123123123
    val myLong: Long = 39_812_309_487_120_300

    val myFloat: Float = 13.37F
    val myDouble: Double = 3.141592653589793

    val isSunny: Boolean = true

    val letterChar: Char = 'A'
    val digitChar: Char = '1'

    val myStr: String = "Hello World"
    val firstCharInStr = myStr[0]
    val lastCharInStr = myStr[myStr.length-1]
    // string template expression or string interpolation
    println("First Character $firstCharInStr and the length of myStr is ${myStr.length}")
    println("Last Character $lastCharInStr")

    var result = 5+3
    val a = 5.0
    val b = 3
    var resultDouble: Double
    resultDouble = a/b
    result = (a/b).toInt()
    println(result)
    println(resultDouble)

    val isEqual = 5==3
    println("isEqual is $isEqual")

    var myNum = 5
    myNum += 5
    println("myNum is $myNum")
    myNum++
    println("myNum is ${myNum++}")
    println("myNum is ${++myNum}")
    println("myNum is ${--myNum}")
    println("myNum is ${myNum--}")
    println("myNum is ${myNum--} and now it is ${++myNum}")
    println("myNum is $myNum")

    var heightPerson1 = 170
    var heightPerson2 = 189

    if (heightPerson1 > heightPerson2) {
        println("use raw force")
    } else if(heightPerson1 == heightPerson2) {
        println("use your power technique 1337")
    } else {
        println("use technique")
    }

    val age = 22
    val drinkingAge = 21
    val votingAge = 18
    val drivingAge = 16

    val currentAge = if (age >= drinkingAge) {
        println("Now you may drink in the US")
        //return the value for this block
        drinkingAge
    } else if (age >= votingAge) {
        println("You may vote now")
        votingAge
    } else if (age >= drivingAge) {
        println("You may drive now")
        drivingAge
    } else {
        println("You are too young")
        age
    }

    println("current age is $currentAge")

    var season = 3
    when(season) {
        1 -> println("Spring")
        2 -> println("Summer")
        3 -> {
            println("Fall")
            println("Autumn")
        }
        4 -> println("Winter")
        else -> println("Invalid Season")
    }

    var month = 3
    when(month) {
        in 3..5 -> println("Spring")
        in 6..8 -> println("Summer")
        in 9..11 -> println("Fall")
        12, 1, 2 -> println("Winter")
        else -> println("Invalid Season")
    }

    var x : Any = 13.37 // any data type
    when(x) { // bunch of lambda expressions
        is Int -> println("$x is an Int")
        is Double -> println("$x is a Double")
        is String -> println("$x is a String")
        else -> println("$x is none of the above")
    }
    x = 13.37f
    val whatItIs = when(x) {
        is Int -> "is an Int"
        !is Double -> "is not Double"
        is String -> "is a String"
        else -> "is none of the above"
    }
    println("$x $whatItIs")

    var i = 1
    while (i <= 10) {
        println("$i")
        i++
    }

    println("---------------------------")
    i = 1
    do {
        println("$i")
        i++
    } while (i <= 10)

    println("---------------------------")
    var feltTemp = "cold"
    var roomTemp = 10
    while (feltTemp == "cold") {
        roomTemp++
        if (roomTemp >= 20) {
            feltTemp = "comfy"
            println("it's comfy now")
        }
    }

    println("---------------------------")
    for (num in 1..10) {
        println("$num")
    }

    println("---------------------------")
    for (i in 1 until 10) {
        println("$i")
    }

    println("---------------------------")
    for (i in 10 downTo 1) {
        println("$i")
    }

    println("---------------------------")
    for (i in 10 downTo 1 step 2) {
        println("$i")
    }

    println("---------------------------")
    for (i in 10.downTo(1).step(2)) {
        println("$i")
    }

    println("---------------------------")
    for (i in 1 until 20) {
        println("$i")
        if (i/2 == 5) {
            break
        }
    }

    println("---------------------------")
    for (i in 1 until 20) {
        if (i/2 == 5) {
            continue
        }
        println("$i")
    }

    myFunction()
    println(addUp(3,5))
    println(avg(3.0,5.0))
    var nullableName : String? = "Android"
//    nullableName = null
    val len = nullableName?.length
    println(len)
    println(nullableName?.toLowerCase())
    nullableName?.let { println(it.length) } // code inside the let block will only be executed if nullableName is not null
    val name = nullableName ?: "Guest" // Elvis Operator
    println(name)
    println(nullableName!!.toLowerCase()) // Not Null Assertion

    println("---------------------------")
    var walter = Person("Walter", "White", 33)
    walter.hobby = "to skateboard"
    walter.age = 35
    println("Walter is ${walter.age} years old")
    walter.stateHobby();
    var abc = Person("abc", "def") // Calls only the Primary Constructor

    println("---------------------------")
    var  myCar = Car()
    println(myCar.owner)
    println("brand is : ${myCar.myBrand}")
    myCar.maxSpeed = 200
    println("Maxspeed is ${myCar.maxSpeed}")
//    myCar.maxSpeed = -4
    println("Model is ${myCar.myModel}")

    println("---------------------------")
    val user1 = User(1, "Paul")
    user1.name = "Michael"
    val user2 = User(1, "Michael")
    println(user1.equals(user2))
    println("User Details: $user1") // our data class has toString() method, because every class is inherited from "Any" class
    val updatedUser = user1.copy(name="Mickey") // copy all properties except name.
    println(user1)
    println(updatedUser)
    println(updatedUser.component1())
    println(updatedUser.component2())
    val (updatedUserId, updatedUserName) = updatedUser
    println("id=$updatedUserId, name=$updatedUserName")

    println("---------------------------")
    var audiA3 = Truck(200.0, "A3", "Audi")
    var teslaS = ElectricTruck(240.0, "S-Model", "Tesla", 85.0)
    teslaS.chargerType = "Type2"
    teslaS.extendRange(200.0)
    teslaS.drive()
    teslaS.brake()
    audiA3.brake()

    // Polymorphism: treating objects with similar traits in a common way
    audiA3.drive(200.0)
    teslaS.drive(200.0)

    println("---------------------------")
    val human = Human("Peter", "Russia", 70.0, 28.0)
    val elephant = Elephant("Jumbo", "America", 5400.0, 25.0)

    human.run()
    elephant.run()

    human.breath()
    elephant.breath()

    println("---------------------------")
    val stringList: List<String> = listOf("Raymond", "Frank","Michael", "Garry")
    val mixedTypeList: List<Any> = listOf("Raymond", 31, 5, "BDay", 70.5, "weights", "Kg")
    for (value in mixedTypeList) {
        if (value is Int) {
            println("Integer: '$value'")
        } else if (value is Double) {
            println("Double: '$value' with Floor value ${floor(value)}")
        } else if (value is String) {
            println("String: '$value' of length ${value.length}")
        } else {
            println("Unknown Type")
        }
    }

    // SMART CAST
    val obj1: Any = "I have a dream"
    if (obj1 !is String) {
        println("Not a String")
    } else {
        // obj1 is automatically cast to a String in this scope
        println("Found a String of length ${obj1.length}")
    }

    // Explicit (unsafe) Casting using the "as" keyword - can go wrong
    val str1: String = obj1 as String // this will only work if obj1 is String
    println(str1.length)

//    val obj2: Any = 1337
//    val str2: String = obj2 as String
//    println(str2)

    // Explicit (safe) casting using the "as?" keyword
    val obj3: Any = 1337
    val str3: String? = obj3 as? String
    println(str3)

    println("---------------------------")
//    val numbers: IntArray = intArrayOf(1,2,3,4,5,6)
    val numbers = arrayOf(1,2,3,4,5,6)
    println(numbers)
    println(numbers.contentToString())
    for (element in numbers) {
        print(element)
    }
    println("\n${numbers[0]}")
    numbers[1] = 999
    println("Updated Array: ${numbers.contentToString()}")

    val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    println(days.contentToString())
    val fruits = arrayOf(Fruit("Apple", 2.5), Fruit("Grape", 3.5))
    println(fruits.contentToString())
    for (fruit in fruits) {
        println(fruit.name)
    }
    for (index in fruits.indices) {
        println("${fruits[index].name} is in index $index")
    }
    val heterogeneousArray = arrayOf("Tue", 3.5, 4, 'c', true, Fruit("Banana", 3.0))
    println(heterogeneousArray.contentToString())

    println("---------------------------")
    val months = listOf("Jan","Feb", "Mar")
    val anyTypes = listOf(1,2,3, false, true, "String")
    println(anyTypes.size)
    println(months[1])
    for (month in months) {
        println(month)
    }
    val additionalMonths = months.toMutableList()
    val newMonths = arrayOf("Apr","May", "Jun")
    additionalMonths.addAll(newMonths)
    additionalMonths.add("Jul")
    println(additionalMonths)

    val daysMutableList = mutableListOf<String>("Mon", "Tue", "Wed")
    daysMutableList.add("Thu")
    daysMutableList[2] = "Sun"
//    daysMutableList.removeAt(3)
    val removeList = mutableListOf<String>("Mon", "Wed")
    daysMutableList.removeAll(removeList)
    println(daysMutableList)

    println("---------------------------")
    val fruitsSet = setOf("Orange", "Apple", "Grape", "Apple")
    println(fruitsSet.size)
    println(fruitsSet.toSortedSet())
    val newFruits = fruitsSet.toMutableList()
    newFruits.add("Water Melon")
    newFruits.add("Pear")
    println(newFruits)
    println(newFruits.elementAt(2))

    val daysOfTheWeek = mapOf(1 to "Monday", 2 to "Tuesday", 3 to "Wednesday")
    println(daysOfTheWeek[2])
    for (key in daysOfTheWeek.keys) {
        println("$key is to ${daysOfTheWeek[key]}")
    }

    val fruitsMap = mapOf("Favorite" to Fruit("Grape", 2.5), "OK" to Fruit("Apple", 1.0))
    val newDaysOfTheWeek = daysOfTheWeek.toMutableMap()
    newDaysOfTheWeek[4] = "Thursday"
    newDaysOfTheWeek[5] = "Friday"
    println(newDaysOfTheWeek.toSortedMap()) // sorted by key

    println("---------------------------")
    val myArrayList: ArrayList<Double> = ArrayList()
    myArrayList.add(13.212312)
    myArrayList.add(23.151232)
    myArrayList.add(32.651553)
    myArrayList.add(16.223817)
    myArrayList.add(18.523999)
    var total = 0.0
    for (i in myArrayList){
        total += i
    }
    val average = total / myArrayList.size
    println("Average is $average")

    println("---------------------------") // Lambda Expressions
    val sum: (Int, Int) -> Int = {a:Int, b:Int -> a+b} // returns a+b when called
    println(sum(5,10))
}

data class Fruit(val name: String, val price: Double)

fun avg(a: Double, b: Double): Double {
    return (a+b)/2
}

fun addUp(a: Int,b: Int): Int {
    return a+b
}

fun myFunction() {
    println("Called myFunction")
}

// Primary Constructor
class Person (firstName: String = "Peter", lastName: String = "Parker") {
    // Member Variables - Properties
    var age: Int? = null
    var hobby: String = "watch Netflix"
    var firstName: String? = null

    init {
        // initializer
        this.firstName = firstName
        println("Initialized a new Person object with " +
                "firstName = $firstName and lastName = $lastName")
    }

    // Member Secondary Constructor
    constructor(firstName: String, lastName: String, age: Int): this(firstName, lastName) {
        this.age = age
        println("Initialized a new Person object with " +
                "firstName = $firstName and lastName = $lastName and age = $age")
    }
    // there can be many Secondary Constructors

    // Member Functions - Methods
    fun stateHobby() {
        println("$firstName\'s hobby is ${this.hobby}")
    }
}

class Car() {
    lateinit var owner: String

    val myBrand: String = "BMW" // read-only property
        // Custom Getter
        get() {
            return field.toLowerCase()
        }

    var maxSpeed: Int = 250
        // Default Setter
        set(value) {
            // field is backing field, which helps you to refer to properties inside the getter and setter methods
            field = if (value >= 0) value else throw IllegalArgumentException("Maxspeed cannot be less than or equal to 0")
        }

    var myModel: String = "M5"
        // private means something is only available within the same class
        private set // setter is private whilst getter is public

    init {
        this.myModel = "M3"
        this.owner = "Frank"
    }
}

// cannot inherit from a "sealed" class
// can only inherit from an "open" class
// (default) "final" modifier mark classes, properties and methods as not allowed to be overridden.

open class Truck(override val maxSpeed: Double, val name: String, val brand: String): Drivable {
    open var range: Double = 0.0
    fun extendRange(amount: Double) {
        if (amount > 0) {
            range += amount
        }
    }
//    override fun drive(): String = "driving the interface drive"
    override fun drive(): String {
        return "driving the interface drive"
    }
    open fun drive(distance: Double) {
        println("Drove for $distance KM")
    }
}

// subclasses of the class that implements an interface, are also considered to be implementing the interface
class ElectricTruck(maxSpeed: Double, name: String, brand: String, batteryLife: Double): Truck(maxSpeed, name, brand) {
    var chargerType = "Type1"
    override var range = batteryLife * 6
    override fun drive(distance: Double) {
        println("Drove for $distance KM on electricity")
    }
    override fun drive(): String {
        return "Drove for $range KM on electricity"
    }

    override fun brake() {
        super.brake()
        println("brake inside of electric truck")
    }
}

// interface is essentially a contract that a class may choose to sign.
// If it does, the class is obliged to provide implementation of the properties and functions of the interface
// An interface can inherit from another interface
interface Drivable {
    val maxSpeed: Double
    fun drive(): String
    fun brake() {
        println("The drivable is braking")
    }
}

// abstract class cannot be instantiated
// Members of an abstract class are non-abstract, unless you explicitly use the abstract keyword to make them abstract
abstract class Mammal(private val name: String, private val origin: String, private val weight: Double) {
    // above mentioned are the Concrete (Non-Abstract) properties

    // Abstract property (must be overridden by Subclasses)
    abstract var maxSpeed: Double

    // Abstract methods (must be implemented by Subclasses)
    abstract fun run()
    abstract fun breath()

    // Concrete (Non-Abstract) method
    fun displayDetails() {
        println("Name: $name, Origin: $origin, Weight: $weight, " +
                "Max Speed: $maxSpeed")
    }
}

class Human(name: String, origin: String, weight: Double, override var maxSpeed: Double): Mammal(name, origin, weight) {
    override fun run() {
        println("Run on two legs")
    }

    override fun breath() {
        println("Breath through mouth or nose")
    }
}

class Elephant(name: String, origin: String, weight: Double, override var maxSpeed: Double): Mammal(name, origin, weight) {
    override fun run() {
        println("Run on four legs")
    }

    override fun breath() {
        println("Breath through the trunk")
    }
}

// we can implement multiple interfaces, but
// we can only inherit from one class