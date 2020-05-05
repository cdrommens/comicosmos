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
                issueId: 1, comicKey: "comickey", provider: "READCOMICS", issueNumber: "1"
        )
        headers {
            header("type", "download-issue")
            messagingContentType(applicationJson())
        }
    }
}