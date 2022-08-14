package Gdsc.web.category.controller;

import Gdsc.web.common.dto.Response;
import Gdsc.web.category.dto.CategoryRequestDto;
import Gdsc.web.category.dto.CategoryUpdateDto;
import Gdsc.web.category.entity.Category;
import Gdsc.web.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "카테고리 API" , description = "카테고리 정보를 조회하고 수정하는 API")
public class CategoryApiController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 정보 조회", description = "카테고리 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 정보 조회 실패")
    })
    @GetMapping("api/v1/category")
    public Response<Object> categoryList(){
        return Response.success("data", categoryService.카테고리목록());
    }

    @Operation(summary = "카테고리 정보 추가", description = "카테고리 정보 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CategoryRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "카테고리 정보 추가 실패")
    })
    @PostMapping("api/admin/v1/category/add")
    public Response<Object> addCategory(@RequestBody CategoryRequestDto categoryRequestDto){

        categoryService.카테고리추가(categoryRequestDto);

        return Response.success("message", "Success");
    }

    @Operation(summary = "카테고리 정보 삭제", description = "카테고리 정보 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 정보 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 정보 삭제 실패")
    })
    @DeleteMapping("api/admin/v1/category/delete")
    public Response<Object> deleteCategory(@RequestBody CategoryRequestDto categoryRequestDto) {

        categoryService.카테고리삭제(categoryRequestDto);

       return Response.success("message", "Success");
    }

    @Operation(summary = "카테고리 정보 수정", description = "카테고리 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CategoryUpdateDto.class))),
            @ApiResponse(responseCode = "400", description = "카테고리 정보 수정 실패")
    })
    @PutMapping("api/admin/v1/category/update")
    // DTO 를 사용한 이유는 category객체로 받을 경우 데이터 보내는 것이 너무 더러워짐
    //  ex : { Category:{ "GG": "GG", "@$":"GG", "geg" :"GGG" } , String : #%## }
    public Response<Object> updateCategory(@RequestBody CategoryUpdateDto category) {

        categoryService.카테고리업데이트(category);

        return Response.success("message", "Success");
    }
}
