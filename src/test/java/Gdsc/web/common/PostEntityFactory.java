package Gdsc.web.common;

import Gdsc.web.entity.Category;
import Gdsc.web.entity.Member;
import Gdsc.web.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostEntityFactory {
    public static Post falseBlockFalseTmpStorePostEntity(Member member , Category category) {
        Post post = new Post();
        post.setPostId(1L);
        post.setMemberInfo(member.getMemberInfo());
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
    public static Post falseBlockTrueTmpStorePostEntity(Member member , Category category) {
        Post post = new Post();
        post.setMemberInfo(member.getMemberInfo());
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

    public static Post trueBlockFalseTmpStorePostEntity(Member member , Category category) {
        Post post = new Post();
        post.setMemberInfo(member.getMemberInfo());
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
