package Gdsc.web.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @Schema(description = "카테고리 이름" , example = "Backend")
    String categoryName;
}
