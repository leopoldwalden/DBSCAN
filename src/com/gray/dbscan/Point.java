package com.grayfu.dbscan;
import java.lang.String;
import java.io.*;
import java.util.ArrayList;

public class Point {
	//坐标
	double X;
	double Y;
	
	int flag=0;//是否被访问，是=1,；否=0；
	int State=0;//状态标记：未分类=0；核心点=1；边界点=2；噪音点=-1
	int Category=0;//聚类类型
	int Neighbor=0;//邻居个数
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
