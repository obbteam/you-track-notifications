package org.example.adapters.driven.ForCreatingIssues

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class CreatedIssue(
    val idReadable: String,
    val summary: String
)
