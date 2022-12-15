package vk.models;

import lombok.Data;

@Data
public class WallPostCommentInfo {
    private String commentId;
    private String authorId;
    private String textField;
}