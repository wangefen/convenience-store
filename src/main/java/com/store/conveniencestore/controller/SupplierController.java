package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
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
    public ApiResponse<List<Supplier>> findAll() {

        List<Supplier> suppliers =
                supplierService.findAll();

        return ApiResponse.success(suppliers);
    }

    @GetMapping("/{id}")
    public ApiResponse<Supplier> findById(
            @PathVariable Integer id) {

        Supplier supplier =
                supplierService.findById(id);

        return ApiResponse.success(supplier);
    }

    @PostMapping
    public ApiResponse<Supplier> insert(
            @RequestBody Supplier supplier) {

        supplierService.insert(supplier);

        return ApiResponse.success(supplier);
    }

    @PutMapping("/{id}")
    public ApiResponse<Supplier> update(
            @PathVariable Integer id,
            @RequestBody Supplier supplier) {

        supplier.setId(id);
        supplierService.update(supplier);

        Supplier updatedSupplier =
                supplierService.findById(id);

        return ApiResponse.success(updatedSupplier);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable Integer id) {

        supplierService.delete(id);

        return ApiResponse.success(null);
    }
}