package ifmo.dma.apigateway.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class MQueuesDTO @JsonCreator constructor  (
    @JsonProperty("id") val id:Long,
    @JsonProperty("name") val name:String,
    @JsonProperty("numberOfRecorded") val numberOfRecorded:Long

)
