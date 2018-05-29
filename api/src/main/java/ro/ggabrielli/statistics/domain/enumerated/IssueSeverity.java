package ro.ggabrielli.statistics.domain.enumerated;

import lombok.Getter;

@Getter
public enum IssueSeverity {
    BLOCKER("BLOCKER"),
    MAJOR("MAJOR"),
    MINOR("MINOR"),
    INFO("INFO");

    private final String severity;

    IssueSeverity (String severity) {
        this.severity = severity;
    }
}
