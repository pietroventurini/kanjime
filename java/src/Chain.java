import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pietro Venturini
 */
public class Chain {
    static final int NPREF = 2; // Dimensione del prefisso
    static final String NONWORD = "\n"; // Parola che non possa apparire, come "\n"
    HashMap<Prefix, ArrayList> statetab = new HashMap<Prefix, ArrayList>(); // chiave = Prefix, valore = suffix Vector    
    Prefix prefix = new Prefix(NPREF, NONWORD); // Prefisso iniziale    
    Random rand = new Random();
    

    void build(String filePath) throws IOException  // Costruzione pulita: Costruisce tabella degli stati dall'input dell'utente
    {
        Reader reader = new FileReader(filePath);
        StreamTokenizer st = new StreamTokenizer(reader);

        st.resetSyntax(); // remove default rules
        st.wordChars(0, Character.MAX_VALUE); // turn on all chars
        st.whitespaceChars(0, ' '); // except up to blank
        
        while (st.nextToken() != st.TT_EOF) //divide l'input dell'utente in base agli spazi e aggiunge ogni parola tramite il metodo add(parola)
            add(st.sval);      
        add(NONWORD); 
       
    }
    
 
    void add(String word) // Chain add: aggiunge la parola alla lista dei suffissi e aggiorna il prefisso
    {
        ArrayList<String> suf = (ArrayList) statetab.get(prefix); //recupero il suffisso del prefisso inserendolo nel vettore suf
        if (suf == null) { //se è ancora vuoto lo inizializzo
            suf = new ArrayList<String>();
            statetab.put(new Prefix(prefix), suf); //aggiungo alla hashmap la chiave Prefix (['\n']['\n']) e il valore suf [null]
        }
        suf.add(word);
        
        prefix.pref.remove(0); //rimuove la prima parola del vettore di prefissi (["Ciao"]["Sono"]) --> ([]["Sono"])
        prefix.pref.add(word); //aggiunge la parola al vettore di prefissi (["Sono"]["Pietro"])
    }
    
    
    void generate(int nwords) // Chain generate: generate output words
    {
        
        
            
            Writer writer = null;
        try // Chain generate: generate output words
        {
            writer = new BufferedWriter(new FileWriter("text-files/Output.txt"));
            prefix = new Prefix(NPREF, NONWORD);
            for (int i = 0; i < nwords; i++) {
                ArrayList<String> s = (ArrayList) statetab.get(prefix);
                if (s == null) { 
                    System.err.println("Markov: internal error: no state");
                    System.exit(1);
                }
                int r = Math.abs(rand.nextInt()) % s.size();
                String suf = (String) s.get(r);
                if (suf.equals(NONWORD))
                    break;
                System.out.println(suf);
                writer.write(suf + " ");
                
               
                
                prefix.pref.remove(0);
                prefix.pref.add(suf);
            }
        } catch (IOException ex) {
            Logger.getLogger(Chain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Chain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    } 
}