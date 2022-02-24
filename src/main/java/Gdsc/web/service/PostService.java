package Gdsc.web.service;

import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.repository.post.PostRepositoryImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final JpaPostRepository jpaPostRepository;
    private final PostRepositoryImp postRepositoryImp;


}
