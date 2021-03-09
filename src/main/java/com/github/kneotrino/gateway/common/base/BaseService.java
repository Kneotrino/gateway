package com.github.kneotrino.gateway.common.base;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

/**
 * @author Kneotrino
 * @date 17/12/20
 */
public interface BaseService<DTO> {


    DTO SelectOneAvailableById(Long id) throws NotFoundException, IOException;

    DTO InsertOneAvailableById(DTO data) throws NotFoundException, IOException;

    DTO UpdateOneAvailableById(DTO data, Long id) throws NotFoundException, IOException;

    List<DTO> SelectAllAvailable() throws IOException, NotFoundException;

    void DisableOneAvailableById(Long id) throws NotFoundException, IOException;

    DTO EnableOneAvailableById(Long id) throws NotFoundException, IOException;

    String GetEntityName();

    Page<DTO> SelectByPageable(Pageable pageable) throws IOException;
}
