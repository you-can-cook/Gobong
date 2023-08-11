package com.youcancook.gobong.model

data class Card(
    val user: User = User(),
    val thumbnailUrl: String = "",
    val title: String = "내 레시피 대박임",
    val bookmark: String = "0",
    val time: String = "5분",
    val tools: List<String> = listOf("전자레인지"),
    val level: String = "쉬워요",
    val star: String = "3.2공기",
    val description: String = "",
    val ingredients: List<String> = listOf(
        "자이언트 떡볶이",
        "콕콕콕 스파게티",
        "스트링 치즈",
        "의성마늘 소시지 1인",
        "모짜렐라(슈레드) 치즈"
    ),
)
