package org.example.util;

import java.util.Scanner;

import static java.lang.System.exit;
import static org.example.simulate.Simulate.*;
import static org.example.util.ShowUtil.showMessage;
import static org.example.util.ShowUtil.showTable;

/**
 * 运行工具类
 */
public class RunUtil {
    /**
     * 抢占式优先级调度算法
     */
    public static void PreemptivePrioritySchedule() {
        // 从就绪队列中找到优先级最高的进程
        int maxPriority = -1;
        int maxPriorityIndex = 0;
        for (int i = 0; i < readyQueue.size(); i++) {
            if (readyQueue.get(i).priority > maxPriority) {
                maxPriority = readyQueue.get(i).priority;
                maxPriorityIndex = i;
            }
        }
        // 如果没有进程在运行或者该进程的优先级比运行进程的高，将该进程从就绪队列中移除，加入入运行队列中
        if (runProcess.size() == 0) {
            // 将就绪队列中的进程加入运行队列中，修改进程状态为RUNNING
            runProcess.addElement(readyQueue.get(maxPriorityIndex));
            runProcess.get(0).status = "RUNNING";
            readyQueue.remove(readyQueue.indexOf(readyQueue.firstElement()) + maxPriorityIndex);
        } else if (maxPriority > runProcess.get(0).priority) {
            // 将运行队列中的进程放入就绪队列中，修改进程状态为READY
            runProcess.get(0).status = "READY";
            readyQueue.addElement(runProcess.get(0));
            runProcess.clear();
            // 将就绪队列中的进程放入运行队列中，修改进程状态为RUNNING
            runProcess.addElement(readyQueue.get(maxPriorityIndex));
            runProcess.get(0).status = "RUNNING";
            readyQueue.remove(readyQueue.indexOf(readyQueue.firstElement()) + maxPriorityIndex);
        }
    }

