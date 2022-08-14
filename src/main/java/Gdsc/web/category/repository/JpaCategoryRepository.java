package Gdsc.web.category.repository;

import Gdsc.web.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCategoryRepository  extends JpaRepository<Category,Integer> {

    Optional<Category> findByCategoryName(String categoryName);
    Category findByCategoryId(int categoryId);
    void deleteByCategoryName(String categoryName);
}
