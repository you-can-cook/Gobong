package org.youcancook.gobong.domain.recipe.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.youcancook.gobong.domain.recipe.dto.request.CreateRecipeRequest;
import org.youcancook.gobong.domain.recipe.dto.request.UpdateRecipeRequest;
import org.youcancook.gobong.domain.recipe.dto.response.CreateRecipeResponse;
import org.youcancook.gobong.domain.recipedetail.dto.request.UploadRecipeDetailRequest;
import org.youcancook.gobong.global.util.acceptance.AcceptanceTest;
import org.youcancook.gobong.global.util.acceptance.AcceptanceUtils;

import java.util.List;

public class RecipeAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("레시피를 성공적으로 등록한다.")
    public void createRecipe(){
        String accessToken = AcceptanceUtils.signUpAndGetToken("쩝쩝박사", "KAKAO", "123");
        CreateRecipeRequest request = new CreateRecipeRequest("주먹밥", "쉽게 만드는 주먹밥", List.of("밥", "김"), "쉬워요", null, List.of(
                new UploadRecipeDetailRequest("소금간을 해 주세요", null, 30, 0L),
                new UploadRecipeDetailRequest("주먹을 쥐어 밥을 뭉쳐주세요", null, 15, 0L)
        ));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .body(request)
                .when().post("/api/recipes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    @Test
    @DisplayName("레시피를 성공적으로 삭제한다.")
    public void deleteRecipe(){
        String accessToken = AcceptanceUtils.signUpAndGetToken("쩝쩝박사", "KAKAO", "123");
        CreateRecipeRequest request = new CreateRecipeRequest("주먹밥", "쉽게 만드는 주먹밥", List.of("밥", "김"), "쉬워요", null, List.of(
                new UploadRecipeDetailRequest("소금간을 해 주세요", null, 30, 0L),
                new UploadRecipeDetailRequest("주먹을 쥐어 밥을 뭉쳐주세요", null, 15, 0L)
        ));
        Long recipeId = AcceptanceUtils.createDummyRecipe(accessToken, request).as(CreateRecipeResponse.class).getId();

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/api/recipes/" + recipeId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    @Test
    @DisplayName("권한없는 레시피 삭제 요청 시 올바른 에러코드를 반환한다.")
    public void noAuth(){
        String accessToken1 = AcceptanceUtils.signUpAndGetToken("쩝쩝박사", "KAKAO", "123");
        String accessToken2 = AcceptanceUtils.signUpAndGetToken("쩝쩝학사", "GOOGLE", "123a");
        CreateRecipeRequest request = new CreateRecipeRequest("주먹밥", "쉽게 만드는 주먹밥", List.of("밥", "김"), "쉬워요", null, List.of(
                new UploadRecipeDetailRequest("소금간을 해 주세요", null, 30, 0L),
                new UploadRecipeDetailRequest("주먹을 쥐어 밥을 뭉쳐주세요", null, 15, 0L)
        ));
        Long recipeId = AcceptanceUtils.createDummyRecipe(accessToken1, request).as(CreateRecipeResponse.class).getId();

        RestAssured.given().log().all()
                .auth().oauth2(accessToken2)
                .when().delete("/api/recipes/" + recipeId)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @Test
    @DisplayName("레시피를 성공적으로 수정한다.")
    public void updateRecipe(){
        String accessToken = AcceptanceUtils.signUpAndGetToken("쩝쩝박사", "KAKAO", "123");
        CreateRecipeRequest request = new CreateRecipeRequest("주먹밥", "쉽게 만드는 주먹밥", List.of("밥", "김"), "쉬워요", null, List.of(
                new UploadRecipeDetailRequest("소금간을 해 주세요", null, 30, 0L),
                new UploadRecipeDetailRequest("주먹을 쥐어 밥을 뭉쳐주세요", null, 15, 0L)
        ));
        Long recipeId = AcceptanceUtils.createDummyRecipe(accessToken, request).as(CreateRecipeResponse.class).getId();
        UpdateRecipeRequest updateRequest = new UpdateRecipeRequest(recipeId, "고소한 주먹밥", "쉽게 만드는 주먹밥",
                List.of("밥", "김"), "쉬워요", null, List.of(
                new UploadRecipeDetailRequest("소금간을 해 주세요", null, 30, 0L),
                new UploadRecipeDetailRequest("주먹을 쥐어 밥을 뭉쳐주세요", null, 15, 0L)));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .auth().oauth2(accessToken)
                .when().post("/api/recipes/" + recipeId + "/update")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
