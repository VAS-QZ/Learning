package linked_list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode a = ListNode.reverse(l1);
        ListNode b = ListNode.reverse(l2);
        ListNode c = add(a, b);
        return ListNode.reverse(c);
    }

    public ListNode add(ListNode l1, ListNode l2) {
        int i = l1.val;
        int j = l2.val;
        int sum = i + j;
        int carry = sum / 10;
        if(carry != 0) {
            sum = sum % 10;
        }
        ListNode res = new ListNode(sum);
        ListNode node = res;
        l1 = l1.next;
        l2 = l2.next;
        while(l1 != null || l2 != null) {
            if(l1==null) {
                i = 0;
            }else {
                i = l1.val;
                l1 = l1.next;
            }
            if(l2==null) {
                j = 0;
            }else {
                j = l2.val;
                l2 = l2.next;
            }
            sum = i + j + carry;
            carry = sum / 10;
            if(carry != 0) {
                sum = sum % 10;
            }
            ListNode next = new ListNode(sum);
            res.next = next;
            res = res.next;
        }
        if(carry != 0) {
            ListNode head = new ListNode(carry);
            res.next = head;
        }
        return node;
    }

    public int lengthOfLongestSubstring(String s) {
        if(s.length() == 0) {
            return 0;
        }
        int max = 0;
        String tmp = new String();
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            if(tmp.length() == 0) {
                tmp += s.charAt(i);
                num++;
                max = 1;
            }else {
                char b = s.charAt(i);
                int space = tmp.indexOf(b);
                if(space > -1) {
                    if(max < num) {
                        max = num;
                    }
                    tmp = tmp.substring(space + 1, tmp.length()) + b;
                    num = tmp.length();
                }else {
                    tmp += b;
                    num++;
                }
            }
        }
        if(max < num) {
            max = num;
        }
        return max;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int sumNum = nums1.length + nums2.length;
        int first = (sumNum + 1) / 2;
        int second = (sumNum + 2) /2;
        if(first == second) {
            return getKth(nums1, 0, nums1.length, nums2, 0, nums2.length, first);
        }else {
            return (getKth(nums1, 0, nums1.length, nums2, 0, nums2.length, first) +
                    getKth(nums1, 0, nums1.length, nums2, 0, nums2.length, second)) * 0.5;
        }
    }

    /**
     * 获取两个有序数组中第k小的数
     * @param nums1 第一个数组
     * @param head1 有效头位置
     * @param length1 第一个数组的长度
     * @param nums2 第二个数组
     * @param head2 有效头位置
     * @param length2 第二个数组的长度
     * @param k k
     * @return 第k小的数
     */
    private int getKth(int[] nums1, int head1, int length1, int[] nums2, int head2, int length2, int k) {
        int remainLen1 = length1 - head1;
        int remainLen2 = length2 - head2;
        //保证nums1是长度较短的数组
        if(remainLen1 > remainLen2) {
            return getKth(nums2, head2, length2, nums1, head1, length1, k);
        }
        //情况1，某一数组中的所有数都小于中位数
        if(remainLen1 == 0) {
            //k=1表示寻找数组中的第一最小值，即nums[0] = nums2[k-1]
            return nums2[head2 + k - 1];
        }
        //情况2，每个数组中都有小于或大于中位数的数
        if(k == 1) {
            return nums1[head1] < nums2[head2] ? nums1[head1] : nums2[head2];
        }
        int removeNum = k/2;
        int remove1 = (remainLen1 < removeNum ? remainLen1 : removeNum);
        int remove2 = (remainLen2 < removeNum ? remainLen2 : removeNum);
        int newHead1 = head1 + remove1;
        int newHead2 = head2 + remove2;
        //比较的是两个数组中newHead之前的那个数的大小
        if(nums1[newHead1 - 1] < nums2[newHead2 - 1]) {
            return getKth(nums1, newHead1, length1, nums2, head2, length2, k - remove1);
        }else {
            return getKth(nums1, head1, length1, nums2, newHead2, length2, k - remove2);
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<Integer> l = new ArrayList<>();
        for(int i = 0; i < nums.length; i++) {
            l.add(nums[i]);
        }
        List<List<Integer>> res = new ArrayList<>();
        backtrack(res, l, 0);
        return res;
    }

    private void backtrack(List<List<Integer>> res, List<Integer> nums, int index) {
        if(index == nums.size()) {
            res.add(new ArrayList<>(nums));
            return;
        }
        for(int i = index + 1; i < nums.size(); i++) {
            Collections.swap(nums, index, i);
            backtrack(res, nums, index + 1);
            Collections.swap(nums, index, i);
        }
    }

    public String convert(String s, int numRows) {
        if(s.length() <= numRows || numRows == 1) {
            return s;
        }
        List<String> strs = new ArrayList<>();
        List<String> strs2 = new ArrayList<>();
        if(numRows == 2) {
            StringBuilder builder = new StringBuilder();
            for(int i = 0;i < s.length(); i+=2) {
                builder.append(s.charAt(i));
            }
            for(int i = 1;i < s.length(); i+=2) {
                builder.append(s.charAt(i));
            }
            return builder.toString();
        }
        int begin = 0;
        while(true) {
            int end = s.length() < (2 * numRows + begin - 2) ? s.length() : (2 * numRows + begin - 2);
            String a = s.substring(begin, end);
            strs.add(a);
            begin = end;
            if(end == s.length()) {
                break;
            }
        }
        String fullEmpty = new String();
        for(int i = 0; i < numRows; i++) {
            fullEmpty += " ";
        }
        for(int i = 0; i < strs.size(); i++) {
            String tmp = strs.get(i);
            if(tmp.length() < numRows) {
                String empty = new String();
                for(int j = 0; j < numRows - tmp.length(); j++) {
                    empty += " ";
                }
                strs2.add(empty + new StringBuilder(tmp).reverse().toString());
                break;
            }
            strs2.add(new StringBuilder(tmp.substring(0, numRows)).reverse().toString());
            strs2.add(" " + tmp.substring(numRows) + fullEmpty);
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < strs2.size(); j++) {
                char t = strs2.get(j).charAt(numRows - i - 1);
                if(t != ' ') {
                    builder.append(t);
                }
            }
        }

        return builder.toString();

    }

    public String convert_official(String s, int numRows) {
        if(numRows == 1) {
            return s;
        }
        List<StringBuilder> strs = new ArrayList<>();
        for(int i = 0; i < numRows; i++) {
            strs.add(new StringBuilder());
        }

        int row = 0;
        int dir = -1;
        for(char c : s.toCharArray()) {
            strs.get(row).append(c);
            if(row == numRows - 1 || row == 0) {
                dir *= -1;
            }
            row += dir;
        }
        StringBuilder res = new StringBuilder();
        for(StringBuilder stringBuilder : strs) {
            res.append(stringBuilder);
        }
        return res.toString();

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()) {
            int n = in.nextInt();
            List<int[]> ti = new ArrayList<>();
            for(int i = 0; i < n; i++) {
                int hour = in.nextInt();
                int min = in.nextInt();
                int[] a = new int[]{hour, min};
                ti.add(a);
            }
            int cost = in.nextInt();
            int beginHour = in.nextInt();
            int beginMin = in.nextInt();
            int[] ok = sub(beginHour, beginMin, cost);
            for(int t = ti.size() - 1; t >= 0; t--) {
                if(!campare(ti.get(t)[0], ti.get(t)[1], ok[0], ok[1])) {
                    System.out.println(ti.get(t)[0] + " " + ti.get(t)[1]);
                    break;
                }
            }
        }
    }

    public int[] test(List<int[]> ti, int cost, int beginHour, int beginMin) {
        int[] ok = sub(beginHour, beginMin, cost);
        for(int t = ti.size() - 1; t >= 0; t--) {
            if(!campare(ti.get(t)[0], ti.get(t)[1], ok[0], ok[1])) {
//                System.out.println(ti.get(t)[0] + " " + ti.get(t)[1]);
//                break;
                return ti.get(t);

            }
        }
        return null;
    }

    private static boolean campare(int hour1, int min1, int hour2, int min2) {
        if(hour1 < hour2) {
//            if(hour2 > 22) {
//
//            }
            return false;
        }
        if(hour1 > hour2) {
            return true;
        }
        if(min1 < min2) {
            return false;
        }
        if(min1 > min2) {
            return true;
        }
        return false;
    }

    private static int[] sub(int hour, int min, int cost) {
        int t = 0;
        int minRes;
        while(true) {
            if((min + 60 * t) >= cost) {
                minRes = min + 60 * t - cost;
                break;
            }
            t++;
        }
        hour -= t;
        if(hour < 0) {
            hour +=24;
        }
        return new int[]{hour, minRes};
    }

}
