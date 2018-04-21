package com.grayfu.dbscan;
import java.io.IOException;
import java.math.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.io.File;

public class DBSCAN {
	//定义初始化参数
	double eps=1;
	double MinPts=50;
	int Category=0;

	//计算距离
	public double Distance(Point A,Point B) {
		double d;
		d=Math.sqrt(Math.pow((A.X-B.X),2)+Math.pow((A.Y-B.Y),2));//((Xa-Xb)^2+(Ya-Yb)^2)^0.5
		return d;
	}	
	//计算两个点是否为邻居   
	public boolean IsNeighbor(Point A,Point B) {
		if(Distance(A,B)<=eps) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//计算一组点中两两之间的距离
	public void P2PDistance(ArrayList<Point> Points) {
		System.out.println("---------------------------------------------------------------------------");
		ArrayList<Double> Distance=new ArrayList<Double>(); 
		double temp=0;
		double sum=0;	
		for(int i=0;i<Points.size();i++) {
			for(int j=i+1;j<Points.size();j++) {
				temp=Distance(Points.get(i),Points.get(j));
				Distance.add(temp);
				sum+=temp;
			}
		}
		double Average=sum/Distance.size();
		System.out.println("the average distance is: "+Average);
		System.out.println("---------------------------------------------------------------------------");
	}
	
	//计算邻域
	public void CalNeighbor(ArrayList<Point> Points) {
		for(int i=0;i<Points.size();i++) {
			for(int j=i+1;j<Points.size();j++) {
				if(IsNeighbor(Points.get(i),Points.get(j))) {
					Points.get(i).Neighbors.add(j);
					Points.get(j).Neighbors.add(i);
					Points.get(i).Neighbor=Points.get(i).Neighbors.size();
					Points.get(j).Neighbor=Points.get(j).Neighbors.size();
				}
			}		
		}	
	}
	
	//划分噪音点、核心点
	public void DividePoint(ArrayList<Point> Points) {
		int N=Points.size();
		for(int i=0;i<N;i++) {
			if ((double)Points.get(i).Neighbor>=MinPts){
				Points.get(i).State=1;
			}
			else Points.get(i).State=-1;
		}
	}

	//输出核心点
	public ArrayList<Point> CoreOutput(ArrayList<Point> Points) {
		ArrayList<Point> CorePoints=new ArrayList<Point>();
		for(int i=0;i<Points.size();i++) {
			if(Points.get(i).State==1) {
				CorePoints.add(Points.get(i));
			}
		}
		return CorePoints;
	}
	
	//输出噪声点
	public ArrayList<Point> NoiseOutput(ArrayList<Point> Points) {
		ArrayList<Point> NoisePoints=new ArrayList<Point>();
		for(int i=0;i<Points.size();i++) {
			if(Points.get(i).State==-1) {
				NoisePoints.add(Points.get(i));
			}
		}
		return NoisePoints;
	}
	
	//作为核心点，根据该点创建一个类别
	public int CreatCategory(Point Core) {
		if(Core.Category!=0) {
			return Core.Category;
		}
		else Core.Category=Category++;
		return Core.Category;
	}
	
	//根据核心点扩展类别
	public void ExpandPoint(ArrayList<Point> Points) {
		int N=Points.size();
		for(int i=0;i<N;i++) {
			//判断是否为核心点
			if(Points.get(i).State==1) {
				CreatCategory(Points.get(i));
				for(int j=i+1;j<N;j++) {
					//判断是否为核心点
					if(Points.get(j).State==1) {
						if(IsNeighbor(Points.get(i),Points.get(j))) {
							Points.get(j).Category=Points.get(i).Category;

						}
					}
				}
			}
		}
	}
		
	//挑出同一类别的点并存储
	public void SelectCategory(ArrayList<Point> Points,ArrayList<Point> bin,int Category) {
		for(int i=0;i<Points.size();i++) {
			if(Points.get(i).Category==Category) {
				bin.add(Points.get(i));
			}
		}
	}
	//存储分类
	public ArrayList<ArrayList> StoreCategory(ArrayList<Point> Points,int Category) {
		ArrayList<ArrayList> OriginArray=new ArrayList<ArrayList>(Category);
		for(int i=1;i<=Category;i++) {
			ArrayList<Point> Temp=new ArrayList<Point>();
			SelectCategory(Points,Temp,i);
			OriginArray.add(Temp);
		}
		return OriginArray;
	}
	//按类别输出到文件
	public void WriteByCategory(ArrayList<ArrayList> Result,File Path) throws IOException{
		FileOperate file=new FileOperate();
		for(int i=0;i<Result.size()-1;i++) {
//			System.out.println("this is the point of Category "+(i+1)+"\n");
			//开始标志
			String line="\n"+"this is the point of Category "+(i+1);
			file.WriteContext(Path, line);
			//写入类别数据
			file.WriteResult(Path, Result.get(i));
			//结束标志
			file.WriteContext(Path, "end"+"\n");
		}
	}
	
	//按类别打印
	public void PrintByCategory(ArrayList<ArrayList> Result,File Path) {
		FileOperate file=new FileOperate();
		for(int i=0;i<Result.size()-1;i++) {
			String Name="Category "+(i+1);
			file.PrintArray(Result.get(i), Name);
		}
	}
}

	
	
