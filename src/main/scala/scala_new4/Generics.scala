package scala_new4


class Animal
class Dog extends Animal
class Cat extends Animal

class CovariantList[+A]   // accept child class instances

class ContravariantList[-A]  // accept parent class instances

class InvariantList[A]   // accept same type instances

object Generics {
  def main(args: Array[String]): Unit = {


    val animal: Animal = new Dog

    val animalsList: CovariantList[Animal] = new CovariantList[Dog]

//    val animalsList: CovariantList[Dog] = new CovariantList[Animal]

    val dogsList: ContravariantList[Dog] = new ContravariantList[Animal]

//    val dogsList: ContravariantList[Animal] = new ContravariantList[Dog]

    val dogs: InvariantList[Dog] = new InvariantList[Dog]


    // bound types:

    // 1: upper bound - <:
    class Cage[A <: Animal](aniaml: A)

    class NewCage

    val cage = new Cage(new Dog)
    println(cage.getClass)

//    val cageNew = new Cage(new NewCage)


//    val newCage = new Cage(new NewCage) // throws error

    // 2: lower bound - >:

    class Trainer[A >: Dog](dog: A)

    val trainer = new Trainer(new Animal)

    val trainerNew = new Trainer(new NewCage)




  }

}
