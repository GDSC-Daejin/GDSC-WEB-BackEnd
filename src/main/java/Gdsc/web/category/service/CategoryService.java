package Gdsc.web.category.service;

import Gdsc.web.category.dto.CategoryRequestDto;
import Gdsc.web.category.dto.CategoryUpdateDto;
import Gdsc.web.category.entity.Category;
import Gdsc.web.category.repository.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final JpaCategoryRepository jpaCategoryRepository;
    @Transactional(readOnly = true)
    public List<Category> 카테고리목록(){
        return jpaCategoryRepository.findAll();
    }

    @Transactional
    public void 카테고리추가(CategoryRequestDto CategoryRequestDto) {
        validateDuplicateCategoryName(CategoryRequestDto.getCategoryName());
        Category category = new Category();
        category.setCategoryName(CategoryRequestDto.getCategoryName());
        jpaCategoryRepository.save(category);
    }
    @Transactional
    public void 카테고리삭제(Category category){
        jpaCategoryRepository.deleteByCategoryName(category.getCategoryName());
    }
    @Transactional
    public void 카테고리업데이트(CategoryUpdateDto categoryUpdateDto){
        Category category = jpaCategoryRepository.findByCategoryName(categoryUpdateDto.getCategoryName()).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다."));

        validateDuplicateCategoryName(categoryUpdateDto.getUpdateCategoryName());
        category.setCategoryName(categoryUpdateDto.getUpdateCategoryName());
    }
    private void validateDuplicateCategoryName(String categoryName) {
        jpaCategoryRepository.findByCategoryName(categoryName)
                .ifPresent(m-> {
                    throw new IllegalStateException("이미 존재하는 카테고리 입니다.");
                });
    }
}
