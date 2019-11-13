package spark_poc

import org.apache.spark.sql.SparkSession
import scala.util.Random
import scala.math.BigDecimal
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.types._

object Skew {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.
      master("local").
      appName("espn2").
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("ERROR")
    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    val makeModelSet: Seq[MakeModel] = Seq(
      MakeModel("FORD", "FIESTA")
      , MakeModel("NISSAN", "QASHQAI")
      , MakeModel("HYUNDAI", "I20")
      , MakeModel("SUZUKI", "SWIFT")
      , MakeModel("MERCEDED_BENZ", "E CLASS")
      , MakeModel("VAUCHALL", "CORSA")
      , MakeModel("FIAT", "500")
      , MakeModel("SKODA", "OCTAVIA")
      , MakeModel("KIA", "RIO")
    )

    def randomMakeModel(): MakeModel = {
      val makeModelIndex = if (Random.nextBoolean()) 0 else Random.nextInt(makeModelSet.size)
      makeModelSet(makeModelIndex)
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

    val ds2 = Seq.fill(1000)(randomT2()).toDS()

   // ds1.show
   // ds2.show

    val t1 = System.currentTimeMillis()

    val ds3 = ds1.join(ds2, Seq("make", "model")).
      filter(abs(ds2("engine_size") - ds1("engine_size")) <= BigDecimal("0.1")).
      groupBy("registration").
      agg(avg("sale_price") as "avg_price")

    ds3.show(100)

    val duration1 = (System.currentTimeMillis() - t1) / 1000

    println(duration1)

    // ds3.explain()

    val t2 = System.currentTimeMillis()

    val ds4 = ds1.withColumn("engine_size", explode(array($"engine_size" - BigDecimal("0.1"), $"engine_size", $"engine_size" + BigDecimal("0.1") ))).
      join(ds2, Seq("make", "model", "engine_size")).
      groupBy("registration").
      agg(avg("sale_price") as "avg_price")

    ds4.show(100)

    val duration2 = (System.currentTimeMillis() - t2) / 1000

    println(duration2)

  }

}

case class MakeModel(make: String, model: String)

case class T1(registration: String, make: String, model: String, engine_size: BigDecimal)

case class T2(make: String, model: String, engine_size: BigDecimal, sale_price: Double)

