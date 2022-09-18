package uz.gita.firebasesample.data.models.local

import java.io.Serializable

// Created by Jamshid Isoqov an 9/17/2022

data class ProductCategoryData(
    val id: String,
    val name: String,
    val imageUrl: String,
    val tags: List<String>
) : Serializable
