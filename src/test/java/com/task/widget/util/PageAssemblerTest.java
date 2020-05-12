package com.task.widget.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

class PageAssemblerTest {

    @Test
    void getPage_shouldConvertListIntoPageResponse() {
        Page<String> page = PageAssembler.getPage(PageRequest.of(0, 2), Arrays.asList("1", "2", "3", "4", "5"));

        Assertions.assertEquals(5, page.getTotalElements());
        Assertions.assertEquals(3, page.getTotalPages());
        Assertions.assertEquals(2, page.getContent().size());
    }
}
