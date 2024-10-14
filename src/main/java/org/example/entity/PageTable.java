package org.example.entity;

/**
 * 页表实体
 */
public class PageTable {
    public int pageNum; // 页号
    public int blockNum; // 块号
    public int status; // 状态位，0表示不在内存中，1表示在内存中
    public int access; // 访问字段，在LRU算法中相当于位移寄存器（Shift Register）

    public PageTable(int pageNum, int blockNum, int status, int access) {
        this.pageNum = pageNum;
        this.blockNum = blockNum;
        this.status = status;
        this.access = access;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
