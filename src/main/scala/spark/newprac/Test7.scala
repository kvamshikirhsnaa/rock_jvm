package spark.newprac

import org.apache.spark.sql.{Row, DataFrame,SparkSession}
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import scala.util.Random

 class PartitionFind {
   // calculates record count per partition
   def inspectPartitions(df: DataFrame) = {
     import df.sqlContext.implicits._
     df.rdd.mapPartitions(partIt => {
       Iterator(partIt.toSeq.size)
     }
     ).toDF("record_count")
   }

   // inspects how a given key is distributed accross the partition of a dataframe
   def inspectPartitions(df: DataFrame, key: String) = {
     import df.sqlContext.implicits._
     df.rdd.mapPartitions(partIt => {
       val part = partIt.toSet
       val partSize = part.size
       val partKeys = part.map(r => r.getAs[Any](key).toString.trim)
       val partKeyStr = partKeys.mkString(", ")
       val partKeyCount = partKeys.size
       Iterator((partKeys.toArray, partSize))
     }
     ).toDF("partitions", "record_count")
   }
 }

object Test7 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    spark.conf.set("spark.sql.shuffle.partitions", 200)

    import spark.implicits._


    val df1 = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\fact.txt")

    println(df1.rdd.partitions.size)

    val df2 = df1.repartition(20,$"id") // creates 200 partitions but
                                               // only 6 partitions will have data
                                                 // remains empty partitions
    println(df2.rdd.partitions.size)

    val df3 = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Desktop\\dim.txt")

    println(df3.rdd.partitions.size)

    df3.createOrReplaceTempView("sample2")

    val df4 = df2.join(df3, df2("id") === df3("id"))

    println(df4.rdd.partitions.size)

    val obj = new PartitionFind

   // obj.inspectPartitions(df4).orderBy($"record_count".desc).show(10)
   // obj.inspectPartitions(df2).orderBy($"record_count".desc).show(10)

   // obj.inspectPartitions(df4, "id").orderBy($"record_count".desc).show(10)

    df2.createOrReplaceTempView("sample1")

    val salted_df = spark.sql("select concat(id, '_', FLOOR(RAND(123456)*19)) as salted_id, value from sample1")

    salted_df.createOrReplaceTempView("fact_table")

    val explode_dim_df = spark.sql("select id, value, explode(array(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19)) as salted_id from sample2")


    explode_dim_df.createOrReplaceTempView("dim_table")

    val res_df = spark.sql("select key1, count(*) from (select split(t1.salted_id, '_')[0] as key1 from fact_table t1 join dim_table t2 on t1.salted_id = concat(t2.id,'_',t2.salted_id) ) group by key1 order by key1")

    res_df.show

    println(res_df.rdd.partitions.size)

    obj.inspectPartitions(salted_df).orderBy($"record_count".desc).show(10)

    obj.inspectPartitions(explode_dim_df).orderBy($"record_count".desc).show(10)

    obj.inspectPartitions(res_df).orderBy($"record_count".desc).show(10)


    obj.inspectPartitions(df1.repartition($"value"),"id")
      .where($"record_count">0)
      .show

    obj.inspectPartitions(df2.repartition($"value"),"id")
      .where($"record_count">0)
      .show



  }
}