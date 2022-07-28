package Gdsc.web.category.controller;

import Gdsc.web.common.dto.ApiResponse;
import Gdsc.web.category.dto.CategoryRequestDto;
import Gdsc.web.category.dto.CategoryUpdateDto;
import Gdsc.web.category.entity.Category;
import Gdsc.web.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;
    @GetMapping("api/v1/category")
    public ApiResponse<List<Category>> categoryList(){
        return ApiResponse.success("data", categoryService.카테고리목록());
    }

    @PostMapping("api/admin/v1/category/add")
    public ApiResponse addCategory(@RequestBody CategoryRequestDto categoryRequestDto){

        categoryService.카테고리추가(categoryRequestDto);

        return ApiResponse.success("message", "Success");
    }

    @DeleteMapping("api/admin/v1/category/delete")
    public ApiResponse deleteCategory(@RequestBody Category category) {

        categoryService.카테고리삭제(category);

       return ApiResponse.success("message", "Success");
    }

    @PutMapping("api/admin/v1/category/update")
    // DTO 를 사용한 이유는 category객체로 받을 경우 데이터 보내는 것이 너무 더러워짐
    //  ex : { Category:{ "GG": "GG", "@$":"GG", "geg" :"GGG" } , String : #%## }
    public ApiResponse updateCategory(@RequestBody CategoryUpdateDto category) {

        categoryService.카테고리업데이트(category);

        return ApiResponse.success("message", "Success");
    }




}
