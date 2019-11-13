package com.spark.prac

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object AggFun {
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

    df1.count() // action

    // if we count all values using * or any literal, if table has null values(even entire row)
    // it will include in count
    // if we count on particular column then it won't include null values in count

    df1.select(count($"dno")).show // transformation

//    countDistinct:

//    spark has countDistinct function but when we dealing with large data, if use countDistinct it takes
//    lot of time to compute,
//    there is another function which will approx distinct count result,

//    df1.countDistinct // it won't work, only in select statement it works, cuz countDistinct is transformation

    df1.select(countDistinct($"dno")).show  // transformation


//    approx_count_distinct

//    Often, we find ourselves working with large datasets and the exact distinct count is irrelevant.
//    In fact, getting the distinct count is an expensive operation, and for large datasets it might
//    take a long time to calculate the exact result. There are times when an approximation to a
//    certain degree of accuracy will work just fine, and for that, you can use the  `approx_count_distinct` function:

//    You will notice that `approx_count_distinct` took another parameter with which you can
//    specify the maximum estimation error allowed. In this case, we specified a rather large
//    error and thus receive an answer that is quite far off but does complete more quickly
//    than `countDistinct`. You will see much greater gains with larger datasets.

    df1.select(approx_count_distinct($"dno",0.1)).show  // transformation


//    first and last

    df1.select(first($"name"),last($"sal")).show

//    min and max

    df1.select(min($"sal"),max($"sal")).show

//    sum:

    df1.select(sum($"sal")).show

//    sumDistinct
//    In addition to summing a total, you also can sum a distinct set of values by using the `sumDistinct` function:

    df1.select(sumDistinct($"sal")).show

//    avg:

//    Although you can calculate average by dividing `sum` by `count`, Spark provides an
//    easier way to get that value via the `avg` or `mean` functions. In this next example,
//    we use `alias` in order to more easily reuse these columns later

    df1.select(sum($"sal") as "total_sal", count($"sal") as "cnt", avg($"sal") as "avg_sal", expr("mean(sal) as mean"))
      .selectExpr("total_sal/cnt","avg_sal","mean").show


//    Varioance and Standard Deviation:

//    Calculating the mean naturally brings up questions about the variance and standard deviation.
//    These are both measures of the spread of the data around the mean. The variance is the average
//    of the squared differences from the mean, and the standard deviation is the square root of the
//    variance. You can calculate these in Spark by using their respective functions. However,
//    something to note is that Spark has both the formula for the sample standard deviation as well
//    as the formula for the population standard deviation. These are fundamentally different statistical
//    formulae, and we need to differentiate between them. By default, Spark performs the formula for
//    the sample standard deviation or variance if you use the `variance` or `stddev` functions.
//
//      You can also specify these explicitly or refer to the population standard deviation or variance:

    df1.select(var_pop($"sal"),var_samp($"sal"), stddev_pop($"sal"),stddev_samp($"sal")).show

//    skewness and kurtosis:

//    Skewness and kurtosis are both measurements of extreme points in your data.
//    Skewness measures the asymmetry the values in your data around the mean,
//    whereas kurtosis is a measure of the tail of data. These are both relevant
//    specifically when modeling your data as a probability distribution of a random
//    variable. Although here we won’t go into the math behind these specifically,
//    you can look up definitions quite easily on the internet. You can calculate
//    these by using the eponymous functions:

    df1.select(skewness($"sal"),kurtosis($"sal")).show

//    Covariance and Correlation:
//
//    We discussed single column aggregations, but some functions compare the
//    interactions of the values in two difference columns together. Two of these
//    functions are `cov` and `corr`, for covariance and correlation, respectively.
//    Correlation measures the Pearson correlation coefficient, which is scaled
//    between –1 and +1. The covariance is scaled according to the inputs in the data.
//
//    Like the `var` function, covariance can be calculated either as the sample
//    covariance or the population covariance. Therefore it can be important to specify
//    which formula you want to use. Correlation has no notion of this and therefore
//    does not have calculations for population or sample. Here’s how they work:

    df1.select(corr($"dno", $"sal"), covar_samp($"dno",$"sal"),
      covar_pop($"dno", $"sal")).show

//    Aggregating to Complex Types:
//
//    In Spark, you can perform aggregations not just of numerical values using formulas,
//    you can also perform them on complex types. For example, we can collect a list of
//    values present in a given column or only the unique values by collecting to a set.
//
//    You can use this to carry out some more programmatic access later on in the pipeline
//    or pass the entire collection in a UserDefined Function (UDF):

    df1.select(collect_list($"sal"), collect_set($"sal")).show

//    Grouping:
//
//    This is typically done on categorical data for which we group our data
//    on one column and perform some calculations on the other columns that end up in that group.
//
//    We do this grouping in two phases. First we specify the column(s) on which we would
//    like to group, and then we specify the aggregation(s). The first step returns a
//    `RelationalGroupedDataset`, and the second step returns a `DataFrame`.
//
//    As mentioned, we can specify any number of columns on which we want to group:

    df1.groupBy($"dno",$"sal").count.show

//    Grouping with expressions:
//
//    we specify i `agg` function. This makes it possible for you to pass-in arbitrary expressions
//    that just need to have some aggregation specified. You can even do things like `alias`
//    a column after transforming it for later use in your data flow:

    df1.groupBy($"dno").agg(sum($"sal") as "tot", avg($"sal") as "avg", count($"sal") as "cnt").show

//    Grouping with Maps:
//
//    Sometimes, it can be easier to specify your transformations as a series of `Maps`
//    for which the key is the column name, and the value is the aggregation function
//    (as a string) that you would like to perform. You can reuse multiple column
//    names if you specify them inline, as well:

    df1.groupBy($"dno").agg("sal" -> "avg", "sal" -> "stddev_pop").show
    //in sql like this (select dno, avg(sal),stddev_pop(sal) from emp group by dno;)







  }
}