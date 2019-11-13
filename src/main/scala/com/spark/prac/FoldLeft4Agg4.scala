package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object FoldLeft4Agg4 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._


    val lst =
      List("itemA,CATs,2,4",
        "itemA,CATS,3,1",
        "itemB,CATQ,4,5",
        "itemB,CATQ,4,6",
        "itemC,CARC,5,10")

    val rdd = spark.sparkContext.parallelize(lst)

    val rdd2 = rdd.map { x =>
      val arr = x.split(",")
      val item = arr(0)
      val cat = arr(1)
      val val1 = arr(2).toInt
      val val2 = arr(3).toInt
      (item,cat,val1,val2)
    }

    val rdd3 = rdd2.groupBy(x => x._1).map {x =>
      val cb = x._2
      val item = cb.map(x => x._1)
      val car = cb.map(x => x._2)
      val s1 = cb.map(x => x._3).sum
      val s2 = cb.map(x => x._4).sum
      (x._1,cb.head._2, s1,s2)
    }

    rdd3.collect.foreach(println)

    val rdd4 = rdd.map { x =>
      val arr = x.split(",")
      val item = arr(0)
      val cat = arr(1)
      val val1 = arr(2).toInt
      val val2 = arr(3).toInt
      (item,(cat,val1,val2))
    }

    val rdd5 = rdd4.groupByKey.mapValues(x => (x.map(x => x._1).head,x.map(x => x._2).sum, x.map(x => x._3).sum))

    rdd5.collect.foreach(println)


    // using case class

    // represents a 'row' in the original list
    case class Item(name: String, category: String, amount: Int, price: Int)

    // safely converts the row of strings into case class, throws exception otherwise
    def arrToItem(arr: Array[String]): Item = {
      if (arr.length != 4) {
        throw new Exception(s"Invalid row: ${arr.foreach(print)}; must contain only 4 entries!")
      } else {
        val n = arr.headOption.getOrElse("N/A")
        val cat = arr.lift(1).getOrElse("N/A")
        val amt = arr.lift(2).filter(_.matches("^[0-9]*$")).map(_.toInt).getOrElse(0)
        val p = arr.lastOption.filter(_.matches("^[0-9]*$")).map(_.toInt).getOrElse(0)

        Item(n, cat, amt, p) // we can give (n,cat,amt,p) just tuple instead of case class
      }
    }

    // original code with case class and method above used
    val lst2 = lst.map(_.split(",")).map(arrToItem).groupBy(_.name)
      .map { case (name, items) =>
        Item(
          name,
          category = items.head.category,
          amount = items.map(_.amount).sum,
          price = items.map(_.price).sum
        )
      }

    lst2.foreach(println)


    // using foldLeft

    def accumulateLeft(x: Map[String, Tuple3[String, Int, Int]], y: Map[String, Tuple3[String, Int, Int]]): Map[String, Tuple3[String, Int, Int]] = {
      val key = y.keySet.toList(0)
      if (x.keySet.contains(key)) {
        val oldTuple = x(key)
        val newTuple = y(key)
        x.updated(key, (newTuple._1, oldTuple._2 + newTuple._2, oldTuple._3 + newTuple._3))
      } else
        x.updated(key,(y(key)._1, y(key)._2, y(key)._3))
    }

    val lst3 = lst.map{x =>
      val arr = x.split(",")
      Map(arr(0) -> (arr(1), arr(2).toInt, arr(3).toInt))
    }

    val initial = Map.empty[String, Tuple3[String,Int,Int]]

    val lst4 = lst3.foldLeft(initial)(accumulateLeft)
      .map(x => s"${x._1},${x._2._1},${x._2._2},${x._2._3}")

    lst4.foreach(println)





    }

}
