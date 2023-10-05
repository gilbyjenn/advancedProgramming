package lab3; 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List; 
import java.io.BufferedWriter;
import java.io.FileWriter;


public class FastaSequence 
{ 
	private String header; 
	private String sequence; 
	
	public FastaSequence(String header, String sequence) 
	{
        this.header = header;
        this.sequence = sequence; 
	}
	
	// returns the header of this sequence without the “>”
	public String getHeader() 
	{
        return header.substring(1); 
	}

	// returns the DNA sequence of this FastaSequence (as a string) 
	public String getSequence() 
	{ 
		return sequence;
	}
		
	// returns the number of G’s and C’s divided by the length of this sequence
	public float getGCRatio() 
	{
        float gc = 0;
        float len = sequence.length();
        for (int i = 0; i < sequence.length(); i++) 
        {
            char nt = sequence.charAt(i);
            if (nt == 'G' || nt == 'C') 
            {
                gc++;
            }
        } 
        return (gc/len);
	}
	
	// returns a count of provided nucleotide 
	public int getNucCount(String sequence, char nuc) 
	{
		 int count = 0;
	     for (char c : sequence.toCharArray()) 
	     {
	    	 if (c == nuc) 
	    	 {
	    		 count++;
	         }
	      }
	        return count;
	}
	
	
	// PUBLIC STATIC FACTORY METHOD PARSER, returns a list of fastaSeq objects
	public static List<FastaSequence> readFastaFile(File inFile) throws Exception 
	{		
	       
		try(BufferedReader reader = new BufferedReader(new FileReader(inFile)))
		{ 
			List<FastaSequence> fastaList = new ArrayList<FastaSequence>();
			String line;
	        String header = null;
	        StringBuilder sequence = new StringBuilder();  

	        while ((line = reader.readLine()) != null) 
			{
	        	
	        	if (line.strip().startsWith(">")) 
	        	{	
	        		
	        		if(header != null) 
	        		{ 
	    	        	fastaList.add(new FastaSequence(header, sequence.toString()));
	    	        	sequence.setLength(0);
	        		}
	        		header = line.strip(); 	                    
	        	}
	        	
	        	else 
	        	{ 
	        		sequence = sequence.append(line.strip());
	        	}
	        		
	        }
	        
	        fastaList.add(new FastaSequence(header, sequence.toString()));	// add last header,seq	
	        reader.close();
	        return fastaList;
	   }
	}
	


	// OUTPUT FILE GENERATOR
	public static void writeTableSummary( List<FastaSequence> fastaList, File myFile) throws Exception 
	{	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(myFile))) 
		{
            writer.write("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\n");

            for (FastaSequence fs : fastaList) 
            { 
                String sequenceID = fs.getHeader();
                String sequence = fs.getSequence();
                int countA = fs.getNucCount(sequence, 'A'); 
                int countC = fs.getNucCount(sequence, 'C');
                int countG = fs.getNucCount(sequence, 'G');
                int countT = fs.getNucCount(sequence, 'T');
                
                writer.write(sequenceID + "\t" + countA + "\t" + countC + "\t" + countG + "\t" + countT + "\t" + sequence + "\n");
            }
            writer.flush(); writer.close();
		}
	}


// MAIN METHOD	
public static void main(String[] args) throws Exception 
{
    	File inFile = new File("/Users/jennifergilby/cooperLab/Sorghum_bicolorv5.Sb-BTX623-REFERENCE-JGI-5.1.dna.toplevel.fa");

		List<FastaSequence> fastaList = FastaSequence.readFastaFile(inFile); 

	    	for( FastaSequence fs : fastaList)
	     {
	         System.out.println(fs.getHeader());
	         //System.out.println(fs.getSequence());
	         System.out.println(fs.getGCRatio()); 
	     }

	     File myFile = new File("/Users/jennifergilby/cooperLab/out.txt");

	     writeTableSummary(fastaList, myFile);
	     System.out.println("File written. Program done.");
	}
}

