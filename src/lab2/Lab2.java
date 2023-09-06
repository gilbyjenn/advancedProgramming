package lab2;

import java.util.Random;

public class Lab2 
{
	public static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };

	public static String[] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};


	public static void main(String[] args)
	{
		// initialize reponse counts 
		int correct_responses = 0;
		// int false_responses = 0; 
		
		Random random = new Random(); // does this have to be in the method or can it be up where i call the library? 
		
		// loop: for now it just iterates 10 times, fix later to be concurrent with timer 
		for( int x=0; x < 10; x++) 
		{
			int n = random.nextInt(19); // choose random integer 0-19 to index FULL_NAMES array thus choosing an AA to ask
			System.out.println("What is the abbreviated name for " + FULL_NAMES[n] + " ?");
			String user_answer = System.console().readLine().toUpperCase(); 

			if (user_answer.equals(SHORT_NAMES[n]))
			{
				System.out.println("Correct!");
				correct_responses++; 
			}
				
			else
			{
				System.out.println("Incorrect");
			}
		}
		
		System.out.println("Final score: " + correct_responses);
		
	}


	}	



