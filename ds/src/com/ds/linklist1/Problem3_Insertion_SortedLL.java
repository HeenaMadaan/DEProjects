package com.ds.linklist1;

public class Problem3_Insertion_SortedLL {
    private static Node Insert(Node head, int n){
        Node temp=head;
        Node prev=null;
        Node toInsert=new Node(n);
        if (head==null)
            return toInsert;

        while (temp!=null && n>temp.data){
            prev=temp;
            temp=temp.next;
        }
        toInsert.next=temp;
        if(prev != null) {
            prev.next = toInsert;
        } else {
            head = toInsert;
        }
        return head;
    }
    private static void Print_LL(Node head){
        Node n=head;
        while (n!=null){
            System.out.println(n.data + ",");
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
        Print_LL(head);
        head = Insert(head,35);
        Print_LL(head);
        head = Insert(head,5);
        Print_LL(head);
        head = Insert(head,100);
        Print_LL(head);
        head=null;
        head = Insert(head,90);
        Print_LL(head);
    }
}
