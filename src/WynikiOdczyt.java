import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class WynikiOdczyt {


    public void odczyt(){



        ArrayList<Wyniki> listaWynikow=Wyniki.getListaWynikow();//odczyt listy wyników i sortowanie
        Collections.sort(listaWynikow);
        System.out.println(listaWynikow.size()+1);

        GridLayout grid = new GridLayout(listaWynikow.size()+1,4);//stworzenie układu
        grid.setVgap(3);
        grid.setHgap(3);
        JPanel background =new JPanel(grid);
        Color tcolor=new Color(107, 71, 171);//kolory do poprawy
        Color scolor=new Color(187, 177, 218);
        Color ccolor=new Color(221, 222, 246);
        background.setBackground(tcolor);

        JLabel title1=new JLabel("MIEJSCE");//tworzenie kafelków tytułowych
        title1.setBackground(tcolor);
        title1.setPreferredSize(new Dimension(80,20));
        title1.setOpaque(true);

        JLabel title2=new JLabel("IMIĘ");
        title2.setBackground(tcolor);
        title2.setPreferredSize(new Dimension(80,20));
        title2.setOpaque(true);

        JLabel title3=new JLabel("NAZWISKO");
        title3.setBackground(tcolor);
        title3.setPreferredSize(new Dimension(80,20));
        title3.setOpaque(true);

        JLabel title4=new JLabel("PUNKTY");
        title4.setBackground(tcolor);
        title4.setPreferredSize(new Dimension(80,20));
        title4.setOpaque(true);

        background.add(title1);//dodanie do tła
        background.add(title2);
        background.add(title3);
        background.add(title4);
        Wyniki wynik;

        for(int i=0; i<listaWynikow.size();i++)//dla każdego wyniku tworzymy kafelki
        {
            wynik =listaWynikow.get(listaWynikow.size()-1-i);

            JLabel tile1=new JLabel(String.valueOf(i+1));
            tile1.setBackground(scolor);
            tile1.setPreferredSize(new Dimension(80,20));
            tile1.setOpaque(true);


            JLabel tile2=new JLabel(wynik.getImie());
            tile2.setBackground(ccolor);
            tile2.setPreferredSize(new Dimension(80,20));
            tile2.setOpaque(true);


            JLabel tile3=new JLabel(wynik.getNazwisko());
            tile3.setBackground(ccolor);
            tile3.setPreferredSize(new Dimension(80,20));
            tile3.setOpaque(true);


            JLabel tile4=new JLabel(String.valueOf(wynik.getPunkty()));
            tile4.setBackground(scolor);
            tile4.setPreferredSize(new Dimension(80,20));
            tile4.setOpaque(true);


            background.add(tile1);
            background.add(tile2);
            background.add(tile3);
            background.add(tile4);

        }

            JFrame frame = new JFrame("ZESTAWIENIE WYNIKOW");
            frame.getContentPane().add(background);
            frame.pack();
            frame.setVisible(true);


        }
    }