    /**
     * 执行运行中进程的指令
     */
    public static void executeOrder() {
        int curOrder = runProcess.get(0).orderSetItem; // 当前执行到了第几条指令
        int pageNum = runProcess.get(0).orderSet[curOrder].orderTargetPageNum; // 当前指令要访问的页号
        int startBlock = runProcess.get(0).startBlock; // 开始块号
        int pageTabSize = 0;
        String orderType = runProcess.get(0).orderSet[curOrder].orderType; // 指令类型
        runProcess.get(0).orderSetItem++; // 当前指令序号后移一位
        // 在内存中的页面的访问字段+1，模拟位移寄存器右移1位；同时统计已在内存中的页面数量
        for (int i = 0; i < ORDER_SET_LENGTH; i++) {
            if (runProcess.get(0).pageTable.get(i).status == 1) {
                runProcess.get(0).pageTable.get(i).access++;
                pageTabSize++;
            }
        }
        // 如果执行输入或输出指令，需要将该进程加入阻塞队列中
        if (orderType.equals("INPUT") || orderType.equals("OUTPUT")) {
            runProcess.get(0).status = "BLOCKED";
            runProcess.get(0).blockingTime = BLOCK_TIME; // 阻塞时间
            blockQueue.addElement(runProcess.get(0));
            String str = "The process " + runProcess.get(0).processName + " executed the order " + orderType + " page " + pageNum + ".";
            str += "\nThe process "  + runProcess.get(0).processName +" has been added to the blocking queue.";
            messager.addElement(str);
        }
        // 判断该页是否已在内存中，即状态位是否为1
        for (int i = 0; i < ORDER_SET_LENGTH; i++) {
            // 该页已在内存中,直接执行指令即可
            if (runProcess.get(0).pageTable.get(i).pageNum == pageNum && runProcess.get(0).pageTable.get(i).status == 1) {
                if (runProcess.get(0).orderSetItem == ORDER_SET_LENGTH) { // 当前为最后一条指令，进程执行完毕
                    finishNum++;
                    String str = "The process " + runProcess.get(0).processName + " executed the order " + orderType + " page " + pageNum + ".";
                    str += "\nThe process " + runProcess.get(0).processName + " has been completed.";
                    runProcess.clear();
                    messager.addElement(str);
                } else { // 当前不是最后一条指令
                    runProcess.get(0).pageTable.get(i).access = 0;
                    String str = "The process " + runProcess.get(0).processName + " executed the order " + orderType + " page " + pageNum + ".";
                    messager.addElement(str);
                }
                // 执行输入或输出指令时，进程被加入阻塞队列，所以要清空运行进程列表
                if (orderType.equals("INPUT") || orderType.equals("OUTPUT")) {
                    runProcess.clear();
                }
                return;
            }
            // 该页不在内存中，需要新加入一页
            if (runProcess.get(0).pageTable.get(i).pageNum == pageNum && runProcess.get(0).pageTable.get(i).status == 0) {
                if (pageTabSize < MAX_PAGE_NUM) { // 内存中还有空位，直接加入该页
                    runProcess.get(0).pageTable.get(i).setBlockNum(startBlock + pageTabSize);
                    runProcess.get(0).pageTable.get(i).setStatus(1);
                    runProcess.get(0).pageTable.get(i).setAccess(0);
                    String str = "The process " + runProcess.get(0).processName + " executed the order " + orderType + " page " + pageNum + ".\nThere is a page fault.";
                    str += "\nPage " + runProcess.get(0).pageTable.get(i).pageNum + " has been added in the internal memory.";
                    messager.addElement(str);
                } else { // 内存已满，需要进行LRU页面置换
                    // 先找到最近最久未使用的页面
                    int maxAccess = runProcess.get(0).pageTable.get(0).access;
                    int maxAccessIndex = 0;
                    for (int j = 0; j < ORDER_SET_LENGTH; j++) {
                        if (runProcess.get(0).pageTable.get(j).access > maxAccess && runProcess.get(0).pageTable.get(j).status == 1) {
                            maxAccess = runProcess.get(0).pageTable.get(j).access;
                            maxAccessIndex = j;
                        }
                    }
                    int oldPageNum = runProcess.get(0).pageTable.get(maxAccessIndex).pageNum; // 即将被淘汰的页面的页号
                    // 将该页面的块号分配给新页面，同时该页面状态位设为0，新页面状态位设为1
                    runProcess.get(0).pageTable.get(i).setBlockNum(runProcess.get(0).pageTable.get(maxAccessIndex).blockNum);
                    runProcess.get(0).pageTable.get(i).setStatus(1);
                    runProcess.get(0).pageTable.get(maxAccessIndex).setBlockNum(-1);
                    runProcess.get(0).pageTable.get(maxAccessIndex).status = 0;
                    runProcess.get(0).pageTable.get(maxAccessIndex).access = 0;
                    String str = "The process " + runProcess.get(0).processName + " executed the order " + orderType + " page " + pageNum +
                            ".\nThere is a page fault.";
                    str += "\nPage " + oldPageNum + " has been replaced with page " + pageNum + ".";
                    messager.addElement(str);
                }
                // 进程执行完毕
                if (runProcess.get(0).orderSetItem == ORDER_SET_LENGTH) {
                    finishNum++;
                    String str = "The process " + runProcess.get(0).processName + " has been completed.";
                    runProcess.clear();
                    messager.addElement(str);
                    break;
                }
            }
        }
        // 执行输入或输出指令时，进程被加入阻塞队列，所以要清空运行进程列表
        if (orderType.equals("INPUT") || orderType.equals("OUTPUT")) {
            runProcess.clear();
        }
    }

    /**
     * 将进程移出阻塞队列
     */
    public static void checkBlockingQueue() {
        for (int i = 0; i < blockQueue.size(); i++) {
            blockQueue.get(i).blockingTime--;
            // 如果阻塞时间为0，则将该从阻塞队列中移除，加入到就绪队列中
            if (blockQueue.get(i).blockingTime == 0) {
                blockQueue.get(i).status = "READY";
                readyQueue.addElement(blockQueue.get(i));
                blockQueue.remove(blockQueue.indexOf(blockQueue.firstElement()) + i);
                i--;
            }
        }
    }

    /**
     * 运行时间片
     */
    public static void run() {
        while (true) {
            if (finishNum == 5) { // 共5个进程，全部执行完毕
                System.out.println("\033[30m" + "\nAll the process were completed. Simulation ended.");
                exit(0);
            }
            timeSlice++;
            // 先用抢占式优先级调度选出一个进程
            PreemptivePrioritySchedule();
            // 运行进程的优先度-3,就绪队列的优先度+1
            runProcess.get(0).priority -= 3;
            runProcess.get(0).runningTime++;
            for (org.example.entity.PCB pcb : readyQueue) {
                pcb.priority += 1;
            }
            executeOrder(); // 读取指令类型
            checkBlockingQueue(); // 检查阻塞队列
            showTable(); // 显示队列表格
            showMessage(); // 显示提示信息
            // 输入一个回车就执行一个时间片
            System.out.println("\033[30m" + "\nPlease press ENTER to continue.");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
        }
    }
}
