
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class UniqueWordsThread {
    HashMap<String, Integer> wordsTable;
    String args[];

    public UniqueWordsThread(String args[]) {
        wordsTable = new HashMap<>();
        this.args = args;
    }

    public void countWords()  {
        List<Thread> threadsList = new ArrayList<>();

        for(int i = 0; i < args.length; i++) {
            WordsCounterWorker task = new WordsCounterWorker(args[i], wordsTable);
            Thread thread = new Thread(task);
            thread.start();
            threadsList.add(thread);
        }
        waitForThreads(threadsList);
    }

    private static void waitForThreads(List<Thread> threadsList) {
        for (Thread thread : threadsList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadsList.clear();
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
