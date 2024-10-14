package org.example.entity;

import java.util.Vector;

import static org.example.simulate.Simulate.ORDER_SET_LENGTH;

/**
 * 进程控制块实体
 */
public class PCB {
    public int id; // 进程序号
    public String processName; // 进程名
    public int priority; // 进程优先级
    public int runningTime; // 运行时间
    public int blockingTime; // 阻塞时间
    public String status; // 进程状态
    public int orderSetItem; // 记录当前指令序号
    public Order[] orderSet = new Order[ORDER_SET_LENGTH]; // 指令集
    public int startBlock; // 起始块号
    public Vector<PageTable> pageTable = new Vector<>(); // 每个进程对应的页表

    public PCB() {}

    public void setPcbInfo(int id, String processName, int priority, int blockingTime, String status, int orderSetItem, int startBlock) {
        this.id = id;
        this.processName = processName;
        this.priority = priority;
        this.blockingTime = blockingTime;
        this.status = status;
        this.orderSetItem = orderSetItem;
        this.startBlock = startBlock;
    }
}
