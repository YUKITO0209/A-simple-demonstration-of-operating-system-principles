package org.example.util;

import org.example.entity.Order;
import org.example.entity.PCB;
import org.example.entity.PageTable;

import static org.example.simulate.Simulate.*;

/**
 * 初始化工具类
 */
public class InitUtil {
    /**
     * 初始化内存
     */
    public static void initMemory() {
        int i = 0;
        int pNum = 0; // 进程号
        // 系统区 0-24
        for (; i <= 24; i++) {
            memory[i] = 0;
        }
        // 用户区 25-50
        for (; i < 50; i++) {
            if (i % 5 == 0) { // 每个进程分配5个物理块
                pNum++;
            }
            memory[i] = pNum;
        }
    }

    /**
     * 初始化进程
     */
    public static void initProcess(PCB p1, PCB p2, PCB p3, PCB p4, PCB p5) {
        // 初始化PCB
        p1.setPcbInfo(0, "p1", 55, 0, "READY", 0, 25);
        p2.setPcbInfo(1, "p2", 36, 0, "READY", 0, 30);
        p3.setPcbInfo(2, "p3", 14, 0, "READY", 0, 35);
        p4.setPcbInfo(3, "p4", 27, 0, "READY", 0, 40);
        p5.setPcbInfo(4, "p5", 19, 0, "READY", 0, 45);

        // 初始化各进程的页表
        for (int i = 0; i < ORDER_SET_LENGTH; i++) {
            p1.pageTable.addElement(new PageTable(i, -1, 0, 0));
            p2.pageTable.addElement(new PageTable(i, -1, 0, 0));
            p3.pageTable.addElement(new PageTable(i, -1, 0, 0));
            p4.pageTable.addElement(new PageTable(i, -1, 0, 0));
            p5.pageTable.addElement(new PageTable(i, -1, 0, 0));
        }

        // 对所有进程的指令集进行初始化，否则会产生空引用异常
        for (int i = 0; i < ORDER_SET_LENGTH; i++) {
            p1.orderSet[i] = new Order(null, 0);
            p2.orderSet[i] = new Order(null, 0);
            p3.orderSet[i] = new Order(null, 0);
            p4.orderSet[i] = new Order(null, 0);
            p5.orderSet[i] = new Order(null, 0);
        }

        p1.orderSet[0].setInfo("READ", 0); // 阻塞，缺页中断
        p1.orderSet[1].setInfo("WRITE", 1); // 缺页中断
        p1.orderSet[2].setInfo("READ", 2); // 缺页中断
        p1.orderSet[3].setInfo("WRITE", 3); // 缺页中断
        p1.orderSet[4].setInfo("INPUT", 4); // 阻塞，缺页中断
        p1.orderSet[5].setInfo("WRITE", 5); // 页面置换，缺页中断
        p1.orderSet[6].setInfo("READ", 1);
        p1.orderSet[7].setInfo("WRITE", 7); // 页面置换，缺页中断
        p1.orderSet[8].setInfo("READ", 8); // 页面置换，缺页中断
        p1.orderSet[9].setInfo("WRITE", 9); // 页面置换，缺页中断

        p2.orderSet[0].setInfo("READ", 0); // 缺页中断
        p2.orderSet[1].setInfo("INPUT", 1); // 阻塞，缺页中断
        p2.orderSet[2].setInfo("READ", 1);
        p2.orderSet[3].setInfo("OUTPUT", 3); // 阻塞，缺页中断
        p2.orderSet[4].setInfo("READ", 2); // 缺页中断
        p2.orderSet[5].setInfo("WRITE", 3);
        p2.orderSet[6].setInfo("INPUT", 6); // 阻塞，缺页中断
        p2.orderSet[7].setInfo("OUTPUT", 1); // 阻塞
        p2.orderSet[8].setInfo("READ", 0);
        p2.orderSet[9].setInfo("WRITE", 1);

        p3.orderSet[0].setInfo("READ", 0); // 缺页中断
        p3.orderSet[1].setInfo("WRITE", 1); // 缺页中断
        p3.orderSet[2].setInfo("INPUT", 1); // 阻塞
        p3.orderSet[3].setInfo("WRITE", 2); // 缺页中断
        p3.orderSet[4].setInfo("READ", 2);
        p3.orderSet[5].setInfo("WRITE", 4); // 缺页中断
        p3.orderSet[6].setInfo("READ", 1);
        p3.orderSet[7].setInfo("WRITE", 5); // 缺页中断
        p3.orderSet[8].setInfo("READ", 6); // 页面置换，缺页中断
        p3.orderSet[9].setInfo("WRITE", 2);

        p4.orderSet[0].setInfo("READ", 0); // 缺页中断
        p4.orderSet[1].setInfo("WRITE", 1); // 缺页中断
        p4.orderSet[2].setInfo("READ", 2); // 缺页中断
        p4.orderSet[3].setInfo("WRITE", 3); // 缺页中断
        p4.orderSet[4].setInfo("READ", 4); // 缺页中断
        p4.orderSet[5].setInfo("WRITE", 5); // 页面置换，缺页中断
        p4.orderSet[6].setInfo("READ", 6); // 页面置换，缺页中断
        p4.orderSet[7].setInfo("WRITE", 7); // 页面置换，缺页中断
        p4.orderSet[8].setInfo("READ", 8); // 页面置换，缺页中断
        p4.orderSet[9].setInfo("WRITE", 9); // 页面置换，缺页中断

        p5.orderSet[0].setInfo("READ", 0); // 缺页中断
        p5.orderSet[1].setInfo("INPUT", 1); // 阻塞，缺页中断
        p5.orderSet[2].setInfo("OUTPUT", 2); // 阻塞，缺页中断
        p5.orderSet[3].setInfo("WRITE", 2);
        p5.orderSet[4].setInfo("READ", 3); // 缺页中断
        p5.orderSet[5].setInfo("OUTPUT", 0); // 阻塞
        p5.orderSet[6].setInfo("READ", 2);
        p5.orderSet[7].setInfo("READ", 5); // 缺页中断
        p5.orderSet[8].setInfo("READ", 3);
        p5.orderSet[9].setInfo("WRITE", 8); // 页面置换，缺页中断
    }

    /**
     * 初始化
     */
    public static void init(PCB p1, PCB p2, PCB p3, PCB p4, PCB p5) {
        // 初始化内存
        initMemory();
        // 初始化进程
        initProcess(p1, p2, p3, p4, p5);
        // 将进程加入就绪队列
        readyQueue.addElement(p1);
        readyQueue.addElement(p2);
        readyQueue.addElement(p3);
        readyQueue.addElement(p4);
        readyQueue.addElement(p5);
    }
}
