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
                issueToDownload: [[
                    issueId: -3,
                    comicKey: "batman-2016",
                    provider: "READCOMICS",
                    issueNumber: "Annual-1",
                    dateOfRelease: [2016,2,1]
                ],
                [   issueId: -4,
                    comicKey: "batman-2016",
                    provider: "READCOMICS",
                    issueNumber: "1",
                    dateOfRelease: [2018,2,1]
                ]]
        )
        headers {
            contentType(applicationJson())
        }
    }
}