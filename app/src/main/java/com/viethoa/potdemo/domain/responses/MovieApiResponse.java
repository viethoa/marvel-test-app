package com.viethoa.potdemo.domain.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VietHoa on 05/11/2016.
 */

public class MovieApiResponse<T> extends BaseResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("total_page")
    private int totalPage;
    @SerializedName("results")
    private T data;

    public T getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
