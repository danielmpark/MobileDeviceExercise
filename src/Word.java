public class Word implements Comparable<Word> {
    private String word;
    private int conf;

    //Instantiate the Word Constructor Class
    public Word(){
        word="";
        conf=0;
    }

    //Word Object Helper Methods
    public Word(String word, int conf){
        this.word = word;
        this.conf=conf;
    }
    public String getWord(){
        return word;
    }
    public int getConf(){
        return conf;
    }
    public void setWord(String s){
        word=s;
    }
    public void addConf(){
        conf++;
    }
    public String toString(){
        String str="";
        str+= "\"" + word + "\"" + " ("+conf+")";
        return str;
    }

    //Use the Comparable Interface
    //Word value is determined based on the confidence value (order is highest to lowest)
    public int compareTo(Word w){
        Integer original = conf;
        Integer comparing = w.getConf();
        return original.compareTo(comparing) * -1;
    }
}
