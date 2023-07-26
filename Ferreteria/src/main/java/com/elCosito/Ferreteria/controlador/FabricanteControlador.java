package com.elCosito.Ferreteria.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elCosito.Ferreteria.Excepciones.MiException;
import com.elCosito.Ferreteria.entidades.Fabricante;
import com.elCosito.Ferreteria.repositorio.FabricanteRepositorio;
import com.elCosito.Ferreteria.servicios.FabricanteServicio;

@Controller
@RequestMapping("/fabricante")
public class FabricanteControlador {

    @Autowired
    private FabricanteServicio fabricanteServicio;

    @Autowired
    private FabricanteRepositorio fabricanteRepositorio;

    // @GetMapping(value = { "/", "fabricante" })
    @GetMapping("")
    public String mostrarfabricante() {
        return "inicioFabricante.html";
    }

    @GetMapping("/nuevoFabricante")
    public String crearFabricante() {
        return "cargaFabricante.html";
    }

    @PostMapping("/cargaFabricante")
    public String creacionFabricante(@RequestParam String nombre) {
        fabricanteServicio.crearFabricante(nombre);
        return "inicioFabricante.html";
    }

    @GetMapping("/verFabricantes")
    public String verFabricantes(ModelMap modelo) {
        List<Fabricante> listaFabricantes = fabricanteServicio.listaFabricantes();
        modelo.addAttribute("listaFabricantes", listaFabricantes);
        return "listaFabricantes.html";
    }

    @GetMapping("/{id}")
    public String modificarFabricante(@PathVariable String id, ModelMap modelo) throws MiException {
        // fabricanteServicio.modificarFabricante(id, nombre);
        modelo.put("fabricante", fabricanteRepositorio.getReferenceById(id));
        return "corregirFabricante.html";
    }

    @PostMapping("/corregirFabricante/{id}")
    public String modificadoFabricante(@PathVariable String id, String nombre) throws MiException {
        fabricanteServicio.modificarFabricante(id, nombre);
        return "redirect:/fabricante/verFabricantes";
    }

    @GetMapping("/borrarFabricante/{id}")
    public String borrarFabricante(@PathVariable String id, ModelMap modelo) throws MiException {

        modelo.put("fabricante", fabricanteRepositorio.getReferenceById(id));
        return "borrarFabricante.html";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable String id) throws MiException {
        fabricanteServicio.borrarFabricante(id);

        return "redirect:/fabricante/verFabricantes";
    }
}
