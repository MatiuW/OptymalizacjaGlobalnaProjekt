package model;

import java.util.ArrayList;

public class Test {
    static final int N = 6; // permutacje n-elementowe
    private static int[] p = new int[N];
    ArrayList<String> path = new ArrayList<>();

    static boolean nieMa(int wyraz, int indeks)
    {
        for (int k = 0; k < indeks; k++)
            if (p[k] == wyraz) return false;

        return true;
    }

    public ArrayList<String> generuj(int i)
    {
        if (i == N)
        {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < p.length; k++){
//                System.out.print(p[k] + " ");
                sb.append(p[k]-1 + " ");
            }
                path.add(sb.toString());
                sb.setLength(0);

//            System.out.println();

        }
        else
        {
            for (int j = 1; j <= N; j++)
            {
                if (nieMa(j,i))
                {
                    p[i] = j;
                    generuj(i+1);
                }
            }
        }

        return path;
    }
}
