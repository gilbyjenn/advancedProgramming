package FinalProject;

import java.io.IOException;
import java.util.List;

import FinalProject.gffParser.gffEntry;



public class CLI {


	public static void main(String[] args) 
	{
		int argIndex = 0;
		String filepath = null;
		String geneFile = null;


		if ( args.length == 0) {
			System.out.println("no arguments provided.");
		}
		
		while (argIndex < args.length){
			
			String arg = args[argIndex++];
			
			if(arg.startsWith("-")){	
				
				if (arg.equals("-g")) {
					filepath = args[argIndex++];
					System.out.println("gff file path:" + filepath);
				}

				
				else if (arg.equals("-i")) {
					geneFile = args[argIndex];
					System.out.println("gene name file path:" + geneFile);
				}
			}
		}
		
		
    	try {
            List<gffEntry> entries = gffParser.parseGFFFile(filepath, geneFile);

            for (gffEntry entry : entries) {
                System.out.println(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
	
	}
}