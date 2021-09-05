package operation

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description('Test process fail method')
    request {
        method 'POST'
        url '/api/v1/operation/process'
        body(
            id: 1,
            searchString: 'test'
        )
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 500
        body(
            message: "Id '1' too low"
        )
        headers {
            contentType(applicationJson())
        }
    }
})
