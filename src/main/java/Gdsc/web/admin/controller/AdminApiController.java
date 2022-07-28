/*
package Gdsc.web.admin.controller;

import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.admin.dto.WarningDto;
import Gdsc.web.admin.dto.MemberRoleUpdateDto;
import Gdsc.web.member.model.RoleType;
import Gdsc.web.admin.service.AdminService;
import Gdsc.web.admin.service.PostBlockService;
import Gdsc.web.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController\
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;
    private final PostService postService;
    private final PostBlockService postBlockService;

    */
/*@ApiOperation(value = "권한변경", notes = "권한 등급을 변경함.")
    @PutMapping("v1/update/role")
    public ApiResponse<?> updateRole(@RequestBody MemberRoleUpdateDto memberRoleUpdateDto){
        String userId = memberRoleUpdateDto.getUserId();
        RoleType role = memberRoleUpdateDto.getRole();
        adminService.맴버권한수정(userId, role);
        return ApiResponse.success("message", "Success");
    }*//*


   */
/* @ApiOperation(value = "전체회원목록", notes = "모든 회원을 조회합니다.")
    @GetMapping("v1/all/list")
    public ApiResponse<List<Member>> retrieveUserList(){
        return ApiResponse.success("data", adminService.전체회원목록());
    }

    @ApiOperation(value = "멤버목록", notes = "게스트가 아닌 멤버를 조회합니다. 전화번호 Not null 인 회원만")
    @GetMapping("v1/member/list")
    public ApiResponse<List<Member>> retrieveMemberList(){
        return ApiResponse.success("data", adminService.멤버목록());
    }

    @ApiOperation(value = "게스트목록", notes = "게스트를 조회합니다 Not null 인 회원만")
    @GetMapping("v1/guest/list")
    public ApiResponse<List<Member>> retrieveGuestList(){
        return ApiResponse.success("data", adminService.게스트목록());
    }*//*


    @ApiOperation(value = "관리자 경고 주기" , notes = "관리자들이 멤버에게 경고를 줍니다. 로그인이 되어 있어야 합니다. ")
    @PostMapping("/v1/warning")
    public ApiResponse giveWarning(@RequestBody WarningDto warningDto , @AuthenticationPrincipal User principal) {
        adminService.경고주기(principal.getUsername() , warningDto);
        return ApiResponse.success("message", "Success");
    }

    @ApiOperation(value = "관리자 포스트 블럭", notes = "관리자는 유해한 게시글을 블럭하거나 해제할 수 있습니다.")
    @PostMapping("v1/block/{postId}")
    public ApiResponse blockPost(@PathVariable Long postId){
        postBlockService.block(postId);
        return ApiResponse.success("message", "Success");
    }

    @ApiOperation(value = "관리자 블럭 포스트 불러오기", notes = "블럭된 포스트를 불러옵니다")
    @GetMapping("v1/block")
    public ApiResponse blockPost(@PageableDefault(size = 16 ,sort = "postId",direction = Sort.Direction.DESC) Pageable pageable){
        Page<?> post = postService.findBockedPostAll(pageable);
        return ApiResponse.success("data", post);
    }
}
*/
