package ifmo.dma.apigateway.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.el.lang.ELArithmetic.LongDelegate

data class CreateQueueDTO @JsonCreator constructor(
        @JsonProperty("queueName") val queueName: String
)
