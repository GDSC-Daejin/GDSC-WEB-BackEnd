package Gdsc.web.repository.category;

import Gdsc.web.entity.Category;
import Gdsc.web.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCategoryRepository  extends JpaRepository<Category,Integer> {

    Optional<Category> findByCategoryName(String categoryName);
    Optional<Category> findByCategoryId(Integer categoryId);
}
