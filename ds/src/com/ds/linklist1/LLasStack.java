package com.ds.linklist1;

public class LLasStack {
    Node top=null;
    int len=0;
    void push(int data){
        Node n=new Node(data);
        if (top == null) {
            top=n;
        }
        else {
            n.next=top;
            top=n;
        }
        len++;
    }
    Node pop(){
        if (top==null){
            return null;
        }
        Node t=top;
        top=top.next;
        len--;
        return t;
    }
    void print(){
        if (top==null)
            System.out.println("empty");
        else {
            System.out.println("Top:" + top.data + " len is : " + len + " Stack is:");
            Node curr = top;
            while (curr.next != null) {
                System.out.print(curr.data + ",");
                curr=curr.next;
            }
            System.out.print(curr.data);
            System.out.println();
        }
    }
}
