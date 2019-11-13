package myprac.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

import scala.util.Random
import scala.math.BigDecimal

case class MakeModel(make: String, model: String)

case class T1(registration: String, make: String, model: String, engine_size: BigDecimal)

case class T2(make: String, model: String, engine_size: BigDecimal, sale_price: Double)




object Test2 {
  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    import spark.implicits._

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

    def randomRegistration(): String = s"${Random.alphanumeric.take(7).mkString("")}"

    def randomMakeModel(): MakeModel = {
      val makeModelIndex = if (Random.nextBoolean()) 0 else Random.nextInt(makeModelSet.size)
      makeModelSet(makeModelIndex)
    }

    def randomEngineSize() = BigDecimal(s"1.${Random.nextInt(9)}")

    def randomPrice() = 500 + Random.nextInt(5000)

    def randomT1(): T1 = {
      val makeModel = randomMakeModel()
      T1(randomRegistration(), makeModel.make, makeModel.model, randomEngineSize())
    }

    def randomT2(): T2 = {
      val makeModel = randomMakeModel()
      T2(makeModel.make, makeModel.model, randomEngineSize(), randomPrice())
    }

    val t1 = Seq.fill(10000)(randomT1()).toDS()

    val t2 = Seq.fill(100000)(randomT2()).toDS()

    println(t1.rdd.partitions.size)
    println(t2.rdd.partitions.size)

    t1.show

    t2.show

    val t = System.currentTimeMillis()

    println(t)

//    t1.join(t2, Seq("make", "model"))
//      .filter(abs(t2("engine_size") - t1("engine_size")) <= BigDecimal("0.1"))
//      .groupBy("registration")
//      .agg(avg("sale_price").as("average_price")).collect()

    println(System.currentTimeMillis() - t)

    val t3 = t1.withColumn("engine_size", explode(array($"engine_size" - BigDecimal("0.1"), $"engine_size", $"engine_size" + BigDecimal("0.1"))))
      .join(t2, Seq("make", "model", "engine_size"))
      .groupBy("registration")
      .agg(avg("sale_price").as("average_price"))

    println(System.currentTimeMillis() - t)

    println(t3.rdd.partitions.size)
    println(t3.rdd.mapPartitions(iter => Iterator(iter.toSeq.size)))

    t1.write.option("header","true").csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\cars")
    t2.write.option("header","true").csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\sales")








  }
}
