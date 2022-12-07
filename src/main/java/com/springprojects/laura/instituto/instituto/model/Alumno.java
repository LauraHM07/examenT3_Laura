package com.springprojects.laura.instituto.instituto.model;

import com.springprojects.laura.instituto.instituto.utils.ImageUtil;

public class Alumno {
    private int codigo;
    private String nombre;
    private String apellidos;
    private String dni;
    private String fechaNacimiento;
    private String email;
    private boolean nuevo;
    private byte[] foto;
    
    public Alumno() {
    }

    public Alumno(int codigo) {
        this.codigo = codigo;
    }

    public Alumno(int codigo, String nombre, String apellidos, String dni, String fechaNacimiento, String email,
            boolean nuevo, byte[] foto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.nuevo = nuevo;
        this.foto = foto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getImageView() {
        return ImageUtil.getImgData(this.foto);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigo;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alumno other = (Alumno) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }
}
