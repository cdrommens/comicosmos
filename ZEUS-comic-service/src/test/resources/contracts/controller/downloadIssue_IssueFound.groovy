package contracts.controller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("returns the number of scheduled issues")
    request {
        method GET()
        url "/issue/download"
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body(
                numberOfIssuesScheduled: $(regex('[0-9]{1,}'))
        )
        headers {
            contentType(applicationJson())
        }
    }
}