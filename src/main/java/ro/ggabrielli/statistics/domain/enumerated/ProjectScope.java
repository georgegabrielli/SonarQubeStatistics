package ro.ggabrielli.statistics.domain.enumerated;

import lombok.Getter;

/**
 * Created by gobi on 5/9/2018.
 */
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
