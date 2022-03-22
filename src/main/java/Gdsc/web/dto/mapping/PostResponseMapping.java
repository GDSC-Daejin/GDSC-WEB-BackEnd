package Gdsc.web.dto.mapping;

import Gdsc.web.entity.Category;
import Gdsc.web.entity.Likes;
import Gdsc.web.entity.MemberInfo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface PostResponseMapping {
    Long getPostId();
    String imagePath();
    String getContent();
    String getTitle();
    boolean isTmpStore();
    Category getCategory();
    String getPostHashTags();
    List<Likes> getLikes();
    Date getModifiedAt();
    Date getUploadDate();
    MemberInfoPublicResponseMapping getMemberInfo();

}
