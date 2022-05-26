package Gdsc.web.service;

import Gdsc.web.entity.Post;
import Gdsc.web.repository.postSearch.PostSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostSearchService {
    private final PostSearchRepository postSearchRepository;
    public Page<Post> searchSimilar(String word, Pageable pageable) {
        return postSearchRepository.searchByTitle(word, pageable);
    }
}
