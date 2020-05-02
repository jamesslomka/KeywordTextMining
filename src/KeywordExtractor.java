import java.util.*;
import java.io.*;

public class KeywordExtractor {

  /**
  *   This class does all logic of computing tf-idf scores.
  */
    public static void main(String[] args) {
        // This code allows you to have the correct output (for all 40 files), plus 1.txt,2.txt...etc headers when program runs
        /*
       for(int counter =1; counter<=40;counter++) {
            System.out.println(counter +".txt");
            HashMap<String, Double> j = computeTFIDF(computeTermFrequencies(counter+".txt"), readDocumentFrequencies("freqs.txt"), 40);
            printTopKeywords(j, 5);
            System.out.println("\n");
        }
        */
        for(int counter =1; counter<=40;counter++) {
            System.out.println(counter +".txt");
            HashMap<String, Double> j = computeTFIDF(computeTermFrequencies("../docs/"+counter+".txt"), readDocumentFrequencies("../output/frequency.txt"), 40);
            printTopKeywords(j, 5);
            System.out.print("\n");
        }
    }

    public static HashMap<String, Integer> computeTermFrequencies(String filename) {
        HashMap<String,Integer> words = new HashMap<String,Integer>();

        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);printTopKeywords
            String line = br.readLine();
            while(line != null){
                line = normalize(line);

                String [] temp = line.split(" ");

                for(int counter = 0; counter < tecomputeTermFrequenciesmp.length; counter++){
                    temp[counter] = temp[counter].trim();

                    if(words.containsKey(temp[counter])){
                        int val = words.get(temp[counter]);
                        val++;
                        words.put(temp[counter], val);
                    }
                    else if(temp[counter] != null){

                        words.put(temp[counter], 1);
                    }
                }
                line = br.readLine();
            }
        }computeTermFrequencies
        catch(IOException e){
            System.out.println("IO ERROR! Reading File");
        }
        return words;
    }

    public static HashMap<String, Integer> readDocumentFrequencies(String filename) {
        HashMap<String,Integer> wordFreq = new HashMap<String,Integer>();

        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while(line != null){
                String tempWords = line.replaceAll("[^a-zA-Z ']","").trim();
                int tempNum = Integer.parseInt(line.replaceAll("[^0-9]","").trim());
                wordFreq.put(tempWords,tempNum);

                line = br.readLine();
            }
            br.close();
            fr.close();

        }
        catch(IOException e ){
            System.out.println("*** IO error! Reading ***");
        }

        return wordFreq;
    }

    public static HashMap<String, Double> computeTFIDF(HashMap<String, Integer> tfs, HashMap<String, Integer> dfs, double nDocs){
        // TF-IDF score: (w, d) = tf(w, d) / idf(w)
        HashMap<String,Double> score = new HashMap<String,Double>();

        int[] tfval = new int[tfs.size()];

        int counter =0;
        for(String key: tfs.keySet()){computeTermFrequencies
           int val = tfs.get(key);
            tfval[counter] = val;
            counter ++;
        }
        String[] tfkey = new String[tfs.size()];

        int count =0;
        for(String key: tfs.keySet()){// Method that returns a HashSet of all the unique words in a document
            tfkey[count] = key;
            count ++;
        }

        // Now we have tfkey which holds the terms, and tfval which holds their corresponding times
        // that it is found in a given document
        double[] IDF = new double[tfs.size()];

        for(int c =0; c < tfs.size(); c++){
            if(dfs.get(tfkey[c]) != null){
                IDF[c] = Math.log(nDocs/dfs.get(tfkey[c]));
            }
        }

        for(int j =0; j < IDF.length;j++) {
            double temp = tfval[j] * IDF[j];
            score.put(tfkey[j],temp);
        }

        return score;
    }

    /**
     * This method prints the top K keywords by TF-IDF in descending order.
     */
    public static void printTopKeywords(HashMap<String, Double> tfidfs, int k) {
        ValueComparator vc =  new ValueComparator(tfidfs);
        TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(vc);
        sortedMap.putAll(tfidfs);

        int i = 0;
        for(Map.Entry<String, Double> entry: sortedMap.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();

            System.out.println(key + " " + value);
            i++;
            if (i >= k) {
                break;
            }
        }
    }
    public static String normalize(String word) {
        return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
    }

}

/*
 * This class makes printTopKeywords work. Do not modify.
 */
class ValueComparator implements Comparator<String> {

    Map<String, Double> map;

    public ValueComparator(Map<String, Double> base) {
        this.map = base;
    }

    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
