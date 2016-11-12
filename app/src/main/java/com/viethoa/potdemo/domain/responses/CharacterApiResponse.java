package com.viethoa.potdemo.domain.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VietHoa on 10/11/2016.
 */

public class CharacterApiResponse<T> extends BaseResponse {

    @SerializedName("results")
    private Data<T> data;

    public Data<T> getData() {
        return data;
    }

    public class Data<T> {
        @SerializedName("offset")
        private int page;
        @SerializedName("total")
        private int totalPage;
        @SerializedName("results")
        private T results;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public T getResults() {
            return results;
        }

        public void setResults(T results) {
            this.results = results;
        }
    }
}
