package com.ds.linklist;

public class TestSinglyLinkList {

    public static void main(String[] args) {
        NodeImpl1 impl = new NodeImpl1();
        impl.add(10);
        impl.add(100);
        impl.add(20);
        impl.remove(3);
        System.out.println("checking is empty " + impl.isEmpty());
        impl.remove(20);
        System.out.println("size is :" + impl.size());
        impl.remove(100);
        System.out.println("size is :" + impl.size());
    }

}
