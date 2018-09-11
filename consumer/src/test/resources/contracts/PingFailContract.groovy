import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description('Test ping fail method')
    request {
        method 'get'
        url '/api/ping?fail=true'
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 422
        headers {
            contentType(applicationJson())
        }
    }
})
