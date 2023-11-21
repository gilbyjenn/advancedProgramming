package FinalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class gffParser {

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

    // PARSER --> returns list of gffEntry objects (name, start, stop pos) 
    public static List<gffEntry> parseGFFFile(String filePath) throws IOException {
        List<gffEntry> entries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                
            	// Skip comment lines
                if (line.startsWith("#")) {
                    continue;
                }

                String[] fields = line.split("\t");
                if (fields.length >= 9 && fields[2].equalsIgnoreCase("gene")) {
                    String geneName = extractGeneName(fields[8]);
                    int start = Integer.parseInt(fields[3]);
                    int stop = Integer.parseInt(fields[4]);

                    gffEntry entry = new gffEntry(geneName, start, stop);
                    entries.add(entry);
                }
            }
        }

        return entries;
    }
    
    // helper method to pull just the gene name from full feature in gff file 
    // may need to add to this if there is a lot of variation amongst gff files, but this works for my test files 
    private static String extractGeneName(String attributes) {
        
    	String[] attributePairs = attributes.split(":");  
        for (String pair : attributePairs) {
            String[] keyValue = pair.trim().split("=");  
            if (keyValue.length == 2 && keyValue[1].equalsIgnoreCase("gene")) {
//                System.out.println(attributePairs[1].split(";")[0]);
            	return attributePairs[1].split(";")[0];
            }
        }
        return "UnknownGeneName";
    }
    

    public static void main(String[] args) {
        
    	try {
            String filePath = "/Users/jennifergilby/cooperLab/javaTest/sorghumv5.gff";
            List<gffEntry> entries = parseGFFFile(filePath);

            for (gffEntry entry : entries) {
                System.out.println(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
