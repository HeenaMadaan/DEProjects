package exercises

abstract class MyListGeneric[+A] {
/*
create list of integers
  head= return first element of the list
  tail- remainder of the list
  isEmpty- is this list empty?
  add(int)- receives an integer and return new list with added element
  toString- a string representation of list
 */
  def head:A
  def tail:MyListGeneric[A]
  def isEmpty:Boolean
  def add[B>:A](n:B):MyListGeneric[B]
  def printElements:String
  override def toString:String="["+printElements+"]"
//  def map[B](transformer:MyTransformer[A,B]):MyListGeneric[B]
//  def flatMap[B](transformer:MyTransformer[A,MyListGeneric[B]]):MyListGeneric[B]
//  def filter(predicate: MyPredicate[A]):MyListGeneric[A]

  //scala conversion
  // higher order functions- the ones that either take function as a parameter or return function as a result

  def map[B](transformer:A=>B):MyListGeneric[B]
  def flatMap[B](transformer:A=>MyListGeneric[B]):MyListGeneric[B]
  def filter(predicate: A=>Boolean):MyListGeneric[A]
  // concatenation
  def ++ [B >: A] (list:MyListGeneric[B]):MyListGeneric[B]
}
object Empty extends MyListGeneric[Nothing] {  // nothing is a proper substitute for any type
  def head:Nothing = throw new NoSuchElementException
  def tail:MyListGeneric[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](n: B): MyListGeneric[B] = new Construct(n,Empty)
  def printElements: String = ""
//  def map[B](transformer:MyTransformer[Nothing,B]):MyListGeneric[B] = Empty
//  def flatMap[B](transformer:MyTransformer[Nothing,MyListGeneric[B]]):MyListGeneric[B] = Empty
//  def filter(predicate: MyPredicate[Nothing]):MyListGeneric[Nothing] = Empty

  //scala conversion
  def map[B](transformer:Nothing=>B):MyListGeneric[B] = Empty
  def flatMap[B](transformer:Nothing=>MyListGeneric[B]):MyListGeneric[B] = Empty
  def filter(predicate: Nothing=>Boolean):MyListGeneric[Nothing] = Empty
  def ++ [B >: Nothing](list:MyListGeneric[B]) : MyListGeneric[B] = list
}

class Construct [+A] (h:A, t:MyListGeneric[A]) extends MyListGeneric[A] {
  def head:A = h
  def tail:MyListGeneric[A] = t
  override def isEmpty: Boolean = false
  override def add[B >: A](n: B): MyListGeneric[B] = new Construct(n,this)
  def printElements:String ={
    if(t.isEmpty)
      " "+ h
    else
      h+ " " + t.printElements
  }
//  def map[B](transformer:MyTransformer[A,B]):MyListGeneric[B]= {
//    new Construct(transformer.transform(h),t.map(transformer))
//  }

  //scala conversion
  def map[B](transformer: A=>B):MyListGeneric[B]= {
    new Construct(transformer(h),t.map(transformer))
  }
  def ++[B >: A](list:MyListGeneric[B]):MyListGeneric[B]= new Construct(h,t ++ list)

//  def flatMap[B](transformer:MyTransformer[A,MyListGeneric[B]]):MyListGeneric[B]=
//    transformer.transform(h) ++ t.flatMap(transformer)

  // scala conversion
  def flatMap[B](transformer:A=>MyListGeneric[B]):MyListGeneric[B]=
    transformer(h) ++ t.flatMap(transformer)

//  def filter(predicate: MyPredicate[A]):MyListGeneric[A]={
//    if(predicate.test(h)) new Construct(h,t.filter(predicate))
//    else t.filter(predicate)
//  }

  //scala conversion
  def filter(predicate: A=>Boolean):MyListGeneric[A]={
    if(predicate(h)) new Construct(h,t.filter(predicate))
    else t.filter(predicate)
  }
}

/*
  1. generic trait MyPredicate[-T] -method test(T) to test value of T passes the condition?
  2. Generic trait MyTransformer[-A,B] - method transform(A) to convert value of type A into value of type B
  3. Functions on my list-
    - map(transformer) -> MyList
    - filter(transformer) -> MyList
    - flatmap(tranformer from A to MyList[B]) -> MyList[B]

    class EvenPredicate extends MyPredicate[Int]
    class StringToIntTranformer extends MyTransformer[String,Int]

    ex- [1,2,3].map(n*2) = [2,4,6]
    [1,2,3,4].filter(n%2)=[2,4]
    [1,2,3].flatMap(n=> [n,n+1]) => [1,2,2,3,3,4]
 */

// for changing code to scala fully functional programming, removing below traits and using function types in place of them
//trait MyPredicate[-T] {
//  def test(elem:T):Boolean
//}
//trait MyTransformer[-A,B] {
//  def transform(element:A):B
//}

object listtest1 extends App {
  val listOfInteger : MyListGeneric[Int] = new Construct(1, new Construct(2, new Construct( 3, Empty)))
  val listOfInteger1 : MyListGeneric[Int] = new Construct(4, new Construct(5, new Construct( 6, Empty)))
  val listOfStrings : MyListGeneric[String] = new Construct("hello", new Construct[String]("Scala", Empty))

  println(listOfInteger.toString)
  println(listOfStrings.toString)

//  println(listOfInteger.map(new MyTransformer[Int,Int] {
//    override def transform(element: Int): Int = element*2
//  }).toString)
//
//  println(listOfInteger.filter(new MyPredicate[Int] {
//    override def test(elem: Int): Boolean = elem%2==0
//  }).toString)
//
//  println((listOfInteger ++ listOfInteger1).toString)
//  println(listOfInteger.flatMap(new MyTransformer[Int,MyListGeneric[Int]] {
//    override def transform(element: Int): MyListGeneric[Int] = new Construct(element, new Construct(element+1,Empty))
//  }).toString)

  // for scala conversion
  println(listOfInteger.map(new (Int=>Int) {
    override def apply(element: Int): Int = element*2
  }).toString)

  println(listOfInteger.filter(new (Int=>Boolean) {
    override def apply(elem: Int): Boolean = elem%2==0
  }).toString)

  println((listOfInteger ++ listOfInteger1).toString)
  println(listOfInteger.flatMap(new (Int=>MyListGeneric[Int]) {
    override def apply(element: Int): MyListGeneric[Int] = new Construct(element, new Construct(element+1,Empty))
  }).toString)
}