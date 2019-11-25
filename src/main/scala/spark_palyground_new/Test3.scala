package spark_palyground_new

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._


object Test3 {
  def main(args: Array[String]): Unit = {

    Logger.getLogger( "org" ).setLevel( Level.ERROR )

    val spark = SparkSession.builder().
      master( "local" ).appName( "sample" ).
      getOrCreate()

    import spark.implicits._

    val dfnew = spark.read.option( "multiLine", "true" ).format( "json" ).
      load( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\dummy2.json" )

    dfnew.show( false )
    println( dfnew.schema )
    dfnew.printSchema()

    val cust_schema = new StructType().
      add( "IFAM", StringType, true ).
      add( "KTM", LongType, true ).
      add( "COL", IntegerType, true ).
      add( "DATA", ArrayType( new StructType().
        add( "MLrate", StringType, true ).
        add( "Create", ArrayType( new StructType().
          add( "key", StringType, true ).
          add( "value", StringType, true ) ) ) ) ).
      add( "CHECK", new StructType().
        add( "Check1", IntegerType, true ).
        add( "Check2", StringType, true ), true ).
      add("EMP", ArrayType(new StructType().
        add("ID", IntegerType, true).
        add("Name", StringType, true).
        add("Edu", new StructType().
          add("SSC", StringType, true).
          add("Inter", StringType, true).
          add("Degree", StringType, true), true)), true)


    var json_df = spark.read.option( "multiLine", "true" ).
      format( "json" ).schema( cust_schema ).
      load( "C:\\Users\\Kenche.vamshikrishna\\Downloads\\inputfiles\\dummy2.json" )

    json_df.show( false )
    println( json_df.schema )
    json_df.printSchema()

    // processing nested structure

    var nested_column_count = 1

    while (nested_column_count != 0) {
      println( "Printing nested_columns_count " + nested_column_count )

      var nested_column_count_temp = 0

      // iterating each column again to check any next xml data is exists
      for (column_name <- json_df.schema.names) {
        println( "Iterating DataFrame Columns " + column_name )

        // checking if column type is ArrayType
        if (json_df.schema( column_name ).dataType.isInstanceOf[ArrayType]) {
          nested_column_count_temp += 1
        }
        else if (json_df.schema( column_name ).dataType.isInstanceOf[StructType]) {
          nested_column_count_temp += 1
        }
      }

      if (nested_column_count_temp != 0) {
        json_df = expand_nested_column( json_df )
        json_df.show( false )
      }
      print( "Printing nested_column_count_temp: " + nested_column_count_temp )
      nested_column_count = nested_column_count_temp
    }

  }

  def expand_nested_column(json_data_df_temp: DataFrame): DataFrame = {
    var json_data_df = json_data_df_temp
    var select_clause_list = List.empty[String]

    // iterating each column again to check any next xml data is exists
    for (column_name <- json_data_df.schema.names) {
      println( "Outside instance loop:" + column_name )

      // checking if column type is ArrayType
      if (json_data_df.schema( column_name ).dataType.isInstanceOf[ArrayType]) {
        println( "Inside instance loop of ArrayType: " + column_name )

        // Extracting nested_xml columns/data using explode function
        json_data_df = json_data_df.withColumn( column_name, explode( json_data_df( column_name ) ) )
        select_clause_list :+= column_name
      }
      else if (json_data_df.schema( column_name ).dataType.isInstanceOf[StructType]) {
        println("Inside instance loop of StuctType: " + column_name )

        for (field <- json_data_df.schema(column_name).dataType.asInstanceOf[StructType].fields) {

          // select_clause_list += col(column_name + "." + field.name).as(column_name + "_" + field.name)

          select_clause_list :+= column_name + "." + field.name
        }
      }
      else {
        select_clause_list :+= column_name
      }
    }

    val columnNames = select_clause_list.map(x => col(x).as(x.replace(".", "_")))

    // selecting columns using select_clause_list from dataframe
    val json_data_df_new = json_data_df.select(columnNames:_*)
    json_data_df_new

  }

}
