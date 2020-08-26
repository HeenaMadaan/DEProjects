package com.ds.linklist1;

import java.util.HashMap;
import java.util.HashSet;

public class Problem2_CycleInLL {
    private static boolean way1_hash(Node head){
        if (head==null)
            return false;
        HashSet<Node> hset=new HashSet<Node>();
        Node temp=head;
        while (temp!=null && !hset.contains(temp)){
            hset.add(temp);
            temp=temp.next;
        }
        return (temp!=null);
    }

    private static Node Way2_Floyd(Node head){
        if (head==null)
            return null;
        Node t1=head;
        Node t2=head;
        while (t2!=null && t2.next!=null) { // cover no node, 1 node, or end condition of no cycle
            t1=t1.next;
            t2=t2.next.next;
            if (t1==t2)
                return t1;
        }
        return null;
    }
    private static Node Cycle_Start_Node(Node head){
        Node cyclicNode=Way2_Floyd(head);
        if (cyclicNode!=null){
            Node temp=head;
            while(cyclicNode!=temp){
                cyclicNode=cyclicNode.next;
                temp=temp.next;
            }
            return temp;
        }
        return null;
    }
    private static Integer Cycle_Length(Node head){
        if (head==null)
            return -1;
        Node cyclic1=Way2_Floyd(head);
        Node cyclic2=cyclic1;
        if (cyclic1!=null){
            int count=0;
            do {
                cyclic1=cyclic1.next;
                count++;
            }
            while (cyclic1!=cyclic2);
            return count;
        }
        return 0;
    }
    private static void Print_LL(Node head){
        Node n=head;
        while (n!=null){
            System.out.print(n.data + ",");
            n=n.next;
        }
    }
    public static void main (String[] args) {
        Node n1 = new Node(20);
        Node n2 = new Node(30);
        Node n3 = new Node(40);
        Node n4 = new Node(50);
        Node n5 = new Node(60);
        Node n6 = new Node(70);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        Node head = n1;
        Node temp = null;
        Print_LL(head);
        if (way1_hash(head))
            System.out.println("Way 1: cycle present");
        else
            System.out.println("Way 1: No cycle");
        if (Way2_Floyd(head)!=null)
            System.out.println("Way 2:cycle present");
        else
            System.out.println("Way 2: No cycle");
        temp=Cycle_Start_Node(head);
        if (temp==null)
            System.out.println("No cycle");
        else
            System.out.println("starting node:"+temp.data);
        System.out.println("Cycle length:"+Cycle_Length(head));
        n6.next=n3;
        if (way1_hash(head))
            System.out.println("Way 1:cycle present");
        else
            System.out.println("Way 1:No cycle");
        if (Way2_Floyd(head)!=null)
            System.out.println("Way 2:cycle present");
        else
            System.out.println("Way 2:No cycle");
        temp=Cycle_Start_Node(head);
        if (temp==null)
            System.out.println("No cycle");
        else
            System.out.println("starting node:"+temp.data);
        System.out.println("Cycle length:"+Cycle_Length(head));
        head=null;
        if (way1_hash(head))
            System.out.println("Way 1:cycle present");
        else
            System.out.println("Way 1:No cycle");
        if (Way2_Floyd(head)!=null)
            System.out.println("Way 2:cycle present");
        else
            System.out.println("Way 2:No cycle");
        temp=Cycle_Start_Node(head);
        if (temp==null)
            System.out.println("No cycle");
        else
            System.out.println("starting node:"+temp.data);
        System.out.println("Cycle length:"+Cycle_Length(head));
    }
}
