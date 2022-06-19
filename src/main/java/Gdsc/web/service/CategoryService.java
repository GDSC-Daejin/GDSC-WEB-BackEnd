package Gdsc.web.service;

import Gdsc.web.dto.requestDto.CategoryUpdateDto;
import Gdsc.web.entity.Category;
import Gdsc.web.repository.category.JpaCategoryRepository;
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
    public void 카테고리추가(Category category) {
        validateDuplicateCategoryName(category.getCategoryName());
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
