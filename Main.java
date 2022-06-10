import javax.swing.*;

/**
 * Glowna klasa programu
 * @author Krzysztof Wisniewski
 */
public class Main {
    /**
     * okno programu
     */
    static Window window;

    /**
     * Podstawowa metoda uruchamiajaca program
     * @param args argumenty main
     */
    public static void main(String[] args) {
        JTextField rowInput = new JTextField();
        JTextField colInput = new JTextField();
        JTextField speedInput = new JTextField();
        JTextField probInput = new JTextField();
        int rows = 0;
        int columns = 0;
        int speed = 0;
        double probability = 0;
        Object[] message = {
                "Ilość wierszy:", rowInput,
                "Ilość kolumn:", colInput,
                "Szybkość działania (ms):", speedInput,
                "Prawdopodobieństwo (0.0-1.0):", probInput
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Wprowadź dane", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            rows = Integer.parseInt(rowInput.getText());
            columns = Integer.parseInt(colInput.getText());
            speed = Integer.parseInt(speedInput.getText());
            probability = Double.parseDouble(probInput.getText());
        }


        if(rows>0 && columns>0 && probability>0 && probability<1 && speed>0){
            window = new Window(rows, columns, speed, probability);
        }
        else{
            JOptionPane.showMessageDialog(null, "Błędne dane");
        }
    }

}
