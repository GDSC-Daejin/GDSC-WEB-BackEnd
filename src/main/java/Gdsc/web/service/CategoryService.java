package Gdsc.web.service;

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
}
