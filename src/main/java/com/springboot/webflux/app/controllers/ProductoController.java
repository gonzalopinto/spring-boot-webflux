package com.springboot.webflux.app.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.springboot.webflux.app.daos.ProductoDao;
import com.springboot.webflux.app.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {

	private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoDao productoDao;

	@GetMapping(
	{
			"/listar", "/"
	})
	public String listar(Model m)
	{
		Flux<Producto> productos = productoDao.findAll().map(p ->
		{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		});

		productos.subscribe(p -> log.info(p.getNombre()));

		m.addAttribute("productos", productos);
		m.addAttribute("titulo", "Listado de productos");
		return "listar";
	}

	@GetMapping("/listar-datadriver")
	public String listarDataDriver(Model m)
	{
		Flux<Producto> productos = productoDao.findAll().map(p ->
		{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		}).delayElements(Duration.ofSeconds(1));

		productos.subscribe(p -> log.info(p.getNombre()));

		m.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 1));
		m.addAttribute("titulo", "Listado de productos");
		return "listar";
	}

	@GetMapping("/listar-full")
	public String listarFull(Model m)
	{
		Flux<Producto> productos = productoDao.findAll().map(p ->
		{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		}).repeat(5000);

		productos.subscribe(p -> log.info(p.getNombre()));

		m.addAttribute("productos", productos);
		m.addAttribute("titulo", "Listado de productos");
		return "listar";
	}

	@GetMapping("/listar-chunked")
	public String listarChunked(Model m)
	{
		Flux<Producto> productos = productoDao.findAll().map(p ->
		{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		}).repeat(5000);

		productos.subscribe(p -> log.info(p.getNombre()));

		m.addAttribute("productos", productos);
		m.addAttribute("titulo", "Listado de productos");
		return "listar-chunked";
	}

}
