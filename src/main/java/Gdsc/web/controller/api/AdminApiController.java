package Gdsc.web.controller.api;

import Gdsc.web.dto.ResponseDto;
import Gdsc.web.entity.Member;
import Gdsc.web.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminApiController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "권한변경", notes = "권한 등급을 변경함.")
    @PutMapping
    public ResponseDto<?> updateRole(@RequestBody Member member){
        adminService.맴버권한수정(member);
        return new ResponseDto<Integer>(HttpStatus.OK, 1, "update 성공");
    }

    @ApiOperation(value = "멤버목록", notes = "게스트가 아닌 멤버를 조회합니다")
    @GetMapping("/member")
    public ResponseDto<?> retrieveMemberList(){
        return new ResponseDto<>(HttpStatus.OK,adminService.멤버목록(), "멤버 목록");
    }

    @ApiOperation(value = "게스트목록", notes = "게스트를 조회합니다")
    @GetMapping("/guest")
    public ResponseDto<?> retrieveGuestList(){
        return new ResponseDto<>(HttpStatus.OK,adminService.게스트목록(), "게스트 목록");
    }
}
