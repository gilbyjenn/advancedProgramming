package lab2;

import java.util.Random;

public class Lab2 {
	public static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };

	public static String[] FULL_NAMES = 
		{ "alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};


	public static void main(String[] args){
		
		int correctResponses = 0;
		Random random = new Random();
		
		long startTime = System.currentTimeMillis();
		long endTime = startTime + 30000;
		 
		while(System.currentTimeMillis() < endTime){
			
			int n = random.nextInt(20);
			System.out.println("What is the abbreviated name for " + FULL_NAMES[n] + "?");
			String userAnswer = System.console().readLine().toUpperCase(); 

			if (userAnswer.equals(SHORT_NAMES[n])){
				
				System.out.println("Correct!");
				correctResponses++; }
				
			else{
				
				System.out.println("Incorrect");}
		}
		System.out.println("\nTime is up!\nFinal score: " + correctResponses);
	}

}


