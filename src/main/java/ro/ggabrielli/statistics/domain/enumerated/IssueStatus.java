package ro.ggabrielli.statistics.domain.enumerated;

import lombok.Getter;

@Getter
public enum IssueStatus {
    OPEN("OPEN"),
    CLOSED("CLOSED");

    private final String status;

    IssueStatus(String status){
        this.status = status;
    }
}
