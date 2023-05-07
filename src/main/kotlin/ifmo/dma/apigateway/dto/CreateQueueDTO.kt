package ifmo.dma.apigateway.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class CreateQueueDTO @JsonCreator constructor  (
        @JsonProperty("queueName") val queueName: String
)
