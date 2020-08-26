package com.ds.linklist1;

public class callLLasStack {
    public static void main (String[] args){
        LLasStack l=new LLasStack();
        l.print();
        Node val=l.pop();
        if(val==null)
            System.out.println("empty stack");
        else
            System.out.println("item deleted"+val);
        l.print();
        l.push(19);
        l.print();
        l.push(20);
        l.print();
        l.push(22);
        l.print();
        l.push(39);
        l.print();
        val=l.pop();
        if(val==null)
            System.out.println("empty stack");
        else
            System.out.println("item deleted"+val);
        l.print();
        l.push(49);
        l.print();
        val=l.pop();
        if(val==null)
            System.out.println("empty stack");
        else
            System.out.println("item deleted"+val);
        l.print();
        val=l.pop();
        if(val==null)
            System.out.println("empty stack");
        else
            System.out.println("item deleted"+val);
        l.print();
        val=l.pop();

        if(val==null)
            System.out.println("empty stack");
        else
            System.out.println("item deleted"+val);
        l.print();
        val=l.pop();
        if(val==null)
            System.out.println("empty stack");
        else
            System.out.println("item deleted"+val);
        l.print();
        val=l.pop();
        if(val==null)
            System.out.println("empty stack");
        else
            System.out.println("item deleted"+val);
        l.print();
    }
}
