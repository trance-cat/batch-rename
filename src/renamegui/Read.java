/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */

package renamegui;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class Read {
    private String path;
    public Read(String file_path){
        path=file_path;
    }
    
    public String[] OpenFile() throws IOException{
        //FileReader fr = new FileReader(path);
        BufferedReader textReader=new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
        int numOfLines=readLines();
        String[] textData = new String[numOfLines];
        for(int i=0; i<numOfLines; i++){
            textData[i]=textReader.readLine();
        }
        System.arraycopy(textData, 1, textData, 0, textData.length - 1);
        textReader.close();
        return textData;
    }
    
    int readLines() throws IOException{
        FileReader file_to_read = new FileReader(path);
        BufferedReader bf = new BufferedReader(file_to_read);
        
        String aLine;
        int numOfLines = 0;
        
        while((aLine=bf.readLine()) != null){
            numOfLines++;
        }
        bf.close();
        return numOfLines;
    }   
}
