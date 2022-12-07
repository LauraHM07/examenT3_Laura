package com.springprojects.laura.instituto.instituto.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.springprojects.laura.instituto.instituto.model.Alumno;

public class AlumnoMapper implements RowMapper<Alumno> {
    @Override
    @Nullable
    public Alumno mapRow(ResultSet rs, int rowNum) throws SQLException {
        Alumno alumno = new Alumno();

        alumno.setCodigo(rs.getInt("codigo"));
        alumno.setNombre(rs.getString("nombre"));
        alumno.setApellidos(rs.getString("apellidos"));
        alumno.setDni(rs.getString("dni"));
        alumno.setFechaNacimiento(rs.getString("fecha_nac"));
        alumno.setEmail(rs.getString("email"));
        alumno.setNuevo(rs.getBoolean("nuevo"));
        alumno.setFoto(rs.getBytes("foto"));

        return alumno;
    }
}
