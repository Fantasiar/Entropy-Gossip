package fenbushi;

import java.util.Random;

public class anti_entropy { 
	//���鳤�ȳ���
	static final int LENGTH =100;
	
	public static void main(String[] args) {
		double[] arr=new double[LENGTH];
		Random random=new Random();
		
		//��ӡ��ʼ����
		System.out.print("old array: ");
		for(int i=0;i<arr.length;i++) {
			arr[i]=random.nextInt(100);
			System.out.print(arr[i]+"  ");
		}
		System.out.println();
		
		//��ʼ��Ⱦ
		entropy(arr);
	}
	
	public static void entropy(double[] arr){
		//��¼��������
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

		//ת�������������Ԫ����ֵ����ȣ�ȡ��1��Ԫ����Ϊƽ����
		double average=arr[0];
		
		//��ӡת���������
		System.out.print("new array: ");
		for (double a : arr) {
			System.out.print(a+"  ");
		}
		System.out.println();
		
		System.out.println("ƽ������"+average);
		System.out.println("����������"+count);
	}
	
	//ͨ��exchange������ʽ���д���
	public static double exchange(double m,double n) {
		return (m+n)/2;
	}
	
	//ѭ����������
	public static boolean isDifferent(double []arr) {
		boolean isDifferrnt=false;
		
		//����������ֵ�����ʱ�˳�
		for (int i=1;i<arr.length;i++) {
			if (arr[0]!=arr[i]) {
				isDifferrnt=true;
			}
		}
		
		return isDifferrnt;
	}
}
