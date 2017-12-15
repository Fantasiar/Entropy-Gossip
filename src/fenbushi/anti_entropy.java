package fenbushi;

import java.util.Random;

public class anti_entropy { 
	//数组长度常量
	static final int LENGTH =100;
	
	public static void main(String[] args) {
		double[] arr=new double[LENGTH];
		Random random=new Random();
		
		//打印初始数组
		System.out.print("old array: ");
		for(int i=0;i<arr.length;i++) {
			arr[i]=random.nextInt(100);
			System.out.print(arr[i]+"  ");
		}
		System.out.println();
		
		//开始传染
		entropy(arr);
	}
	
	public static void entropy(double[] arr){
		//记录传播次数
		int count=0;
		
		while (isDifferent(arr)) {
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr.length; j++) {
					if (i!=j) {
						arr[j]=arr[i]=exchange(arr[i], arr[j]);
						count++;
					}else {
						continue;
					}
				}
			}
		}	

		//转换后的数组所有元素数值均相等，取第1个元素作为平均数
		double average=arr[0];
		
		//打印转换后的数组
		System.out.print("new array: ");
		for (double a : arr) {
			System.out.print(a+"  ");
		}
		System.out.println();
		
		System.out.println("平均数："+average);
		System.out.println("传播次数："+count);
	}
	
	//通过exchange交换方式进行传播
	public static double exchange(double m,double n) {
		return (m+n)/2;
	}
	
	//循环结束条件
	public static boolean isDifferent(double []arr) {
		boolean isDifferrnt=false;
		
		//数组中所有值均相等时退出
		for (int i=1;i<arr.length;i++) {
			if (arr[0]!=arr[i]) {
				isDifferrnt=true;
			}
		}
		
		return isDifferrnt;
	}
}
