package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.Supplier;
import com.store.conveniencestore.exception.ResourceNotFoundException;
import com.store.conveniencestore.mapper.SupplierMapper;
import com.store.conveniencestore.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierMapper supplierMapper;

    public SupplierServiceImpl(SupplierMapper supplierMapper) {this.supplierMapper = supplierMapper;}

    @Override
    public List<Supplier> findAll(){
        return supplierMapper.findAll();
    }

    @Override
    public Supplier findById(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "供应商编号必须大于0"
            );
        }

        Supplier supplier = supplierMapper.findById(id);

        if (supplier == null) {
            throw new ResourceNotFoundException(
                    "供应商不存在，供应商编号：" + id
            );
        }

        return supplier;
    }

    @Override
    public void insert(Supplier supplier){
        supplierMapper.insert(supplier);
    }

    @Override
    public void update(Supplier supplier){
        supplierMapper.update(supplier);
    }

    @Override
    public void delete(Integer id) {
        supplierMapper.delete(id);
    }


}
