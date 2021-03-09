package com.github.kneotrino.gateway.controller;

import com.github.kneotrino.gateway.common.base.BaseModel;
import com.github.kneotrino.gateway.common.base.BaseRestController;
import com.github.kneotrino.gateway.dto.PegawaiDto;
import com.github.kneotrino.gateway.service.PegawaiService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/gate/pegawai")
public class PegawaiRestController implements BaseRestController<PegawaiDto> {

    @Autowired
    private PegawaiService service;

    @GetMapping
    public PegawaiDto getOneByUserId(@RequestParam Long userId) throws NotFoundException, IOException {
        return service.SelectOneAvailableById(userId);
    }


    @Override
    public PegawaiDto getOneById(Long id) throws NotFoundException, IOException {
        return service.SelectOneAvailableById(id);
    }

    @Override
    public PegawaiDto postOne(PegawaiDto data) throws NotFoundException, IOException {
        return service.InsertOneAvailableById(data);
    }

    @Override
    public PegawaiDto putOneById(PegawaiDto data, Long id) throws NotFoundException, IOException {
        return service.UpdateOneAvailableById(data, id);
    }

    @Override
    public PegawaiDto restoreOneById(Long id) throws NotFoundException, IOException {
        return service.EnableOneAvailableById(id);

    }

    @Override
    public List<PegawaiDto> getAll() throws IOException, NotFoundException {
        return service.SelectAllAvailable();
    }

    @Override
    public void deleteOneById(Long id) throws NotFoundException, IOException {
        service.DisableOneAvailableById(id);
    }

    @Override
    public List<PegawaiDto> getPage(int page, int size) throws IOException {
        Pageable paging = PageRequest.of(page, size, Sort.by(BaseModel.DEFAULT_SORT).descending());
        return service.SelectByPageable(paging).getContent();
    }
}
