import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WynikiDodaj {


        JButton applyButton;
        JFrame frame;
        JTextField imieText;
        JTextField nazwiskoText;
        int punkty;
        public void dodajWynik(int punkty1) {
            punkty=punkty1;



            frame= new JFrame("DODAJ WYNIK");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            GridLayout grid = new GridLayout(4,2);//stworzenie układu
            grid.setVgap(3);
            grid.setHgap(3);
            JPanel back =new JPanel(grid);
            back.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));




            JLabel imieLabel =new JLabel("Imię:");
            JLabel nazwiskoLabel =new JLabel("Nazwisko:");
            JLabel punktyLabel =  new JLabel("Twój wynik to:");
            JLabel punktywartLabel =  new JLabel(String.valueOf(punkty));
            JLabel emptyLabel =  new JLabel("");

            imieText =new JTextField(15);
            nazwiskoText =new JTextField(15);
            applyButton= new JButton("ZASTOSUJ");
            applyButton.addActionListener(new ApplyButtonListener());


            back.add(imieLabel);
            back.add(imieText);
            back.add(nazwiskoLabel);
            back.add(nazwiskoText);
            back.add(punktyLabel);
            back.add(punktywartLabel);
            back.add(emptyLabel);
            back.add(applyButton);

            frame.setContentPane(back);
            frame.setResizable(false);
            frame.setPreferredSize(new Dimension(300, 150));
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);

        }
        public class ApplyButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent a) {

                Wyniki nowyWynik =new Wyniki(imieText.getText(), nazwiskoText.getText(),punkty);
                Wyniki.add_Wynik(nowyWynik);
                frame.dispose();
                }

        }
    }


