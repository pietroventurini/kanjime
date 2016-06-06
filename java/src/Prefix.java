import java.util.ArrayList;

public class Prefix {
    public ArrayList<String> pref; // NPREF adjacent words from input
    static final int MULTIPLIER = 31; // for hashCode()
    
    
    public Prefix(Prefix p) // Prefix constructor: duplicate existing prefix
    {
       pref = (ArrayList<String>) p.pref.clone();
    }
    
    
    public Prefix(int n, String str) // Costruttore di Prefix: n copie del parametro str ("\n")
    {
       pref = new ArrayList<>();
       for (int i = 0; i < n; i++)
            pref.add(str);
    }
    
    
    public int hashCode() // Prefix hashCode: generate hash from all prefix words 
    {
        int h = 0;
        for (int i = 0; i < pref.size(); i++)
            h = MULTIPLIER * h + pref.get(i).hashCode();
        return h;
    }
    

    public boolean equals(Object o) // Prefix equals: compare two prefixes for equal words
    {
        Prefix p = (Prefix) o;
        for (int i = 0; i < pref.size(); i++)
            if (!pref.get(i).equals(p.pref.get(i)))
                return false;
        return true;
    }
}