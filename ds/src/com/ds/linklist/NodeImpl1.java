package com.ds.linklist;

public class NodeImpl1 {
    Node head = null;
    Node tail=null;
    int len=0;
    void add(int data) {
        Node n1 = new Node(data);
        if (head==null)
        {
            head=n1;
            tail=n1;
        }
        else {
            tail.next = n1;
            tail = n1;
        }
        System.out.println("Node inserted");
        len++;
    }

    void remove(int data) {
        if (head==null)
            System.out.println("OOps. List is empty");
        else {
            Node prev=null;
            Node t=head;
            while (t.data!=data && t.next!=null)
            {
                prev=t;
                t=t.next;
            }
            if (t.data==data) {
                System.out.println("element being deleted is:" + t.data);
                if (head==t)
                    head=t.next;
                else{
                    if (tail==t)
                        tail=prev;
                    prev.next=t.next;}
                len--;
            }
            else
                System.out.println("Item not present");
        }
    }

    boolean isEmpty() {
        return head == null;
    }

    int size() {
        return len;
    }
}
