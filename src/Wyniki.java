import java.io.*;
import java.util.ArrayList;

public class Wyniki implements Comparable<Wyniki> , Serializable{//tworzy typ obiektu WYNIKI który zawiera potrzebne info i przechowuje dane

    private final String imie;
    private final String nazwisko;
    private final int punkty;
    private static final ArrayList<Wyniki> listaWynikow = new ArrayList<>();
    private static int ilosc=0;

    public Wyniki(String imie, String nazwisko, int punkty) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.punkty = punkty;
    }
    @Override//nadpisujemy comparable żeby móc segregować przy pomocy ilości punktów bo tak by nie działało
    public int compareTo(Wyniki wyniki) {

        return Integer.compare(this.punkty, wyniki.punkty);
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }
    public int getPunkty() {
        return punkty;
    }
    public static ArrayList<Wyniki> getListaWynikow() {
        return listaWynikow;
    }

    public static void add_Wynik(Wyniki wynik) {

        try {
            FileOutputStream fs = new FileOutputStream("Wyniki"+ilosc+".ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(wynik);
            os.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        listaWynikow.add(wynik);
        ilosc++;
    }
    public static void load_Wynik()  {
        ObjectInputStream is;




        while (true) {
            try {
                is = new ObjectInputStream(new FileInputStream("Wyniki"+ilosc+".ser"));
                listaWynikow.add((Wyniki) is.readObject());
                ilosc++;
                is.close();
            } catch (IOException e) {
                break;

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
