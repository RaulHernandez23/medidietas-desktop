package odiowpf.medidietasdesktop.modelos;

public class Alimento {
    private String nombre;
    private int calorias;
    private double carbohidratos;
    private double grasas;
    private double proteinas;
    private String imagen;
    private double tamanoRacion;
    private boolean estado;
    private String marca;
    private String categoria;
    private int idUnidadMedida;

    public Alimento(String nombre, int calorias, double carbohidratos, double grasas, double proteinas, String imagen, double tamanoRacion, boolean estado, String marca, String categoria, int idUnidadMedida) {
        this.nombre = nombre;
        this.calorias = calorias;
        this.carbohidratos = carbohidratos;
        this.grasas = grasas;
        this.proteinas = proteinas;
        this.imagen = imagen;
        this.tamanoRacion = tamanoRacion;
        this.estado = estado;
        this.marca = marca;
        this.categoria = categoria;
        this.idUnidadMedida = idUnidadMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public double getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    public double getProteinas() {
        return proteinas;
    }

    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getTamanoRacion() {
        return tamanoRacion;
    }

    public void setTamanoRacion(double tamanoRacion) {
        this.tamanoRacion = tamanoRacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }
}
