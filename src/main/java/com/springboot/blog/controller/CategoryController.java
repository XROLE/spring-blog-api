package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategorySerrvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategorySerrvice categorySerrvice;

    public CategoryController(CategorySerrvice categorySerrvice) {
        this.categorySerrvice = categorySerrvice;
    }

    @PostMapping

   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
       CategoryDto savedCategory = categorySerrvice.addCategory(categoryDto);
       return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
   }

   @GetMapping("{id}")
   public ResponseEntity<CategoryDto> getCategory(@PathVariable("id")  Long categoryId) {
        CategoryDto response = categorySerrvice.getCategory(categoryId);

        return  ResponseEntity.ok(response);
   }
   @GetMapping
   public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categorySerrvice.getAllCategories();

        return  ResponseEntity.ok(categories);
   }

   @PreAuthorize("hasRole('ADMIN')")
   @PutMapping("{id}")
   public ResponseEntity<CategoryDto> udateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") Long categoryId) {
    CategoryDto response = categorySerrvice.updateCategory(categoryDto, categoryId);

    return ResponseEntity.ok(response);
   }

@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("{id}")
   public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId) {
    categorySerrvice.deleteCategory(categoryId);

    return ResponseEntity.ok("Category deleted successfuly");
   }
}
