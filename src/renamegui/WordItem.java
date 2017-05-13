/*
 * AUTHOR     : Kanta Kikubo
 * AUTHOR URL : http://www.trance-cat.com/
 * LICENSE    : Apache 2.0
 */
package renamegui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class WordItem {

    private final SimpleStringProperty word = new SimpleStringProperty("");
    private final SimpleIntegerProperty priority = new SimpleIntegerProperty();

    public WordItem() {
        this("", 0);
    }
    
    public WordItem(String word, int priority) {
        setWord(word);
        setPriority(priority);
    }

    public String getWord() {
        return word.get();
    }

    public void setWord(String inword) {
        word.set(inword);
    }

    public int getPriority() {
        return priority.get();
    }

    public void setPriority(int num) {
        priority.set(num);
    }
}
