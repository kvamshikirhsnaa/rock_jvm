package udemy_rockjvm

object TuplesNMapsExer {
  def main(args: Array[String]): Unit = {

    val aMap: Map[String, Int] = Map(("saikrishna", 24), ("SAIKRISHNA", 25), ("Aravind", 25)).withDefaultValue(0)
    println(aMap)  // Map(saikrishna -> 24, SAIKRISHNA -> 25, Aravind -> 25)
    println(aMap.map(x => (x._1.toLowerCase, x._2)))  // Map(saikrishna -> 25, aravind -> 25)

/*
     Overly simplified social network based on maps
     Person = String
     - add a person to the network
     - friend (mutual)
     - unfriend
     - remove

     - number of friends of a Person
     - person with most friends
     - how many people have NO friends
     - if there is a social connection between two people (direct or not)

*/
    def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
      network + (person -> Set())
    }

    def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
      val friendsA = network(a)
      val friendsB = network(b)
      network + (a -> (friendsA + b)) + (b -> (friendsB + a))
    }

    def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
      val friendsA = network(a)
      val friendsB = network(b)
      network + (a -> (friendsA - b)) + (b -> (friendsB - a))
    }
/*
    def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
      def removeAux(network: Map[String, Set[String]]): Map[String, Set[String]] = {
        network.mapValues(x => x - person)
      }
      removeAux(network).filterKeys(x => x != person)
    }
*/

    def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
      def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] = {
        if (friends.isEmpty) networkAcc
        else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))
      }
      val unfriended = removeAux(network(person), network)
      unfriended - person
    }

    val empty: Map[String, Set[String]] = Map()
    val network = add(add(add(empty, "Saikrishna"), "Aravind"), "Prakash")
    println(network)

    val newNetwork = friend(network, "Saikrishna", "Aravind")
    println(newNetwork)

    val newNetwork2 = friend(newNetwork, "Aravind", "Prakash")
    println(newNetwork2)

    val newNetwork3 = friend(newNetwork2, "Saikrishna", "Prakash")
    println(newNetwork2)

    val newNetwork4 = unfriend(newNetwork3, "Saikrishna", "Aravind")
    println(newNetwork4)

    val newNetwork5 = remove(newNetwork4, "Prakash")
    println(newNetwork5)

    val newNetwork6 = add(add(newNetwork2, "Vamshikrishna"), "Narahari")
    val newNetwork7 = friend(newNetwork6, "Vamshikrishna", "Narahari")
    println(newNetwork6)
    println(newNetwork7)
    val newNetwork8 = friend(newNetwork7, "Narahari", "Saikrishna")
    println(newNetwork8)


    println(newNetwork2)

    // - number of friends of a Person

    def findFrndsCnt(network: Map[String, Set[String]], person: String): Int = {
      if (!network.contains(person)) 0
      else network(person).size
    }
    println(findFrndsCnt(newNetwork2, "Saikrishna"))  // 1
    println(findFrndsCnt(newNetwork2, "Aravind"))   // 2
    println(findFrndsCnt(newNetwork2, "Prakash"))  // 1

    //  - person with most friends
    def maxFrnds(network: Map[String, Set[String]]): String = {
      network.maxBy(x => x._2.size)._1
    }
    println(maxFrnds(newNetwork2))  // Aravind

    // - how many people have NO friends
    def noFrnds(network: Map[String, Set[String]]): Int = {
      network.count(x => x._2.isEmpty)
    }
    println(noFrnds(newNetwork))   // 1
    println(noFrnds(newNetwork2))  // 0
    println(noFrnds(newNetwork3))  // 0
    println(noFrnds(newNetwork4))  // 0
    println(noFrnds(newNetwork5))  // 0


    // - if there is a social connection between two people (direct or not)

    def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
      def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
        if (discoveredPeople.isEmpty) false
        else {
          val person = discoveredPeople.head
          if (person == target) true
          else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
          else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
        }
      }

      bfs(b, Set(), network(a) + a )
    }

    def relation(network: Map[String, Set[String]], a: String, b: String): String = {
      val friendsA = network(a)
      val friendsB = network(b)
      if (friendsA.contains(b)) s"$a and $b are friends"
      else if (friendsA.intersect(friendsB).nonEmpty) s"$a and $b are mutual friends"
      else s"$a and $b doesn't have any relation"
    }

    println("newNetwork7")
    println("------------")
    println(newNetwork7)

    println(socialConnection(newNetwork7, "Saikrishna", "Aravind")) // true
    println(socialConnection(newNetwork7, "Prakash", "Aravind"))  // true
    println(socialConnection(newNetwork7, "Prakash", "Saikrishna"))   // true
    println(socialConnection(newNetwork7, "Vamshikrishna", "Narahari"))  // true
    println(socialConnection(newNetwork7, "Vamshikrishna", "Aravind"))  // false
    println(socialConnection(newNetwork8, "Vamshikrishna", "Aravind")) // true


    println(relation(newNetwork7, "Saikrishna", "Aravind"))  // Saikrishna and Aravind are friends
    println(relation(newNetwork7, "Prakash", "Aravind"))  // Prakash and Aravind are friends
    println(relation(newNetwork7, "Prakash", "Saikrishna"))  // Prakash and Saikrishna are mutual friends
    println(relation(newNetwork7, "Vamshikrishna", "Narahari"))  // Vamshikrishna and Narahari are friends
    println(relation(newNetwork7, "Vamshikrishna", "Aravind"))  // Vamshikrishna and Aravind doesn't have any relation










  }


}
