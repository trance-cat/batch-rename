/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */
package renamegui;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ProgressBar;

public class PreRenameLogic extends Thread {

    public static String[] filenames;
    public static String[] parentfilepath;
    public static int howmanyfiles = 0;
    private int CountF = 0;
    private final int MAX_FILES = 10000;//該当ファイル数の上限ではなく、読み取るファイル数の上限
    private final boolean SET_MAX = false;
    private String matome;
    private String errorMatome;

    TextArea consoleTextArea;
    TextArea errorConsoleTextArea;
    Button doit;
    private String FILEPATH;
    private boolean INCLUDE_SUBDIR;
    ProgressBar progressBar;
    Button showFiles;

    PreRenameLogic(ProgressBar pb, TextArea ta, TextArea eta, Button btn, Button shw, String path, boolean include_subdirectory) {
        progressBar = pb;
        consoleTextArea = ta;
        errorConsoleTextArea = eta;
        doit = btn;
        showFiles = shw;
        FILEPATH = path;
        INCLUDE_SUBDIR = include_subdirectory;
    }

    @Override
    public void run() {
        doit.setDisable(true);
        showFiles.setDisable(true);
        progressBar.setVisible(true);

        RegisterFiles(FILEPATH, INCLUDE_SUBDIR);
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(PreRenameLogic.class.getName()).log(Level.SEVERE, null, ex);
        }

        doit.setDisable(false);
        showFiles.setDisable(false);
        progressBar.setVisible(false);
    }

    private void errorAppend(String error) {
        errorMatome += error;
    }

    /*
     private void outLogic(String out) {//テキストボックスにStringを表示する

     try {
     consoleTextArea.appendText(out + "\n");
     System.out.print(out + "\n");
     } catch (java.lang.NullPointerException ex) {
     System.out.println(ex.toString());
     errorAppend("エラー：" + ex.toString() + "\n");
     } catch (Exception e) {
     System.out.println(e.toString());
     errorAppend("エラー：" + e.toString() + "\n");
     }
     }*/
    private void RegisterFiles(String path, boolean include_subdirectory) {
        if (Wordlist.wordlist_exists()) {
            Wordlist.loadWordlist();
            matome = "";
            errorMatome = "";
            howmanyfiles = 0;//リセット
            CountFiles(path, MAX_FILES, SET_MAX, include_subdirectory);

            //outLogic("該当ファイル数：" + String.valueOf(howmanyfiles));
            //filenameからのみ除去ワードを削除したいからfilenamesとfilepathに分ける
            CountF = 0;
            filenames = new String[howmanyfiles];
            parentfilepath = new String[howmanyfiles];
            RegFiles(path, MAX_FILES, SET_MAX, include_subdirectory);

            if (howmanyfiles > 0) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        errorConsoleTextArea.appendText(errorMatome);
                        consoleTextArea.appendText(matome);
                        consoleTextArea.appendText("\n以上のファイル" + String.valueOf(howmanyfiles) + "個の名称を変更します。続行する場合は「実行」を押してください。\n");
                        doit.setVisible(true);
                    }
                });
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        errorConsoleTextArea.appendText(errorMatome);
                        consoleTextArea.appendText("該当するファイルが見つかりませんでした。\n");
                        doit.setVisible(false);
                    }
                });
            }
            CountF = 0;
        } else {
            errorAppend("エラー:wordlist.iniが見つかりません");
        }

    }

    private void CountFiles(String filePath, int maxFiles, boolean setmax, boolean include_subdirectory) {
        Path dir = FileSystems.getDefault().getPath(filePath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    int once = 0;
                    String name = path.getFileName().toString();
                    for (String out : Wordlist.getItems()) {
                        if ((name.contains(out)) && (once == 0)) {
                            //outLogic(path.toString());
                            //Platform.runLater(() -> consoleTextArea.appendText(path.toString() + "\n"));
                            System.out.println(path.toString());
                            matome += path.toString() + "\n";
                            howmanyfiles++;
                            /*
                             if(howmanyfiles%2000==0){
                             outLogic(matome);
                             outLogic("現在の該当ファイル数："+String.valueOf(howmanyfiles)+"個");
                             matome="";
                             }*/
                            once++;//除去する文字列が複数含まれているファイルを複数回登録しないために
                        }
                    }

                    //outLogic(String.valueOf(CountF) + "個目 : " + path.toString());
                    if ((++CountF > maxFiles) && (setmax == true)) {
                        break;
                    }
                } else if (include_subdirectory == true) {
                    CountFiles(path.toString(), maxFiles - CountF, setmax, include_subdirectory);
                }

            }
            stream.close();
        } catch (java.nio.file.AccessDeniedException ex) {
            System.out.println(ex.toString());
            errorAppend("アクセスが拒否されました：" + ex.getFile() + "\n");
            /*
             Platform.runLater(new Runnable() {
             @Override
             public void run() {
             consoleTextArea.appendText("アクセスが拒否されました(CF)：" + ex.getFile() + "\n");
             }
             });
             */
            //consoleTextArea.appendText("アクセスが拒否されました：" + ex.getFile() + "\n");
        } catch (DirectoryIteratorException ex) {
            System.out.println(ex.toString());
            errorAppend("エラー@CountFiles：" + ex.toString() + "\n");
        } catch (IOException ex) {
            System.out.println(ex.toString());
            errorAppend("エラー@CountFiles：" + ex.toString() + "\n");
        }
    }

    private void RegFiles(String filePath, int maxFiles, boolean setmax, boolean include_subdirectory) {
        int howmany = 0;
        Path dir = FileSystems.getDefault().getPath(filePath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    int once = 0;
                    String name = path.getFileName().toString();
                    for (String out : Wordlist.getItems()) {
                        if ((name.contains(out)) && (once == 0)) {
                            filenames[howmany] = name;
                            parentfilepath[howmany] = path.getParent().toString();
                            howmany++;
                            once++;//除去する文字列が複数含まれているファイルを複数回登録しないために
                        }
                    }
                    //System.out.println("" + CountF + ": " + path.toString());
                    if ((++CountF > maxFiles) && (setmax == true)) {
                        break;
                    }
                } else if (include_subdirectory == true) {
                    RegFiles(path.toString(), maxFiles - CountF, setmax, include_subdirectory);
                }
            }
            stream.close();
        } catch (java.nio.file.AccessDeniedException ex) {
            System.out.println(ex.toString());
            //errorAppend("アクセスが拒否されました(RF)：" + ex.getFile() + "\n");
            /*
             Platform.runLater(new Runnable() {
             @Override
             public void run() {
             consoleTextArea.appendText("アクセスが拒否されました(RF)：" + ex.getFile() + "\n");
             }
             });*/
        } catch (DirectoryIteratorException ex) {
            System.out.println(ex.toString());
            errorAppend("エラー@RegistertFiles：" + ex.toString() + "\n");
        } catch (IOException ex) {
            System.out.println(ex.toString());
            errorAppend("エラー@RegisterFiles：" + ex.toString() + "\n");
        }
    }

}
