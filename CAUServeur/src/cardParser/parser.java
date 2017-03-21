/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardParser;
import java.io.BufferedReader;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser   ;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.cartesBlanches;
import model.cartesNoire;
/**
 *
 * @author Mystic
 */
public class parser {
    
    private static  String jsonFileB = "C:\\Users\\usager\\Desktop\\blackCards.json";
    private static  String jsonFileW = "C:\\Users\\usager\\Desktop\\WhiteCards.json";
    public static ArrayList listeBlanches = new ArrayList();
    public static ArrayList listeNoires = new ArrayList();
    public static void main (String[]args){
        try {
               FileReader reader = new FileReader(jsonFileB);
               FileReader readerW = new FileReader(jsonFileW);
                JSONObject jsonObject =  (JSONObject) new JSONParser().parse(reader);
                JSONObject jsonObjectW =  (JSONObject) new JSONParser().parse(readerW);
                JSONArray card = (JSONArray)jsonObject.get("black");
                JSONArray cardW = (JSONArray)jsonObjectW.get("white");
                 Iterator b = card.iterator();
                 while (b.hasNext()){
                    System.out.println(b.next()+ " Black ");
                    listeNoires.add(b.next());
                 }
                 Iterator w = cardW.iterator();
                 while (w.hasNext()){
                    System.out.println(w.next() + " white");
                    listeBlanches.add(w.next());
                 }
            }
        catch(Exception e) 
        {
             e.printStackTrace();
        }
    }   
    
}
