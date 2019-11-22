package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object Test3 {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().
      master("local").appName("sample").
      getOrCreate()

    import spark.implicits._

    val df = spark.read.format("vk").
      load

    df.show()
  }

}
