package spark_poc2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._

object Test4 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df1 = spark.read.option("header", "true").
      option("inferSchema", "true").
      csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\posts")

    df1.show

    // Finding max interaction for each profile_id


    // approach 1: using windowing aggregating
    val spec = Window.partitionBy('profile_id)

    val df2 = df1.withColumn("max", max('interaction) over spec).
      filter('interaction === 'max).
      select('post_id, 'profile_id, 'interaction).orderBy('profile_id)

    df2.show


    // approach 2: using windowing ranking
    val spec2 = Window.partitionBy('profile_id).orderBy('interaction.desc)

    val df3 = df1.withColumn("rnk", dense_rank over spec2).
      filter('rnk === 1).
      select('post_id, 'profile_id, 'interaction).orderBy('profile_id)

    df3.show


    // approach 3: using groupBy + join
    val grouped = df1.groupBy('profile_id).agg(max('interaction) as "interaction")

    val df4 = df1.join(grouped, Seq("profile_id", "interaction")).
      select('post_id, 'profile_id, 'interaction).orderBy('profile_id)

    df4.show

    df1.createOrReplaceTempView("sample")

    // approach 4: using subquery
    val query1 =
      """
        |select post_id, profile_id, interaction from sample where (profile_id, interaction) in
        |(select profile_id, max(interaction) interaction from sample group by profile_id) order by profile_id
      """.stripMargin

    spark.sql(query1).show


    // approach 5: using correlated subquery
    val query2 =
      """
        |select * from sample t1 where interaction = (
        |select max(interaction) from sample t2 where t1.profile_id = t2.profile_id
        |) order by profile_id
      """.stripMargin

    spark.sql(query2).show









  }

}
