/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renamegui;

/**
 *
 * @author Kanta
 */
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GetXML {
    
    private static Element ticked;
    private static Element default_path;
    
    public static void get(String path) throws Exception{
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();

        // ドキュメントビルダーを作成する
        DocumentBuilder builder = dbfactory.newDocumentBuilder();

        // XMLをツリー上に展開する
        Document doc = builder.parse(new FileInputStream(path));

        // ルートノードを取得
        Element root = doc.getDocumentElement();

        // 1つ目の「商品」要素を取得する
        Element item = (Element) root.getElementsByTagName("BasicSettings").item(0);

        // 1つ目の「商品」要素内の「名称」要素を取得する
        default_path = (Element) item.getElementsByTagName("DefaultPath").item(0);
        ticked = (Element) item.getElementsByTagName("TickedDefault").item(0);
        // 「名称」要素のテキストコンテンツを表示する
        System.out.println("DefaultPath:\"" + default_path.getTextContent() + "\"");
        System.out.println("Ticked:\"" + ticked.getTextContent() + "\"");
    }
    
    public static String fetchDefaultPath(){
        return default_path.getTextContent();
    }
    
    public static String fetchTicked(){
        return ticked.getTextContent();        
    }
}

