package site.kuril.types.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.kuril.types.enums.ResponseCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 5317680961212299217L;

    /** 异常码 */
    private String code;

    /** 异常信息 */
    private String info;

    public AppException(ResponseCode responseCode) {
        super(responseCode.getInfo()); // 设置异常消息
        this.code = responseCode.getCode();
        this.info = responseCode.getInfo();
    }

    public AppException(String code) {
        super(code); // 设置异常消息
        this.code = code;
    }

    public AppException(String code, Throwable cause) {
        super(code, cause); // 设置异常消息
        this.code = code;
    }

    public AppException(String code, String message) {
        super(message); // 设置异常消息
        this.code = code;
        this.info = message;
    }

    public AppException(String code, String message, Throwable cause) {
        super(message, cause); // 设置异常消息
        this.code = code;
        this.info = message;
    }

    @Override
    public String toString() {
        return "site.kuril.x.api.types.exception.XApiException{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

}
