package com.youcancook.gobong.ui.detail

import androidx.lifecycle.viewModelScope
import com.youcancook.gobong.model.Card
import com.youcancook.gobong.model.RecipeStep
import com.youcancook.gobong.model.repository.GoBongRepository
import com.youcancook.gobong.model.repository.UserRepository
import com.youcancook.gobong.ui.base.NetworkViewModel
import com.youcancook.gobong.util.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val goBongRepository: GoBongRepository,
    private val userRepository: UserRepository,
) : NetworkViewModel() {
    private val _isMine = MutableStateFlow(false)
    val isMine: StateFlow<Boolean> get() = _isMine

    private val _isReviewed = MutableStateFlow(false)
    val isReviewed: StateFlow<Boolean> get() = _isReviewed

    private val _isDeleted = MutableStateFlow(false)
    val isDeleted: StateFlow<Boolean> get() = _isDeleted

    private val _cardInfo = MutableStateFlow(Card.createEmpty())
    val cardInfo: StateFlow<Card> get() = _cardInfo

    private val _recipes = MutableStateFlow(
        listOf(
            RecipeStep("3분", emptyList(), "", "자이언트 떡볶이를 순서대로 조리"),
            RecipeStep("3분", emptyList(), "", "스파게티는 뜨거운 물에 건더기와 면만 익힌 후, 완성된 떡볶이에 넣기"),
            RecipeStep(
                "2분", emptyList(), "", "스파게티의 분말스프와 액상 소스를 넣고 섞기\n" +
                        "이후 모짜렐라 치즈를 뿌리기 + 소시지도 먹기 좋은 크기로 잘라서 넣어주기 + 스트링 치즈 찢어서 올리기"
            ),
            RecipeStep(
                "1분 30분", listOf("전자레인지"), "", "전자레인지에 1분 30초 돌리기"
            ),
            RecipeStep(
                "1분 30분", listOf("전자레인지", "오븐"), "", "전자레인지에 1분 30초 돌리기"
            ),
            RecipeStep(
                "1분 30분", listOf("전자레인지"), "", "전자레인지에 1분 30초 돌리기"
            ),
            RecipeStep(
                "1분 30분", listOf("전자레인지"), "", "전자레인지에 1분 30초 돌리기"
            ),
            RecipeStep(
                "1분 30분", listOf("전자레인지"), "", "전자레인지에 1분 30초 돌리기"
            ),
            RecipeStep(
                "1분 30분", listOf("전자레인지", "오븐", "에어프라이어", "믹서기", "가스레인지"), "", "전자레인지에 1분 30초 돌리기"
            ),
            RecipeStep(
                "1분 30분", listOf("전자레인지"), "", "전자레인지에 1분 30초 돌리기"
            ),
        )
    )
    val recipes: StateFlow<List<RecipeStep>> get() = _recipes

    private val _activeRecipeStep = MutableStateFlow(0)
    val activeRecipeStep: StateFlow<Int> get() = _activeRecipeStep

    private val _starCount = MutableStateFlow(0)
    val starCount: StateFlow<Int> get() = _starCount

    fun setStar(count: Int) {
        _starCount.value = count
    }

    fun getStar() = _starCount.value

    fun getIsBookmarked() = _cardInfo.value.bookmarked

    fun getCurrentRecipe(recipePostId: String) {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestCurrentRecipe(recipePostId)
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private suspend fun requestCurrentRecipe(recipePostId: String) {
        val response = goBongRepository.getCurrentRecipe(recipePostId)
        _cardInfo.value = response
        _recipes.value = response.recipes
    }

    fun activeRecipeStep(position: Int) {
        _activeRecipeStep.value = position
    }

    fun bookmarkRecipe(isBookmarked: Boolean) {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestBookmark(isBookmarked)
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private suspend fun requestBookmark(isBookmarked: Boolean) {
        goBongRepository.bookmarkRecipe(isBookmarked)
        _cardInfo.value = _cardInfo.value.copy(bookmarked = isBookmarked)
    }

    fun deleteRecipePost() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestDeleteRecipe()
                _isDeleted.value = true
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private suspend fun requestDeleteRecipe() {
        goBongRepository.deleteRecipe(_cardInfo.value.id)
    }

    fun reviewRecipe() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                if (_isReviewed.value) {
                    requestReview()
                    _isReviewed.value = true
                } else {
                    requestUpdatedReview()
                }
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private suspend fun requestReview() {
        goBongRepository.reviewRecipe(_starCount.value)
    }

    private suspend fun requestUpdatedReview() {
        goBongRepository.reviewRecipe(_starCount.value)
    }

    fun follow() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestFollow()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    fun unfollow() {
        viewModelScope.launch {
            setNetworkState(NetworkState.LOADING)
            try {
                requestUnfollow()
                setNetworkState(NetworkState.SUCCESS)
            } catch (e: Exception) {
                setNetworkState(NetworkState.FAIL)
                setSnackBarMessage(e.message ?: "")
            }
        }
    }

    private suspend fun requestFollow() {
        userRepository.follow(_cardInfo.value.user.userId)
        _cardInfo.value = _cardInfo.value.copy(user = _cardInfo.value.user.copy(followed = true))
    }

    private suspend fun requestUnfollow() {
        userRepository.unfollow(_cardInfo.value.user.userId)
        _cardInfo.value = _cardInfo.value.copy(user = _cardInfo.value.user.copy(followed = false))
    }

}