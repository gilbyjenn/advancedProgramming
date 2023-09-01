package lab1;

import java.util.Random;

public class Lab1 {
	
	/* (1) Write code to print to the console 1,000 randomly generated 
		DNA 3 mers (e.g. “ACA”, “TCG” ) where the frequency of A,C,G and T 
		is 25% and is uniformly sampled. */ 

	public static void main(String[] args)
		{ 
		Random random = new Random();
		
		for( int x=0; x < 1000; ) // iterates 1000 times
			{ 
			String s = ""; 
			for (int y=0; y<3; y++)
				{
				int n = random.nextInt(4);
				if (n == 0)
					s += "A";
				else if (n == 1)
					s += "C";
				else if (n == 2)
					s += "G";
				else if (n == 3)
					s += "T";
				}
			System.out.println(s);
		
			x++; // increments x to reset the loop 
			}
			
		}
/* (2) Have your code track how often it prints out the 3 mer (“AAA”) 
How often would you expect to see this 3mer by chance?  Is Java’s number
close to the number that you would expect?
*/ 
	
	
}


// can just count how many of each 3mer there is rather than storing or printing all of them