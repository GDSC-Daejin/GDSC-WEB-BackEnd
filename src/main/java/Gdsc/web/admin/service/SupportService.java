package Gdsc.web.admin.service;

import Gdsc.web.admin.dto.SupportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SupportService {

    private final SupportDto supportDto = new SupportDto();

    public SupportDto 지원제한() {
        return supportDto;
    }
    public void 지원제한업데이트(SupportDto requestSupportDto) {
        supportDto.setAndroid(requestSupportDto.isAndroid());
        supportDto.setBackend(requestSupportDto.isBackend());
        supportDto.setBeginner(requestSupportDto.isBeginner());
        supportDto.setDesign(requestSupportDto.isDesign());
        supportDto.setFrontend(requestSupportDto.isFrontend());
        supportDto.setHome(requestSupportDto.isHome());
        supportDto.setMl(requestSupportDto.isMl());
    }
}
