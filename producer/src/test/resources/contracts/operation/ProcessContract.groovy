package operation

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description('Test ping fail method')
    request {
        method 'POST'
        url '/api/op/process'
        body(
            id: 101,
            searchString: 'test'
        )
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body(
            code: 100,
            data: 'TEST'
        )
        headers {
            contentType(applicationJson())
        }
    }
})
