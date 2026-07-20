package com.store.conveniencestore.controller;


import com.store.conveniencestore.entity.Supplier;
import com.store.conveniencestore.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<Supplier> findAll(){
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    public Supplier findById (@PathVariable Integer id){
        return supplierService.findById(id);
    }

    @PostMapping
    public Supplier insert(@RequestBody Supplier supplier){
        supplierService.insert(supplier);
        return supplier;
    }

    @PutMapping("/{id}")
    public Supplier update(@PathVariable Integer id, @RequestBody Supplier supplier){
        supplier.setId(id);
        supplierService.update(supplier);
        return supplierService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        supplierService.delete(id);
    }
}
