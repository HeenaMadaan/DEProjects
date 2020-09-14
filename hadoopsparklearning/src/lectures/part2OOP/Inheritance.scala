package lectures.part2OOP

object InheritanceAndTraits extends App {
  // Scala has single class inheritance i.e extend only one class
  // subclass inherits non-private members of super class
  // protected members are accessible within subclass
  class Animal {
    private val age=2
    val name="xyz"
    def eat= println("carni")
  }

  class Cat extends Animal {
    def crunch= {
      eat   // calls super class function eat if it is public or protected only
      println("crunch")
    }


    def cruch1: String = {
      ""
    }

  }



  val cat=new Cat
  // cat.age // inaccessible
  cat.eat

  // constructors
  class Person(name:String, age:Int){
    def this(name:String) = this(name,0)
  }

  // while extending parameterized constructor class, it is necessary to provide values while extending
  // extends Person only won;t work
  // class Adult (name:String, age:Int, idCard:String) extends Person  // error
  class Adult (name:String, age:Int, idCard:String) extends Person(name,age)
  // this works

  // you can have overloaded constructor as well, calling it also works
   class Adult1 (name:String, age:Int, idCard:String) extends Person(name)

  // overriding super class method and fields
  class Dog extends Animal{
    override def eat = {
      // to call parent class eat function, cannot use eat only.
      // use super keyword to refer its call
      // eat // recursive call
      super.eat  // Animal class eat method call
      println("crunch Dog")
    }
    override val name= "Puppy"
  }
  val dog= new Dog
  // instances of child classes used the overriden values
  println(dog.name)
  println(dog.eat)

  // you can directly override field via constructor
  // rather than overriding inside it

  class Dog1(override val name:String) extends Animal{

  }
  val dog1=new Dog1("tuffy")
  println(dog1.name)

  // type substitution (broadly it is polymorphism)
  val unknownAnimal : Animal = new Dog
  unknownAnimal.eat  // it calls the eat method of Dog not Animal

  // super - to call parent class variables
  // preventing overrides using FINAL keyword
  //1. use final on member
  // 2. use final on the entire class
  // 3. seal the class- extend classes in this file, prevent extension in other files
  // keyword- "sealed" before class, it will be extended within this file

}
