package tests;

import framework.base.BaseTest;
import framework.helpers.RandomHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import vk.api.util.ApiVkUtil;
import vk.models.WallPostCommentInfo;
import vk.models.WallPostInfo;
import vk.pages.PasswordPage;
import vk.pages.HomePage;
import vk.pages.MyPage;
import vk.pages.loginpage.LoginPage;

import java.util.List;

import static framework.helpers.RandomHelper.RANDOM_STRING_LENGTH;
import static vk.configs.AccountConfig.*;
import static vk.configs.MainConfig.*;

public class VkMediaTest extends BaseTest {

    @Test
    public static void mediaTest() {
        //Step 1
        browser.goTo(URL);
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.state().waitForDisplayed(), "LoginPage not open");
        //Step 2
        loginPage.getLoginForm().setLogin(LOGIN);
        loginPage.getLoginForm().clickSignInButton();
        PasswordPage passwordPage = new PasswordPage();
        Assert.assertTrue(passwordPage.state().waitForDisplayed(), "IdPage not open");
        passwordPage.setPassword(PASSWORD);
        passwordPage.clickSubmitButton();
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.state().waitForDisplayed(), "HomePage not open");
        //Step 3
        homePage.getLeftSideMenuForm().clickMyPageLink();
        MyPage myPage = new MyPage();
        Assert.assertTrue(myPage.state().waitForDisplayed(), "MyPage not open");
        //Step 4
        String randomStringForWallPost = RandomHelper.getRandomString(RANDOM_STRING_LENGTH);
        String wallPostId = ApiVkUtil.addWallPost(randomStringForWallPost);
        //Step 5
        WallPostInfo wallPostInfo = myPage.getProfileWallForm().getWallPostInfo(wallPostId);
        Assert.assertEquals(wallPostInfo.getAuthorId(), OWNER_ID, "Wall post author not as expected");
        Assert.assertEquals(wallPostInfo.getTextField(), randomStringForWallPost, "Wall post text not as expected");
        //Step 6
        String randomStringForEditWallPost = RandomHelper.getRandomString(RANDOM_STRING_LENGTH);
        String uploadedPhotoId = ApiVkUtil.savePhotoForWallOnTheServer(PHOTO_PATH);
        String editedWallPostId = ApiVkUtil.editWallPost(wallPostId, randomStringForEditWallPost, uploadedPhotoId);
        Assert.assertEquals(wallPostId, editedWallPostId, "The id of the created post on the wall and the id of the edited post on the wall are not equal");
        //Step 7
        wallPostInfo = myPage.getProfileWallForm().getWallPostInfo(wallPostId);
        Assert.assertEquals(wallPostInfo.getTextField(), randomStringForEditWallPost, "Text by wall post is incorrect");
        Assert.assertTrue(wallPostInfo.isPhotoIdPresent(uploadedPhotoId), "Expected photo id not found in wall post");
        //Step 8
        String randomCommentForWallPost = RandomHelper.getRandomString(RANDOM_STRING_LENGTH);
        String wallPostCommentId = ApiVkUtil.addCommentToWallPost(wallPostId, randomCommentForWallPost);
        //Step 9
        WallPostCommentInfo wallPostCommentInfo = myPage.getProfileWallForm().getWallPostCommentInfo(wallPostId, wallPostCommentId);
        Assert.assertEquals(wallPostCommentInfo.getAuthorId(), OWNER_ID, "Author id not as expected");
        Assert.assertEquals(wallPostCommentInfo.getTextField(), randomCommentForWallPost, "Text by comment not as expected");
        //Step 10
        myPage.getProfileWallForm().clickLikePost(wallPostId);
        //Step 11
        List<String> usersIdWhoLiked = ApiVkUtil.getWallPostLikes(wallPostId);
        Assert.assertTrue(usersIdWhoLiked.contains(OWNER_ID), "Like from expected userId not found");
        //Step 12
        ApiVkUtil.deleteWallPost(wallPostId);
        //Step 13
        Assert.assertFalse(myPage.getProfileWallForm().isWallPostPresent(wallPostId), "Wall post by id " + wallPostId + " not deleted");
    }
}