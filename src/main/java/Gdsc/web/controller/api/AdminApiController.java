package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.entity.Member;
import Gdsc.web.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminApiController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "권한변경", notes = "권한 등급을 변경함.")
    @PutMapping
    public ApiResponse<?> updateRole(@RequestBody Member member){
        adminService.맴버권한수정(member);
        return ApiResponse.success("message", "Success");
    }

    @ApiOperation(value = "멤버목록", notes = "게스트가 아닌 멤버를 조회합니다")
    @GetMapping("/member")
    public ApiResponse<List<Member>> retrieveMemberList(){
        return ApiResponse.success("data", adminService.멤버목록());
    }

    @ApiOperation(value = "게스트목록", notes = "게스트를 조회합니다")
    @GetMapping("/guest")
    public ApiResponse<List<Member>> retrieveGuestList(){
        return ApiResponse.success("data", adminService.게스트목록());
    }


}
