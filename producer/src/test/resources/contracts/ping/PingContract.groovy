package ping

import org.springframework.cloud.contract.spec.Contract

Contract.make({
    description('Ping method')
    request {
        method 'GET'
        url '/api/ping'
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body(
                response: 'ok'
        )
        headers {
            contentType(applicationJson())
        }
    }
})
