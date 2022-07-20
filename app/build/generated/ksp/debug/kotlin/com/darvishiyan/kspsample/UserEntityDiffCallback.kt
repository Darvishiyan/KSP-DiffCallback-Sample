package com.darvishiyan.kspsample

import androidx.recyclerview.widget.DiffUtil

// It is user entity diff call back
// It is the default comment!
object UserEntityDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
	override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
		return oldItem.id == newItem.id && oldItem.isPublic == newItem.isPublic 
	}

	override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
		return oldItem == newItem
	}
}