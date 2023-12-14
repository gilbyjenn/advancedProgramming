package FinalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gffParser {
	public static String filepath;

	public static class gffEntry {
        
    	private String geneName;
        private int startPosition;
        private int stopPosition;

        public gffEntry(String geneName, int startPosition, int stopPosition) {
            this.geneName = geneName;
            this.startPosition = startPosition;
            this.stopPosition = stopPosition;
        }

        public String getGeneName() {
            return geneName;
        }

        public int getStartPosition() {
            return startPosition;
        }

        public int getStopPosition() {
            return stopPosition;
        }

        @Override
        public String toString() {
            return "Gene: " + geneName + ", Start: " + startPosition + ", Stop: " + stopPosition;
        }
    }

    public static List<gffEntry> parseGFFFile(String gffFile, String geneNameFile) throws IOException {
        
    	List<gffEntry> entries = new ArrayList<>();
    	List<String> geneNames = parseGeneNames(geneNameFile);

        try (BufferedReader br = new BufferedReader(new FileReader(gffFile))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                
                if (line.startsWith("#")) {
                    continue;
                }

                String[] fields = line.split("\t");
                
                if (fields.length >= 9 && fields[2].equalsIgnoreCase("gene")) {
                    String geneName = extractGeneName(fields[8]);
                    int start = Integer.parseInt(fields[3]);
                    int stop = Integer.parseInt(fields[4]);
                    
                    for (String gene : geneNames) {
                    	
                    	if (gene.equalsIgnoreCase(geneName)) {
                        	System.out.println("hit");
                       	 	gffEntry entry = new gffEntry(geneName, start, stop);
                            entries.add(entry);	
                    	}
                    }
                }
            }
        }
        return entries;
    }
    
    
    public static List<String> parseGeneNames(String inFile) throws IOException {
    	
    	List<String> geneNames = new ArrayList<>();
    	
    	try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
            
    		String line;
            
            while ((line = br.readLine()) != null) {
            	String[] gene = line.split(" ");
            	
            	for (String g : gene) {
            		geneNames.add(g);
            	}
            }
    	}
    	return geneNames;
    }
    

    private static String extractGeneName(String attributes) {
        
    	String[] attributePairs = attributes.split(":");  
        
    	for (String pair : attributePairs) {
            String[] keyValue = pair.trim().split("=");  
            
            if (keyValue.length == 2 && keyValue[1].equalsIgnoreCase("gene")) {
            	String val = attributePairs[1].split(";")[0];
            	String gene = val.split(".v")[0];
            	System.out.println(gene);
            	
            	return gene;
            }
        }
        return "UnknownGeneName";
    }
}
