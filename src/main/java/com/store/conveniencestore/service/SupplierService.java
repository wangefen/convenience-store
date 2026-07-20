package com.store.conveniencestore.service;

import com.store.conveniencestore.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> findAll();

    Supplier findById(Integer id);

    void insert(Supplier supplier);

    void update(Supplier supplier);

    void delete(Integer id);
}
