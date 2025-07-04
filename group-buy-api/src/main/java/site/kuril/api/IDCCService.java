package site.kuril.api;

import site.kuril.api.response.Response;


public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);

}
