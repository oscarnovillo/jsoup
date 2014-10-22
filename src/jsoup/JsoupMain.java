/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoup;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author oscar
 */
public class JsoupMain {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws IOException {
    // TODO code application logic here

    Profesor p = new Profesor("onc1","clase2014", null, null);
      
    
    Wafd wafd = new Wafd();
    
    wafd.iniciaSesion(p);
    
    LinkedHashMap<String, Asignatura> asig = wafd.sacarCodigosAsignaturas(wafd.getResponse());

    System.out.println(asig);
    
    
    System.out.println("OK");

  }
}
