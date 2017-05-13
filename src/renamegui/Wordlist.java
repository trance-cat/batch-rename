/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */
package renamegui;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Wordlist {
    private static List<String> words = new ArrayList<String>();
    
    public static List<String> getItems(){   
        return words;
    }
    
    public static boolean loadWordlist() {
        words.clear();
        if (wordlist_exists()) {
            String file_name = "wordlist.ini";
            try {
                Read wordlist = new Read(file_name);
                
                String[] prewords = wordlist.OpenFile();

                
                for(int j=0;j<prewords.length-2;j++){
                    words.add(prewords[j]);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            System.out.println("wordlist.iniの読み込みに成功");
            return true;
        } else{
            System.out.println("wordlist.iniの読み込みに失敗");
            return false;
        }          
    }
    
    public static boolean writeWordlist(ArrayList<String> input)  {
        words.clear();
        List<String> lines = new ArrayList<String>();
        lines.add("###最初の行は何も記載しないでください###");
        for(String a:input){
                lines.add(a);
        }
        lines.add("###最後の行は何も記載しないで下さい###");
                
        Path file = Paths.get("wordlist.ini");
        try{
        Files.write(file, lines, Charset.forName("UTF-8"));
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
            System.out.println("wordlist.iniの描き込みに成功");
            return true;       
    }

    public static boolean wordlist_exists() {
        String file_name = "./wordlist.ini";
        File wordlist = new File(file_name);
        return wordlist.exists();
    }
}
