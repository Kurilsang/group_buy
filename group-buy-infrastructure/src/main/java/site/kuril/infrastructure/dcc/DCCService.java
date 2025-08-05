package site.kuril.infrastructure.dcc;

import site.kuril.types.annotations.DCCValue;
import org.springframework.stereotype.Service;


@Service
public class DCCService {

    /**
     * 降级开关 0关闭、1开启
     */
    @DCCValue("downgradeSwitch:0")
    private String downgradeSwitch;

    @DCCValue("cutRange:100")
    private String cutRange;

    public boolean isDowngradeSwitch() {
        // 添加null检查，如果字段未初始化，默认返回false（不降级）
        // 临时为测试环境关闭降级
        return false; // downgradeSwitch != null && "1".equals(downgradeSwitch);
    }

    public boolean isCutRange(String userId) {
        // 添加null检查，如果cutRange未初始化，默认返回true（允许切量）
        // 临时为测试环境关闭切量限制
        return true;
        
        /*
        if (cutRange == null) {
            return true;
        }
        
        // 计算哈希码的绝对值
        int hashCode = Math.abs(userId.hashCode());

        // 获取最后两位
        int lastTwoDigits = hashCode % 100;

        // 判断是否在切量范围内
        if (lastTwoDigits <= Integer.parseInt(cutRange)) {
            return true;
        }

        return false;
        */
    }

}
