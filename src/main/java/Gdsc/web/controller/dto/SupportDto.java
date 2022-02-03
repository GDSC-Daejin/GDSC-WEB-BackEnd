package Gdsc.web.controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportDto {
    @Builder.Default
    boolean frontend = false;
    @Builder.Default
    boolean backend = false;
    @Builder.Default
    boolean design = false;
    @Builder.Default
    boolean android = false;
    @Builder.Default
    boolean ml = false;
    @Builder.Default
    boolean beginner = false;
    @Builder.Default
    boolean home = false;
}
