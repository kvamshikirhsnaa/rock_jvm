package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions._

import scala.util.Random

object Test5 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    spark.conf.set( "spark.sql.autoBroadcastJoinThreshold", -1 )

    spark.conf.set("spark.sql.shuffle.partitions", 8)

    import spark.implicits._

    def randnum = Random.nextInt(5000)

    def randnum2 = BigDecimal(s"1.${Random.nextInt(9)}")

    def randGen = (randnum, "saikrishna", randnum2)

    val data1 = Seq.fill(5000)(randGen)

    val data2 = Seq.fill(5000)(randGen)

    val df1 = data1.toDF("id1", "name1", "dno1")

    val dfnew1 = df1.repartition(10)

    println(dfnew1.rdd.getNumPartitions)

    //println(dfnew1.count)

    val df2 = data2.toDF("id2", "name2", "dno2")

    val dfnew2 = df2.repartition(4)

    println(dfnew2.rdd.getNumPartitions)

    //println(dfnew2.count)

//    val joindf = dfnew1.join(dfnew2, $"name1" === $"name2")
//
//    println(joindf.count)
//
//    println(joindf.rdd.getNumPartitions)
//
//    joindf.show()
//
//    joindf.mapPartitions(x => Iterator(x.size)).collect.foreach(println)


    println("joined1")

    val joined = dfnew1.join(dfnew2, $"name1" === $"name2" && $"dno1" === $"dno2")

    println(joined.count())

    joined.mapPartitions(x => Iterator(x.size)).collect.foreach(println)

    val dffnew1 = dfnew1.withColumn("dnonew1", explode(array($"dno1" - BigDecimal(0.1), $"dno1",
    $"dno1" + BigDecimal(0.1))))

    println("joined2")

    val joined2 = dffnew1.join(dfnew2, $"name1" === $"name2" && $"dnonew1" === $"dno2")

    println(joined2.count())

    joined2.mapPartitions(x => Iterator(x.size)).collect.foreach(println)


    println("joined3")

    val joined3 = dfnew2.join(dffnew1, $"name2" === $"name1" && $"dno2" === $"dnonew1", "left_Outer")

    println(joined3.count())

    joined3.mapPartitions(x => Iterator(x.size)).collect.foreach(println)


    println("joined4")

    val joined4 = dffnew1.join(dfnew2, $"name1" === $"name2" && $"dnonew1" === $"dno2", "left_Outer")

    println(joined4.count())

    joined4.mapPartitions(x => Iterator(x.size)).collect.foreach(println)










  }

}
