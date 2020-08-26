package com.ds.linklist1;

import java.util.HashSet;
import java.util.PrimitiveIterator;

public class Problem5_MeetingPointOf2LL {
    private static int llLength(Node head){
        int len=0;
        while (head!=null)
            len++;
        return len;
    }
    private static Node way1Hash(Node head1, Node head2){
        int len=0;
        HashSet<Node> hset = new HashSet<Node>();
        Node t1=head1;
        while (t1!=null){
            hset.add(t1);
            t1=t1.next;
        }
        t1=head2;
        while (t1!=null && !hset.contains(t1))
            t1=t1.next;
        if (t1==null)
            return null;
        return t1;
    }
    private static Node way2Stack (Node head1 , Node head2){
        LLasStack stack1 = new LLasStack();
        while (head1!=null){
            stack1.push(head1.data);
            head1=head1.next;
        }
        System.out.println("Stack 1:");
        stack1.print();
        LLasStack stack2 = new LLasStack();
        while (head2!=null){
            stack2.push(head2.data);
            head2=head2.next;
        }
        System.out.println("Stack 2:");
        stack2.print();
        Node t1 = null;
        Node t2;
        Node prev;
        do{
            prev=t1;
            t1=stack1.pop();
            t2=stack2.pop();
        }
        while(t1 != null && t2 != null && t1.data==t2.data);
        if(t1==null || t2==null)
            return null;
        return prev;
    }
    private static void way3LenDiff (Node head1, Node head2){
        int len1=llLength(head1);
        int len2=llLength(head2);
        int diff=len1-len2;
        int p=0;
        Node t1 = head1;
        Node t2= head2;
        if (diff<0){

        }
        if (len1>len2 && t1!=null){
            t1=t1.next;

        }
        else
    }
    private static void PrintLL(Node head){
        if (head==null)
            System.out.println("empty");
        else {
            System.out.println("Top:" + head.data + " Stack is:");
            Node curr = head;
            while (curr != null) {
                System.out.print(curr.data + ",");
                curr=curr.next;
            }
            System.out.println();
        }
    }
    public static void main (String [] args){
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
        Node head1 = n1;
        Node n7= new Node(25);
        n6.next = n7;
        n7.next = n3;
        Node head2 = n6;
        System.out.print("LL1: ");
        PrintLL( head1);
        System.out.print("LL2: ");
        PrintLL(head2);
        Node t= way1Hash(head1,head2);
        if (t!=null)
            System.out.println("Meeting node: " + t.data);
        else
            System.out.print("No meeting point");
        n7.next=null;
        System.out.print("LL2: ");
        PrintLL(head2);
        t= way1Hash(head1,head2);
        if (t!=null)
            System.out.println("Meeting node: " + t.data);
        else
            System.out.print("No meeting point");
        n7.next = n3;
        System.out.print("LL1: ");
        PrintLL( head1);
        System.out.print("LL2: ");
        PrintLL(head2);
        t= way2Stack(head1,head2);
        if (t!=null)
            System.out.println("Meeting node: " + t.data);
        else
            System.out.print("No meeting point");
        n7.next=null;
        System.out.print("LL2: ");
        PrintLL(head2);
        t= way2Stack(head1,head2);
        if (t!=null)
            System.out.println("Meeting node: " + t.data);
        else
            System.out.print("No meeting point");
    }
}
