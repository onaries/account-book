package com.example.accountbook.controller

import com.example.accountbook.entity.MainCategoryDto
import com.example.accountbook.model.MainCategory
import com.example.accountbook.service.MainCategoryService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class MainCategoryController(private val mainCategoryService: MainCategoryService) {

    @GetMapping("/api/main-category/all")
    fun getMainCategoryList(): ResponseEntity<List<MainCategory>> {
        val hearers = HttpHeaders()
        hearers.add("X-Total-Count", mainCategoryService.countMainCategory().toString())

        return ResponseEntity.ok().headers(hearers).body(mainCategoryService.getMainCategoryAll())
    }

    @GetMapping("/api/main-category")
    fun getMainCategoryListPage(pageable: Pageable, order: String, sort: String): ResponseEntity<List<MainCategory>> {
        val hearers = HttpHeaders()
        hearers.add("X-Total-Count", mainCategoryService.countMainCategory().toString())

        return ResponseEntity.ok().headers(hearers).body(mainCategoryService.getMainCategoryPage(pageable, order, sort))
    }


    @GetMapping("/api/main-category/{id}")
    fun getMainCategory(@PathVariable id: Long): Optional<MainCategory> {
        return mainCategoryService.getMainCategory(id)
    }

    @PostMapping("/api/main-category")
    fun postMainCategory(@RequestBody mainCategoryDto: MainCategoryDto): MainCategory {
        return mainCategoryService.createMainCategory(mainCategoryDto)
    }

    @PutMapping("/api/main-category/{id}")
    fun putMainCategory(@PathVariable id: Long, @RequestBody mainCategoryDto: MainCategoryDto): MainCategory {
        return mainCategoryService.updateMainCategory(id, mainCategoryDto)
    }

    @DeleteMapping("/api/main-category/{id}")
    fun deleteMainCategory(@PathVariable id: Long) {
        mainCategoryService.deleteMainCategory(id)
    }
}
