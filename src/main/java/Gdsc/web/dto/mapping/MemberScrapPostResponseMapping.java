package Gdsc.web.dto.mapping;

import Gdsc.web.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberScrapPostResponseMapping {
    List<PostResponseMapping> getPost();
}
