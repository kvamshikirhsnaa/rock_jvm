package new_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import scala.util.Random

object Test9 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val lst = Seq(("A1", "B1", "C1",1),
      ("A2", "B2", "C2",2))

    def randGen = if (Random.nextBoolean) lst(0) else lst(1)

    val data = Seq.fill(10)(randGen)

    val df1 = data.toDF("A", "B", "C", "amt")

    df1.show()

    // cube:

    val df2 = df1.cube("A", "B").agg(sum($"amt") as "amt").orderBy($"A", $"B")

    df2.show

    val df3 = df1.cube("A", "B", "C").agg(sum($"amt") as "amt").orderBy($"A", $"B", $"C")

    df3.show


    // rollup:

    val df4 = df1.rollup("A", "B").agg(sum($"amt") as "amt").orderBy($"A", $"B")

    df4.show

    val df5 = df1.rollup("A", "B", "C").agg(sum($"amt") as "amt").orderBy($"A", $"B", $"C")

    df5.show


  }

}
