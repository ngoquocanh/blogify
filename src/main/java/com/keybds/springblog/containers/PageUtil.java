package com.keybds.springblog.containers;

import java.util.List;

public class PageUtil<T> {

    private List<T> currentPageRecords;
    private Integer totalRecords;
    private Integer totalPages;
    private List<Integer> listPageSizeOptions;
    private Integer startPage;
    private Integer endPage;
    private Integer pageSize;
    private Integer pageIndex;
    private Integer pageRange;
    private String sortName;
    private String sortDir;

    public PageUtil(List<T> currentPageRecords, Integer pageIndex, Integer pageSize,
                    List<Integer> listPageSizeOptions, Integer pageRange, Integer totalRecords) {
        this.pageSize = ((pageSize == null) || (pageSize <= 0)) ? Integer.MAX_VALUE : pageSize;
        this.pageRange = ((pageRange == null) || (pageRange <= 0)) ? 5 : pageRange;
        this.listPageSizeOptions = listPageSizeOptions;
        this.totalRecords = totalRecords;
        this.currentPageRecords = currentPageRecords;
        this.totalPages = (totalRecords + pageSize -1 ) / pageSize;

        if (this.totalRecords > 0 && this.totalPages == 0) {
            this.totalPages = 1;
        }

        if (pageIndex == null || pageIndex < 0) {
            this.pageIndex = 0;
        } else {
            if (pageIndex >= this.totalPages - 1) {
                this.pageIndex = this.totalPages - 1;
            } else {
                this.pageIndex = pageIndex;
            }
        }

        int roundUp = (this.pageRange + 1) / 2;
        this.startPage = this.pageIndex - roundUp + 1;
        this.endPage = this.pageIndex + roundUp - 1;

        if (endPage >= totalPages - 1) {
            endPage = totalPages - 1;
            if (endPage <= 0) {
                endPage = 0;
            }
            startPage = endPage - this.pageRange + 1;
            if (startPage < 0) {
                startPage = 0;
            }
        }
        if (startPage < 0) {
            startPage = 0;
            endPage = startPage + this.pageRange - 1;
            if (endPage >= totalPages - 1) {
                endPage = totalPages - 1;
                if (endPage <= 0) {
                    endPage = 0;
                }
            }
        }

        this.sortName = null;
        this.sortDir = null;
    }

    public PageUtil(List<T> currentPageRecords, Integer pageIndex, Integer pageSize,
                    List<Integer> listPageSizeOptions, Integer pageRange, Integer totalRecords, String sortName, String sortDir) {
        this(currentPageRecords, pageIndex, pageSize, listPageSizeOptions, pageRange, totalRecords);
        this.sortName = sortName;
        this.sortDir = sortDir;
    }

    public List<T> getCurrentPageRecords() {
        return currentPageRecords;
    }

    public void setCurrentPageRecords(List<T> currentPageRecords) {
        this.currentPageRecords = currentPageRecords;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Integer> getListPageSizeOptions() {
        return listPageSizeOptions;
    }

    public void setListPageSizeOptions(List<Integer> listPageSizeOptions) {
        this.listPageSizeOptions = listPageSizeOptions;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageRange() {
        return pageRange;
    }

    public void setPageRange(Integer pageRange) {
        this.pageRange = pageRange;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}
