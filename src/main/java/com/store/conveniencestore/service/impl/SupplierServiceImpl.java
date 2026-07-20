package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.Supplier;
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
    public Supplier findById(Integer id){
        return supplierMapper.findById(id);
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
