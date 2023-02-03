package com.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        try {
			IAMRunner.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
