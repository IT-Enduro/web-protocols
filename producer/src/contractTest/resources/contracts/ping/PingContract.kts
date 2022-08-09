import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    description = "Ping method"
    request {
        method = GET
        url = url("/api/v1/ping")
    }
    response {
        status = OK
        body = body(
            mapOf(
                "response" to "OK",
            )
        )
        headers {
            header(CONTENT_TYPE, APPLICATION_JSON)
        }
    }
}