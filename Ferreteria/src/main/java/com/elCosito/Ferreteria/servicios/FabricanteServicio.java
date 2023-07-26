package com.elCosito.Ferreteria.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elCosito.Ferreteria.Excepciones.MiException;
import com.elCosito.Ferreteria.entidades.Fabricante;
import com.elCosito.Ferreteria.repositorio.FabricanteRepositorio;

@Service
public class FabricanteServicio {

    @Autowired
    FabricanteRepositorio fabricanteRepositorio;

    @Transactional
    public void crearFabricante(String nombre) {
        Fabricante fabricante = new Fabricante();
        fabricante.setNombre(nombre);
        fabricanteRepositorio.save(fabricante);
    }

    public List<Fabricante> listaFabricantes() {
        List<Fabricante> listaFabricantes = new ArrayList<Fabricante>();
        listaFabricantes = fabricanteRepositorio.findAll();
        return listaFabricantes;
    }

    private void validarDatos(String nombre, String id) throws MiException {
        if (nombre.equals(null)) {
            throw new MiException("El nombre del fabricante no puede estar vacio");
        }
        if (id.equals(null)) {
            throw new MiException("El ID del fabricante no puede estar vacio");
        }
    }

    @Transactional
    public void modificarFabricante(String id, String nombre) throws MiException {

        validarDatos(id, nombre);
        Optional<Fabricante> respuesta = fabricanteRepositorio.findById(id);
        if (respuesta.isEmpty()) {
            throw new MiException("El fabricante no existe");
        }
        Fabricante fabricante = respuesta.get();
        fabricante.setNombre(nombre);
        fabricanteRepositorio.save(fabricante);

    }

    @Transactional
    public void borrarFabricante(String id) throws MiException {
        Optional<Fabricante> respuesta = fabricanteRepositorio.findById(id);
        if (respuesta.isEmpty()) {
            throw new MiException("El fabricante no existe");
        }
        Fabricante fabricante = respuesta.get();
        fabricanteRepositorio.delete(fabricante);
    }
}
