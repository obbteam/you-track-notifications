package adapters.driven.ForCreatingIssues

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class CreateIssueRequest(
    val summary: String,
    val project: ProjectIdentifier
)

@Serializable
data class ProjectIdentifier(
    val id: String
)