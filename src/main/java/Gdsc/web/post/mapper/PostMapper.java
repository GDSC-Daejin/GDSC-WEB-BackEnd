package Gdsc.web.post.mapper;

import Gdsc.web.member.dto.MemberInfoResponseServerDto;
import Gdsc.web.member.service.MemberService;
import Gdsc.web.post.dto.PostResponseDto;
import Gdsc.web.post.entity.Post;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {
    default Page<PostResponseDto> toPostResponseDtoPage(List<Post> posts, MemberService memberService , Pageable pageable){
        List<MemberInfoResponseServerDto> memberInfoResponseServerDtos = memberService.getNicknameImages();
        List<PostResponseDto> postResponseDtoList = posts.stream()
                .map(post ->
                        PostResponseDto.builder()
                                .postId(post.getPostId())
                                .title(post.getTitle())
                                .content(post.getContent())
                                .category(post.getCategory())
                                .userId(post.getUserId())
                                .memberInfo(memberInfoResponseServerDtos.stream()
                                        .filter(memberInfoResponseServerDto -> memberInfoResponseServerDto.getUserId().equals(post.getUserId()))
                                        .findFirst()
                                        .orElse(null))
                                .blocked(post.isBlocked())
                                .postHashTags(Arrays.stream(post.getPostHashTags().split(",")).collect(Collectors.toList()))
                                .imagePath(post.getImagePath())
                                .uploadDate(post.getUploadDate())
                                .modifiedAt(post.getModifiedAt())
                                .build())
                .collect(Collectors.toList());
        return new PageImpl<>(postResponseDtoList, pageable, posts.size());
    }
    default PostResponseDto toPostResponseDto(Post post , MemberService memberService){
        MemberInfoResponseServerDto memberInfoResponseServerDto = memberService.getNicknameImage(post.getUserId());
        return PostResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .userId(post.getUserId())
                .memberInfo(memberInfoResponseServerDto)
                .blocked(post.isBlocked())
                .postHashTags(Arrays.stream(post.getPostHashTags().split(",")).collect(Collectors.toList()))
                .imagePath(post.getImagePath())
                .uploadDate(post.getUploadDate())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
