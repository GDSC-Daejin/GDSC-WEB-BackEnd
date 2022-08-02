package Gdsc.web.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryUpdateDto {
    @Schema(description = "카테고리 이름" , example = "Backend")
    String categoryName;
    @Schema(description = "카테고리 이름" , example = "ML")
    String updateCategoryName;
}
