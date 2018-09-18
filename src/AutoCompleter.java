
import java.util.*;

public class AutoCompleter implements Dictionary {

    private TrieNode root;
    private int size;
    private HashMap<String, Integer> confTracker;

    //Instantiate AutoCompleter Constructor Class
    public AutoCompleter() {
        root = new TrieNode();
        confTracker = new HashMap<String, Integer>();

    }

    /**
     * Method: addWord
     * Desc: Insert a word into the trie and increment confidence
     * based on the number of times the word is seen.
     */
    public boolean addWord(String word) {
        //Check to see if the String is actually a word
        if (word.length() == 0 || word == null) {
            return false;
        }

        //Ignore word capital-case
        String tmpWord = word.toLowerCase();
        TrieNode curr = root;

        /* Add confidence value to the confTracker bank */
        if (confTracker.containsKey(tmpWord)) {
            int value = confTracker.get(tmpWord);
            confTracker.replace(tmpWord, value + 1);
        } else {
            confTracker.put(tmpWord, 1);
        }

        //Traverse through the Trie structure to add the word in appropriately
        for (Character c : tmpWord.toCharArray()) {
            if (curr.getValidNextCharacters().contains(c)) {
                curr = curr.getChild(c);
            } else {
                curr = curr.insert(c);
            }
        }

        //Check to see if the TrieNode is the end of the word and set boolean
        if (curr.endsWord()) {
            return false;
        } else {
            curr.setEndsWord(true);
            curr.setText(tmpWord);
            size++;

        }
        return true;
    }

    /*
     * Method: buildKnowledge
     * Desc: Take in a String of words and add this to the Dictionary of
     * words to build confidence in the AutoComplete functionality.
     */
    public void buildKnowledge(String passage) {

        //Split the passage based on spaces
        String[] words = passage.split(" ");
        String word = "";
        for (int i = 0; i < words.length; i++) {
            word = words[i].toLowerCase();
            addWord(word);
        }
    }

    /*
     * Method: predictCompletions
     * Desc: Given a String prefix, return a list of all the words
     * that could be used to AutoComplete the prefix with the current
     * knowledge base that exists in the dictionary. The returned list
     * of words will be sorted based on the confidence value.
     */
    public List<Word> predictCompletions(String prefix) {
        Queue<TrieNode> q = new LinkedList<TrieNode>();
        List<Word> completions = new LinkedList<Word>();
        int confidence = 0;
        Word w;
        TrieNode node;
        TrieNode curr;

        //Initial validation check of the prefix
        if (!isWord(prefix)) {
            return completions;
        } else {
            curr = root;
            //Traverse through the characters in the prefix through the Trie
            for (Character c : prefix.toCharArray()) {
                curr = curr.getChild(c);
            }
            q.add(curr);

            while ((!q.isEmpty())) {
                node = q.remove();
                if (node != null) {
                    //If the node represents a full word
                    if (node.endsWord()) {
                        confidence = 0;
                        //Set the value of the confidence based on the Hashmap
                        if (confTracker.containsKey(node.getText())) {
                            confidence = confTracker.get(node.getText());
                        }
                        w = new Word(node.getText(), confidence);

                        //Add the full word to the List of AutoComplete predictions
                        completions.add(w);
                    }

                    //Find the next set of potential next characters
                    Set<Character> keySet = node.getValidNextCharacters();

                    for (Character c : keySet) {
                        q.add(node.getChild(c));
                    }
                }

            }

        }

        return completions;
    }

    /*
     * Method: givePrediction
     * Desc: Use the predictCompletions method to print out a list of
     * potential AutoComplete words ordered based on confidence level.
     */
    public String givePrediction(String prefix) {
        String result = "";
        List<Word> res = predictCompletions(prefix);
        Word w;

        //The sort method compares the value of the confidence int to determine order
        Collections.sort(res);
        result += "\"" + prefix + "\"" + " --> ";
        for (int i = 0; i < res.size(); i++) {
            w = res.get(i);
            result += " ";
            result += w.toString();
            if (i != res.size() - 1) {
                result += ",";
            }
        }

        return result;
    }


    /*
     * Method: isWord
     * Desc: Determines if the string represents a word and returns boolean
     */
    public boolean isWord(String s) {

        char fchar = 0;
        if (s.length() > 0)
            fchar = s.charAt(0);
        if (s.length() == 0 || s == null || (fchar > 65 && fchar < 90))
            return false;


        String wordLower = s.toLowerCase();
        TrieNode curr = root;

        for (Character c : wordLower.toCharArray()) {
            if (curr.getValidNextCharacters().contains(c)) {
                curr = curr.getChild(c);
            } else {
                return false;
            }

        }
        return true;

    }

    /*
     * Method: size
     * Desc: returns the current size for the word bank of the AutoComplete dictionary
     */
    public int size() {
        return size;
    }
}
