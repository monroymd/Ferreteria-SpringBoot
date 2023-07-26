package com.elCosito.Ferreteria.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elCosito.Ferreteria.entidades.Producto;
import com.elCosito.Ferreteria.enumeraciones.Tipo;

public interface ProductoRepositorio extends JpaRepository<Producto, String> {

    @Query("SELECT p FROM Producto p WHERE p.tipo = :tipo")
    public List<Producto> productosPorTipo(@Param("tipo") Tipo tipo);

    @Query("SELECT p FROM Producto p WHERE p.fabricante.nombre = :nombre")
    public List<Producto> productosPorFabricante(@Param("nombre") String fabricante);

}
