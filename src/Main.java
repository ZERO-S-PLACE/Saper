import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static javax.swing.BoxLayout.Y_AXIS;


public class Main extends JFrame{

    private static int wiersze=10;
    private static int kolumny=10;
    private static int bomby =30;
    
    int[][] liczby;
    int punkty=0;
    boolean firstTry=true;

    JFrame frame;
    JFrame popFrame;
    JLayeredPane warstwy;
    JPanel buttonPanel;
    JPanel labelPanel;
    Box background;
    Box endPanel;
    JButton ustawienia;
    JButton wyniki;
    JButton nowaGra;
    JButton poddajSie;
    JCheckBox flagaBox;
    JLabel flagaLabel;
    JLabel koniecPunkty;
    JLabel licznikPunkty;




    public  static void main(String[] args) {
        Main main = new Main();
        main.buildMainGUI();
        Wyniki.load_Wynik();
        //dodanie próbnych wyników żeby tabelka nie była pusta
    }



    public void buildMainGUI() {
        firstTry=true;

        ustawienia = new JButton("Ustawienia");//tworzymy po kolei przyciski , i action listenery jak je naciśniemy
        ustawienia.addActionListener(new UstawieniaListener());
        ustawienia.setFont(new Font("Arial", Font.BOLD, 10));


        wyniki = new JButton("Wyniki");
        wyniki.addActionListener(new WynikiListener());
        wyniki.setFont(new Font("Arial", Font.BOLD, 10));


        nowaGra =new JButton("Nowa Gra");
        nowaGra.addActionListener(new NowaGraListener());
        nowaGra.setFont(new Font("Arial", Font.BOLD, 10));


        poddajSie=new JButton("Poddaj się");
        poddajSie.addActionListener(new PoddajSieListener());
        poddajSie.setEnabled(false);
        poddajSie.setFont(new Font("Arial", Font.BOLD, 10));

        flagaBox= new JCheckBox();    //jak będzie zaznaczone, kliknięcie będzie oznaczało przycisk znakiem zapytania
        flagaBox.setSelected(false);

        flagaLabel= new JLabel("  Flaga:  ");
        flagaLabel.setFont(new Font("Arial", Font.BOLD, 10));

        licznikPunkty= new JLabel("<html>  PUNKTY: "+punkty+" <html>");
        licznikPunkty.setFont(new Font("Arial", Font.BOLD, 10));
        licznikPunkty.setOpaque(true);


        JPanel upperButtonBox = new JPanel();// tworzymy panele na przyciski i dodajemy je do nich
        upperButtonBox.setPreferredSize(new Dimension(kolumny*20+55, 30));

        JPanel lowerButtonBox = new JPanel();
        lowerButtonBox.setPreferredSize(new Dimension(kolumny*20+55, 15));

        upperButtonBox.add(BorderLayout.CENTER, nowaGra);
        upperButtonBox.add(BorderLayout.WEST,poddajSie);
        upperButtonBox.add(BorderLayout.EAST,licznikPunkty);
        upperButtonBox.add(BorderLayout.SOUTH, flagaBox);
        upperButtonBox.add(BorderLayout.SOUTH, flagaLabel);

        lowerButtonBox.add(BorderLayout.WEST,ustawienia);
        lowerButtonBox.add(BorderLayout.CENTER, wyniki);


        GridLayout grid = new GridLayout(wiersze,kolumny);// tworzymy panele pod główne pole gry
        grid.setVgap(0);
        grid.setHgap(0);
        buttonPanel =new JPanel(grid);
        labelPanel =new JPanel(grid);
        buildPanels();     //dodawanie cyfereki przycisków do paneli w innej metodzie

        JLabel koniec =new JLabel("KONIEC");  // tu tworzymy napis który się pojawi gdy ktoś przegra
        koniec.setFont(new Font("Arial", Font.BOLD, 40));
        koniec.setForeground(Color.DARK_GRAY);
        koniec.setOpaque(false);

        koniecPunkty =new JLabel("<html>" +"TWÓJ WYNIK TO \n "+punkty +" \n PUNKTÓW"+"<html>");
        koniecPunkty.setFont(new Font("Arial", Font.BOLD, 16));
        koniecPunkty.setForeground(Color.DARK_GRAY);
        koniecPunkty.setOpaque(false);

        endPanel =  new Box(BoxLayout.Y_AXIS);
        endPanel.setOpaque(false);
        endPanel.add(koniec);
        endPanel.add(koniecPunkty);
        endPanel.setVisible(false);
        endPanel.setPreferredSize(new Dimension(kolumny*20, wiersze*20));

        warstwy=new JLayeredPane(); // na głównym panelu pod spodem są cyfry, na wierzchu przyciski, w razie przegranej komunikat
        warstwy.setLayout(null);
        warstwy.setPreferredSize(new Dimension(kolumny*20, wiersze*20));
        warstwy.add(labelPanel, Integer.valueOf(1));
        labelPanel.setBounds(10,10,20*kolumny,20*wiersze);
        warstwy.add(buttonPanel, Integer.valueOf(2));
        buttonPanel.setBounds(10,10,20*kolumny,20*wiersze);
        warstwy.add(endPanel, Integer.valueOf(3));
        endPanel.setBounds(10,10,20*kolumny,20*wiersze);


        background = new Box(Y_AXIS);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        background.add(upperButtonBox);// dodajemy wszystko do tła, a tło do ramki
        background.add(warstwy);
        background.add(lowerButtonBox);

        frame= new JFrame("S_A_P_E_R___J_A_V_A");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(background);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(kolumny*20+55, wiersze*20+180));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

    }


    public void buildPanels(){
        UkladPlanszy uklad = new UkladPlanszy();
        liczby = uklad.rozmiescBomby(wiersze, kolumny, bomby);// tworzenie tablicy liczb w klasie układ planszy

        for(int i=0;i<wiersze;i++) {
            for (int j = 0; j < kolumny; j++) {

                int temp=liczby[i][j];

                JLabel c = getjLabel(temp);

                JButton b =new JButton();
                b.setPreferredSize(new Dimension(20, 20));
                b.setName(String.valueOf(100*i+j));// nazywam przyciski, musi być string. żeby się dało je przypisać do
                //komórek tabeli mnożę pierwszą wartość*100(bo zawsze będą mniejsze) i powstanie w ten sposób liczba
                //która się nie powtórzy, i można odzyskać i i j
                b.addActionListener(new TryListener());
                b.setBackground(new Color(204, 255, 204));
                b.setBorder(BorderFactory.createRaisedSoftBevelBorder());

                labelPanel.add(c);
                buttonPanel.add(b);

            }
            buttonPanel.setOpaque(false);// przeźroczystość panelu, żeby gdy ukryje się jeden  z przycisków było widać liczby pod spodem
        }
    }

    private static JLabel getjLabel(int temp) {
        JLabel c =new JLabel(String.valueOf(temp));               ///dodawanie po 1 kafelków pod spodem z liczbami
        c.setFont(new Font("Arial", Font.BOLD, 16));

        switch (temp){
            case 0:
                c=new JLabel("");
                break;
            case 1:
                c.setForeground(new Color(0, 102, 255));
                break;
            case 2:
                c.setForeground(new Color(146, 120, 199));
                break;
            case 3:
                c.setForeground(new Color(168, 96, 168));
                break;
            case 4:
                c.setForeground(new Color(148, 63, 124));
                break;
            case 5:
                c.setForeground(new Color(175, 84, 132));
                break;
            case 6:
                c.setForeground(new Color(124, 35, 62));
                break;
            case 7:
                c.setForeground(new Color(107, 14, 42));
                break;
            case 8:
                c.setForeground(new Color(180, 0, 0));
                break;
            case 9:
                c=new JLabel("☠");
                break;

        } /// przypisanie kolorów czcionki dla rónych cyfr

        c.setBorder(BorderFactory.createRaisedBevelBorder());
        c.setHorizontalAlignment(SwingConstants.CENTER);
        c.setPreferredSize(new Dimension(20, 20));
        return c;
    }


    public class TryListener implements ActionListener {//jeśli zostanie wciśnięty jeden z przycisków na głónym panelu
        public void actionPerformed(ActionEvent a) {

            JButton temp = (JButton) a.getSource();//identyfikuje który to

            if(flagaBox.isSelected()){// jeśli zaznaczony chceckbox  to tylko zmieni ikonkę przycisku na ?
                temp.setText("?");
                temp.revalidate();
            }

            else {
                if(temp.getText().equals("?")){// jeśli nie, ale przycisk był już oznaczony,tylko zmieni ikonkę na domyślną
                    temp.setText("");

                }else {// jeśli nie to ukryje przycisk
                    temp.setEnabled(true);
                    temp.setVisible(false);


                    int ij = Integer.parseInt(temp.getName()); // odzyskujemy numer przycisku
                    int i=ij/100;
                    int j=ij-100*i;

                    if (liczby[i][j] == 9&&!firstTry) {endGame();
                        // jeśli pod spodem jest bomba zakończy grę (inna metoda)
                    return;}
                    if (liczby[i][j] == 9&&firstTry) {
                        buildMainGUI();// jeśli była to pierwsza próba to zresetuje okno
                        temp = findButtonByName(String.valueOf(ij));
                        assert temp != null;
                        temp.doClick();

                        return;}

                    firstTry=false;
                    nowaGra.setEnabled(false);//żeby nie klikać przyciskow które nie mają sensu w danym momencie
                    ustawienia.setEnabled(false);
                    poddajSie.setEnabled(true);


                    if (liczby[i][j] == 0) {

                        if (i > 0 && liczby[i - 1][j] == 0) {
                           tryToClick(i-1,j);
                        }

                        if ((j > 0) && liczby[i][j - 1] == 0) {
                            tryToClick(i,j-1);
                        }
                        if ((j < kolumny - 1) && liczby[i][j + 1] == 0) {
                            tryToClick(i,j+1);
                        }

                        if ((i < wiersze - 1) && liczby[i + 1][j] == 0) {
                            tryToClick(i+1,j);
                        }

                    }//jeśli to pole nie ganiczy z bąbą i sąsiednie pole nie graniczy z bąbą
                    //symuluje jakby przycisk nad tym polem został przyciśnięty(tak jest w orginale)


                    punkty=punkty+liczby[i][j]*bomby*bomby/wiersze/kolumny;// dodaje punkty i aktualizuje licznik
                    licznikPunkty.setText("<html> PUNKTY: "+punkty+" <html>");
                    licznikPunkty.repaint();

                }
            }
        }

    }


    //private static final Lock lock=new ReentrantLock();
    private final Semaphore semaphore= new Semaphore(8,true);
    public void tryToClick(int i ,int j) {
        JButton temp1 = findButtonByName(String.valueOf(i * 100 + j));

        assert temp1 != null;
        if (temp1.isVisible()) {

            try {
                semaphore.acquire();



            //lock.lock();
                Thread t1 = new Thread(temp1::doClick);
                t1.start();
                //lock.unlock();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {


                semaphore.release();
            }

        }
        buttonPanel.repaint();




    }

    private JButton findButtonByName(String name) {

        Component[] components = buttonPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton && name.equals(component.getName())) {
                return (JButton) component;
            }
        }
        return null;
    }/// funkcja z odzysku z internetu

    private void disableButtons() {

        Component[] components = buttonPanel.getComponents();
        for (Component component : components) {
            component.setEnabled(false);
        }
    }

    private void endGame(){

        disableButtons();
        poddajSie.setEnabled(false);
        nowaGra.setEnabled(true);
        ustawienia.setEnabled(true);
        koniecPunkty.setText("<html>" +"TWÓJ WYNIK TO \n "+punkty +" \n PUNKTÓW"+"<html>");
        koniecPunkty.repaint();
        endPanel.setVisible(true);
        popframe();
    }


    public void popframe(){// ramka z zapytaniem czy zapisać wynik

        JButton takButton=new JButton("TAK");
        takButton.addActionListener(new TakListener());

        JButton nieButton=new JButton("NIE");
        nieButton.addActionListener(new NieListener());

        JLabel label =new JLabel("    Zapisać wynik?");
        label.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        JPanel back =new JPanel();
        back.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        back.add(BorderLayout.NORTH,label);
        back.add(BorderLayout.WEST,takButton);
        back.add(BorderLayout.EAST,nieButton);


        popFrame = new JFrame("WYNIKI");
        popFrame.setContentPane(back);
        popFrame.setResizable(false);
        popFrame.setLocationRelativeTo(null);
        popFrame.setPreferredSize(new Dimension(200,110));
        popFrame.setBounds(500,410,140,120);
        popFrame.pack();
        popFrame.setVisible(true);
        }

    public class TakListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            popFrame.dispose();
            WynikiDodaj dodaj = new WynikiDodaj();// przekazuje do klasy w której jest funkcja dodawania wyniku
            dodaj.dodajWynik(punkty);
        }
    }
    public class NieListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            popFrame.dispose();
        }
    }



    public class NowaGraListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {

            frame.dispose();
            Main main = new Main();
            main.buildMainGUI();
        }
    }

    public class PoddajSieListener implements ActionListener {
            public void actionPerformed(ActionEvent a) {
                endGame();
            }
    }
    static public class WynikiListener implements ActionListener {
            public void actionPerformed(ActionEvent a) {
                WynikiOdczyt odczyt =new WynikiOdczyt();// przenosi do kasy w której jest program pokazujący zapisane wyniki
                odczyt.odczyt();
            }
    }
    public class UstawieniaListener implements ActionListener
    {

            JButton applyButton;
            JTextField wierszeText ;
            JFrame ustFrame;
            JTextField kolumnyText;
            JTextField bombyText;

            public void actionPerformed(ActionEvent a) {

                frame.dispose();
                ustFrame= new JFrame("USTAWIENIA");
                ustFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GridLayout grid = new GridLayout(4,2);//stworzenie układu
                grid.setVgap(3);
                grid.setHgap(3);
                JPanel back =new JPanel(grid);
                back.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                JLabel wierszeLabel =new JLabel(" Ilość wierszy <5;35>");
                JLabel kolumnyLabel =new JLabel(" Ilość kolumn  <5;75>");
                JLabel bombyLabel =  new JLabel(" Ilość bomb");
                JLabel emptyLabel =  new JLabel("");

                wierszeText =new JTextField(4);
                kolumnyText =new JTextField(4);
                bombyText =new JTextField(4);

                applyButton= new JButton("ZASTOSUJ");
                applyButton.addActionListener(new ApplyButtonListener());


                back.add(wierszeLabel);
                back.add(wierszeText);
                back.add(kolumnyLabel);
                back.add(kolumnyText);
                back.add(bombyLabel);
                back.add(bombyText);
                back.add(emptyLabel);
                back.add(applyButton);



                ustFrame.setContentPane(back);

                ustFrame.setResizable(false);

                ustFrame.setPreferredSize(new Dimension(300, 150));
                ustFrame.setLocationRelativeTo(null);

                ustFrame.pack();
                ustFrame.setVisible(true);


            }


            public class ApplyButtonListener implements ActionListener {
                public void actionPerformed(ActionEvent a)
                {


                    if(!wierszeText.getText().isEmpty()&& !kolumnyText.getText().isEmpty()&&!bombyText.getText().isEmpty())
                    {

                    int noweWiersze = Integer.parseInt(wierszeText.getText());
                    int noweKolumny = Integer.parseInt(kolumnyText.getText());
                    int noweBomby = Integer.parseInt(bombyText.getText());

                    if (noweWiersze > 4 && noweWiersze < 38 && noweKolumny > 4 && noweKolumny < 76 && noweBomby < noweKolumny * noweBomby - 5) {
                        wiersze = noweWiersze;
                        kolumny = noweKolumny;
                        bomby = noweBomby;
                        ustFrame.dispose();
                        Main main = new Main();
                        main.buildMainGUI();

                    }
                }
                    else{
                        ustFrame.dispose();
                        ustawienia.doClick();
                        frame.dispose();
                    }
                }
            }
        }


}