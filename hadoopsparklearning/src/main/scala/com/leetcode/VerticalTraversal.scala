package com.leetcode

import java.util

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


class TreeNode(var _value: Int) {
     var value: Int = _value
     var left: TreeNode = null
     var right: TreeNode = null
   }

object VerticalTraversal {
  var pos : mutable.Map[Int,mutable.Map[Int,ListBuffer[Int]]] = mutable.Map[Int,mutable.Map[Int,ListBuffer[Int]]]()
  def verticalTraversal(root: TreeNode): List[List[Int]] = {
    traverse(root,0,0);
    var output : ListBuffer[ListBuffer[Int]]=new ListBuffer[ListBuffer[Int]]()//List<List<Integer>> output = new ArrayList<>();
    var index=0;    // index to add all y lists in same x as single list in output

    pos
      .map(yCord => (yCord._1,yCord._2
        .map(yCord => (yCord._1, yCord._2.sorted))
        .toSeq
        .sortBy(key => key._1)
        .flatMap(values => values._2).toList))
      .toSeq
      .sortBy(key => key._1)
      .map(pair => pair._2).toList

   /* for (util.TreeMap[Int, ListBuffer[Integer]] cols : pos.values()){  //for (TreeMap<Integer, List<Integer>> cols : pos.values()){// collecting all y for x
      output.add(new util.ArrayList<>());
      for (List<Integer> rows : cols.values()){   // collecting all lists in x,y
        Collections.sort(rows);                 // sorting list at same y value
        output.get(index).addAll(rows);         // adding y lists at final index i.e. x in output
      }
      index++;
    }
    return output;*/
  }
  def traverse(node: TreeNode, x : Int, y:Int): Unit ={
    if (node==null)
      return;
    if(!pos.contains(x))  // No key x present, so initialize treemap

    pos(x) = mutable.Map[Int,ListBuffer[Int]]() //pos.add(x,new TreeMap<>());
   if(!pos(x).contains(y)) //if(!pos.get(x).containsKey(y))
      pos(x) += (y -> new ListBuffer[Int]()) //pos.get(x).put(y,new ArrayList<>());
    pos(x)(y) += node.value      //pos.get(x).get(y).add(node.val);
    traverse(node.left,x-1,y+1);
    traverse(node.right,x+1,y+1);
  }

  def main(args: Array[String]): Unit = {

  }
}
