package com.ds.linklist1;

public class TestSinglyLinkedList {
    public static void main (String[] args){
        SingleLLNodeImpl impl=new SingleLLNodeImpl();
        impl.print();
        impl.removeAtEnd();
        impl.print();
        impl.removeAtStart();
        impl.print();
        impl.removeAtPos(4);
        impl.print();
        impl.removeAtPos(-1);
        impl.print();
        impl.addAtStart(30);
        impl.print();
        impl.removeAtPos(4);
        impl.print();
        impl.removeAtPos(1);
        impl.print();
        impl.addAtStart(10);
        impl.print();
        impl.removeAtStart();
        impl.print();
        impl.addAtEnd(20);
        impl.print();
        impl.removeAtEnd();
        impl.print();
        impl.addAtStart(10);
        impl.print();
        impl.addAtEnd(34);
        impl.print();
        impl.addAtPos(15,1);
        impl.print();
        impl.addAtPos(29,4);
        impl.print();
        impl.addAtPos(40,7);
        impl.print();
        impl.addAtPos(20,2);
        impl.print();
        impl.addAtEnd(19);
        impl.print();
        impl.removeAtEnd();
        impl.print();
        impl.removeAtStart();
        impl.print();
        impl.removeAtPos(7);
        impl.print();
        impl.removeAtPos(1);
        impl.print();
        impl.removeAtPos(2);
        impl.print();
    }
}