import javax.swing.*;
import java.awt.*;

/**
 * klasa konkretnego watku stworzona na podstawie JButton
 */
public class Field extends JButton implements Runnable{
    /** Predkosc dzialania */
    int mySpeed;
    /** Prawdopodobienstwo dzialania */
    double myProbability;
    /** Poszczegolne skladowe koloru */
    int r, g, b;
    /** poszczegolne wspolrzedne konkretnego prostokata */
    int currentRow, allRows, currentColumn, allColumns;
    /** sprawdzanie czy watek dziala */
    boolean isWorking;

    /**
     * konstruktor przycisku implementujacy runnable
     * @param thisRow rzad w ktorym znajduje się prostokat
     * @param thisColumn kolumna w ktorym znajduje się prostokat
     * @param row ilosc rzedow
     * @param column ilosc kolumn
     * @param speed predkosc dzialania
     * @param probability prawdopodobienstwo
     */
    Field(int thisRow, int thisColumn, int row, int column, int speed, double probability){
        currentRow = thisRow;
        allRows = row;
        currentColumn = thisColumn;
        allColumns = column;
        isWorking = true;

        mySpeed = speed;
        myProbability = probability;
        setColor(Window.rand.nextInt(0, 255), Window.rand.nextInt(0, 255), Window.rand.nextInt(0, 255));

        addActionListener(e -> {
            synchronized (this){
                if(isWorking) {
                    isWorking = false;
                    setText("STOP");
                }
                else{
                    isWorking = true;
                    setText("");
                    notify();
                }
            }
        });
    }

    /**
     * nadpisywanie funkcji run, ktora zmienia kolor pola zgodnie z prawopodobienstwem
     */
    @Override
    public void run() {
        while(true){
            try {
                int randomSpeed = Window.rand.nextInt(mySpeed/2, mySpeed*3/2);
                Thread.sleep(randomSpeed);
                if (!isWorking) {
                    synchronized(this) {
                        while (!isWorking)
                            wait();
                    }
                }
            } catch (InterruptedException ignored){
            }

            double random = Window.rand.nextDouble();
            synchronized (Window.panel){
                if(myProbability > random) setColor(Window.rand.nextInt(0, 256), Window.rand.nextInt(0, 256), Window.rand.nextInt(0, 256));
                else{
                    int r=0;
                    int g=0;
                    int b=0;
                    int numberOfWorking = 0;
                    Field[] nextTo = {Window.fields[(currentRow+allRows+1)%allRows][currentColumn], Window.fields[(currentRow+allRows-1)%allRows][currentColumn], Window.fields[currentRow][(currentColumn+allColumns+1)%allColumns],Window.fields[currentRow][(currentColumn+allColumns-1)%allColumns]};
                    for(int i=0; i<4; i++){
                        if(nextTo[i].isWorking){
                            r += nextTo[i].r;
                            g += nextTo[i].g;
                            b += nextTo[i].b;
                            numberOfWorking++;
                        }
                    }
                    if(numberOfWorking!=0){
                        setColor(r/numberOfWorking, g/numberOfWorking, b/numberOfWorking);
                    }
                }
                Thread.yield();
            }
        }
    }

    /**
     * funkcja zmieniajaca kolor
     * @param newR wartosc R koloru
     * @param newG wartosc G koloru
     * @param newB wartosc B koloru
     */
    private void setColor(int newR, int newG, int newB){
        r = newR;
        g = newG;
        b = newB;
        setBackground(new Color(r, g, b));
    }
}
