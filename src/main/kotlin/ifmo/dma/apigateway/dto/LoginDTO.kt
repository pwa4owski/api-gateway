package ifmo.dma.apigateway.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class LoginDTO @JsonCreator constructor(
    @JsonProperty("username") val username: String,
    @JsonProperty("password") val password: String
)
