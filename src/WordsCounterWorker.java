import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WordsCounterWorker implements Runnable {
    HashMap<String, Integer> wordsTable;
    String filename;

    public WordsCounterWorker(String filename, HashMap<String, Integer> wordsTable) {
        this.filename = filename;
        this.wordsTable = wordsTable;
    }

    public ArrayList<String> parseFile(String filename) {
        ArrayList<String> result = new ArrayList<>();
        File textFile = new File(filename);
        Scanner fileReader;
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try {
            fileReader = new Scanner(textFile);
            
            while (fileReader.hasNext()) {
                String word = (String) fileReader.next();
                if(wordsTable.containsKey(word)) {
                    wordsTable.merge(word, 1, Integer::sum);
                }
                else
                    wordsTable.put(word, 1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void run() {
        File textFile = new File(filename);
        try {
            Scanner fileReader = new Scanner(textFile);
            while (fileReader.hasNext()) {
                String word = (String) fileReader.next();
                synchronized(wordsTable) {
                    if (wordsTable.containsKey(word)) {
                            wordsTable.put(word, wordsTable.get(word) + 1);
                    }
                    else {
                        System.out.println("\t" + word + " value is " + wordsTable.get(word));
                        wordsTable.put(word, 1);
                    }
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String word : wordsTable.keySet()) {
            str.append(word + " ");
            str.append(wordsTable.get(word) + "\n");
        }
        return str.toString();
    }
}