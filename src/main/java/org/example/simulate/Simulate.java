package org.example.simulate;

import org.example.entity.PCB;
import org.example.util.InitUtil;
import org.example.util.ShowUtil;

import java.util.Scanner;
import java.util.Vector;

import static org.example.util.RunUtil.run;

public class Simulate {
    public static final int ORDER_SET_LENGTH = 10; // 指令集大小
    public static final int PIECE_NUM = 50; // 内存块数
    public static final int MAX_PAGE_NUM = 5; // 调入内存的页表长度上限
    public static final int BLOCK_TIME = 3; // 阻塞时间
    public static int[] memory = new int[PIECE_NUM]; // 内存数组
    public static Vector<PCB> readyQueue = new Vector<>(); // 就绪队列
    public static Vector<PCB> runProcess = new Vector<>(); // 运行进程
    public static Vector<PCB> blockQueue = new Vector<>(); // 阻塞队列
    public static int timeSlice = 0; // 时间片
    public static int finishNum = 0; // 已完成进程数
    public static Vector<String> messager = new Vector<>(); // 提示信息
    public static void main(String[] args) {
        boolean isInit = false;
        while (true) {
            System.out.println("\033[30m" + "\nUse items below to simulate a simple operating system.");
            System.out.println("1. initiate processes and memory");
            System.out.println("2. start simulation");
            System.out.println("Please enter the serial number:");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    PCB p1 = new PCB();
                    PCB p2 = new PCB();
                    PCB p3 = new PCB();
                    PCB p4 = new PCB();
                    PCB p5 = new PCB();
                    if (!isInit) {
                        isInit = true;
                        InitUtil.init(p1, p2, p3, p4, p5);
                        ShowUtil.showTable();
                        ShowUtil.showMemory();
                    }
                }
                case 2 -> run();
                default -> System.out.println("Error input. Please re-enter!");
            }
        }
    }
}
