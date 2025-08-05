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


        return downgradeSwitch != null && "1".equals(downgradeSwitch); // downgradeSwitch != null && "1".equals(downgradeSwitch);
    }

    public boolean isCutRange(String userId) {



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

    }

}
