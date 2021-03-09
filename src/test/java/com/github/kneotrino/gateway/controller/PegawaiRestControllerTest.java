package com.github.kneotrino.gateway.controller;

import com.github.kneotrino.gateway.dto.PegawaiDto;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PegawaiRestControllerTest {

    @Autowired
    private PegawaiRestController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void getOneByUserId() throws NotFoundException, IOException {
        assertThrows(IOException.class, () -> {
            controller.getOneById(0L);
        });
        assertThat(controller.getOneById(1L)).isNotNull();
    }

    @Test
    void getOneById() {
    }

    @Test
    void postOne() throws NotFoundException, IOException {
        PegawaiDto dto = new PegawaiDto();
        dto.setNama("Kamyu Kamyu");
        dto.setAlamat("Lagi Lagi");
        assertThat(controller.postOne(dto)).isNotNull();
    }

    @Test
    void putOneById() throws NotFoundException, IOException {
        PegawaiDto dto = new PegawaiDto();
        dto.setNama("Ganti ganti");
        dto.setAlamat("tester tester");

        PegawaiDto trackingDto = controller.putOneById(dto, 1L);
        assertThat(trackingDto).isNotNull();
        assertThat(trackingDto.getAlamat()).isEqualTo("tester tester");
        assertThat(trackingDto.getNama()).isEqualTo("Ganti ganti");
    }

    @Test
    void getAll() throws IOException, NotFoundException {
        assertThat(controller.getAll().size()).isNotNull();
    }

    @Test
    void getPage() throws IOException {
        assertThat(controller.getPage(0, 10).size()).isLessThanOrEqualTo(10);
        assertThat(controller.getPage(0, 2000).size()).isLessThanOrEqualTo(2000);
        assertThat(controller.getPage(10, 100).size()).isLessThanOrEqualTo(100);
        assertThat(controller.getPage(0, 1000).size()).isLessThanOrEqualTo(1000);
    }
}