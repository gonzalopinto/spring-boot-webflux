package com.springboot.webflux.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.webflux.app.daos.ProductoDao;
import com.springboot.webflux.app.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

	private static final Logger log = LoggerFactory.getLogger(ProductoRestController.class);

	@Autowired
	private ProductoDao productoDao;

	@GetMapping
	public Flux<Producto> index()
	{
		Flux<Producto> productos = productoDao.findAll().map(p ->
		{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		}).doOnNext(p -> log.info(p.getNombre()));

		return productos;
	}

	@GetMapping("/{id}")
	public Mono<Producto> show(@PathVariable String id)
	{
		// Mono<Producto> producto = productoDao.findById(id);
		Flux<Producto> productos = productoDao.findAll();

		Mono<Producto> producto = productos.filter(p -> p.getId().equals(id)).next()
				.doOnNext(p -> log.info(p.getNombre()));
		return producto;
	}
}
