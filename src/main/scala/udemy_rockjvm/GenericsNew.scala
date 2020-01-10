package udemy_rockjvm

object GenericsNew {
  def main(args: Array[String]): Unit = {

    class Animal
    class Dog extends Animal
    class Cat extends Animal

    abstract class MyList[+A]{
      def add[B >: A](x: B): MyList[B]
    }


    object Empty extends MyList[Nothing] {
      def add[B >: Nothing](x: B): MyList[B] = new CovariantList(x, this)
    }

    class CovariantList[+A](h: A, t: MyList[A]) extends MyList[A] /*accept child class instances */ {
      def add[B >: A](x: B): CovariantList[B] = {
        new CovariantList(x, this)
      }
    }

    val d1 = new Dog
    val c1 = new Cat

    val dogs: CovariantList[Dog]= new CovariantList[Dog](d1, Empty )
    println(dogs)
    println(dogs.add(c1))

    val animals: CovariantList[Animal]= new CovariantList[Dog](d1, Empty )
    println(animals)
    println(animals.add(c1))

//    val dogsNcats: CovariantList[Dog]= new CovariantList[Cat](c1, Empty )

    class ContravariantList[-A]

    val dogsList: ContravariantList[Dog] = new ContravariantList[Animal]
    println(dogsList)

//    val Animallist: ContravariantList[Dog] = new ContravariantList[Cat]




  }



}
