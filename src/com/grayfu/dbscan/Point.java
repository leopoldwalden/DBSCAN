package com.grayfu.dbscan;
import java.lang.String;
import java.io.*;
import java.util.ArrayList;

public class Point {
	//����
	double X;
	double Y;
	
	int flag=0;//�Ƿ񱻷��ʣ���=1,����=0��
	int State=0;//״̬��ǣ�δ����=0�����ĵ�=1���߽��=2��������=-1
	int Category=0;//��������
	int Neighbor=0;//�ھӸ���
	ArrayList<Integer> Neighbors=new ArrayList<Integer>();
	
	Point(){
	}
	
	public void PrintPoint() {
		String line="X="+Double.toString(X)+"  "+"Y="+Double.toString(Y)+"		NumNeighbor: "+Neighbor;
		System.out.println(line);
	}
	
	public void BeVisited() {
		flag=1;
	}
	
	public void Reset() {
		flag=0;
	}
	public void ShowState() {
		String state="";
		switch (State) {
		case 0:state="UnDivided";break;
		case 1:state="CorePoint";break;
		case 2:state="BorderPoint";break;
		case -1:state="NoisePoint";break;
		}
		System.out.println(state);
	}
	
	Point(double x,double y) {
		X=x;
		Y=y;
	}
	
	double GetX() {
		return this.X;
	}
	
	double GetY() {
		return this.Y;
	}
	

}
