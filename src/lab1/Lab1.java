package lab1;

import java.util.Random;

public class Lab1 {
	

	public static void main(String[] args)
		{ 
		Random random = new Random();
		
		int aaa = 0;
		
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
					aaa++;
			//System.out.println(s);
		
			x++; // increments x to reset the loop 
			}
		System.out.println("3mer AAA was generated " + aaa + " times.");
			
		}

	
}


// can just count how many of each 3mer there is rather than storing or printing all of them