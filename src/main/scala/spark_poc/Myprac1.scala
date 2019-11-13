package spark_poc

import org.apache.spark.sql.SparkSession

object Myprac1 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("sample1").
      getOrCreate()

    import spark.implicits._

    val myRange = spark.range(1000)

    myRange.show
    print(myRange.getClass.getTypeName)
    //val divisBy2 = myRange.where($"number" % 2 === 0)

    //divisBy2.show

  }

}
