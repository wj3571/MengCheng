package com.core.mvp.model;

import com.core.service.Api;
import com.core.service.ApiService;

/**
 * Created by JieGuo on 2017/5/8.
 */

public abstract class BaseModel {

//    TTICarServer apiService;

    ApiService apiService;

    public BaseModel() {
        //apiService = RetrofitNet.createApiService();
        apiService = Api.getInstance();
    }


}
