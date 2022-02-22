package Gdsc.web.repository.category;

import Gdsc.web.entity.Category;
import Gdsc.web.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository  extends JpaRepository<Category,Integer> {
}
