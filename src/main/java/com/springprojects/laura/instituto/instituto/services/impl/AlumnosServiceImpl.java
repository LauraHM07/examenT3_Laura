package com.springprojects.laura.instituto.instituto.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springprojects.laura.instituto.instituto.dao.AlumnosDAO;
import com.springprojects.laura.instituto.instituto.model.Alumno;
import com.springprojects.laura.instituto.instituto.services.AlumnosService;

@Service
public class AlumnosServiceImpl implements AlumnosService{
    @Autowired
    AlumnosDAO alumnosDAO;

    @Override
    public Page<Alumno> findAll(Pageable page) {
        return alumnosDAO.findAll(page);
    }

    @Override
    public Alumno findById(int codigo) {
        return alumnosDAO.findById(codigo);
    }

    @Override
    public void insert(Alumno alumno){
        alumnosDAO.insert(alumno);
    }

    @Override
    public void update(Alumno alumno) {
        alumnosDAO.update(alumno);

        if(alumno.getFoto() != null && alumno.getFoto().length > 0) {
            alumnosDAO.updateImage(alumno);
        } 
    }

    @Override
    public void delete(int codigo){
        alumnosDAO.delete(codigo);
    }
}
