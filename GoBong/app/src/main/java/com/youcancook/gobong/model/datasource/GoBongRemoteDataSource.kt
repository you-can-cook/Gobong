package com.youcancook.gobong.model.datasource

import com.youcancook.gobong.R
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.RecipeStep
import com.youcancook.gobong.model.UploadRecipe
import com.youcancook.gobong.model.User
import com.youcancook.gobong.model.UserProfile
import com.youcancook.gobong.model.network.GoBongService
import com.youcancook.gobong.model.network.ImageService


class GoBongRemoteDataSource(
    private val goBongService: GoBongService,
    private val imageService: ImageService,
) {
    fun getUrl(res: Int): String {
        return "android.resource://com.youcancook.gobong/$res"
    }

    suspend fun getCurrentRecipe(recipePostId: String): Card {
        return Card(
            "mark",
            UserProfile(followed = true),
            getUrl(R.drawable.markthumb),
            "마크정식 100% 원조 레시피",
            "100",
            true,
            "5분",
            listOf("전자레인지"),
            "쉬워요",
            "4.5공기",
            "편의점 꿀조합 레시피 best of best",
            listOf(
                "자이언트떡볶이 1개",
                "의성마늘후랑크 소시지 1개",
                "콕콕콕 스파게티  1개",
                "모짜렐라 피자치즈 20g"
            ),
            listOf(
                RecipeStep(
                    "30초",
                    listOf("전자레인지"),
                    getUrl(R.drawable.mark1),
                    "준비한 소시지를 전자렌지에 약 30초간 데워주세요",
                ),
                RecipeStep(
                    "20초",
                    listOf(),
                    getUrl(R.drawable.mark2),
                    "자이언트떡볶이와 콕콕콕 스파게티에 물을 넣어주세요",
                ),
                RecipeStep(
                    "3분",
                    listOf("전자레인지"),
                    getUrl(R.drawable.mark3),
                    "물을 넣은 자이언트 떡볶이를 전자렌지에 3분 돌려주세요",
                ),
                RecipeStep(
                    "50초",
                    listOf(),
                    "",
                    "스파게티 물을 버리고 전자렌지에 돌린 떡볶이에 라면과 스프를 넣고 잘 섞어주세요. 의성마늘후랑크소시지를 먹기 좋은 크기로 잘라 넣어주세요",
                ),
                RecipeStep(
                    "30초",
                    listOf("전자레인지"),
                    getUrl(R.drawable.mark4),
                    "모짜렐라 피자치즈를 위에 얹어주고, 치즈가 녹을 정도로 전자렌지에 약 30초간 데워주세요",
                )
            )
        )
    }

    suspend fun getFollowingRecipes(): List<Card> {
//        return emptyList()
        return listOf(
            Card(
                "mark",
                UserProfile(followed = true),
                getUrl(R.drawable.markthumb),
                "마크정식 100% 원조 레시피",
                "100",
                true,
                "5분",
                listOf("전자레인지"),
                "쉬워요",
                "4.5공기",
                "편의점 꿀조합 레시피 best of best",
                listOf(
                    "자이언트떡볶이 1개",
                    "의성마늘후랑크 소시지 1개",
                    "콕콕콕 스파게티  1개",
                    "모짜렐라 피자치즈 20g"
                ),
                listOf()
            ),
            Card(
                "potato",
                UserProfile(followed = true),
                getUrl(R.drawable.potatothumb),
                "초간단 고구마말랭이",
                "125",
                false,
                "10분",
                listOf("전자레인지"),
                "쉬워요",
                "4.2공기",
                "전자레인지로 간단하게 만들 수 있는 고구마말랭이",
                listOf(),
                listOf()
            ),
            Card(
                "noodle",
                UserProfile(nickname = "국수 박사학위", followed = true),
                getUrl(R.drawable.noodlethumb),
                "냐혼자산다 화사의 간장국수 따라해보기",
                "324",
                false,
                "7분 30초",
                listOf("전자레인지"),
                "보통이에요",
                "4.4공기",
                "만드는데 비용도 얼마 들지 않고 시간도 얼마 안걸리는 진짜 초간단 레시피",
                listOf(),
                listOf()
            )
        )
    }

    suspend fun getAllRecipes(): List<Card> {
//        return emptyList()
        return listOf(
            Card(
                "mark",
                UserProfile(),
                getUrl(R.drawable.markthumb),
                "마크정식 100% 원조 레시피",
                "100",
                true,
                "5분",
                listOf("전자레인지"),
                "쉬워요",
                "4.5공기",
                "편의점 꿀조합 레시피 best of best",
                listOf(
                    "자이언트떡볶이 1개",
                    "의성마늘후랑크 소시지 1개",
                    "콕콕콕 스파게티  1개",
                    "모짜렐라 피자치즈 20g"
                ),
                listOf()
            ),
            Card(
                "potato",
                UserProfile(),
                getUrl(R.drawable.potatothumb),
                "초간단 고구마말랭이",
                "125",
                false,
                "10분",
                listOf("전자레인지"),
                "쉬워요",
                "4.2공기",
                "전자레인지로 간단하게 만들 수 있는 고구마말랭이",
                listOf(),
                listOf()
            ),
            Card(
                "noodle",
                UserProfile(nickname = "국수 박사학위"),
                getUrl(R.drawable.noodlethumb),
                "냐혼자산다 화사의 간장국수 따라해보기",
                "324",
                false,
                "7분 30초",
                listOf("전자레인지"),
                "보통이에요",
                "4.4공기",
                "만드는데 비용도 얼마 들지 않고 시간도 얼마 안걸리는 진짜 초간단 레시피",
                listOf(),
                listOf()
            )
        )
    }

    suspend fun getFilteredRecipes(): List<Card> {
//        return emptyList()
        return listOf(
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty(),
            Card.createEmpty()
        )
    }

    suspend fun getBookmarkedRecipes(): List<Card> {
        return emptyList()
    }

    suspend fun bookmarkRecipe(marked: Boolean) {
        goBongService
    }

    suspend fun deleteRecipe(postId: String) {

    }

    suspend fun reviewRecipe(star: Int) {
        goBongService
    }

    suspend fun uploadRecipe(uploadRecipe: UploadRecipe) {

    }

    suspend fun getMyRecipes(): User {
        return User.createEmpty()
    }

    suspend fun getUserRecipes(userId: String): User {
        return User.createEmpty()
    }
}