package com.jean.community.entity;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author The High Priestess
 * @date 2021/10/6 21:28
 * f分页
 */
public class Page {

    //    当前页码
    private int current = 1;
    //    显示上限
    private int limit = 10;
    //    数据总数（用于计算总页数）
    private int rows;
    //    查询路径（用于复用分页链接）
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 10) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /*
    * 获得当前页面的起始页
    * */
    public int getOffset() {
        return (current - 1) * limit;
    }

    /*
    * 获取总的页数
    * */
    public int  getTotal(){
        if (rows % limit == 0) {
            return rows/limit;
        } else {
            return rows/limit +1;
        }
    }

    /*
        获得起始页码
     */
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /*
    获得终止页码
   */
    public int getTo() {
        int to = current + 2;
        int total =getTotal();
        return to > total ? total : to;
    }

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", limit=" + limit +
                ", rows=" + rows +
                ", path='" + path + '\'' +
                '}';
    }
}
