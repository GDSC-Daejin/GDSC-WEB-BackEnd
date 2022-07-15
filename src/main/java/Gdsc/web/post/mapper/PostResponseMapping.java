package Gdsc.web.post.mapper;

import Gdsc.web.category.entity.Category;
import Gdsc.web.member.mapper.MemberInfoPublicResponseMapping;

import java.util.Date;
import java.util.List;

public interface PostResponseMapping {
    Long getPostId();
    String imagePath();
    String getContent();
    String getTitle();
    String getImagePath();
    boolean isTmpStore();
    Category getCategory();
    String getPostHashTags();
    Date getModifiedAt();
    Date getUploadDate();
    MemberInfoPublicResponseMapping getMemberInfo();

}
