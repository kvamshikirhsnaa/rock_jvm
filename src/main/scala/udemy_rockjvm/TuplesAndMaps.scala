package udemy_rockjvm

object TuplesAndMaps {
  def main(args: Array[String]): Unit = {

    // Tuples - finite ordered  "Lists"
    val aTuple = (1, "hello, Scala") // syntax sugar of Tuple2[Int, String] - (Int, String)
    println(aTuple)   // (1,hello, Scala)
    println(aTuple._1)  // 1
    println(aTuple._2)   // hello, Scala
    println(aTuple.copy(_2 = "goodbye, Java"))   // (1,goodbye, java)
    println(aTuple.swap)   // (hello, Scala,1)


    // Maps - keys -> values
    // Maps are immutable
    val aMap: Map[Int, String] = Map((1,"Saikrishna"), (2, "Aravind"), (3, "Prakash")).withDefaultValue("no value")
    println(aMap)  // Map(1 -> Saikrishna, 2 -> Aravind, 3 -> Prakash)
    println(aMap.keys)  // Set(1, 2, 3)
    println(aMap.values)   // MapLike(Saikrishna, Aravind, Prakash)
    println(aMap(1))  // Saikrishna
    println(aMap.contains(3))  // true
    println(aMap(4))  // no value

    val aPair = (4, "Narahari")
    val aNewMap = aMap + aPair
    println(aNewMap)

    // functions on Map
    // map, flatMap, filter
    println(aNewMap.map(x => (x._1 - 1, x._2.toLowerCase)))

    // filterKeys, mapValues most used functions on Map
    println(aNewMap.filterKeys(x => x % 2 != 0))
    println(aNewMap.mapValues(x => x.toLowerCase()))
    println(aNewMap.mapValues(x => x.size))

    // conversion to other collections
    println(aNewMap.toList)
    println(List((1, "Saikrishna"), (2, "Aravind")).toMap)

    val names = List("hi", "hello", "what", "where", "cricket", "chess")
    println(names.grouped(2).toList)
    println(names.grouped(3).toList)
    println(names.groupBy(x => x.charAt(0)))














  }

}
