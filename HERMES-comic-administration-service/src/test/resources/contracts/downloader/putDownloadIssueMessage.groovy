package contracts.downloader

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("""
        the issue to download
    """)
    label "download_issue"
    input {
        triggeredBy('downloadIssue()')
    }
    outputMessage {
        sentTo "downloader-in"
        body(
                issueId: $(regex('-{0,1}[0-9]{1,}')),
                comicKey: "batman-2016",
                provider: "READCOMICS",
                issueNumber: "Annual-1"
        )
        headers {
            header("type", "download-issue")
            messagingContentType(applicationJson())
        }
    }
}