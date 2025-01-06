package com.ecommerce.demo.controller;

import com.ecommerce.demo.common.APIResponse;
import com.ecommerce.demo.model.Category;
import com.ecommerce.demo.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "Category management APIs")
public class CategoryController {

    @Autowired
    CategoryService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new Category", tags = {"category", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Category.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/create")
    public ResponseEntity<APIResponse> createCategory(@RequestBody Category category) {
        service.check(category);
        service.createCategory(category);
        return new ResponseEntity<>(new APIResponse(true, "category created successfully"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    @Operation(summary = "Update a Category", tags = {"category", "post"})
    public ResponseEntity<APIResponse> updateCategory(@PathVariable int id, @RequestBody Category category) {
        if (!service.exist(id)) {
            return new ResponseEntity<>(new APIResponse(false, "category not found"), HttpStatus.NOT_FOUND);
        }
        service.updateCategory(id, category);
        return new ResponseEntity<>(new APIResponse(true, "category updated successfully"), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    @Operation(summary = "Find all categories", tags = {"category", "get"})
    public @ResponseBody List<Category> findAll() {
        return service.findAll();
    }

    @GetMapping
    @Operation(summary = "Find by name", tags = {"category", "get"})
    public @ResponseBody Category findByName(@RequestParam String name) {
        return service.findByName(name);
    }


}
