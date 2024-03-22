

public class UkladPlanszy {
    public int[][] rozmiescBomby(int wiersze, int kolumny, int bomby)
    {
        int[][]liczby=new int[wiersze][kolumny];

        for(int i= bomby; i>0;i--)//generujemy losowo lokalizacje bomb
        {
            int tempwiersze=(int)Math.floor(Math.random()*(wiersze));
            int tempkolumny=(int)Math.floor(Math.random()*(kolumny));

            if(liczby[tempwiersze][tempkolumny]==0)
            {
                liczby[tempwiersze][tempkolumny]=9;
            }else{i++;}// jeśli natrafiliśmy na bombę trzeba wykonać losowanie jeszcze raz
        }


        for(int i=0;i<wiersze;i++)//sprawdzamy dla każdej komórki ile bobm z nią sąsiaduje i zliczamy do tej zmiennej
        {
            for(int j=0; j<kolumny;j++)
            {
                int obok=0;
                if(liczby[i][j]!=9){
                    if(i>0&&j>0&&liczby[i-1][j-1]==9){obok++;}
                    if(i>0&&liczby[i-1][j]==9){obok++;}
                    if(i>0&&(j<kolumny-1)&&liczby[i-1][j+1]==9){obok++;}
                    if((j>0)&&liczby[i][j-1]==9){obok++;}
                    if((j<kolumny-1)&&liczby[i][j+1]==9){obok++;}
                    if((i<wiersze-1)&&j>0&&liczby[i+1][j-1]==9){obok++;}
                    if((i<wiersze-1)&&liczby[i+1][j]==9){obok++;}
                    if((i<wiersze-1)&&(j<kolumny-1)&&liczby[i+1][j+1]==9){obok++;}
                    liczby[i][j]=obok;
                }
            }
        }

    return liczby;

    }
}
