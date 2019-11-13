package new_spark

import scala.util.Random

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._


object Test4 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName( "sample21" ).
      master( "local" ).
      getOrCreate

    spark.sparkContext.setLogLevel( "ERROR" )

    spark.conf.set( "spark.sql.autoBroadcastJoinThreshold", -1 )

    spark.conf.set("spark.sql.shuffle.partitions", 8)

    import spark.implicits._

    val names = Seq("saikrishna", "aravind", "prakash", "narahari", "vamshikrishna")

    def randnum = Random.nextInt(1000)

    def randGen = if (Random.nextBoolean()) (randnum, names(0)) else (randnum, names(Random.nextInt(names.size)))

    val data1 = Seq.fill(1000)(randGen)

    val data2 = Seq.fill(1000)(randGen)

    val df1 = data1.toDF("id1", "name1")

    val dfnew1 = df1.repartition(10)

    println(dfnew1.rdd.getNumPartitions)

    val df2 = data2.toDF("id2", "name2" )

    val dfnew2 = df2.repartition(4)

    println(dfnew2.rdd.getNumPartitions)

//    val joindf = df1.join(df2, $"name1" === $"name2")
//
//    joindf.show
//
//    println(joindf.explain)
//
//    println(joindf.where($"name1" === "saikrishna").count)
//
//    println(joindf.count)

    val dff1 = dfnew1.where($"name1" === "saikrishna")

    val dff2 = dfnew2.where($"name2" === "saikrishna")

    println(dff1.count)

    println(dff2.count)

    println(dff1.rdd.getNumPartitions)

    println(dff2.rdd.getNumPartitions)

    val joindfnew = dff1.join(dff2, $"name1" === $"name2")

    println(joindfnew.count)

    println(joindfnew.rdd.getNumPartitions)

    joindfnew.mapPartitions(x => Iterator(x.size)).collect.foreach(println)

    def newrand = Random.nextInt(50)

    val dffnew1 = dff1.withColumn("rand1", substring(rand() , 0,3))

    val dffnew2 = dff2.withColumn("rand2", substring(rand() , 0,3))

    dffnew1.show

    dffnew2.show

    val newdf1 = dffnew1.select($"id1", concat_ws("_", $"name1", $"rand1") as "name1")

    val newdf2 = dffnew2.select($"id2", concat_ws("_", $"name2", $"rand2") as "name2")

    newdf1.show
    newdf2.show

    val joined = newdf1.join(newdf2, $"name1" === $"name2")

    println(joined.count)











  }

}
