import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    description = "Test process method"
    request {
        method = POST
        url = url("/api/v1/operation/process")
        body = body(
            mapOf(
                "id" to 101,
                "searchString" to "test"
            )
        )
        headers {
            header(CONTENT_TYPE, APPLICATION_JSON)
        }
    }
    response {
        status = OK
        body = body(
            mapOf(
                "code" to 100,
                "data" to "TEST"
            )
        )
        headers {
            header(CONTENT_TYPE, APPLICATION_JSON)
        }
    }
}
