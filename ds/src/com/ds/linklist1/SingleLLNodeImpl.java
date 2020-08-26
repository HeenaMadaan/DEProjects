package com.ds.linklist1;

public class SingleLLNodeImpl{
    Node head=null;
    Node tail=null;
    int len =0;
    void addAtStart (int data){
        addAtPos(data,1);
    }
    void addAtEnd (int data){
        addAtPos(data,len+1);
    }
    void addAtPos(int data, int p){
        Node n=new Node(data);
        if (p>len+1 || p<0){
            System.out.println("wrong position mentioned. Insertion not possible");
            return;
        }
        else if (p==1){   // if first element is inserted
            n.next=head;
            tail=n;
            head=n;
            System.out.println("Inserted"+data );
        }
        else if (p==len+1){  // if last element is inserted
            tail.next=n;
            tail=n;
            System.out.println("Inserted value:" + data);
        }

        else{
            int pos=1;
            Node curr=head;
            Node prev=null;
            while(pos!=p && curr.next!=null){
                prev=curr;
                curr=curr.next;
                pos++;
            }
            n.next = curr;
            prev.next = n;
            System.out.println("Inserted value:" + data );
        }
        len++;
    }
    void removeAtStart(){
        removeAtPos(1);
    }
    void removeAtEnd(){
        removeAtPos(len);
    }
    void removeAtPos(int p){
        if (p>len || p<=0){
            System.out.println("nothing to delete");
            return;
        }
        else if (len==1){  // if it is the only element
            head = null;
            tail = null;
            len--;
        }
        else {

            int pos = 1;
            Node prev = null;
            Node curr = head;
            while (p != pos && curr.next!=null) {
                prev = curr;
                curr = curr.next;
                pos++;
            }
            if (prev==null)
                head=head.next;
            else if (p == len) {  // if it is the last element
                prev.next = null;
                tail = prev;
            }
            else
                prev.next = curr.next;
            System.out.print("Item deleted:" + curr.data);
            len--;
        }
    }
    boolean isEmpty(){
        return (len==0);
    }

    int size(){
        return len;
    }

    void print(){
        if (head==null)
            System.out.println("empty");
        else {
            System.out.println("head:" + head.data + " tail : " + tail.data + " len is : " + len + " Linked list is:");
            Node curr = head;
            while (curr.next != null) {
                System.out.print(curr.data + ",");
                curr=curr.next;
            }
            System.out.print(curr.data);
            System.out.println();
        }
    }
}