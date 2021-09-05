package ping

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description('Ping method')
    request {
        method 'GET'
        url '/api/v1/ping'
    }
    response {
        status 200
        body(
                response: 'OK'
        )
        headers {
            contentType(applicationJson())
        }
    }
})
