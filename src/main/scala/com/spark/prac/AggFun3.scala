package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object AggFun3 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      appName("sample21").
      master("local").
      getOrCreate

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val df1 = spark.read
      .option("inferSchema", "true")
      .csv("C:\\Users\\Kenche.vamshikrishna\\Downloads\\employee.txt")
      .toDF("name","age","dno","sal","day")

    df1.show


    val newDF = df1.filter($"name".isNotNull)

    newDF.show

    val df2 = newDF.withColumn("date", to_date($"day","yyyy/mm/dd")).drop($"day")

    df2.show

//    Grouping Sets:
//
//    sometimes we want something a bit more complete—an aggregation across multiple groups.
//    We achieve this by using _grouping sets_. Grouping sets are a low-level tool for combining
//    sets of aggregations together. They give you the ability to create arbitrary aggregation
//    in their group-by statements.
//
//    Grouping sets depend on null values for aggregation levels. If you do not filter-out
//    null values, you will get incorrect results. This applies to cubes, roll ups, and grouping sets.



//  df2.filter("name is null or dno is null or sal is null or date is null") to get all null values

    val df3 = df2.filter("name is not null and dno is not null and sal is not null and date is not null")

    df3.show

    df3.createOrReplaceTempView("emp")

    spark.sql("select date, dno, sum(sal) from emp group by dno, date order by dno, date").show


//     using grouping set:
//
//    Simple enough, but what if you _also_ want to include the total number of items,
//    regardless of customer or stock code? With a conventional group-by statement, this
//    would be impossible. However it’s simple with grouping sets: we simply specify that
//    we would like to aggregate at that level, as well, in our grouping set. This is,
//    effectively, the union of several different groupings together:

    spark.sql("select date, dno, sum(sal) from emp group by dno, date grouping sets((date, dno)) order by dno, date").show

//    the above both results same

//    The GROUPING SETS operator is only available in SQL. To perform the same in DataFrames,
//    you use the `rollup` and `cube` operators - which allow us to get the same results.

//    Rollups:
//
//    Thus far, we’ve been looking at explicit groupings. When we set our grouping keys of
//    multiple columns, Spark looks at those as well as the actual combinations that are
//    visible in the dataset. A Rollup is a multidimensional aggregation that performs
//    a variety of group-by style calculations for us.



    val df4 = df3.rollup($"date",$"dno").agg(sum($"sal") as "tot")
      .selectExpr("date","dno","tot").orderBy("date")

    df4.show

//    Now where you see the `null` values is where you’ll find the grand totals.
//    A `null` in both rollup columns specifies the grand total across both of those column.

    df4.where($"date" isNull).show

    df4.where($"dno" isNull).show

//    Cube:
//
//    A cube takes the rollup to a level deeper. Rather than treating elements hierarchically,
//    a cube does the same thing across all dimensions. This means that it won’t just go by
//    date over the entire time period, but also the country. To pose this as a question again,
//
//    can you make a table that includes the following:
//    • The total across all dates and countries
//    • The total for each date across all countries
//    • The total for each country on each date
//    • The total for each country across all dates
//    The method call is quite similar, but instead of calling `rollup`, we call `cube`:

    val df5 = df3.cube($"date",$"dno").agg(sum($"sal") as "tot")
      .selectExpr("date","dno","tot").orderBy("date")

    df5.show

//    This is a quick and easily accessible summary of nearly all of the information in our
//    table, and it’s a great way to create a quick summary table that others can use later on.


//    Grouping Metadata:
//
//    Sometimes when using cubes and rollups, you want to be able to query the aggregation
//    levels so that you can easily filter them down accordingly. We can do this by using
//    the `grouping_id` operator, which gives us a column specifying the level of aggregation
//    that we have in our result set

//    grouping_id():
//
//    3::
//        This will appear for the highest-level aggregation, which will gives us the
//    total sal regardless of `date` and `dno`.
//
//    2::
//      This will appear for all aggregations of individual dno. This gives us the total sal
//      per dno, regardless of date.
//
//    1::
//      This will give us the total sal on a date basis, regardless of dno.
//
//    0::
//      This will give us the total sal for individual `date` and `dno` combinations.


    val df6 = df3.rollup($"date",$"dno").agg(grouping_id(), sum($"sal"))
      .orderBy($"grouping_id()" desc)

    df6.show

    val df7 = df3.cube($"date",$"dno").agg(grouping_id(), sum($"sal"))
      .orderBy($"grouping_id()" desc)

    df7.show


//    Pivot:
//
//    Pivots make it possible for you to convert a row into a column.

    val df8 = df3.groupBy($"dno").pivot($"sal").sum()

    df8.show // we should change column names after pivot

    df8.where($"dno" <= 12).select($"dno", $"200_sum(CAST(dno AS BIGINT))").show

//    Now all of the columns can be calculated with single groupings, but the value
//    of a pivot comes down to how you would like to explore the data. It can be useful,
//    if you have low enough cardinality in a certain column to transform it into columns
//    so that users can see the schema and immediately know what to query for.




  }

}
