package Gdsc.web.common;

import Gdsc.web.entity.Category;

import java.time.LocalDateTime;

public class CategoryEntityFactory {
    public static Category categoryEntity(){
        Category category = new Category();
        category.setUploadDate(LocalDateTime.now());
        category.setCategoryName("Backend");
        return category;
    }
}
