package com.geektech.intellect_memort.domain.models

import com.geektech.intellect_memort.common.base.IBaseDiffModel

data class StudentsModel(

    val id: String? = null,
    val fullName: String? = null,
    override val password: String? = null,
    val teacher: String? = null,
    val branch: String? = null,
    val location: String? = null,
    val points: Int? = null,
) : IBaseDiffModel