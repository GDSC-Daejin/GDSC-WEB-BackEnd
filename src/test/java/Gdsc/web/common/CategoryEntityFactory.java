package Gdsc.web.common;

import Gdsc.web.entity.Category;

import java.time.LocalDateTime;

public class CategoryEntityFactory {
    public static Category categoryBackendEntity(){
        Category category = new Category();
        category.setUploadDate(LocalDateTime.now());
        category.setCategoryName("Backend2");
        return category;
    }
    public static Category categoryFrontEndEntity(){
        Category category = new Category();
        category.setUploadDate(LocalDateTime.now());
        category.setCategoryName("Frontend");
        return category;
    }
}
