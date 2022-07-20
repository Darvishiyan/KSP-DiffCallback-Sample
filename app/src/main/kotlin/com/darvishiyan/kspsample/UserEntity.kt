package com.darvishiyan.kspsample

import com.darvishiyan.annotations.DiffCallback
import com.darvishiyan.annotations.DiffCallbackID

@DiffCallback("It is user entity diff call back")
data class UserEntity(
    @DiffCallbackID val id: Long,
    val name: String,
    val avatarUrl: String,
    @DiffCallbackID val isPublic: Boolean
)
