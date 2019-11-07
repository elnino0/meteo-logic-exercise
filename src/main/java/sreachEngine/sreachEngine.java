package sreachEngine;

import java.util.*;

public class sreachEngine {

    private  static sreachEngine instance;

    public static sreachEngine getInstance() {

        if(instance == null){
            List<String> vocabulary = new ArrayList<>();
            vocabulary.add("pneumonoultramicroscopicsilicovolcanoconiosis");
            vocabulary.add("pseudopseudohypoparathyroidism ");
            vocabulary.add("bird");
            vocabulary.add("honorificabilitudinitatibus ");
            vocabulary.add("thyroparathyroidectomized ");
            vocabulary.add("antiestablishmentarian ");
            vocabulary.add("abc");
            vocabulary.add("abcd");
            vocabulary.add("abcdefg");
            instance = new sreachEngine(vocabulary);
        }

        return instance;
    }

    Map<Character, TreeSet<String>> dic;

    private sreachEngine(List<String> vocabulary) {
        dic = new HashMap<>();
        addToDictionary(vocabulary);


    }

    private  void addToDictionary(List<String> vocabulary){
        for (String str : vocabulary){

            if(str.isEmpty()){
                continue;
            }

            if(dic.containsKey(str.charAt(0)) ){
                dic.get(str.charAt(0)).add(str);
            }else{
                dic.put(str.charAt(0),new TreeSet<>());
                dic.get(str.charAt(0)).add(str);
            }
        }
    }

    public  List<String> findClosestWords(String word, int len){

        List<String> match = new ArrayList<>();
        float dis = 0;

        if(!dic.containsKey(word.charAt(0))){
           return match;
        }

        TreeSet<String> vocabulary = dic.get(word.charAt(0));

        for(String str : vocabulary){
            dis = calculate(word, str);


            if(dis/str.length() <=  0.75){
                match.add(str);
                if(match.size() >= len){
                    break;
                }
            }
        }

        return match;
    }

    public List<String> findSuggentendsOrAdd(String word, int len){
        List<String> suggentends = findClosestWords(word,len);
        if(suggentends.size() == 0 ){
            if(dic.containsKey(word.charAt(0)) ){
                dic.get(word.charAt(0)).add(word);
            }else{
                dic.put(word.charAt(0),new TreeSet<>());
                dic.get(word.charAt(0)).add(word);
            }
        }
        return suggentends;
    }

    static int calculate(String x, String y) {

        if (x.isEmpty()) {
            return y.length();
        }

        if (y.isEmpty()) {
            return x.length();
        }

        int substitution = calculate(x.substring(1), y.substring(1))
                + costOfSubstitution(x.charAt(0), y.charAt(0));
       // int insertion = calculate(x, y.substring(1)) + 1;
      //  int deletion = calculate(x.substring(1), y) + 1;

       // return min(substitution, insertion, deletion);
        return substitution;
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
}
