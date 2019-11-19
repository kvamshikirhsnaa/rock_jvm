package spark_poc2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

object Test2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel( "ERROR" )

    // println(spark.conf.get("spark.sql.autoBroadcastJoinThreshold")) // 10485760

    val df1 = spark.read.option("header", "true").
      option("inferSchema", "true").option("delimiter", "|").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\sample5.txt")

    df1.show

    val df2 = df1.filter(('label === "h" && 'tag.isNull) || ('label === "w" && 'tag.isNotNull) || 'label === "v")
    df2.show

    // getting for each id one label h & tag is null or one label w & tag is not null or one label v

    val dfnew = df2.groupBy('id, 'label).agg(first('tag) as "tag").orderBy('id)
    dfnew.show

                                     //   (OR)

    val spec = Window.partitionBy('id, 'label).orderBy('id, 'label)

    val dfnew2 = df2.withColumn("rnk", row_number() over spec)
    dfnew2.show

    val dfnew3 = dfnew2.filter('rnk === 1).orderBy('id).drop( "rnk")
    dfnew3.show

    // here dfnew and dfnew3 both give same result

    val df6 = dfnew.groupBy('id).count.filter('count === 3)
    df6.show

    val df7 = dfnew.join(df6, "id").drop("count")
    df7.show

    dfnew.createOrReplaceTempView("sample")

    spark.sql("select id, label, tag from sample where id in (select id from (select id, count(*) cnt " +
      "from sample group by id) where cnt = 3) ").show








  }

}
