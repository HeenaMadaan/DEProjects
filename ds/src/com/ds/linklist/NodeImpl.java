package com.ds.linklist;

public class NodeImpl {

    Node head = null;

    void add(int data) {
        Node n1 = new Node(data);
        if (head==null)
        {
            head=n1;
        }
        else
        {
            Node t=head;
            while (t.next != null) {
                t = t.next;
            }
            t.next=n1;
        }
        System.out.println("Node inserted");
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
                else
                    prev.next=t.next;
                t.next=null;
            }
            else
                System.out.println("Item not present");
        }
    }

    boolean isEmpty() {
        return head == null;
    }

    int size() {
        Node t=head;
        int len=0;
        while (t.next!=null)
        {
            len++;
            t=t.next;
        }
        len++;
        return len;
    }
}
