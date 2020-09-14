package exercises

abstract class MyList{
/*
create list of integers
  head= return first element of the list
  tail- remainder of the list
  isEmpty- is this list empty?
  add(int)- receives an integer and return new list with added element
  toString- a string representation of list
 */
  def head:Int
  def tail:MyList
  def isEmpty:Boolean
  def add(n:Int):MyList
  def printElements:String
  override def toString:String="["+printElements+"]"
}
object empty extends MyList{
  def head:Int = throw new NoSuchElementException
  def tail:MyList = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def add(n: Int): MyList = new Cons(n,empty)
  def printElements: String = ""
}

class Cons (h:Int, t:MyList) extends MyList {
  def head:Int = h
  def tail:MyList = t
  override def isEmpty: Boolean = false
  override def add(n: Int): MyList = new Cons(n,this)
  def printElements:String ={
    if(t.isEmpty)
      " "+ h
    else
      h+ " " + t.printElements
  }
}

object listtest extends App {
  val list= new Cons(1,new Cons(2,new Cons(3,empty)))
  println(list.head)
  println(list.tail.head)
  println(list.add(4).head)
  println(list.toString)
}