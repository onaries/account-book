package com.example.accountbook.controller

import com.example.accountbook.entity.CategoryDto
import com.example.accountbook.model.Category
import com.example.accountbook.service.CategoryService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping("/api/category/all")
    fun getCategoryList(): ResponseEntity<List<Category>> {
        val hearers = HttpHeaders()
        hearers.add("X-Total-Count", categoryService.countCategory().toString())

        return ResponseEntity.ok().headers(hearers).body(categoryService.getCategoryAll())
    }

    @GetMapping("/api/category")
    fun getCategoryListPage(pageable: Pageable, order: String, sort: String): ResponseEntity<List<Category>> {
        val hearers = HttpHeaders()
        hearers.add("X-Total-Count", categoryService.countCategory().toString())

        return ResponseEntity.ok().headers(hearers).body(categoryService.getCategoryList(pageable, order, sort))
    }

    @GetMapping("/api/category/{id}")
    fun getCategory(@PathVariable id: Long): Optional<Category> {
        return categoryService.getCategory(id)
    }

    @PostMapping("/api/category")
    fun postCategory(@RequestBody categoryDto: CategoryDto): Category {
        return categoryService.createCategory(categoryDto)
    }

    @PutMapping("/api/category/{id}")
    fun putCategory(@PathVariable id: Long, @RequestBody @Valid categoryDto: CategoryDto): Category {
        return categoryService.updateCategory(id, categoryDto)
    }

    @DeleteMapping("/api/category/{id}")
    fun deleteCategory(@PathVariable id: Long) {
        categoryService.deleteCategory(id)
    }
}
