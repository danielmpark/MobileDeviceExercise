public class Driver {
    public static void main(String[] args){

        AutoCompleter ac=new AutoCompleter();

        ac.buildKnowledge("The third thing that I need to tell you is that this thing does not think thoroughly.");
        System.out.println(ac.givePrediction("thi"));
        System.out.println(ac.givePrediction("nee"));
        System.out.println(ac.givePrediction("th"));
    }
}
