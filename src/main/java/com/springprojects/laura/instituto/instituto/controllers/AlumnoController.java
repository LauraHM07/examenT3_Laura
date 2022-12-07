package com.springprojects.laura.instituto.instituto.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springprojects.laura.instituto.instituto.model.Alumno;
import com.springprojects.laura.instituto.instituto.services.AlumnosService;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {
    
    @Autowired
    AlumnosService alumnosService;

    @Value("${pagination.size}")
    int sizePage;

    @GetMapping(value = "/list")
    public ModelAndView list(Model model){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:list/1/codigo/asc");

        return modelAndView;
    }

    @GetMapping(value = "/list/{numPage}/{fieldSort}/{directionSort}")
    public ModelAndView listPage(Model model,
            @PathVariable("numPage") Integer numPage,
            @PathVariable("fieldSort") String fieldSort,
            @PathVariable("directionSort") String directionSort) {


        Pageable pageable = PageRequest.of(numPage - 1, sizePage,
            directionSort.equals("asc") ? Sort.by(fieldSort).ascending() : Sort.by(fieldSort).descending());

        Page<Alumno> page = alumnosService.findAll(pageable);

        List<Alumno> alumnos = page.getContent();

        ModelAndView modelAndView = new ModelAndView("alumnos/list");
        modelAndView.addObject("alumnos", alumnos);


        modelAndView.addObject("numPage", numPage);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalElements", page.getTotalElements());

        modelAndView.addObject("fieldSort", fieldSort);
        modelAndView.addObject("directionSort", directionSort.equals("asc") ? "asc" : "desc");

        return modelAndView;
    }

    @GetMapping(path = { "/edit" })
    public ModelAndView edit(
            @RequestParam(name = "codigo", required = true) int codigo) {

        Alumno alumno = alumnosService.findById(codigo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("alumno", alumno);
        modelAndView.setViewName("alumnos/edit");

        return modelAndView;
    }

    @GetMapping(path = { "/create" })
    public ModelAndView create(Alumno alumno) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("alumno", new Alumno());
        modelAndView.setViewName("alumnos/new");

        return modelAndView;
    }

    @PostMapping(path = { "/save" })
    public ModelAndView save(Alumno alumno, @RequestParam("imagenForm") MultipartFile multipartFile)
            throws IOException {

        byte[] imagen = multipartFile.getBytes();

        alumno.setFoto(imagen);

        alumnosService.insert(alumno);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:edit?codigo=" + alumno.getCodigo());
        return modelAndView;
    }

    @PostMapping(path = { "/update" })
    public ModelAndView update(Alumno alumno, @RequestParam("imagenForm") MultipartFile multipartFile) throws IOException {

        byte[] imagen = multipartFile.getBytes();

        alumno.setFoto(imagen);
        
        alumnosService.update(alumno);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:edit?codigo=" + alumno.getCodigo());

        return modelAndView;
    }

    @GetMapping(path = { "/delete/{codigo}" })
    public ModelAndView delete(
            @PathVariable(name = "codigo", required = true) int codigo) {

        alumnosService.delete(codigo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/alumnos/list");

        return modelAndView;
    }
}
