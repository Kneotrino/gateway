package com.github.kneotrino.gateway.common.base;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Kneotrino
 * @date 17/12/20
 */
public interface BaseRestController<DTO> {

  @GetMapping("/{id}")
  DTO getOneById(@PathVariable Long id) throws NotFoundException, IOException;

  @PostMapping("/")
  DTO postOne(@RequestBody DTO data) throws NotFoundException, IOException;

  @PutMapping("/{id}")
  DTO putOneById(@RequestBody DTO data, @PathVariable Long id) throws NotFoundException, IOException;

  @PatchMapping("/{id}")
  DTO restoreOneById(@PathVariable Long id) throws NotFoundException, IOException;

  @GetMapping("/all")
  List<DTO> getAll() throws IOException, NotFoundException;

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  void deleteOneById(@PathVariable Long id) throws NotFoundException, IOException;

  @GetMapping(path = "/page")
  List<DTO> getPage(@RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10") int size
  ) throws IOException;
}
