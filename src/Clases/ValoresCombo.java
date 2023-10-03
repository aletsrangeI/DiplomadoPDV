package Clases;
/**
 *
 * @author Brandon Emmanuel Reséndiz Granados, Ruben Lora Cruz, Alejandro Avila Rangel
 * 
 */
public class ValoresCombo {

    private int id; // This will store the ID or value of the item
    private String text; // This will store the text or display name of the item

    public ValoresCombo(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return text; // This is what will be displayed in the JComboBox
    }
}
