package Gdsc.web.post.dto;


import Gdsc.web.category.entity.Category;
import Gdsc.web.member.dto.MemberInfoResponseServerDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private boolean tmpStore;
    private boolean blocked;
    private String imagePath;
    private Category category;
    private String postHashTags;
    private String userId;
    private MemberInfoResponseServerDto memberInfo;
    private LocalDateTime modifiedAt;
    private LocalDateTime uploadDate;




}
