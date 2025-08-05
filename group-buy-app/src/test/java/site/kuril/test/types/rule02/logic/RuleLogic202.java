package site.kuril.test.types.rule02.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RuleLogic202 {

    public XxxResponse apply(String request) {
        log.info("业务处理规则 RuleLogic202");
        return new XxxResponse("hello world!");
    }

}
