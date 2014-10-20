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

    Connection.Response response = null;
//    Jsoup.connect("https://gestiona.madrid.org/wafd/ValidaUsuario.icm")
//        .method(Connection.Method.GET)
//        .execute();

    response = Jsoup.connect("https://gestiona.madrid.org/wafd/ValidaUsuario.icm")
            .data("USUARIO", "onc1")
            .data("CLAVE", "clase2014")
            // .cookies(response.cookies())
            .method(Connection.Method.POST)
            .execute();

    LinkedHashMap<String, Asignatura> asig = sacarCodigosAsignaturas(response);

    System.out.println(asig);
    
    
    System.out.println("OK");

  }

  public static LinkedHashMap<String, Asignatura> sacarCodigosAsignaturas(Connection.Response response) {
    Document homePage = null;
    LinkedHashMap<String, Asignatura> asignaturas = new LinkedHashMap<>();
    try {
      homePage = Jsoup.connect("https://gestiona.madrid.org/wafd/HorarioProfesor.icm")
              .cookies(response.cookies())
              .post();

      Elements ele = homePage.select("a[href*=irA]");
      ListIterator l = ele.listIterator();
      while (l.hasNext()) {
        Element a = (Element) l.next();
        System.out.println( a.attr("href"));
        Asignatura asig = parseaLineaHorario(asignaturas, a.attr("href"));
        
      }
      for (Asignatura a : asignaturas.values())
      {
        sacarAlumnosAsignatura(response, a);
      }
      
      
    } catch (Exception e) {
    }

    return asignaturas;
  }

  private static Asignatura parseaLineaHorario(LinkedHashMap<String, Asignatura> asignaturas, String href) {
    Asignatura asig = null;
    href = href.substring(href.indexOf("(") + 1);
    String datos[] = href.split(",");
    for (int i=0;i<datos.length;i++)
    {
      datos[i] = datos[i].substring(1,datos[i].length()-1);
    }
    asig = asignaturas.get(datos[2]);

    if (asig == null) {
      asig = new Asignatura(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
      asignaturas.put(asig.getCodigo(), asig);
    }
    
    LinkedHashMap<String,SesionAsignatura> sesiones = asig.getSesiones();
    
    SesionAsignatura ses =  new SesionAsignatura(datos[7], datos[8], datos[9], datos[10]);
    
    sesiones.put(ses.getId(), ses);
    
    return asig;
  }

  public static void sacarAlumnosAsignatura(Connection.Response response,Asignatura asig) {
    Document homePage = null;
    
    //cogemos la primera sesion
    SesionAsignatura sesion = asig.getSesiones().values().iterator().next();
    Calendar ahora  = Calendar.getInstance();
    ahora.set(Calendar.DAY_OF_WEEK, sesion.getDiaSemanaCalendar());
     DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
     Date date = new Date();
     String datestring = dateFormat.format(ahora.getTime()); 

    
    
    try {
      homePage = Jsoup.connect("https://gestiona.madrid.org/wafd/IntroIncidenciasCentro.icm")
              .data("optionsProfesores", "null")
              .data("codigoAsignatura", asig.getCodigo())
              .data("codigoIdioma", asig.getIdioma())
              .data("codigoReligion", asig.getReligion())
              .data("esGrupoMateria", asig.getEsGrupoMateria())
              .data("codigoSesion", sesion.getSesion())
              .data("diaSemana", sesion.getDiaSemana())
              .data("nombreProfesor", "")
              //.data("franja","08:30:09:25")
              .data("haySesiones", "0")
              .data("sesion", "1")
              .data("grupo", asig.getGrupo())
              .data("asignatura", asig.getDescripcion())
              .data("pantallaIncidencia", "S")
              .data("profesor", "")
              .data("dia", datestring)
              .data("diaold", datestring)
              .cookies(response.cookies())
              .post();
    } catch (IOException ex) {
      Logger.getLogger(JsoupMain.class.getName()).log(Level.SEVERE, null, ex);
    }
    Elements ele = homePage.getElementsByTag("form");
    Element form = ele.get(0);
    String codAlumnos = form.getElementsByAttributeValue("name", "alumnos").attr("value");
    String [] alumnos = codAlumnos.split("#");
    Elements tds = form.getElementsByAttributeValue("width", "250");
    tds = form.select("td > a");

    Element td = null;
    for (int i = 0; i < alumnos.length; i++) {
      td = tds.get(i);
      Alumno a = new Alumno( alumnos[i],td.text());
      asig.getAlumnos().put(a.getId(), a);
    }

  }

  public static void meterIncidencia(Connection.Response response, String alumnos[], String codAlumnos) {
    Connection con = Jsoup.connect("https://gestiona.madrid.org/wafd/IntroIncidenciasCentro.icm")
            .data("optionsProfesores", "null")
            .data("codigoAsignatura", "0486")
            .data("codigoIdioma", "")
            .data("codigoReligion", "")
            .data("esGrupoMateria", "false")
            .data("codigoSesion", "1")
            .data("diaSemana", "J")
            .data("nombreProfesor", "OSCAR NOVILLO CAMACHO")
            //.data("franja","08:30:09:25")
            .data("haySesiones", "0")
            .data("sesion", "1")
            .data("grupo", "2DAM")
            .data("asignatura", "Acceso a datos")
            .data("pantallaIncidencia", "S")
            .data("profesor", "51946107D")
            .data("dia", "08/10/2014")
            .data("alumnos", codAlumnos);

    for (int i = 0; i < alumnos.length; i++) {
      if (i == 22) {
        con.data("asistencia" + alumnos[i], "F")
                .data("amonestacion" + alumnos[i], "-1")
                .data("dirty" + alumnos[i], "S");
      } else {
        con.data("asistencia" + alumnos[i], "-1")
                .data("amonestacion" + alumnos[i], "-1")
                .data("dirty" + alumnos[i], "S");
      }
    }
    try {
      con.cookies(response.cookies())
              .post();
    } catch (IOException ex) {
      Logger.getLogger(JsoupMain.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

}
