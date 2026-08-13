package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.dto.SupplierRequest;
import com.store.conveniencestore.entity.Supplier;
import com.store.conveniencestore.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@Tag(
        name = "供应商管理",
        description = "供应商的查询、新增、修改和删除接口"
)
@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Operation(
            summary = "查询全部供应商",
            description = "查询并返回系统中的全部供应商"
    )
    @GetMapping
    public ApiResponse<List<Supplier>> findAll() {

        List<Supplier> suppliers =
                supplierService.findAll();

        return ApiResponse.success(suppliers);
    }

    @Operation(
            summary = "根据编号查询供应商",
            description = "根据供应商编号查询对应的供应商信息"
    )
    @GetMapping("/{id}")
    public ApiResponse<Supplier> findById(
            @Parameter(description = "供应商编号", example = "1")
            @PathVariable @Positive(message = "供应商编号必须大于0") Integer id) {

        Supplier supplier =
                supplierService.findById(id);

        return ApiResponse.success(supplier);
    }

    @Operation(
            summary = "新增供应商",
            description = "接收供应商信息并创建一个新供应商"
    )
    @PostMapping
    public ApiResponse<Supplier> insert(
            @Valid @RequestBody SupplierRequest request) {

        Supplier supplier = new Supplier();

        supplier.setName(request.name());
        supplier.setContact(request.contact());
        supplier.setPhone(request.phone());
        supplier.setAddress(request.address());

        supplierService.insert(supplier);

        return ApiResponse.success(supplier);
    }


    @Operation(
            summary = "修改供应商",
            description = "根据供应商编号修改已有供应商的信息"
    )
    @PutMapping("/{id}")
    public ApiResponse<Supplier> update(
            @Parameter(description = "需要修改的供应商编号", example = "1")
            @PathVariable @Positive(message = "供应商编号必须大于0") Integer id,
            @Valid @RequestBody SupplierRequest request) {

        Supplier supplier = new Supplier();

        supplier.setId(id);
        supplier.setName(request.name());
        supplier.setContact(request.contact());
        supplier.setPhone(request.phone());
        supplier.setAddress(request.address());

        supplierService.update(supplier);

        Supplier updatedSupplier =
                supplierService.findById(id);

        return ApiResponse.success(updatedSupplier);
    }

    @Operation(
            summary = "删除供应商",
            description = "根据供应商编号删除对应的供应商"
    )
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @Parameter(description = "需要删除的供应商编号", example = "1")
            @PathVariable @Positive(message = "供应商编号必须大于0") Integer id) {

        supplierService.delete(id);

        return ApiResponse.success(null);
    }
}