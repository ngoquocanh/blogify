package com.quopri.blogify.service.impl;

import org.springframework.data.domain.Pageable;

public abstract class AbstractService {

    /**
     * Need to call this method before findAll() because of SimpleJpaRepository -> readPage() parse error
     * when page too large.
     *
     * in readPage():
     *      query.setFirstResult((int) pageable.getOffset());
     * but in pageable.getOffset() is:
     *      return (long) page * (long) size;
     *
     * @param pageable
     * @return
     */
    protected boolean isPaged(Pageable pageable) {
        return (pageable.getOffset() + Long.valueOf(pageable.getPageSize()) <= Long.valueOf(Integer.MAX_VALUE)) ? true : false;
    }
}
