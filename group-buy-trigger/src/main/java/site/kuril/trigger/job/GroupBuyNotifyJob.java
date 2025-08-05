package site.kuril.trigger.job;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import site.kuril.domain.trade.service.settlement.ITradeSettlementOrderService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 拼团回调通知定时任务
 * <p>
 * 定时扫描并处理未完成的拼团回调通知任务
 * </p>
 */
@Slf4j
@Service
public class GroupBuyNotifyJob {

    @Resource
    private ITradeSettlementOrderService tradeSettlementOrderService;

    /**
     * 每15秒执行一次回调通知任务
     */
    @Scheduled(cron = "0/15 * * * * ?")
    public void exec() {
        try {
            Map<String, Integer> result = tradeSettlementOrderService.execSettlementNotifyJob();
            log.info("定时任务，回调通知拼团完结任务 result:{}", JSON.toJSONString(result));
        } catch (Exception e) {
            log.error("定时任务，回调通知拼团完结任务失败", e);
        }
    }

} 