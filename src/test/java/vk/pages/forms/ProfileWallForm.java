package vk.pages.forms;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import framework.helpers.StringHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.grid.config.ConfigException;
import vk.models.WallPostCommentInfo;
import vk.models.WallPostInfo;

import java.util.ArrayList;
import java.util.List;

import static vk.configs.AccountConfig.OWNER_ID;

public class ProfileWallForm extends Form {
    private static final By wallLocator = By.id("profile_wall");
    private final ILabel postLabel = getElementFactory().getLabel(By.xpath("//div[contains(@class,'post--withPostBottomAction')]"), "Post Label");
    private final ILabel postAuthorLabel = getElementFactory().getLabel(By.xpath("//h5[contains(@class,'post_author')]//a[contains(@class,'author')]"), "Post Author Label");
    private final ILabel postTextLabel = getElementFactory().getLabel(By.xpath("//div[contains(@class,'wall_post_text')]"), "Post Text Label");
    private final ILabel postPhotoLabel = getElementFactory().getLabel(By.xpath("//a[contains(@href,'photo')]"), "Post Photo Label");
    private final ILabel commentLabel = getElementFactory().getLabel(By.xpath("//div[contains(@id,'post')]"), "Comment Label");
    private final ILabel commentAuthorLabel = getElementFactory().getLabel(By.xpath("//a[contains(@class,'author')]"), "Comment Author Label");
    private final ILabel commentTextLabel = getElementFactory().getLabel(By.xpath("//div[contains(@class,'wall_reply_text')]"), "Comment Text Label");
    private final ILink showNextCommentLink = getElementFactory().getLink(By.xpath("//div[contains(@class,'replies_list')]//a[contains(@class,'replies_next')]"), "Show Next Comment Link");
    private final IButton likePostButton = getElementFactory().getButton(By.xpath("//div[contains(@class,'PostBottomActionContainer')]"), "LikePostButton");
    private final IButton activeLikePostButton = getElementFactory().getButton(By.xpath("//div[contains(@class,'icon--animationActive')]"), "Active Like Post Button");

    public ProfileWallForm() {
        super(wallLocator, "Wall Form");
    }

    public boolean isWallPostPresent(String postId) {
        return !getElementFactory().getLabel(By.id(String.format("post%s_%s", OWNER_ID, postId)), "Wall Post Label").state().waitForNotDisplayed();
    }

    public WallPostInfo getWallPostInfo(String postId) {
        Logger.getInstance().info("Getting wall post info by id - " + postId);
        ILabel wallPost = findWallPost(postId);
        ILabel AuthorByThisWallPostLabel = getElementFactory().findChildElement(wallPost, postAuthorLabel.getLocator(), ElementType.LABEL);
        ILabel TextByThisWallPostLabel = getElementFactory().findChildElement(wallPost, postTextLabel.getLocator(), ElementType.LABEL);
        List<ILabel> postPhotosByThisPostLabels = getElementFactory().findChildElements(wallPost, postPhotoLabel.getLocator(), ElementType.LABEL);
        WallPostInfo wallPostInfo = new WallPostInfo();
        wallPostInfo.setPostId(postId);
        wallPostInfo.setAuthorId(StringHelper.getNumericValueFromString(AuthorByThisWallPostLabel.getAttribute("href")));
        wallPostInfo.setTextField(TextByThisWallPostLabel.getText());
        if (!postPhotosByThisPostLabels.isEmpty()) {
            List<String> photosId = new ArrayList<>();
            for (ILabel postPhoto : postPhotosByThisPostLabels) {
                String hrefAttribute = postPhoto.getAttribute("href");
                int indexPhotoId = hrefAttribute.lastIndexOf("_");
                photosId.add(hrefAttribute.substring(indexPhotoId + 1));
            }
            wallPostInfo.setPhotosId(photosId);
        }
        return wallPostInfo;
    }

    public WallPostCommentInfo getWallPostCommentInfo(String postId, String commentId) {
        Logger.getInstance().info("Getting wall post comment info by post id - " + postId + ", comment id - " + commentId);
        ILabel wallPost = findWallPost(postId);
        getElementFactory().findChildElement(wallPost, showNextCommentLink.getLocator(), ElementType.LINK).click();
        ILabel wallPostComment = getElementFactory().findChildElement(wallPost, commentLabel.getLocator(), ElementType.LABEL);
        String idAttribute = wallPostComment.getAttribute("id");
        System.out.println("ACTUAL ID ATTRIBUTE - " + idAttribute);
        int indexCommentId = idAttribute.lastIndexOf("_");
        idAttribute = idAttribute.substring(indexCommentId + 1);
        System.out.println("ACTUAL ID ATTRIBUTE AFTER SUBSTRING- " + idAttribute);
        if (idAttribute.equals(commentId)) {
            WallPostCommentInfo wallPostCommentInfo = new WallPostCommentInfo();
            ILabel thisWallPostCommentAuthor = getElementFactory().findChildElement(wallPostComment, commentAuthorLabel.getLocator(), ElementType.LABEL);
            ILabel thisWallPostCommentText = getElementFactory().findChildElement(wallPostComment, commentTextLabel.getLocator(), ElementType.LABEL);
            wallPostCommentInfo.setCommentId(commentId);
            wallPostCommentInfo.setAuthorId(StringHelper.getNumericValueFromString(thisWallPostCommentAuthor.getAttribute("href")));
            wallPostCommentInfo.setTextField(thisWallPostCommentText.getText());
            return wallPostCommentInfo;
        }
        Logger.getInstance().error("Wall post comment by id " + commentId + " not found");
        throw new ConfigException("Wall post comment by id " + commentId + " not found");
    }

    public void clickLikePost(String postId) {
        ILabel wallPost = findWallPost(postId);
        getElementFactory().findChildElement(wallPost, likePostButton.getLocator(), ElementType.BUTTON).click();
        getElementFactory().findChildElement(wallPost, activeLikePostButton.getLocator(), ElementType.BUTTON).state().waitForDisplayed();
    }

    private ILabel findWallPost(String postId) {
        Logger.getInstance().info("search post on the wall by id - " + postId);
        List<ILabel> postsLabels = getElementFactory().findElements(postLabel.getLocator(), ElementType.LABEL);
        for (ILabel postOnTheWall : postsLabels) {
            if (postOnTheWall.getAttribute("id").equals(String.format("post%s_%s", OWNER_ID, postId))) {
                return postOnTheWall;
            }
        }
        Logger.getInstance().error("Wall post by id " + postId + " not found");
        throw new ConfigException("Wall post by id " + postId + " not found");
    }
}