package com.geektech.intellect_memory.domain.models

data class PictureQuestionModel(
    val imageModel : PictureImageModel,
    val firstAnswer : String?,
    val secondAnswer : String?,
    val thirdAnswer : String?,
    val fourthAnswer : String?,
    val correctAnswer : String?,
)
