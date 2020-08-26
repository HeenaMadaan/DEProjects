package javaPractice;

import java.io.InputStreamReader;
import java.util.Scanner;

public class testException {
    public static void main (String args[]){

        
        System.out.println(System.getProperty("test.b"));
        System.out.println(args[0]);

        //InputStreamReader isr = new InputStreamReader(System.in);
        /*Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            System.out.println(scanner.nextLine());
        }*/
        /*try {
            int data=100/0;
        }
        catch (ArithmeticException e){
            System.out.println(e);
        }
        System.out.println("Rest of the code");*/
    }
}
