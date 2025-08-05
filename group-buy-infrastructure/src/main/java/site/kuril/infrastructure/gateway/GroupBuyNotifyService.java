package site.kuril.infrastructure.gateway;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import site.kuril.domain.trade.adapter.port.ITradePort;
import site.kuril.domain.trade.model.entity.NotifyTaskEntity;
import site.kuril.types.enums.NotifyTaskHTTPEnumVO;
import site.kuril.types.enums.ResponseCode;
import site.kuril.types.exception.AppException;

import javax.annotation.Resource;

/**
 * 拼团回调通知服务
 * <p>
 * 基于OkHttp实现HTTP回调通知
 * </p>
 */
@Slf4j
@Service
public class GroupBuyNotifyService implements ITradePort {

    @Resource
    private OkHttpClient okHttpClient;

    @Override
    public String groupBuyNotify(NotifyTaskEntity notifyTask) throws Exception {
        String apiUrl = notifyTask.getNotifyUrl();
        String parameterJson = notifyTask.getParameterJson();
        
        try {
            log.info("拼团回调通知开始 - URL: {}, 参数: {}", apiUrl, parameterJson);
            
            // 1. 构建请求参数
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, parameterJson);
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .build();

            // 2. 调用接口
            Response response = okHttpClient.newCall(request).execute();
            String responseBody = response.body().string();
            
            log.info("拼团回调通知完成 - URL: {}, 响应: {}", apiUrl, responseBody);

            // 3. 返回结果判断
            if (NotifyTaskHTTPEnumVO.SUCCESS.getCode().equals(responseBody)) {
                return NotifyTaskHTTPEnumVO.SUCCESS.getCode();
            } else {
                log.warn("拼团回调通知失败 - URL: {}, 响应: {}", apiUrl, responseBody);
                return NotifyTaskHTTPEnumVO.ERROR.getCode();
            }
        } catch (Exception e) {
            log.error("拼团回调 HTTP 接口服务异常 - URL: {}", apiUrl, e);
            return NotifyTaskHTTPEnumVO.ERROR.getCode();
        }
    }

} 