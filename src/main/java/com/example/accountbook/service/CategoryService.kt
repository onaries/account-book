package com.example.accountbook.service

import com.example.accountbook.entity.CategoryDto
import com.example.accountbook.model.Category
import com.example.accountbook.repository.CategoryRepository
import com.example.accountbook.repository.MainCategoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val mainCategoryRepository: MainCategoryRepository
) {

    fun getCategoryAll(): List<Category> {
        val sort = Sort.by(Sort.Order.asc("mainCategory"), Sort.Order.asc("id"))
        return categoryRepository.findAll(sort)
    }

    fun countCategory(): Long {
        return categoryRepository.countBy()
    }

    fun getCategoryList(pageable: Pageable, order: String, sort: String): List<Category> {
        val pageRequest =
            PageRequest.of(pageable.pageNumber - 1, pageable.pageSize, Sort.Direction.valueOf(order), sort)
        return categoryRepository.findAll(pageRequest).toList()
    }

    fun getCategory(id: Long): Optional<Category> {
        return categoryRepository.findById(id)
    }

    fun getCategoryByName(name: String): Optional<Category> {
        return categoryRepository.findByName(name)
    }

    fun createCategory(categoryDto: CategoryDto): Category {
        val category: Category = Category()
        category.mainCategory =
            mainCategoryRepository.findById(categoryDto.mainCategory).orElseThrow { Exception("메인 카테고리가 존재하지 않습니다.") }
        category.name = categoryDto.name
        category.type = categoryDto.type
        categoryRepository.save(category)
        return category
    }

    @Transactional
    fun updateCategory(id: Long, categoryDto: CategoryDto): Category {
        val category = categoryRepository.findByIdOrNull(id) ?: throw Exception("카테고리가 존재하지 않습니다.")

        category.mainCategory =
            mainCategoryRepository.findById(categoryDto.mainCategory).orElseThrow { Exception("메인 카테고리가 존재하지 않습니다.") }
        category.name = categoryDto.name
        category.type = categoryDto.type

        return category
    }

    fun deleteCategory(id: Long) {
        val category = categoryRepository.findByIdOrNull(id) ?: throw Exception("카테고리가 존재하지 않습니다.")
        categoryRepository.delete(category)
    }

}
