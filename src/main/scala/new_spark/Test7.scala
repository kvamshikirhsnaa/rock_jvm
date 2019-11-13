package new_spark

import scala.util.Random

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._


object Test7 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    spark.conf.set( "spark.sql.autoBroadcastJoinThreshold", -1 )

    spark.conf.set("spark.sql.shuffle.partitions", 8)

    import spark.implicits._

    val names = Seq("saikrishna")

    def randnum = Random.nextInt(1000)

    def randGen = (randnum, names(0))

    val data1 = Seq.fill(1000)(randGen)

    val data2 = Seq.fill(1)(randGen)

    val df1 = data1.toDF("id1", "name1")

    val dfnew1 = df1.repartition(10)

    println(dfnew1.rdd.getNumPartitions)

    val df2 = data2.toDF("id2", "name2" )

    val dfnew2 = df2

    println(dfnew2.rdd.getNumPartitions)

    println("JOINED")

    val joined = df1.join(df2, $"name1" === $"name2", "left_Outer")

    joined.show

    //println(joined.explain)

    println(joined.count)  // 10000

    println(joined.rdd.getNumPartitions)   // 8

    joined.mapPartitions(x => Iterator(x.size)).collect.foreach(println)

    val dffnew1 = dfnew1.withColumn("rand1", substring(rand() , 0,3))

    val dffnew2 = dfnew2.withColumn("rand2", substring(rand() , 0,3))

    val newdf1 = dffnew1.select($"id1", concat_ws("_", $"name1", $"rand1") as "name1")

    val newdf2 = dffnew2.select($"id2", concat_ws("_", $"name2", $"rand2") as "name2")

    val newdff2 = newdf2.withColumn("name3",
      explode(array(concat_ws("_", split($"name2", "_")(0), abs(split($"name2", "_")(1) - BigDecimal(0.1))) ,
        $"name2", concat_ws("_", split($"name2", "_")(0), abs(split($"name2", "_")(1) + BigDecimal(0.1))))))

    newdf1.show
    newdf2.show
    newdff2.show


    println("JOINED2")

    val joined2 = newdf1.join(newdf2, $"name1" === $"name2", "left_Outer")

    println(joined2.count)    // 974

    println(joined2.rdd.getNumPartitions)

    joined2.mapPartitions(x => Iterator(x.size)).collect.foreach(println)


    println("JOINED3")

    val joined3 = newdf1.join(newdff2, $"name1" === $"name3", "left_Outer")

    println(joined3.count)

    println(joined3.rdd.getNumPartitions)

    joined3.mapPartitions(x => Iterator(x.size)).collect.foreach(println)


  }

}
