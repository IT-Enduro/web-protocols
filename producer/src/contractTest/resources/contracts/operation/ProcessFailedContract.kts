import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    description = "Test process fail method"
    request {
        method = POST
        url = url("/api/v1/operation/process")
        body = body(
            mapOf(
                "id" to 1,
                "searchString" to "test"
            )
        )
        headers {
            header(CONTENT_TYPE, APPLICATION_JSON)
        }
    }
    response {
        status = INTERNAL_SERVER_ERROR
        body = body(
            mapOf(
                "message" to "Id '1' too low"
            )
        )
        headers {
            header(CONTENT_TYPE, APPLICATION_JSON)
        }
    }
}
