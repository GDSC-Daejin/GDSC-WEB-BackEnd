package Gdsc.web.post.dto;


import Gdsc.web.category.entity.Category;
import Gdsc.web.member.dto.MemberInfoResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private boolean tmpStore;
    private boolean blocked;
    private String imagePath;
    private Category category;
    private String postHashTags;
    private MemberInfoResponseDto memberInfo;
    private LocalDateTime modifiedAt;
    private LocalDateTime uploadDate;


    public PostResponseDto(Long postId, String title, String content, boolean tmpStore, boolean blocked, String imagePath, Category category, String postHashTags, MemberInfoResponseDto toMemberInfoResponseDto, LocalDateTime modifiedAt, LocalDateTime uploadDate) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.tmpStore = tmpStore;
        this.blocked = blocked;
        this.imagePath = imagePath;
        this.category = category;
        this.postHashTags = postHashTags;
        this.memberInfo = toMemberInfoResponseDto;
        this.modifiedAt = modifiedAt;
        this.uploadDate = uploadDate;
    }

}
