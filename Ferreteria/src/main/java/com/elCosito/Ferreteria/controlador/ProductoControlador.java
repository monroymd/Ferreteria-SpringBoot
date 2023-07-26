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
import com.elCosito.Ferreteria.entidades.Producto;
import com.elCosito.Ferreteria.enumeraciones.Tipo;
import com.elCosito.Ferreteria.servicios.FabricanteServicio;
import com.elCosito.Ferreteria.servicios.ProductoServicio;

@Controller
@RequestMapping("/producto")
public class ProductoControlador {

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    FabricanteServicio fabricanteServicio;

    @GetMapping("")
    public String mostrarProducto() {
        return "inicioProducto.html";
    }

    @GetMapping("/nuevoProducto")
    public String crearProducto(ModelMap modelo) {
        List<Fabricante> listaFabricantes = fabricanteServicio.listaFabricantes();

        Tipo[] tipos = Tipo.values();
        modelo.addAttribute("tipo", tipos);
        modelo.addAttribute("listaFabricantes", listaFabricantes);

        return "cargaProducto.html";
    }

    @PostMapping("/cargaProducto")
    public String creacionFabricante(@RequestParam String nombre,
            @RequestParam Integer cantidad, @RequestParam Tipo tipo,
            @RequestParam String idFabricante) throws MiException {
        productoServicio.cargarProducto(nombre, cantidad, tipo, idFabricante);
        return "redirect:/producto";
    }

    @GetMapping("/verProductos")
    public String verProductos(ModelMap modelo) {
        List<Producto> listaProductos = productoServicio.mostrarProductos();
        modelo.addAttribute("listaProductos", listaProductos);
        return "listaProductos.html";
    }

    @GetMapping("/verProductosPorFabricante")
    public String verProductosPorFabricante(@RequestParam Fabricante fabricante, ModelMap modelo) {
        List<Fabricante> listaFabricantes = fabricanteServicio.listaFabricantes();
        // List<Producto> listaProductosPorFabricante =
        // productoRepositorio.productosPorFabricante(fabricante.getNombre());
        modelo.addAttribute("listaFabricantes", listaFabricantes);
        // modelo.addAttribute("listaProductosPorFabricante",
        // listaProductosPorFabricante);
        return "listaProductosPorFabricante.html";
    }

    @GetMapping("/corregir/{id}")
    public String modificarProducto(@PathVariable String id, ModelMap modelo) {
        List<Fabricante> listaFabricantes = fabricanteServicio.listaFabricantes();
        Tipo[] tipos = Tipo.values();
        modelo.addAttribute("tipo", tipos);
        modelo.put("producto", productoServicio.mostrarProducto(id));
        modelo.addAttribute("listaFabricantes", listaFabricantes);
        return "corregirProducto.html";
    }

    @PostMapping("/corregirProducto/{id}")
    public String modificadoProducto(@PathVariable String id, String nombre, Integer cantidad, Tipo tipo,
            String idFabricante) throws MiException {
        productoServicio.modificarProducto(id, nombre, cantidad, tipo, idFabricante);
        return "redirect:/producto/verProductos";
    }

    @GetMapping("/borrarProducto/{id}")
    public String borrarProducto(@PathVariable String id, ModelMap modelo) throws MiException {

        productoServicio.eliminarProducto(id);
        return "redirect:/producto/verProductos";
    }

    // @PostMapping("/borrar/{id}")
    // public String borrar(@PathVariable String id) throws MiException {

    // return "redirect:/producto/verProductos";
    // }

}
