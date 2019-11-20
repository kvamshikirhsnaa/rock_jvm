package spark_palyground_new

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._
import org.apache.log4j.{Level, Logger}


case class Street(number: Int, name: String)
case class Address(city: String, street: Street)
case class Company(name: String, address: Address)
case class Employee(name: String, company: Company)

object ComplexTypes {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().
      master("local").appName("sample").
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val e1 = Employee("Govind", Company("Google", Address("Hyderabad", Street(1, "kondapur"))))
    val e2 = Employee("Anji", Company("Facebook", Address("Bangalore", Street(2, "marathahalli"))))
    val e3 = Employee("Venkat", Company("Microsoft", Address("Bangalore", Street(3, "Sarjapur"))))

    val df = Seq(e1, e2, e3).toDF
    df.show
    df.printSchema()

    val df2 = df.select('name,
      struct(upper($"company.name") as "name", $"company.address" as "adress") as "company")
    df2.show







  }


}
