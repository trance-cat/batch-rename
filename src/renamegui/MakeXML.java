/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */
package renamegui;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class MakeXML {

    public static void write(String defaultpath, boolean ticked) throws Exception {
        // XMLを作成する
        Document doc = buildXML(defaultpath, ticked);
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        Source  src = new DOMSource(doc);
        Result  result = new StreamResult(new File("settings/settings.xml"));
        transformer.transform(src, result);
        
        //GetXML.get("./result.xml");
        //System.out.println("DP "+GetXML.fetchDefaultPath());
        //System.out.println("fT "+GetXML.fetchTicked());
    }


    // XMLを構築して戻すメソッド
    private static Document  buildXML(String defaultpath, boolean ticked) throws Exception {
        // ドキュメントビルダーファクトリーを作成して
        DocumentBuilderFactory dbfactory =
            DocumentBuilderFactory.newInstance();

        // ドキュメントビルダーを作成する
        DocumentBuilder builder = dbfactory.newDocumentBuilder();

        // XMLツリーを作成
        Document  doc = builder.newDocument();

        // 発注要素をルートノードとしてXMLツリーに追加
        Element  orderElement = doc.createElement("Settings");
        doc.appendChild(orderElement);

        Element  itemElement = doc.createElement("BasicSettings");
        orderElement.appendChild(itemElement);

        Element  codeElement = doc.createElement("DefaultPath");
        itemElement.appendChild(codeElement);
        
        Element  tickedElement = doc.createElement("TickedDefault");
        itemElement.appendChild(tickedElement);

        // テキストノードを型番要素の下に追加
        Text  codeText = doc.createTextNode(defaultpath);
        codeElement.appendChild(codeText);
        
        
        String writetick;
        if(ticked==true){
            writetick="true";
        }else{
            writetick="false";
        }
        
        Text  tickedText = doc.createTextNode(writetick);
        tickedElement.appendChild(tickedText);

        return  doc;
    }
}
