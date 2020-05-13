package contracts.controller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("returns a list of issues to download")
    request {
        method GET()
        url "/issue/todownload"
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body(
                issueToDownload: []
        )
        headers {
            contentType(applicationJson())
        }
    }
}