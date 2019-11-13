package scala_new2

object Test6 {
  def main(args: Array[String]): Unit = {

    // Seq(T)
    // Seq childs are IndexedSeq(T), Buffer(T), LinearSeq(T)
   // IndexedSeq(T) childs are immutable Range(C), Vector(C), StringBuilder(C),
    //              for mutable  it calss ArrayBuffer(C), etc
    // Buffer(T) childs are ArrayBuffer(C), ListBuffer(C), etc
    // LinearSeq(T) childs are List(C), Stack(C), Queue(C)
    //                                  , Stream(C),etc

    val a = 1 to 10 // returns immutable Range
    println(a) // Range(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)


    println("INDEXEDSEQ")
    println("----------")

    val indlst = IndexedSeq(1,2,3)
    // IndexedSeq is trait, even if we create object for
    // IndexedSeq, it internally calls Vector and creates object of Vector

    println(indlst) // indlst:IndexedSeq[Int] = Vector(1, 2, 3)
              // type will be IndexedSeq[Int]

    val indlst2 = scala.collection.mutable.IndexedSeq(1,2,3)
    // IndexedSeq is trait, even if we create object for
    // mutable IndexedSeq, it internally calls ArrayBuffer and creates object of ArrayBuffer

    println(indlst2) // indlst2: scala.collection.mutable.IndexedSeq[Int] =  ArrayBuffer(1, 2, 3)


    println("LINEARSEQ")
    println("----------")

    val linlst = scala.collection.immutable.LinearSeq(1,2,3)
    // LinearSeq is trait, even if we create object for
    // LinearSeq, it internally calls List and creates object of List
    // we can't create directly LinearSeq like IndexedSeq
    // the class path is not avail for LinearSeq so use whole path

    println(linlst)// linlist: scala.collection.immutable.LinearSeq[Int] = List(1,2,3)

    val linlst2 = scala.collection.mutable.LinearSeq(1,2,3)
    // LinearSeq is trait, even if we create object for
    // mutable LinearSeq, it internally calls MutableList and creates object of MutableList

    println(linlst2) // linlst2: scala.collection.mutable.LinearSeq[Int] = MutableList(1, 2, 3)



    // Buffer: when we instantiate Buffer object, it internally
    // calls the ArrayBuffer and create object of ArrayBuffer
    println("BUFFER")
    println("-------")

    val buf = scala.collection.mutable.Buffer(1,2,3)

    println(buf) // buf: scala.collection.mutable.Buffer[Int] = ArrayBuffer(1,2,3)


  }

}
