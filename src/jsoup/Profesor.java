/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoup;

/**
 *
 * @author profesor
 */
public class Profesor {
    
    String username;
    String password;
    String nombre;
    String DNI;

    public Profesor(String username, String password, String nombre, String DNI) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.DNI = DNI;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
    
    
}
