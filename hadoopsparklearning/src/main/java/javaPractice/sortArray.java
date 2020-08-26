package javaPractice;

public class sortArray {
    public static int[] sort(int[] nums) {
        int i = 0, j = nums.length - 1, ind = nums.length - 1;
        int[] output = new int[nums.length];
        while (nums[j] >= 0 && i < j) {
            if (Math.abs(nums[i]) > nums[j]) {
                output[ind] = nums[i];
                i++;
            } else {
                output[ind] = nums[j];
                j--;
            }
            ind--;
        }
        while (i < j && nums[j] >= 0) {
            output[ind] = nums[j];
            j--;
            ind--;
        }
        while (i < j && nums[i] < 0) {
            output[ind] = nums[i];
            i++;
            ind--;
        }
        return output;
    }

    public static void main(String args[]) {
        int[] arr = {-8, -5, -3, -1, 0, 4, 6, 9};
        arr = sort(arr);
        for(int a:arr){
            System.out.print(a+" ");
        }
    }
}
