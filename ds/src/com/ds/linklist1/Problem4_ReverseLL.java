package com.ds.linklist1;

public class Problem4_ReverseLL {
    private static Node Way1ReverseLinkedList(Node head){
        Node newHead=null;
        Node curr=head;
        while(curr!=null){
            curr=curr.next;
            head.next=newHead;
            newHead=head;
            head=curr;
        }
        return newHead;
    }
    private static Node Way2RecursiveReverse(Node head){
        if (head== null || head.next==null)
            return head;
        Node newHead = Way2RecursiveReverse(head.next);
        Node curr=newHead;
        while(curr.next!=null){
            curr=curr.next;
        }
        curr.next=head;
        head.next=null;
        return newHead;
    }
    private static void printLL(Node head){
        Node n=head;
        while (n!=null){
            System.out.print(n.data + ",");
            n=n.next;
        }
        System.out.println();
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
        printLL(head);
        head=Way1ReverseLinkedList(head);
        printLL(head);
        head=null;
        head=Way1ReverseLinkedList(head);
        printLL(head);
        head=n1;
        printLL(head);
        n1.next=null;
        head=Way1ReverseLinkedList(head);
        printLL(head);
        head=n1;
        n1.next=n2;
        n2.next=n3;
        n3.next=n4;
        n4.next=n5;
        n5.next=n6;
        n6.next=null;
        printLL(head);
        head=Way2RecursiveReverse(head);
        printLL(head);
        head=n1;
        n1.next=null;
        head=Way2RecursiveReverse(head);
        printLL(head);
        head=null;
        head=Way2RecursiveReverse(head);
        printLL(head);
    }
}
