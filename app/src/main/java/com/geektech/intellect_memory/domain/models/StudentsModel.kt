package com.geektech.intellect_memory.domain.models

import com.geektech.intellect_memory.common.base.SBaseDiffModel

data class StudentsModel(

    override val id: String? = null,
    val fullName: String? = null,
    val password: String? = null,
    val teacher: String? = null,
    val branch: String? = null,
    val location: String? = null,
    val points: Int? = null,
) : SBaseDiffModel