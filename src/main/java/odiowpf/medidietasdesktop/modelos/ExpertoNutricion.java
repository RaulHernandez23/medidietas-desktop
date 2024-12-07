package odiowpf.medidietasdesktop.modelos;

import java.util.Date;

public class ExpertoNutricion {
    private int id;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String contrasena;
    private String correo;
    private Date fecha_nacimiento;
    private String foto;
    private String educacion;
    private String perfilProfesional;

    public ExpertoNutricion (int id, String nombre, String apellido_paterno, String apellido_materno, String contrasena,
                             String correo, Date fecha_nacimiento, String foto, String educacion,
                             String perfilProfesional) {
        this.id = id;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.contrasena = contrasena;
        this.correo = correo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.foto = foto;
        this.educacion = educacion;
        this.perfilProfesional = perfilProfesional;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidoPaterno() { return apellido_paterno; }
    public String getApellidoMaterno() { return apellido_materno; }
    public String getContrasena() { return contrasena; }
    public String getCorreo() { return correo; }
    public Date getFechaNacimiento() { return fecha_nacimiento; }
    public String getFoto() { return foto; }
    public String getEducacion() { return educacion; }
    public String getPerfilProfesional() { return perfilProfesional; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidoPaterno(String apellido_paterno) { this.apellido_paterno = apellido_paterno; }
    public void setApellidoMaterno(String apellido_materno) { this.apellido_materno = apellido_materno; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setFechaNacimiento(Date fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }
    public void setFoto(String foto) { this.foto = foto; }
    public void setEducacion(String educacion) { this.educacion = educacion; }
    public void setPerfilProfesional(String perfilProfesional) { this.perfilProfesional = perfilProfesional; }
}
