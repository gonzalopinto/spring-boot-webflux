package com.springboot.webflux.app.daos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springboot.webflux.app.documents.Producto;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {

}
