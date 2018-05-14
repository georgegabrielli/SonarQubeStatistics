package ro.ggabrielli.statistics.domain.enumerated;

import lombok.Getter;

@Getter
public enum ProjectScope {
    PROJECT("PRJ"),
    DIRECTORY("DIR"),
    FILE("FIL");

    private final String scope;

    ProjectScope(String scope){
        this.scope = scope;
    }
}
