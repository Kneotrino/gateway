package com.github.kneotrino.gateway.service;

import com.github.kneotrino.gateway.common.base.BaseService;
import com.github.kneotrino.gateway.common.util.ConnectionUtil;
import com.github.kneotrino.gateway.dto.PegawaiDto;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.*;

import java.io.IOException;
import java.util.List;

interface PegawaiRest {

    @GET("/api/pegawai/all")
    Call<List<PegawaiDto>> getAll();

    @GET("/api/pegawai/{id}")
    Call<PegawaiDto> getOneById(@Path("id") long Id);

    @GET("/api/pegawai/page")
    Call<List<PegawaiDto>> getPage(@Query("page") long page,@Query("size") long size);

    @DELETE("/api/pegawai/{id}")
    Call<Void> disableOneById(@Path("id") long Id);

    @PUT("/api/pegawai/{id}")
    Call<PegawaiDto> updateOneById(@Body PegawaiDto data, @Path("id") long Id);

    @POST("/api/pegawai/")
    Call<PegawaiDto> createOne(@Body PegawaiDto data);

    @PATCH("/api/pegawai/{id}")
    Call<PegawaiDto> restoreOneById(@Path("id") long Id);

}

@Slf4j
@Service
public class PegawaiService implements BaseService<PegawaiDto> {
    final static String ENTITY_NAME = "Pegawai";

    private static final Retrofit retrofit = null;

    PegawaiRest rest;

    public PegawaiService(@Value("${service.employee.url}") String Url, @Value("${service.employee.port}") String Port) {
        String URL = Url + ":" + Port + "/";
        if (ConnectionUtil.isConnectedToServer(URL, 10000)) {
            log.info("Connect Health Service URL = " + URL);
        } else {
            log.warn("Cannot Connect Health Service URL = " + URL);
        }
        rest = ConnectionUtil.BuildClient(URL).create(PegawaiRest.class);
    }


    @Override
    public PegawaiDto SelectOneAvailableById(Long id) throws IOException, NotFoundException {
        Call<PegawaiDto> call = rest.getOneById(id);
        Response<PegawaiDto> response = call.execute();
        log.info("request " + call.request().url() + "\n;response.code() = " + response.code());
        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
        }
        else {
            return response.body();
        }
    }

    @Override
    public PegawaiDto InsertOneAvailableById(PegawaiDto data) throws IOException {
        Call<PegawaiDto> call = rest.createOne(data);
        Response<PegawaiDto> response = call.execute();
        log.info("request " + call.request().url() + "\n;response.code() = " + response.code() + ";data = " + data);

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
        } else {
            return response.body();
        }
    }

    @Override
    public PegawaiDto UpdateOneAvailableById(PegawaiDto data, Long id) throws IOException, NotFoundException {
        Call<PegawaiDto> call = rest.updateOneById(data, id);
        Response<PegawaiDto> response = call.execute();
        log.info("request " + call.request().url() + "\n;response.code() = " + response.code() + ";data = " + data);
        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
        } else {
         return response.body();
        }
    }

    @Override
    public List<PegawaiDto> SelectAllAvailable() throws IOException, NotFoundException {
        Call<List<PegawaiDto>> call = rest.getAll();
        Response<List<PegawaiDto>> response = call.execute();

        log.info("request " + call.request().url() + "\n;response.code() = " + response.code() + ";data = " + response.body().size());
        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
        }
        else {
            return response.body();
        }
    }

    @Override
    public void DisableOneAvailableById(Long id) throws IOException {
        Call<Void> call = rest.disableOneById(id);
        Response<Void> response = call.execute();
        log.info("request.url = " + call.request().url());
        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
        }
    }

    @Override
    public PegawaiDto EnableOneAvailableById(Long id) throws IOException {
        Call<PegawaiDto> call = rest.restoreOneById(id);
        Response<PegawaiDto> response = call.execute();
        log.info("request.url = " + call.request().url());
        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
        } else {
            return response.body();
        }
    }

    @Override
    public String GetEntityName() {
        return ENTITY_NAME;
    }

    @Override
    public Page<PegawaiDto> SelectByPageable(Pageable pageable) throws IOException {
        Call<List<PegawaiDto>> call = rest.getPage(pageable.getPageNumber(),pageable.getPageSize());
        Response<List<PegawaiDto>> response = call.execute();
        log.info("request " + call.request().url() + "\n;response.code() = " + response.code() + ";data = " + response.body().size());
        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
        }
        else {
            return new PageImpl<>(response.body());
        }
    }
}