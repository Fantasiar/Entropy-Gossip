package fenbushi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.sql.rowset.RowSetWarning;

public class gossip{

    public static class Node{
    	
        private String name;    //节点名
        private double num;	   //节点数值
        private boolean isInfect;      //节点感染状态
        private double interest;      //节点兴趣（继续传染的概率）

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

    //节点数量常量
    static final int LENGTH=100;

    public static void main(String[] args) {
        List<Node> list=new ArrayList<Node>();
        for (int i = 0; i < LENGTH; i++) {
            list.add(new Node("p" + i, new Random().nextInt(100), false, 1.0));
        }
        
        //将首节点标记为已感染状态
        list.get(0).setInfect(true);
        
        //打印初始节点集合
        System.out.print("old list: ");
        for(Node n:list){
            System.out.print(n.getName()+":"+n.getNum()+"  ");
        }
        System.out.println();
        
        //开始传染
       	spread(list);
		        
    }

   
    public static void spread(List<Node> list){
    	//count记录传播次数
        int count=0;
        while(isDifferent(list)){
        	count++;   	
        	Node sender=chooseSender(list);
        	
        	//随机获得一个兴趣值，与发送节点的兴趣值进行比较，如果前者不大于后者，则可以继续操作（生成接收节点）
            double randomInterest=new Random().nextDouble();
      
            if (randomInterest<=sender.getInterest()) {             
                Node receiver=list.get(new Random().nextInt(LENGTH));  
                //只有发送节点与接受节点不是同一节点时可以进行传染
                if(sender.getName()!=receiver.getName()){
                    exchange(sender, receiver);
                }
            }
        }
        
        double sum=0;
        for (Node node : list) {
        	sum+=node.getNum();
		}
        
        //打印转换后的list
        System.out.print("new list: ");
        for(Node n:list){
            System.out.print(n.getName()+":"+n.getNum()+"  ");
        }
        
        //平均数=转换后的list中所有数求和除节点个数
        double average=sum/list.size();
        
        //任取list中的一个数作为基准
        double standard=list.get(new Random().nextInt(LENGTH)).getNum();  
        
        //误差=平均数与基准做差的绝对值
        double error=Math.abs(average-standard);   

        System.out.println();
        System.out.println("平均数: "+average);
        System.out.println("传播次数: "+count);
        System.out.println("误差: "+error);
        
    }

    //循环退出的条件（所有节点的数值差不超过0.1）
    public static boolean isDifferent(List<Node> list){
        boolean isDifferent=false;
        double first=list.get(0).getNum();
        
        //所有节点与首节点的数值差不超过0.1时即可视为各节点数值相等
        for(Node n:list){
            if(Math.abs(n.getNum()-first)>0.1){
                isDifferent=true;
                break;
            }
        }
        
        return isDifferent;
    }

    //通过exchange交换方式进行传播
    public static void exchange(Node sender,Node receiver) {
    	
    	//取平均数赋值
    	double binary=(sender.getNum()+receiver.getNum())/2;
        sender.setNum(binary);
        receiver.setNum(binary);
        
        //发送节点已感染，接收节点未感染的情况
        if(sender.isInfect()==true && receiver.isInfect()==false){
            receiver.setInfect(true);
        }
        //发送节点和接收节点均感染的情况（发送节点的兴趣减半）
        else if (sender.isInfect()==true && receiver.isInfect()==true) {
            double sendInterest=sender.getInterest()/2;
            sender.setInterest(sendInterest);
        }
        
    }

    //从已感染节点中选择发送节点
    public static Node chooseSender(List<Node> list) {
    	
    	//已感染节点集合
        List<Node> infectList=new ArrayList<Node>();
        
        //添加新的已感染节点
        for (Node node : list) {
            if (node.isInfect() && !infectList.contains(node)) {
                infectList.add(node);
            }
        }
        
        //从已感染节点集合中随机选择发送节点
        return list.get(new Random().nextInt(list.size()));
    }

}
