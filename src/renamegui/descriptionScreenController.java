/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */
package renamegui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class descriptionScreenController implements Initializable {

    @FXML
    private TextArea description;
    
    private Stage stage;
    
    private String output = "=説明=\n"
            + "ファイル名から指定した文字列を削除するソフトです。実行しなければ、単なるファイル検索としても使用できます。\n\n"
            + "=使用方法=\n"
            + "まず、名称変更/検索したいファイルが集まっているフォルダを選択します。\n"
            + "「該当ファイル表示」を押すと、指定された文字列がファイル名に含まれているファイルが表示されます。\n"
            + "最後に「実行」を押せば、名称変更がファイルに適応されます。検索のみしたい場合は実行しないでください。\n\n"
            + "=このアプリができること=\n"
            + "・写真ファイルの名称から「DSC」や「PIC」を一斉に削除する。\n"
            + "・拡張子を統一する。例：「.JPG」を「.jpg」に変更する。\n"
            + "・特定の文字列がファイル名に含まれているファイルを検索する。\n"
            + "・Cドライブ内のすべてのPDFファイル（「.pdf」、「.PDF」）のパスを表示させる。\n";

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @FXML
    public void close() {
        this.stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        description.setText(output);
    }
}
