/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */
package renamegui;

import java.io.File;
import javafx.scene.control.Button;

import javafx.scene.control.TextArea;
import javafx.scene.control.ProgressBar;

public class RenameLogic extends Thread {

    public String[] filenames;
    public String[] parentfilepath;
    public static int howmanyfiles;
    private String tempReplaceStr;
    private String matome="";
    
    TextArea consoleArea;
    Button doit = new Button();
    ProgressBar PB1;

    RenameLogic(ProgressBar PIIN, TextArea txtareain, Button btnin, String[] ParentFilePath, String[] FileNames, int HowManyFiles) {
        PB1=PIIN;
        consoleArea=txtareain;
        doit = btnin;
        parentfilepath=ParentFilePath;
        filenames=FileNames;
        howmanyfiles=HowManyFiles;
    }

    @Override
    public void run() {
            PB1.setVisible(true);
            Replace();
            PB1.setVisible(false);
    }

    public void outLogic(String out) {//テキストボックスにStringを表示する
        consoleArea.appendText(out + "\n");
        System.out.print(out + "\n");
        
        try{
        //Thread.sleep(50);
        }catch(Exception e){
                System.out.println(e.toString());
                consoleArea.appendText("エラー："+e.toString()+"\n");
        }
    }

    private void Replace() {
        int successful_times=0;
        consoleArea.appendText("\nファイルの名前を変更しています・・・\n");
        matome = "ファイルの名前を変更しています・・・\n\n";
        for (int i = 0; i < howmanyfiles; i++) {
            tempReplaceStr = filenames[i];
            File file = new File(parentfilepath[i] + "\\" + tempReplaceStr);
            for (String out : Wordlist.getItems()) {
                tempReplaceStr = tempReplaceStr.replace(out, "");
            }
            File file2 = new File(parentfilepath[i] + "\\" + tempReplaceStr);
            if (file2.exists()) {
                matome += ("失敗、名称重複: " + parentfilepath[i] + "\\" + tempReplaceStr + "\n");
            } else {
                boolean success = file.renameTo(file2);
                if (success) {
                    matome += ("名称変更: " + parentfilepath[i] + "\\" + tempReplaceStr + "\n");
                    successful_times++;
                } else {
                    matome += ("失敗: " + parentfilepath[i] + "\\" + tempReplaceStr + "\n");
                }
            }
        }
        matome += "\n\n完了："+howmanyfiles+"個の該当ファイルのうち、"+String.valueOf(successful_times)+"個のファイルの名前を変更しました。\n";
        consoleArea.setText(matome + "\n");
        howmanyfiles = 0;//リセット
    }
}
