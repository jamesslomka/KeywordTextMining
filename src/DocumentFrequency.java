import java.util.*;
import java.io.*;
import java.util.HashSet;

/**
*  This class is to be run first. Does all preprocessing.
*  Extracts and writes key,value pairs where k: unique word, v: frequency
*
*/
public class DocumentFrequency {

    public static void main(String[] args) {
        String dir = "../docs";
        HashMap<String, Integer> dfs;
        dfs = extractDocumentFrequencies(dir, 40);
        writeDocumentFrequencies(dfs,"../output/frequency.txt");
    }

    /**
    *
    * @param String directory: docs folder that has the .txt files of all documents to iterate
    * @param int nDocts: number of documents in folder
    * @return HashMap<String,Integer> key,val pair of words with their overall frequency
    *
    */
    public static HashMap<String, Integer> extractDocumentFrequencies(String directory, int nDocs) {
        HashMap<String,Integer> wordFreq = new HashMap<String,Integer>();
        HashSet<String> allWords = new HashSet<String>();

        String dir = directory;
        for(int counter = 1; counter<= nDocs; counter++){

            allWords = extractWordsFromDocument(dir +"/" + counter + ".txt");

            String words[] = allWords.toString().replaceAll("[^a-zA-Z ']", "").toLowerCase().trim().split(" ");

            // Adding all words from the first file to the HashMap
            if(counter == 1) {
                for(int c = 0; c < allWords.size();c++){
                    wordFreq.put(words[c],1);
                }
            }
            else {
                for(int c = 0; c < allWords.size();c++){
                    // If a pre-existing word is found, increment the value
                    if(wordFreq.containsKey(words[c])) {
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

    public static HashSet<String> extractWordsFromDocument(String filename) {

        HashSet<String> words = new HashSet<String>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null){

                line = normalize(line);
                String [] temp = line.split(" ");

                for(int counter = 0; counter <temp.length; counter ++){
                    temp[counter] = temp[counter].trim();
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
        } catch(IOException e){
            System.out.println("*** IO Errror - Reading File ***");
        }
        return words;
    }

    public static void writeDocumentFrequencies(HashMap<String, Integer> dfs, String filename) {

        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            String[] temp = dfs.toString().replaceAll("[^a-zA-Z0-9,'=]","").toLowerCase().split(",");
            Arrays.sort(temp);
            for(int counter = 0;counter<temp.length;counter++) {
                bw.write(temp[counter].replaceAll("=", " ") + "\n");
            }

            String words[] = dfs.toString().replaceAll("[^a-zA-Z=']", "").toLowerCase().trim().split("=");
            String numbers[] = dfs.toString().replaceAll("[^0-9,]", "").toLowerCase().trim().split(",");

            // Code below is formatted to columns
            // for(int counter = 0; counter < words.length; counter++){
            //     bw.write(String.format("%-30s %-10s%n",words[counter] , numbers[counter] ));
            // }
            bw.close();
            fw.close();
        } catch (IOException e ) {
            System.out.println("*** IO error! Writing File ***");
        }
    }

    public static String normalize(String word) {
        return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
    }
}
