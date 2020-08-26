package com.ds.linklist1;

public class Problems {

    /**
     *
     */

    int printNthNodeWithRecursion(Node head, int nth) {
        return -1;
    }


    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        n1.next = n2;
        n2.next = n3;
        Problems ps = new Problems();
        ps.printNthNodeWithRecursion(n1,2);
    }
}
