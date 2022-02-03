package Gdsc.web.controller.api;

import Gdsc.web.controller.dto.ResponseDto;
import Gdsc.web.controller.dto.SupportDto;
import Gdsc.web.domain.Member;
import Gdsc.web.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SupportApiController {
    private final SupportService supportService;
    @GetMapping("/api/support/limit")
    public ResponseDto<SupportDto> list() {
        return new ResponseDto<>(HttpStatus.OK, supportService.지원제한(), "업데이트 성공");
    }
    @PutMapping("/api/support/limit/update")
    public ResponseDto<Integer> update(@RequestBody SupportDto supportDto){
        supportService.지원제한업데이트(supportDto);
        return new ResponseDto<>(HttpStatus.OK, 1, "업데이트 성공");
    }
}
