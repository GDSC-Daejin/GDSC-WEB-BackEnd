package Gdsc.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WarningDto {
    @ApiModelProperty(example = "경고재목")
    String title;
    @ApiModelProperty(example = "경고내용")
    String content;
    @ApiModelProperty(example = "누구한테 줄건지")
    String ToUser;
}
