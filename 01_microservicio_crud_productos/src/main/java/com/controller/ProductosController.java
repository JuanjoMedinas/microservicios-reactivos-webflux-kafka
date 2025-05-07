package com.controller;

import com.model.Producto;
import com.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductosController {
    @Autowired
    ProductosService productosService;
    @GetMapping(value = "productos",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Producto> productos(){
        return productosService.catalogo();
    }
    @GetMapping(value = "productos/{categoria}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Producto> productosCategoria(@PathVariable("categoria") String categoria){
        return productosService.productosCategoria(categoria);
    }
    @GetMapping(value = "producto")
    public Producto productoCodigo(@RequestParam("cod") int cod){
        return productosService.productoCodigo(cod);
    }
    @PostMapping(value = "alta",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void altaProducto(@RequestBody Producto producto){
        productosService.altaProducto(producto);
    }
    @DeleteMapping(value = "eliminar")
    public Producto eliminarProducto(@RequestParam("cod") int cod){
        return productosService.eliminarProducto(cod);
    }
    @PutMapping(value = "actualizar")
    public Producto actualizarPrecio(@RequestParam("cod") int cod,@RequestParam("precio") double precio){
        return productosService.actualizarPrecio(cod,precio);
    }
}
