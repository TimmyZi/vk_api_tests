package vk.models;

import lombok.Data;

import java.util.List;

@Data
public class WallPostInfo {
    private String postId;
    private String authorId;
    private String textField;
    private List<String> photosId;

    public boolean isPhotoIdPresent(String id) {
        return photosId.contains(id);
    }
}