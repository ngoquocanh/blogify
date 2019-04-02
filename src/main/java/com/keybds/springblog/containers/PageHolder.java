package com.keybds.springblog.containers;

import java.util.List;

public class PageHolder<T> extends PageUtil<T> {

    private String baseUrl;

    public PageHolder(PageUtil<T> pageUtil) {
        super(pageUtil.getCurrentPageRecords(), pageUtil.getPageIndex(), pageUtil.getPageSize(),
                pageUtil.getListPageSizeOptions(), pageUtil.getPageRange(), pageUtil.getTotalRecords());
        this.baseUrl = null;
    }

    public PageHolder(PageUtil<T> pageUtil, String baseUrl) {
        super(pageUtil.getCurrentPageRecords(), pageUtil.getPageIndex(), pageUtil.getPageSize(),
                pageUtil.getListPageSizeOptions(), pageUtil.getPageRange(), pageUtil.getTotalRecords());
        this.baseUrl = baseUrl;
    }

    public PageHolder(List<T> currentPageRecords, Integer pageIndex, Integer pageSize,
                      List<Integer> listPageSizeOptions, Integer pageRange, Integer totalRecords) {
        super(currentPageRecords, pageIndex, pageSize, listPageSizeOptions, pageRange, totalRecords);
        this.baseUrl = null;
    }

    public PageHolder(List<T> currentPageRecords, Integer pageIndex, Integer pageSize,
                    List<Integer> listPageSizeOptions, Integer pageRange, Integer totalRecords, String baseUrl) {
        super(currentPageRecords, pageIndex, pageSize, listPageSizeOptions, pageRange, totalRecords);
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
