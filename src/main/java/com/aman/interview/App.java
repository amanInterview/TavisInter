package com.aman.interview;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
       // System.out.println(Breverage.placeOrder("Chai").validateOrder().getPrice());
       
        // String order1 = "chai,-milk,-water,-water";
        // String order1 = "chai,-milk,-water";
        // String order1 = "chai";
        // String order1 = "chai,-milk,-water,-sugar";
         String order1 = "chai,-milk,-water,-sugar";
        
        System.out.println(Breverage.placeOrder(order1).validateOrder().getPrice());

    }
}
