package lab1;

import java.util.Random;

public class Lab1 {

	public static void main(String[] args)
		{ 
		Random random = new Random();
		
		int aaa_frequency = 0;
		
		for( int x=0; x < 1000; ) 
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
				
				if (s.equals("AAA"))
					aaa_frequency++;
	
			x++; 
			}
		System.out.println("3mer AAA was generated " + aaa_frequency + " times with equal probabilities.");
		
		// Modify code to adjust frequency of A,C,G and T:  
		
		int aaa_modified = 0;
		
		for( int x=0; x < 1000; ) 
			{ 
			String s = "";
			for (int y=0; y<3; y++)
				
				{
				int n = random.nextInt(101);
				
				if (n>= 0 && n <= 11)
					s += "A";
				else if (n>= 12 && n <= 49)
					s += "C";
				else if (n>= 50 && n <= 89)
					s += "G";
				else if (n>= 90 && n <= 100)
					s += "T";
				}
				
				if (s.equals("AAA"))
					aaa_modified++;
		
			x++; 
			}
		System.out.println("3mer AAA was generated " + aaa_modified + " times after probabilities were adjusted.");	
		}
	
}


/* With equal probabilities we expect AAA to occur at a frequency of 0.0156, about 10 in every 1000 times. 
 * With the adjusted probabilities, we expect AAA to occur at a frequency of 0.0017, about 1.7 in every 1000 times. 
 * When the code is executed, we are seeing what we would expect in the resulting AAA counts */


