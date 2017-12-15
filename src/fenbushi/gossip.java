package fenbushi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.sql.rowset.RowSetWarning;

public class gossip{

    public static class Node{
    	
        private String name;    //�ڵ���
        private double num;	   //�ڵ���ֵ
        private boolean isInfect;      //�ڵ��Ⱦ״̬
        private double interest;      //�ڵ���Ȥ��������Ⱦ�ĸ��ʣ�

        public Node(String name, double num, boolean isInfect, double interest) {
            this.name = name;
            this.num = num;
            this.isInfect = isInfect;
            this.interest = interest;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            this.interest = interest;
        }

        public double getNum() {
            return num;
        }

        public String getName() {
            return name;
        }

        public void clear(){
            this.isInfect=false;
        }

        public void setNum(double avg) {
            this.num=avg;
        }

        public void setInfect(boolean infect) {
            isInfect=infect;
        }

        public boolean isInfect() {
            return isInfect;
        }
    }

    //�ڵ���������
    static final int LENGTH=100;

    public static void main(String[] args) {
        List<Node> list=new ArrayList<Node>();
        for (int i = 0; i < LENGTH; i++) {
            list.add(new Node("p" + i, new Random().nextInt(100), false, 1.0));
        }
        
        //���׽ڵ���Ϊ�Ѹ�Ⱦ״̬
        list.get(0).setInfect(true);
        
        //��ӡ��ʼ�ڵ㼯��
        System.out.print("old list: ");
        for(Node n:list){
            System.out.print(n.getName()+":"+n.getNum()+"  ");
        }
        System.out.println();
        
        //��ʼ��Ⱦ
       	spread(list);
		        
    }

   
    public static void spread(List<Node> list){
    	//count��¼��������
        int count=0;
        while(isDifferent(list)){
        	count++;   	
        	Node sender=chooseSender(list);
        	
        	//������һ����Ȥֵ���뷢�ͽڵ����Ȥֵ���бȽϣ����ǰ�߲����ں��ߣ�����Լ������������ɽ��սڵ㣩
            double randomInterest=new Random().nextDouble();
      
            if (randomInterest<=sender.getInterest()) {             
                Node receiver=list.get(new Random().nextInt(LENGTH));  
                //ֻ�з��ͽڵ�����ܽڵ㲻��ͬһ�ڵ�ʱ���Խ��д�Ⱦ
                if(sender.getName()!=receiver.getName()){
                    exchange(sender, receiver);
                }
            }
        }
        
        double sum=0;
        for (Node node : list) {
        	sum+=node.getNum();
		}
        
        //��ӡת�����list
        System.out.print("new list: ");
        for(Node n:list){
            System.out.print(n.getName()+":"+n.getNum()+"  ");
        }
        
        //ƽ����=ת�����list����������ͳ��ڵ����
        double average=sum/list.size();
        
        //��ȡlist�е�һ������Ϊ��׼
        double standard=list.get(new Random().nextInt(LENGTH)).getNum();  
        
        //���=ƽ�������׼����ľ���ֵ
        double error=Math.abs(average-standard);   

        System.out.println();
        System.out.println("ƽ����: "+average);
        System.out.println("��������: "+count);
        System.out.println("���: "+error);
        
    }

    //ѭ���˳������������нڵ����ֵ�����0.1��
    public static boolean isDifferent(List<Node> list){
        boolean isDifferent=false;
        double first=list.get(0).getNum();
        
        //���нڵ����׽ڵ����ֵ�����0.1ʱ������Ϊ���ڵ���ֵ���
        for(Node n:list){
            if(Math.abs(n.getNum()-first)>0.1){
                isDifferent=true;
                break;
            }
        }
        
        return isDifferent;
    }

    //ͨ��exchange������ʽ���д���
    public static void exchange(Node sender,Node receiver) {
    	
    	//ȡƽ������ֵ
    	double binary=(sender.getNum()+receiver.getNum())/2;
        sender.setNum(binary);
        receiver.setNum(binary);
        
        //���ͽڵ��Ѹ�Ⱦ�����սڵ�δ��Ⱦ�����
        if(sender.isInfect()==true && receiver.isInfect()==false){
            receiver.setInfect(true);
        }
        //���ͽڵ�ͽ��սڵ����Ⱦ����������ͽڵ����Ȥ���룩
        else if (sender.isInfect()==true && receiver.isInfect()==true) {
            double sendInterest=sender.getInterest()/2;
            sender.setInterest(sendInterest);
        }
        
    }

    //���Ѹ�Ⱦ�ڵ���ѡ���ͽڵ�
    public static Node chooseSender(List<Node> list) {
    	
    	//�Ѹ�Ⱦ�ڵ㼯��
        List<Node> infectList=new ArrayList<Node>();
        
        //����µ��Ѹ�Ⱦ�ڵ�
        for (Node node : list) {
            if (node.isInfect() && !infectList.contains(node)) {
                infectList.add(node);
            }
        }
        
        //���Ѹ�Ⱦ�ڵ㼯�������ѡ���ͽڵ�
        return list.get(new Random().nextInt(list.size()));
    }

}
