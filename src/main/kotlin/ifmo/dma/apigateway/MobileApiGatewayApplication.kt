package ifmo.dma.apigateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
class MobileApiGatewayApplication

fun main(args: Array<String>) {
	runApplication<MobileApiGatewayApplication>(*args)
}
