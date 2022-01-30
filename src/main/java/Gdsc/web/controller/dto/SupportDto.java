package Gdsc.web.controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportDto {
    boolean frontend = false;
    boolean backend = false;
    boolean design = false;
    boolean android = false;
    boolean ml = false;
    boolean beginner = false;
    boolean home = false;
}
