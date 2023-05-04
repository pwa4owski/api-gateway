package ifmo.dma.apigateway.dto


import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class MResponse @JsonCreator constructor(
    @JsonProperty("successful") val successful:Boolean,
    @JsonProperty("responseCode") val responseCode: Int,
    @JsonProperty("errorMessage") val errorMessage:String?,
    @JsonProperty("payload") val payload:Any?
)
