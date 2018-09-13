package operation

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description('Test process fail method')
    request {
        method 'post'
        url '/api/op/process'
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
