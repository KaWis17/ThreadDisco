import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Klasa, ktorej instancja jest glowne okno programu
 */
public class Window extends JFrame {
    /** Funkcja random */
    public static Random rand = new Random();
    /** Glowny panel do wyswietlania prostokatow */
    static final JPanel panel = new JPanel();
    /** Tablica tablic pol */
    public static Field[][] fields;

    /**
     * konstruktor okna
     * @param rows ilosc wierszy
     * @param columns ilosc kolumn
     * @param speed predkosc dzialania
     * @param probability prawdopodobienstwo
     */
    Window(int rows, int columns, int speed, double probability){
        fields = new Field[rows][columns];
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridLayout(rows, columns));
        addingElements(rows, columns, speed, probability);

        add(panel);
        setTitle("Program Lista 6");
        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    /**
     * dodawanie elementow na panel
     * @see #panel
     * @param rows wiersze
     * @param columns kolumny
     * @param speed predkosc dzialania
     * @param probability prawdopodobienstwo
     */
    private void addingElements(int rows, int columns, int speed, double probability){
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                fields[i][j] = new Field(i, j, rows, columns, speed, probability);
                panel.add(fields[i][j]);
            }
        }
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                Thread thr = new Thread(fields[i][j]);
                thr.start();
            }
        }
    }

}
