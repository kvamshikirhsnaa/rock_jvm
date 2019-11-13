package new_spark

import org.apache.spark.sql.SparkSession
import scala.util.Random
import scala.math.BigDecimal
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._

object Test6 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.
      master("local").
      appName("espn2").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    spark.conf.set("spark.sql.shuffle.partitions", 8)

    val makeModelSet: Seq[MakeModel] = Seq(
      MakeModel("FORD", "FIESTA")
    )

    def randomMakeModel(): MakeModel = {
      makeModelSet(0)
    }

    def randomEngineSize() = BigDecimal(s"1.${Random.nextInt(9)}")

    def randomRegistration(): String = s"${Random.alphanumeric.take(7).mkString("")}"

    def randomPrice() = 500 + Random.nextInt(5000)

    def randomT1(): T1 = {
      val makeModel = randomMakeModel()
      T1(randomRegistration(), makeModel.make, makeModel.model, randomEngineSize())
    }

    def randomT2(): T2 = {
      val makeModel = randomMakeModel()
      T2(makeModel.make, makeModel.model, randomEngineSize(), randomPrice())
    }

    val ds1 = Seq.fill(1000)(randomT1()).toDS()

    val ds2 = Seq.fill(1)(randomT2()).toDS()

    // ds1.show
    // ds2.show

    println("ds3")

    val t1 = System.currentTimeMillis()

    val ds3 = ds1.join(ds2, Seq("make", "model"), "left_Outer")

    //ds3.show(100)

    println(ds3.count())

    ds3.mapPartitions(x => Iterator(x.size)).collect.foreach(println)

    val duration1 = (System.currentTimeMillis() - t1) / 1000

    println(duration1)

    // ds3.explain()

    println("ds4")


    val t2 = System.currentTimeMillis()

    val dsnew2 = ds2.withColumn("engine_size", explode(array($"engine_size" - BigDecimal("0.1"), $"engine_size", $"engine_size" + BigDecimal("0.1") )))

    val ds4 = ds1.join(dsnew2, Seq("make", "model", "engine_size"), "left_Outer")

    //ds4.show(100)

    println(ds4.count())

    ds4.mapPartitions(x => Iterator(x.size)).collect.foreach(println)

    val duration2 = (System.currentTimeMillis() - t2) / 1000

    println(duration2)

  }

}

case class MakeModel(make: String, model: String)

case class T1(registration: String, make: String, model: String, engine_size: BigDecimal)

case class T2(make: String, model: String, engine_size: BigDecimal, sale_price: Double)

