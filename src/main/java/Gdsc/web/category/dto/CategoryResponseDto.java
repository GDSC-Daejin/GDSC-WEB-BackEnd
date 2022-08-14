package Gdsc.web.category.dto;

import Gdsc.web.category.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    @Schema(description = "카테고리 이름" , example = "Backend")
    private String categoryName;

    public CategoryResponseDto(Category category) {
        this.categoryName = category.getCategoryName();
    }
}