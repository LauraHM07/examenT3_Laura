package com.springprojects.laura.instituto.instituto.dao.impl;

import com.springprojects.laura.instituto.instituto.dao.AlumnosDAO;
import com.springprojects.laura.instituto.instituto.dao.mappers.AlumnoMapper;
import com.springprojects.laura.instituto.instituto.model.Alumno;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class AlumnosDAOImpl extends JdbcDaoSupport implements AlumnosDAO {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public PageImpl<Alumno> findAll(Pageable page) {

    
        String queryCount = "select count(1) from alumnos";
        Integer total = getJdbcTemplate().queryForObject(queryCount,Integer.class);


        Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Order.by("codigo");

        String query = "SELECT * FROM alumnos ORDER BY " + order.getProperty() + " "
        + order.getDirection().name() + " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset();

        final List<Alumno> alumnos = getJdbcTemplate().query(query, new RowMapper<Alumno>() {

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
            
        });

        return new PageImpl<Alumno>(alumnos, page, total);

    }

    @Override
    public Alumno findById(int codigo) {

        String query = "select * from alumnos where codigo = ?";

        Object params [] = {codigo};
        int types [] = {Types.INTEGER};

        Alumno alumno = (Alumno) getJdbcTemplate().queryForObject(query, params, types, new AlumnoMapper());

        return alumno;
    }

    @Override
    public void insert(Alumno alumno) {

        String query = "insert into alumnos (nombre," + 
                                            " apellidos," + 
                                            " dni," + 
                                            " fecha_nac," + 
                                            " email," + 
                                            " nuevo," + 
                                            " foto)" + 
                                            " values (?, ?, ?, ?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, alumno.getNombre());
                ps.setString(2, alumno.getApellidos());
                ps.setString(3, alumno.getDni());
                ps.setString(4, alumno.getFechaNacimiento());
                ps.setString(5, alumno.getEmail());
                ps.setBoolean(6, alumno.isNuevo());
                InputStream is = new ByteArrayInputStream(alumno.getFoto());
                
                ps.setBlob(7, is);
                return ps;
            }
        }, keyHolder);

        alumno.setCodigo(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Alumno alumno) {

        String query = "update alumnos set nombre = ?," +
                                            " apellidos = ?," + 
                                            " DNI = ?," + 
                                            " fecha_nac = ?," + 
                                            " email = ?," +
                                            " nuevo = ?" +
                                            " where codigo = ?";

        Object[] params = {
            alumno.getNombre(),
            alumno.getApellidos(),
            alumno.getDni(),
            alumno.getFechaNacimiento(),
            alumno.getEmail(),
            alumno.isNuevo(),
            alumno.getCodigo()
        };

        final int[] types = {
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.BOOLEAN,
            Types.INTEGER
        };

        int update = getJdbcTemplate().update(query, params, types);
    }

    @Override
    public void updateImage(Alumno alumno) {

        String query = "update alumnos set foto = ?" +
                                            " where codigo = ?";

        Object[] params = {
            alumno.getFoto(),
            alumno.getCodigo()
        };

        final int[] types = {
            Types.BLOB,
            Types.INTEGER
        };

        int update = getJdbcTemplate().update(query, params, types);
    }

    @Override
    public void delete(int codigo) {

        String query = "delete from alumnos where codigo = ?";

        Object[] params = {
            codigo
        };

        final int[] types = {
            Types.INTEGER
        };

        int update = getJdbcTemplate().update(query, params, types);
    }
}
