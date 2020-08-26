package com.coursera;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        List<Integer> list0 = Arrays.asList(1,2,3);
        List<Integer> list1 = Arrays.asList(5,7,8);
        Map<Integer,List<Integer>> map = null;
        List<List<Integer>> output = new ArrayList<>(map.values());
        Arrays.asList(map.entrySet().stream().sorted().map(Map.Entry::getValue).toArray());


        System.out.println( "Hello World!" );
    }
}
