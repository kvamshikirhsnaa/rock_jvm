package iterative_join.join

import org.apache.spark.sql.{DataFrame, SparkSession}

trait JoinStrategy {

  def join(spark: SparkSession,
           dfLarge: DataFrame,
           dfMedium: DataFrame): DataFrame

}