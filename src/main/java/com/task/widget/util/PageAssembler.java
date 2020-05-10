package com.task.widget.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageAssembler {
    public static <T> Page<T> getPage(Pageable pageable, List<T> list) {
        int size = list.size();
        int pageSize = pageable.getPageSize();

        long start = pageable.getOffset();
        long end = (start + pageSize) > size ? size : (start + pageSize);

        return new PageImpl<>(list.subList(Math.toIntExact(start), Math.toIntExact(end)), pageable, size);
    }
}
