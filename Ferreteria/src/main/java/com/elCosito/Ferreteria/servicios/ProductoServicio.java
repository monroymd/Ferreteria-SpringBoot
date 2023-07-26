package com.elCosito.Ferreteria.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elCosito.Ferreteria.Excepciones.MiException;
import com.elCosito.Ferreteria.entidades.Fabricante;
import com.elCosito.Ferreteria.entidades.Producto;
import com.elCosito.Ferreteria.enumeraciones.Tipo;
import com.elCosito.Ferreteria.repositorio.FabricanteRepositorio;
import com.elCosito.Ferreteria.repositorio.ProductoRepositorio;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private FabricanteRepositorio fabricanteRepositorio;

    @Transactional
    public void cargarProducto(String nombre, Integer cantidad, Tipo tipo, String idFabricante) throws MiException {

        Producto producto = new Producto();
        Fabricante fabricante = new Fabricante();

        validarDatos(nombre, cantidad, tipo, idFabricante);

        Optional<Fabricante> respuesta = fabricanteRepositorio.findById(idFabricante);
        if (respuesta.isPresent()) {
            fabricante = respuesta.get();
        }
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setTipo(tipo);
        producto.setFabricante(fabricante);
        productoRepositorio.save(producto);
    }

    private void validarDatos(String nombre, Integer cantidad, Tipo tipo, String idFabricante) throws MiException {
        if (nombre.equals(null)) {
            throw new MiException("El nombre del producto no puede estar vacio");
        }
        if (cantidad == null) {
            throw new MiException("La cantidad no puede estar vacia");
        }
        if (tipo.equals(null)) {
            throw new MiException("El Tipo del producto no puede estar vacio");
        }
        if (idFabricante.equals(null)) {
            throw new MiException("El nombre del fabricante no puede estar vacio");
        }
    }

    @Transactional
    public void modificarProducto(String id, String nombre, Integer cantidad, Tipo tipo, String idFabricante)
            throws MiException {

        validarDatos(nombre, cantidad, tipo, idFabricante);
        Optional<Producto> respuesta = productoRepositorio.findById(id);
        Optional<Fabricante> respuestaFabricante = fabricanteRepositorio.findById(idFabricante);
        if (respuestaFabricante.isPresent()) {
            Fabricante fabricante = respuestaFabricante.get();

            if (respuesta.isPresent()) {
                Producto producto = respuesta.get();
                producto.setNombre(nombre);
                producto.setCantidad(cantidad);
                producto.setTipo(tipo);
                producto.setFabricante(fabricante);
                productoRepositorio.save(producto);
            }
        }
    }

    public List<Producto> mostrarProductos() {
        List<Producto> productos = new ArrayList<Producto>(productoRepositorio.findAll());
        return productos;
    }

    @Transactional
    public void eliminarProducto(String id) throws MiException {
        Optional<Producto> respuesta = productoRepositorio.findById(id);
        if (respuesta.isEmpty()) {
            throw new MiException("El id del producto no existe");
        }
        Producto producto = respuesta.get();
        productoRepositorio.delete(producto);

    }

    public Producto mostrarProducto(String id) {
        return productoRepositorio.getReferenceById(id);
    }

}
