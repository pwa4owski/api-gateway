package ifmo.dma.apigateway.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class EnterGroupDTO @JsonCreator constructor  (
        @JsonProperty("inviteCode") val inviteCode: String
)