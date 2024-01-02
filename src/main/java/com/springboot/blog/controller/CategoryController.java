package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategorySerrvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
