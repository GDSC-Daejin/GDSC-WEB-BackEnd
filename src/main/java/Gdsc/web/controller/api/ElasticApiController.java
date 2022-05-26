package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.elastic.ElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class ElasticApiController {
    private final ElasticService elasticService;

    @PostMapping("/api/v1/post/index")
    public ApiResponse indexing(){
        return ApiResponse.success("data",elasticService.createIndex());
    }
    @PostMapping("/api/v1/post/insertData")
    public ApiResponse insertData(){
        return ApiResponse.success("data",elasticService.insertDocument());
    }


}
