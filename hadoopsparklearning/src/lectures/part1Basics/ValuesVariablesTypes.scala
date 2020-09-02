package lectures.part1Basics

object ValuesVariablesTypes extends App {
  val x:Int=42
  println(x)

  val y=1+2  // expressions
  println(y)

  val aCondition= true
  val aConditionValue= if(aCondition) 5 else 3  // IF expression
  println(aConditionValue)

  var s=0
  val z=(s=3)  // Unit = void

  val aCodeBlock = {
    val a1=3
    val a2= a1+4

    if(a2>3) "hello" else "bye"

  }

  println("hello world")

  val someValue= {   // gets value from codeblock
    2>3
  }
  println(someValue)

  val someOtherValue={
    if(someValue) 24 else 35   // this is irrelevant. It takes 43 as value of Int type
    43
  }
  println(someOtherValue)
}
