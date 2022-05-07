package app.hiring.common;

import java.util.List;

import app.hiring.model.ResponseDataModel;
import retrofit2.Call;
import retrofit2.http.GET;

/*
 * common/ApiInterface.java - Retrofit call to read JSON
 * Response -> ResponseDataModel(id, listId, name)
 */

public interface ApiInterface {

    @GET("/hiring.json")
    Call<List<ResponseDataModel>> getJSONResponse();
}