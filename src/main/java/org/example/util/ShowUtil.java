package org.example.util;

import static org.example.simulate.Simulate.*;

/**
 * 展示结果工具类
 */
public class ShowUtil {
    /**
     * 显示当前时间片
     */
    public static void showTime() {
        System.out.println("\033[31m" + "\n------------------------Time Slice " + timeSlice + "------------------------\n");
    }

    /**
     * 显示就绪队列
     */
    public static void showReadyQueue() {
        System.out.println("\033[34m" + "Ready Processes:");
        System.out.println("-------------------------------------------------------------");
        System.out.println("PID\t\tProcess Name\tPriority\tStatus\t\tRunning Time");
        for (org.example.entity.PCB pcb : readyQueue) {
            System.out.println(pcb.id + "\t\t\t" + pcb.processName + "\t\t\t" + pcb.priority + "\t\t\t" + pcb.status + "\t\t\t" + pcb.runningTime);
        }
        System.out.println("-------------------------------------------------------------");
    }

    /**
     * 显示运行中的进程和对应页表
     */
    public static void showRunProcess() {
        System.out.println("Running Process:");
        System.out.println("-------------------------------------------------------------");
        System.out.println("PID\t\tProcess Name\tPriority\tStatus\t\tRunning Time");
        for (org.example.entity.PCB process : runProcess) {
            System.out.println(process.id + "\t\t\t" + process.processName + "\t\t\t" + process.priority + "\t\t\t" + process.status + "\t\t\t" + runProcess.get(0).runningTime);
        }
        System.out.println("-------------------------------------------------------------");
        // 显示运行中进程的页表
        for (org.example.entity.PCB ignored : runProcess) {
            System.out.println("\nPage Table:");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Page Number\t\tBlock Number\t\tStatus\t\t\tAccess");
            for (int j = 0; j < runProcess.get(0).pageTable.size(); j++) {
                System.out.println(runProcess.get(0).pageTable.get(j).pageNum + "\t\t\t\t\t" + runProcess.get(0).pageTable.get(j).blockNum + "\t\t\t\t\t" + runProcess.get(0).pageTable.get(j).status + "\t\t\t\t" + runProcess.get(0).pageTable.get(j).access);
            }
            System.out.println("-------------------------------------------------------------");
        }
    }

    /**
     * 显示阻塞队列
     */
    public static void showBlockQueue() {
        System.out.println("Blocked Processes:");
        System.out.println("-------------------------------------------------------------");
        System.out.println("PID\t\tProcess Name\tPriority\tStatus\t\tBlocked Time");
        for (org.example.entity.PCB pcb : blockQueue) {
            System.out.println(pcb.id + "\t\t\t" + pcb.processName + "\t\t\t" + pcb.priority + "\t\t\t" + pcb.status + "\t\t\t" + pcb.blockingTime);
        }
        System.out.println("-------------------------------------------------------------");
    }

    /**
     * 显示内存
     */
    public static void showMemory() {
        System.out.println("\nMemory Allocation: ");
        System.out.println("---------------------------------------");
        for (int i = 0; i < PIECE_NUM; i++) {
            System.out.print(memory[i] + "\t");
            if (i % 10 == 9) {
                System.out.println();
            }
        }
        System.out.println("---------------------------------------");
    }

    /**
     * 显示提示信息
     */
    public static void showMessage() {
        if (messager.isEmpty()) {
            return;
        }
        System.out.println("\033[36m" + "\nPrompt:");
        System.out.println("-------------------------------------------------------------");
        for (String str : messager) {
            System.out.println(str);
        }
        messager.clear();
        System.out.println("-------------------------------------------------------------");
    }

    /**
     * 显示所有信息
     */
    public static void showTable() {
        if (timeSlice == 0) {
            System.out.println("\033[31m" + "\n-------------------Initialized Information-------------------\n");
        } else {
            showTime();
        }
        showReadyQueue();
        System.out.println();
        showRunProcess();
        System.out.println();
        showBlockQueue();
    }
}
