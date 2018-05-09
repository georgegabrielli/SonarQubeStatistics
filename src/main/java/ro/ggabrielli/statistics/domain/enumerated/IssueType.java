package ro.ggabrielli.statistics.domain.enumerated;

import lombok.Getter;

/**
 * Created by gobi on 5/9/2018.
 */
@Getter
public enum IssueType {

    CODE_SMELL(1),
    BUG(2),
    VULNERABILITY(3);

    private final int type;

    IssueType(int type){
        this.type = type;
     }
}
