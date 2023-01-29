package com.example.accountbook.service

import com.example.accountbook.entity.MainCategoryDto
import com.example.accountbook.model.MainCategory
import com.example.accountbook.repository.MainCategoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class MainCategoryService(private val mainCategoryRepository: MainCategoryRepository) {

    fun getMainCategoryAll(): List<MainCategory> {
        return mainCategoryRepository.findAll()
    }

    fun getMainCategoryPage(pageable: Pageable, order: String, sort: String): List<MainCategory> {
        val pageRequest =
            PageRequest.of(pageable.pageNumber - 1, pageable.pageSize, Sort.Direction.valueOf(order), sort)
        return mainCategoryRepository.findAll(pageRequest).toList()
    }

    fun getMainCategory(id: Long): Optional<MainCategory> {
        return mainCategoryRepository.findById(id)
    }

    fun countMainCategory(): Long {
        return mainCategoryRepository.countBy()
    }

    fun getMainCategoryByName(name: String): Optional<MainCategory> {
        return mainCategoryRepository.findByName(name)
    }

    fun createMainCategory(mainCategoryDto: MainCategoryDto): MainCategory {

        val constructor = MainCategory::class.java.getConstructor()
        val mainCategory = constructor.newInstance() as MainCategory
        mainCategory.name = mainCategoryDto.name

        mainCategoryRepository.save(mainCategory)
        return mainCategory
    }

    @Transactional
    fun updateMainCategory(id: Long, mainCategoryDto: MainCategoryDto): MainCategory {
        val mainCategory = mainCategoryRepository.findById(id) ?: throw Exception("메인 카테고리가 존재하지 않습니다.")
        mainCategory.get().name = mainCategoryDto.name

        return mainCategory.get()
    }

    fun deleteMainCategory(id: Long) {
        mainCategoryRepository.deleteById(id)
    }

}
