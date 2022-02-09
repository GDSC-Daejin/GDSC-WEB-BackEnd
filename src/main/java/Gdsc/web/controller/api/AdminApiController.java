package Gdsc.web.controller.api;

import Gdsc.web.controller.dto.ResponseDto;
import Gdsc.web.domain.Member;
import Gdsc.web.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
