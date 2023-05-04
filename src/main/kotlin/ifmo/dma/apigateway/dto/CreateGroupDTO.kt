package ifmo.dma.apigateway.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class CreateGroupDTO @JsonCreator constructor  (
        @JsonProperty("userId") val userId: Long,
        @JsonProperty("groupName") val groupName: String
)