package com.ds.linklist1;

import java.util.HashMap;

public class Problem1_NthNodeFromEndInLL {  // time complexity O(len) but actually worst case is 2(len) runs
    private static int Way1_Basic(Node head,int n){
        Node temp=head;
        int len=0;
        while (temp!=null){
            len++;
            temp=temp.next;
        }
        int rev_n=len-n+1;
        temp=head;
        if (rev_n>len || rev_n<=0){  // to check invalid input
            return -1;
        }
        int p=1;
        while(p!=rev_n){
            temp=temp.next;
            p++;
        }
        return temp.data;
    }
    private static int Way2_Hash(Node head, int n){  // assumption every element is unique.
        int len=0;
        HashMap<Integer,Integer> hmap = new HashMap<Integer, Integer>();
        Node temp=head;
        while (temp != null){
            len++;
            hmap.put(len,temp.data);
            temp=temp.next;
        }
        if (n>len || n<=0)
            return -1;
        return (hmap.get(len-n+1));
    }
    private static int Way3_TwoPointers(Node head, int n){
        Node i=head;
        Node j=head;
        int pos=0;
        while (i!=null && pos!=n){
            i=i.next;
            pos++;
        }
        if (i==null)
            return -1;
        while(i!=null){
            i=i.next;
            j=j.next;
        }
        return (j.data);
    }

    private static Node finalNode = null;

    static int Way4_Recursion(Node head, int n) {
        finalNode = null;
        Way4_Recursion_Trvs(head,n);
        return finalNode != null ? finalNode.data : -1;
    }

    private static int Way4_Recursion_Trvs(Node head, int n){
        if (head==null)
            return 0;
        int pos=1+Way4_Recursion_Trvs(head.next,n);
        if (pos==n)
            finalNode = head;
        return pos;
    }
    private static void Print_LL(Node head){
        Node n=head;
        while (n.next!=null){
            System.out.print(n.data + ",");
            n=n.next;
        }
        System.out.println(n.data);
    }
    public static void main (String[] args){
        Node n1=new Node(20);
        Node n2=new Node(30);
        Node n3=new Node(40);
        Node n4=new Node(50);
        Node n5=new Node(60);
        Node n6=new Node(70);
        n1.next=n2;
        n2.next=n3;
        n3.next=n4;
        n4.next=n5;
        n5.next=n6;
        Node head=n1;
        System.out.println(head);
        Print_LL(head);
        System.out.println("Value: " + Way1_Basic(head,4));
        System.out.println("Value: " + Way1_Basic(head,1));
        System.out.println("Value: " + Way1_Basic(head,6));
        System.out.println("Value: " + Way1_Basic(head,10));
        System.out.println("Value: " + Way2_Hash(head,3));
        System.out.println("Value: " + Way2_Hash(head,1));
        System.out.println("Value: " + Way2_Hash(head,6));
        System.out.println("Value: " + Way2_Hash(head,10));
        System.out.println("Value: " + Way3_TwoPointers(head,5));
        System.out.println("Value: " + Way3_TwoPointers(head,1));
        System.out.println("Value: " + Way3_TwoPointers(head,6));
        System.out.println("Value: " + Way3_TwoPointers(head,10));
        System.out.println("Value: " + Way4_Recursion(head,5));
        System.out.println("Value: " + Way4_Recursion(head,1));
        System.out.println("Value: " + Way4_Recursion(head,6));
        System.out.println("Value: " + Way4_Recursion(head,10));
    }
}
