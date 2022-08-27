package Gdsc.web.common;

import Gdsc.web.category.entity.Category;
import Gdsc.web.post.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostEntityFactory {
    public static Post falseBlockFalseTmpStorePostEntity(String userId , Category category) {
        Post post = new Post();
        post.setPostId(1L);
        post.setUserId(userId);
        post.setTitle("falseBlockFalseTmpStore test title");
        post.setContent("test content");
        post.setTmpStore(false);
        post.setPostHashTags("test,test2");
        post.setCategory(category);
        //post.setUploadDate(LocalDateTime.now());
        post.setBlocked(false);
        post.setView(0);
        return post;
    }
    public static Post falseBlockTrueTmpStorePostEntity(String userId, Category category) {
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle("test title");
        post.setContent("test content");
        post.setTmpStore(true);
        post.setPostHashTags("test,test2");
        post.setCategory(category);
        post.setUploadDate(LocalDateTime.now());
        post.setBlocked(false);
        post.setView(0);
        return post;
    }

    public static Post trueBlockFalseTmpStorePostEntity(String userId , Category category) {
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle("trueBlockFalse test title");
        post.setContent("test content");
        post.setTmpStore(false);
        post.setPostHashTags("test,test2");
        post.setCategory(category);
        post.setUploadDate(LocalDateTime.now());
        post.setBlocked(true);
        post.setView(0);
        return post;
    }
}
