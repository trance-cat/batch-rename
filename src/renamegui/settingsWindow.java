/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */
package renamegui;

import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class settingsWindow {

    public static void show() {

        try {
            GetXML.get("./settings/settings.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String DP = GetXML.fetchDefaultPath();
        String ticked_value = GetXML.fetchTicked();

        DirectoryChooser DPath = new DirectoryChooser();
        CheckBox Dtick = new CheckBox();
        Button DdirButton = new Button("フォルダー選択");
        Label DdirLabel = new Label();
        Label DTickLabel = new Label();
        Label bLabel = new Label("変更はつぎ開いたときに適応されます");
        Button modoru = new Button();
        Button save = new Button();
        Stage stage = new Stage();
        stage.setTitle("デフォルト設定");
        stage.setResizable(false);

        if (ticked_value.equals("true")) {
            Dtick.setSelected(true);
        } else {
            Dtick.setSelected(false);
        }

        //DdirButton.setText("フォルダー選択");
        DdirLabel.setMinWidth(300);
        if (DP.equals("none")) {
            DdirLabel.setText("デフォルトフォルダーパス：なし");
        } else if (!DP.isEmpty()) {
            DdirLabel.setText("デフォルトフォルダーパス：" + DP);
        } else {
            DdirLabel.setText("エラー：設定を読み込めませんでした");
        }

        DTickLabel.setText("デフォルトでサブフォルダーを含める場合はチェック ");
        bLabel.setFont(new Font(10.0));
        
        //Button Settings START//
        DdirButton.setPrefWidth(120);
        modoru.setText("保存せずに戻る");
        modoru.setMinWidth(100);
        save.setText("保存して戻る");
        save.setMinWidth(100);
        //Button Settings END//

        // top
        HBox top = new HBox();
        top.setAlignment(Pos.BASELINE_LEFT);
        top.setSpacing(15);
        top.setPadding(new Insets(5, 10, 5, 10));
        top.getChildren().addAll(DdirLabel, DdirButton);

        HBox middle = new HBox();
        middle.setPadding(new Insets(5, 10, 5, 10));
        middle.setSpacing(15);
        middle.setAlignment(Pos.BASELINE_LEFT);
        middle.getChildren().addAll(DTickLabel, Dtick);

        HBox bottom = new HBox();
        bottom.setPadding(new Insets(5, 10, 5, 10));
        bottom.setSpacing(15);
        bottom.setAlignment(Pos.BASELINE_LEFT);
        bottom.getChildren().addAll(modoru, save, bLabel);

        // root
        VBox root = new VBox();
        root.setAlignment(Pos.BASELINE_LEFT);
        root.setPadding(new Insets(15, 15, 15, 15));
        root.setSpacing(15.0);
        root.getChildren().addAll(top, middle, bottom);

        stage.setScene(new Scene(root));
        stage.show();

        DPath.setTitle("フォルダーを選択してください");
        //String default_path = "E:/KK/PT3";
        String default_path = System.getProperty("user.dir");
        //Fill stage with content
        stage.show();

        DdirButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                DPath.setInitialDirectory(DPath.showDialog(stage));
                if (DPath.getInitialDirectory() != null) {
                    //File path = DPath.getInitialDirectory();
                    DdirLabel.setText("デフォルトフォルダーパス：" + DPath.getInitialDirectory().toString());
                }

            }
        });
        modoru.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
            }
        });
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String path;
                if (DPath.getInitialDirectory() != null) {
                    path = DPath.getInitialDirectory().toString();
                } else {
                    path=DP;
                }

                try {
                    if (Dtick.isSelected() == true) {
                        MakeXML.write(path, true);
                    } else {
                        MakeXML.write(path, false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                stage.close();
            }

        });

    }
}
