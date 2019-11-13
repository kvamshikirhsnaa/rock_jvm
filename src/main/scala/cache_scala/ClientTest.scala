package cache_scala

import java.util.Map.Entry
import java.util.Set
import scala.collection.JavaConversions._


//object ClientTest {
//  def main(args: Array[String]): Unit = {
//    val e1 = new Employee( 1001, "Saikrishna", 1000.00, "sai@gmail.com" )
//    val e2 = new Employee( 1002, "Aravind", 900.00, "arvnd@gmail.com" )
//    val e3 = new Employee( 1003, "Prakash", 600.00, "prakash@gmail.com" )
//    val e4 = new Employee( 1004, "Narahari", 700.00, "narahari@gmail.com" )
//    val e5 = new Employee( 1005, "Vamshikrishna", 950.00, "vk@gmail.com" )
//    val d1 = new Department( 11, "IT", "Bangalore" )
//    val d2 = new Department( 12, "Finance", "Hyderabad" )
//    val cache: LRUCache[Employee, Department] = LRUCache.newInstance( 2 )
//
//    cache.put( e2, d1 )
//    cache.put( e3, d2 )
//    cache.put( e4, d2 )
//    cache.put( e5, d1 )
//
//    val entrySet  = cache.entrySet
//
//    for (entry <- entrySet) {
//      val employee = entry.getKey
//      val department = entry.getValue
//      println( employee )
//      println( department )
//    }
//  }
//}