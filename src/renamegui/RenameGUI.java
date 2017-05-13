/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */

package renamegui;

import java.io.IOException;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class RenameGUI extends Application {
    
    @FXML
    public TableView tableView;

    public RenameGUI() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        
        stage.setTitle("BatchRename V3.0");
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "layout.fxml"
                )
        );        
        stage.getIcons().add(new Image(getClass().getResourceAsStream("BR.png")));
        try {
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("エラー：画面をロードできませんでした");
        }
        Controller controller = loader.<Controller>getController();
        
        try {
            GetXML.get("settings/settings.xml");
        } catch (Exception er) {
            er.printStackTrace();
            controller.out("エラー：設定ファイル、settings.xmlが読み込めませんでした");
        }
        
        String DPath = GetXML.fetchDefaultPath();
        String ticked_value = GetXML.fetchTicked();
        
        controller.init(DPath, ticked_value);
        stage.setResizable(false);
        
        stage.show();

    }

}
