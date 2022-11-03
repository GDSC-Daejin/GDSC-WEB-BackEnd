package Gdsc.web.image.controller;

import Gdsc.web.common.dto.Response;
import Gdsc.web.image.dto.ImageDto;
import Gdsc.web.image.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageUploadController {
    private final ImageUploadService imageUploadService;
   @PostMapping("/api/member/v1/image")
    public Response uploadImage(@RequestBody ImageDto requestImageDto,
                                        @AuthenticationPrincipal User principal) throws IOException {
       return Response.success("data", imageUploadService.uploadImage(requestImageDto, principal.getUsername()));
   }

}
