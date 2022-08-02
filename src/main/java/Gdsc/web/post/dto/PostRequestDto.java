package Gdsc.web.post.dto;

import Gdsc.web.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private boolean blocked;
    private Category category;
    private boolean tmpStore;
    private String postHashTags;
    private MultipartFile Thumbnail;
    private String base64Thumbnail;
    private String fileName;

    public boolean getTmpStore() {
        return this.tmpStore;
    }
}
