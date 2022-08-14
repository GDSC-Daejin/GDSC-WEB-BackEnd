package Gdsc.web.category.service;

import Gdsc.web.category.dto.CategoryRequestDto;
import Gdsc.web.category.dto.CategoryResponseDto;
import Gdsc.web.category.dto.CategoryUpdateDto;
import Gdsc.web.category.entity.Category;
import Gdsc.web.category.repository.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final JpaCategoryRepository jpaCategoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> 카테고리목록(){
        // 저장된 카테고리 Entity 리스트를 모두 조회하여 Dto 리스트로 변환한 후 반환합니다.

        return jpaCategoryRepository.findAll().stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void 카테고리추가(CategoryRequestDto categoryRequestDto) {
        // 카테고리 이름이 중복되는지 확인합니다.
        final Category result = jpaCategoryRepository.findByCategoryName(categoryRequestDto.getCategoryName()).orElse(null);

        if (result != null) {
            throw new IllegalArgumentException("이미 존재하는 카테고리 입니다.");
        }

        Category category = new Category(categoryRequestDto);
        jpaCategoryRepository.save(category);
    }

    @Transactional
    public void 카테고리삭제(CategoryRequestDto categoryRequestDto){
        // 삭제할 카테고리가 존재하는 지 확인합니다.
        Category category = jpaCategoryRepository.findByCategoryName(categoryRequestDto.getCategoryName()).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다."));

        jpaCategoryRepository.deleteByCategoryName(category.getCategoryName());
    }

    @Transactional
    public void 카테고리업데이트(CategoryUpdateDto categoryUpdateDto){
        // 수정할 카테고리가 존재하는 지 확인합니다.
        Category category = jpaCategoryRepository.findByCategoryName(categoryUpdateDto.getCategoryName()).orElseThrow(
                ()-> new IllegalArgumentException("찾을 수 없는 카테고리 입니다."));

        category.update(categoryUpdateDto);
    }
}
