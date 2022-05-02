package Gdsc.web.controller.api;

import Gdsc.web.dto.ApiResponse;
import Gdsc.web.dto.requestDto.CategoryUpdateDto;
import Gdsc.web.entity.Category;
import Gdsc.web.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;
    @ApiOperation(value = "카테고리 목록" , notes = "카테고리 목록 보기 ")
    @GetMapping("api/v1/category")
    public ApiResponse<List<Category>> categoryList(){
        return ApiResponse.success("data", categoryService.카테고리목록());
    }

    @ApiOperation(value = "카테고리 추가" , notes = "카테고리 넣기 ")
    @PostMapping("api/admin/v1/category/add")
    public ApiResponse addCategory(@RequestBody Category category) {

        categoryService.카테고리추가(category);

        return ApiResponse.success("message", "Success");
    }

    @ApiOperation(value = "카테고리 삭제" , notes = "카테고리 삭제, 포스트가 해당 카테고리를 사용하고 있지 않아야 합니다.")
    @DeleteMapping("api/admin/v1/category/delete")
    public ApiResponse deleteCategory(@RequestBody Category category) {
        categoryService.카테고리삭제(category);
       return ApiResponse.success("message", "Success");
    }

    @ApiOperation(value = "카테고리 업데이트" , notes = "카테고리를 업데이트 업데이트를 할 경우 포스트가 자동으로 변경 된 값을 참조함.")
    @PutMapping("api/admin/v1/category/update")
    // DTO 를 사용한 이유는 category객체로 받을 경우 데이터 보내는 것이 너무 더러워짐
    //  ex : { Category:{ "GG": "GG", "@$":"GG", "geg" :"GGG" } , String : #%## }
    public ApiResponse updateCategory(@RequestBody CategoryUpdateDto category) {

        categoryService.카테고리업데이트(category);

        return ApiResponse.success("message", "Success");
    }




}
