import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description('Test ping fail method')
    request {
        method 'post'
        url '/api/process'
        body(
            id: 1,
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
            data: 'ABCDEFGH'
        )
        headers {
            contentType(applicationJson())
        }
    }
})
