package org.example.entity;

/**
 * 指令实体
 */
public class Order {
    public String orderType; // 指令类型：READ 读内存 WRITE 写内存 INPUT 输入 OUTPUT 输出
    public int orderTargetPageNum; // 指令要访问的目标页号

    public Order(String orderType, int orderTargetPageNum) {
        this.orderType = orderType;
        this.orderTargetPageNum = orderTargetPageNum;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setOrderTargetPageNum(int orderTargetPageNum) {
        this.orderTargetPageNum = orderTargetPageNum;
    }

    // 不能直接使用构造方法初始化指令信息，否则可能导致空引用异常
    public void setInfo(String orderType, int orderTargetPageNum) {
        setOrderType(orderType);
        setOrderTargetPageNum(orderTargetPageNum);
    }
}
