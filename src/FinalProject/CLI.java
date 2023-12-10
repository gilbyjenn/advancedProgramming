package FinalProject;

//import java.util.ArrayList;
//import java.util.List;


//import FinalProject.gffParser.gffEntry;


public class CLI {


	public static void main(String[] args) 
	{
		int argIndex = 0;
		//List<String> parameters = new ArrayList<>();
			// see trimmomatic trimmomatic.java and trimmomaticPE.java files (main and run respectively) for ideas on structures and calls 

		if ( args.length == 0) {
			System.out.println("no arguments provided.");
		}
		while (argIndex < args.length)
			{
			String arg = args[argIndex++];
			System.out.println(arg);
			
			if(arg.startsWith("-"))
				{			
				if (arg.equals("-g")) {
					String filepath = args[argIndex++];
					System.out.println("file path:" + filepath);
				}
				
				else if (arg.equals("-f")) {
					// fasta file 
				}
				
				else if (arg.equals("-i")) {
					// tsv file listing gene names to query 
				}
		
				}
			// other optional parameters: 
			}
	
	}
}