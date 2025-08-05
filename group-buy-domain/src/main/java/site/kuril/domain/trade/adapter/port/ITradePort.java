package site.kuril.domain.trade.adapter.port;

import site.kuril.domain.trade.model.entity.NotifyTaskEntity;

/**
 * 交易端口接口
 * <p>
 * 定义与外部系统交互的接口
 * </p>
 */
public interface ITradePort {

    /**
     * 拼团回调通知
     * 
     * @param notifyTask 回调任务实体
     * @return 回调结果 ("success" / "error")
     * @throws Exception 异常信息
     */
    String groupBuyNotify(NotifyTaskEntity notifyTask) throws Exception;

} 