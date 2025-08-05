package site.kuril.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体对象
 * <p>
 * 表示系统中的用户基本信息，用于业务处理中的用户标识
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    /** 用户唯一标识符 */
    private String userId;

    /**
     * 获取用户标识符
     * 
     * @return 用户ID字符串
     */
    public String getCustomerId() {
        return this.userId;
    }

    /**
     * 检查用户ID是否有效
     * 
     * @return 如果用户ID不为空且不为空白字符串，返回true
     */
    public boolean isValidUser() {
        return userId != null && !userId.trim().isEmpty();
    }

    /**
     * 用户实体的字符串表示
     * 
     * @return 格式化的用户信息字符串
     */
    @Override
    public String toString() {
        return String.format("用户实体[ID=%s]", userId != null ? userId : "未设置");
    }
}
