/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoup;

/**
 *
 * @author oscar
 */
public class SesionAsignatura {
  
  private String id;
   private String diaSemana;
  private String sesion;
  private String horaInicio;
  private String horafin;

  public SesionAsignatura(String diaSemana, String sesion, String horaInicio, String horafin) {
    this.diaSemana = diaSemana;
    this.sesion = sesion;
    this.horaInicio = horaInicio;
    this.horafin = horafin;
    this.id = this.diaSemana + "$" +this.sesion;
  }

  public String getId() {
    return id;
  }

  public String getDiaSemana() {
    return diaSemana;
  }

  public int getDiaSemanaCalendar() {
    int dia =0;
    switch (this.diaSemana)
    {
      case "D": dia = 1; break;
      case "L": dia = 2; break;
      case "M": dia = 3; break;
      case "X": dia = 4; break;
      case "J": dia = 5; break;
      case "V": dia = 6; break;
      case "S": dia = 7; break;
            
    }
    return dia;
  }
  
  public String getSesion() {
    return sesion;
  }

  public String getHoraInicio() {
    return horaInicio;
  }

  public String getHorafin() {
    return horafin;
  }

  @Override
  public String toString() {
    return "SesionAsignatura{" + "id=" + id + ", diaSemana=" + diaSemana + ", sesion=" + sesion + ", horaInicio=" + horaInicio + ", horafin=" + horafin + '}';
  }

  
  
}
