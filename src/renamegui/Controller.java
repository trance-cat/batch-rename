/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renamegui;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Kanta
 */
public class Controller implements Initializable {

    @FXML
    private Text actiontarget;
    @FXML
    private Label bigLabel;
    @FXML
    private TextArea consoleArea;
    @FXML
    private TextArea errorConsoleArea;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView tableView;
    @FXML
    private CheckBox subFolderCheckBox;
    @FXML
    private Button execute;
    @FXML
    private Button deleteStringButton;
    @FXML
    private TextField dirField;
    @FXML
    private TextField newString;
    @FXML
    private Button showFiles;

    DirectoryChooser chooser = new DirectoryChooser();
    Stage stage;
    private int currentNumOfWords;

    @FXML
    public void init(String DPath, String ticked_value) {
        currentNumOfWords = 0;
        boolean success = Wordlist.loadWordlist();
        if (!success) {
            out("wordlist.iniの読み込みに失敗");
        } else {
            out("wordlist.iniの読み込みに成功");
        }
        ObservableList<WordItem> data = tableView.getItems();

        for (String s : Wordlist.getItems()) {
            currentNumOfWords++;
            data.add(new WordItem(s, currentNumOfWords));

        }
        chooser.setTitle("フォルダーを選択してください");

        if (DPath.equals("none") || DPath.isEmpty()) {
            setDefaultDir(System.getProperty("user.dir"));
        } else {
            setDefaultDir(DPath);
        }
        if (ticked_value.equals("true")) {
            subFolderCheckBox.setSelected(true);
        } else {
            subFolderCheckBox.setSelected(false);
        }

        listener();
    }

    public void listener() {
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WordItem>() {
            @Override
            public void changed(ObservableValue<? extends WordItem> observable, WordItem oldValue, WordItem newValue) {
                deleteStringButton.setDisable(false);
                WordItem selected = (WordItem) tableView.getSelectionModel().getSelectedItem();
                if(selected!=null){
                System.out.println("選択された文字列:" + selected.getWord());
                }else{
                    System.out.println("Table Cleared!");
                }
            }
        });
    }

    public void setDefaultDir(String str) {
        File defaultDirectory = new File(str);
        if(defaultDirectory.exists()){
        chooser.setInitialDirectory(defaultDirectory);
        }else{
            out("既定ファイルパスをアクセスできません。アプリのパスを使用します。");
            chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        }
        File path = chooser.getInitialDirectory();
        dirField.setText(path.getPath());
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void showHowTo() {
        DescriptionScreen ds = new DescriptionScreen();
        ds.show();
        /*
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("使用方法");
        alert.setHeaderText(null);
        alert.setContentText("まず、まとめて名称変更したいファイルが集まっているフォルダを選択します。\n次に、「該当ファイル表示」を押し、変更が適応されるファイルを表示します。\n最後に「実行」を押せば、名称変更がファイルに適応されます。");
        alert.showAndWait();
        */
    }

    @FXML
    private void showVerInfo() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("バージョン情報");
        alert.setHeaderText(null);
        alert.setContentText("BatchRename V3.0\nCopyright 2016 Trance Cat\nhttp://www.trance-cat.com/");

        alert.showAndWait();
    }

    @FXML
    private void showSettingsWindow() {
        settingsWindow.show();
    }

    @FXML
    private void showSite() {
        openWebpage("http://www.trance-cat.com/");
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showFiles() {
        String path;
        if (!chooser.getInitialDirectory().getPath().isEmpty()) {
            path = chooser.getInitialDirectory().getPath();
        } else {
            path = System.getProperty("user.dir");
        }
        errorConsoleArea.clear();
        consoleArea.clear();
        out("現在のディレクトリパス = " + path+"\n");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        PreRenameLogic PreRL = new PreRenameLogic(progressBar, consoleArea, errorConsoleArea, execute, showFiles, path, subFolderCheckBox.isSelected());
        PreRL.start();
    }

    @FXML
    private void pickDirectory() {
        File chosenFolder = chooser.showDialog(stage);

        if (chosenFolder != null) {
            chooser.setInitialDirectory(chosenFolder);
        }
        if (chooser.getInitialDirectory() != null) {
            dirField.setText(chooser.getInitialDirectory().toString());
        } else {

        }
    }
    
    public void out(String outString) {
        consoleArea.appendText(outString + "\n");
    }

    @FXML
    public void registerWord() {
        ObservableList<WordItem> data = tableView.getItems();
        if (newString.getText().equals("")) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("注意");
            alert.setHeaderText(null);
            alert.setContentText("登録する文字列を入力してください");

            alert.showAndWait();
        } else {
            data.add(new WordItem(newString.getText(), currentNumOfWords + 1));
            writeToWordlist(data);
            currentNumOfWords++;
            newString.clear();
        }
    }

    @FXML
    public void deleteWord() {
        Object a = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().removeAll(a);
        WordItem b = (WordItem) a;
        System.out.println("Removed:" + b.getWord());
        writeToWordlist(tableView.getItems());
    }

    @FXML
    public void execute() {
        RenameLogic RLRename = new RenameLogic(progressBar, consoleArea, execute, PreRenameLogic.parentfilepath, PreRenameLogic.filenames, PreRenameLogic.howmanyfiles);
        RLRename.start();
    }

    private void writeToWordlist(ObservableList<WordItem> data) {
        ArrayList<String> words = new ArrayList<String>();
        for (WordItem b : data) {
            System.out.println("Registered:" + b.getWord());
            words.add(b.getWord());
        }
        Wordlist.writeWordlist(words);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /*
     public void out(String outString){
     consoleArea.appendText(outString);
     }
     */
}
