// James Slomka
// 260626090
// I worked alone
import java.util.*;
import java.io.*;
import java.util.HashSet;

public class DocumentFrequency {

    public static void main(String[] args) {
        String dir = args[0]; // name of directory with input files
        System.out.println(dir);
        HashMap<String, Integer> dfs;
        dfs = extractDocumentFrequencies(dir, 40);
        writeDocumentFrequencies(dfs,"freqs.txt");
    }

    public static HashMap<String, Integer> extractDocumentFrequencies(String directory, int nDocs) {
        // Creating a new HashMap to store the (word, frequency)
        HashMap<String,Integer> wordFreq = new HashMap<String,Integer>();

        HashSet<String> allWords = new HashSet<String>();

        String dir = directory;
        for(int counter = 1; counter<= nDocs; counter++){
            // Populating 'allWords' with the unique words found the directory (which increases by 1 each loop)

            allWords = extractWordsFromDocument(dir +"/" + counter + ".txt");

            // Creating array list of all the unique words found in the file
            String words[] = allWords.toString().replaceAll("[^a-zA-Z ']", "").toLowerCase().trim().split(" ");

            // Adding all words from the first file to the HashMap
            if(counter == 1) {
                for(int c = 0; c < allWords.size();c++){
                    wordFreq.put(words[c],1);
                }
            }
            else{
                for(int c = 0; c < allWords.size();c++){
                    // If a pre-existing word is found, increment the value
                    if(wordFreq.containsKey(words[c])){
                        int val = wordFreq.get(words[c]);
                        val++;
                        wordFreq.put(words[c],val);
                    }
                    // If word is not already in HashMap, add it
                    else{
                        wordFreq.put(words[c],1);
                    }
                }
            }
        }
        return wordFreq;
    }
    // Method that returns a HashSet of all the unique words in a document
    public static HashSet<String> extractWordsFromDocument(String filename) {
        // Declaring new HashSet
        HashSet<String> words = new HashSet<String>();

        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null){

                line = normalize(line);

                // Splitting the words up after a space
                String [] temp = line.split(" ");

                for(int counter = 0; counter <temp.length; counter ++){
                    // Trimming the leading and following whitespace
                    temp[counter] = temp[counter].trim();
                    // Omitting any blank entries and adding correct entry to HashSet
                    if(temp[counter].equals("") || temp[counter].equals(" ")  || temp[counter].equals("'")){
                        continue;
                    }
                    else {
                        //System.out.println(temp[counter]);
                        words.add(temp[counter]);
                    }
                }
                line = br.readLine();
            }
            br.close();
            fr.close();
        }
        catch(IOException e){
            System.out.println("*** IO error! [While reading] ***");
        }
        return words;
    }
    
    public static void writeDocumentFrequencies(HashMap<String, Integer> dfs, String filename) {

        try{
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            String[] temp = dfs.toString().replaceAll("[^a-zA-Z0-9,'=]","").toLowerCase().split(",");
            Arrays.sort(temp);
            for(int counter = 0;counter<temp.length;counter++) {
                bw.write(temp[counter].replaceAll("=", " ") + "\n");
                //System.out.println(temp[counter].replaceAll("=", " "));
            }

            String words[] = dfs.toString().replaceAll("[^a-zA-Z=']", "").toLowerCase().trim().split("=");
            String numbers[] = dfs.toString().replaceAll("[^0-9,]", "").toLowerCase().trim().split(",");


            for(int counter = 0; counter < words.length; counter++){
                // Working code:
                // bw.write(words[counter] + " " + numbers[counter] + "\n");

                // Code below is formatted to columns
                // bw.write(String.format("%-30s %-10s%n",words[counter] , numbers[counter] ));
            }
            bw.close();
            fw.close();
        }
        catch (IOException e ) {
            System.out.println("*** IO error! [While writing] ***");
        }
    }

    // Method to convert text to lowercase, as well as remove extra whitespace and punctuation
    public static String normalize(String word) {
        return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
    }
}
