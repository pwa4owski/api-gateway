package ifmo.dma.apigateway.exception

class UserNotFoundException(s: String?) : IllegalArgumentException(s) {
}

class MicroDbServiceException(message: String?) : RuntimeException(message) {
}

