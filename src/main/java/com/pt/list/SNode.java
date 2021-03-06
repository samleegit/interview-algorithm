package com.pt.list;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 单链表结点
 *
 * @param <T>
 * @author panteng
 */
public class SNode<T> {
    public SNode(T data) {
        this.data = data;
    }

    public T data;
    public SNode<T> next;

    @Override
    public String toString() {
        return String.format("SNode{data = %s}", data.toString());
    }

    public static int len(ListNode head) {
        if (head == null) {
            return 0;
        }
        if (head.next == null) {
            return 1;
        }
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        int count = 3;//特别需要注意，这里初始化是3，因为走了两步，对应3个结点
        while (slow != fast && fast != null) {
            slow = slow.next;
            if (fast.next == null) {
                fast = null;
                count += 1;
            } else {
                fast = fast.next.next;
                count += 2;
            }
        }
        if (fast == null) {
            return count - 1;//将fast=null的结点去除，所以需要减1
        }
        //寻找入环点
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        //此时slow == fast，并且是入环点
        slow = head;
        int meetCount = 0;
        count = 0;
        while (meetCount < 2) {
            if (slow == fast) {
                meetCount++;
            }
            slow = slow.next;
            count++;
        }
        return count - 1;//因为入环结点统计了两次，所以需要减1
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 单链表选择排序
     * @param head
     * @return
     */
    public static ListNode sortList(ListNode head) {
        ListNode lastNodeNext = null;
        while (head.next != lastNodeNext) {
            ListNode pre = null, cur = head, next = cur.next;
            ListNode maxPre = pre, max = cur, maxNext = next;
            ListNode lastNode = null;
            while (cur != lastNodeNext) {
                if (cur.val > max.val) {
                    maxPre = pre;
                    max = cur;
                    maxNext = next;
                }
                if (cur.next == lastNodeNext) {
                    lastNode = cur;
                    break;
                }
                pre = cur;
                cur = cur.next;
                if (cur == null) {
                    next = null;
                } else {
                    next = cur.next;
                }
            }
            if (max == head) {
                head = head.next;
            }
            if (max != lastNode) {//特殊情况，如果这个结点就是最后一个结点 不需要处理
                //删除结点
                if (maxPre != null) {
                    maxPre.next = maxNext;
                }
                //添加到最后
                max.next = lastNodeNext;
                lastNode.next = max;
            }
            lastNodeNext = max;
        }

        return head;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node4.next = node2;
        node2.next = node1;
        node1.next = node3;

        ListNode node = sortList(node4);
        System.out.println(node);
    }
}
