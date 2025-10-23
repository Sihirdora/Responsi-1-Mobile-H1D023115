package com.example.responsi1mobileh1d023115.model
import com.google.gson.annotations.SerializedName
data class TeamResponse(
    val id: Int,
    val name: String,
    val founded: Int?,
    val venue: String?,
    val squad: List<SquadMember>
)

data class SquadMember(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("position") val position: String?,
    @SerializedName("dateOfBirth") val dateOfBirth: String?,
    @SerializedName("nationality") val nationality: String,
    @SerializedName("role") val role: String?
)