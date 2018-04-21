package com.grayfu.dbscan;
import java.io.IOException;
import java.math.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.io.File;

public class DBSCAN {
	//�����ʼ������
	double eps=1;
	double MinPts=50;
	int Category=0;

	//�������
	public double Distance(Point A,Point B) {
		double d;
		d=Math.sqrt(Math.pow((A.X-B.X),2)+Math.pow((A.Y-B.Y),2));//((Xa-Xb)^2+(Ya-Yb)^2)^0.5
		return d;
	}	
	//�����������Ƿ�Ϊ�ھ�   
	public boolean IsNeighbor(Point A,Point B) {
		if(Distance(A,B)<=eps) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//����һ���������֮��ľ���
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
	
	//��������
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
	
	//���������㡢���ĵ�
	public void DividePoint(ArrayList<Point> Points) {
		int N=Points.size();
		for(int i=0;i<N;i++) {
			if ((double)Points.get(i).Neighbor>=MinPts){
				Points.get(i).State=1;
			}
			else Points.get(i).State=-1;
		}
	}

	//������ĵ�
	public ArrayList<Point> CoreOutput(ArrayList<Point> Points) {
		ArrayList<Point> CorePoints=new ArrayList<Point>();
		for(int i=0;i<Points.size();i++) {
			if(Points.get(i).State==1) {
				CorePoints.add(Points.get(i));
			}
		}
		return CorePoints;
	}
	
	//���������
	public ArrayList<Point> NoiseOutput(ArrayList<Point> Points) {
		ArrayList<Point> NoisePoints=new ArrayList<Point>();
		for(int i=0;i<Points.size();i++) {
			if(Points.get(i).State==-1) {
				NoisePoints.add(Points.get(i));
			}
		}
		return NoisePoints;
	}
	
	//��Ϊ���ĵ㣬���ݸõ㴴��һ�����
	public int CreatCategory(Point Core) {
		if(Core.Category!=0) {
			return Core.Category;
		}
		else Core.Category=Category++;
		return Core.Category;
	}
	
	//���ݺ��ĵ���չ���
	public void ExpandPoint(ArrayList<Point> Points) {
		int N=Points.size();
		for(int i=0;i<N;i++) {
			//�ж��Ƿ�Ϊ���ĵ�
			if(Points.get(i).State==1) {
				CreatCategory(Points.get(i));
				for(int j=i+1;j<N;j++) {
					//�ж��Ƿ�Ϊ���ĵ�
					if(Points.get(j).State==1) {
						if(IsNeighbor(Points.get(i),Points.get(j))) {
							Points.get(j).Category=Points.get(i).Category;

						}
					}
				}
			}
		}
	}
		
	//����ͬһ���ĵ㲢�洢
	public void SelectCategory(ArrayList<Point> Points,ArrayList<Point> bin,int Category) {
		for(int i=0;i<Points.size();i++) {
			if(Points.get(i).Category==Category) {
				bin.add(Points.get(i));
			}
		}
	}
	//�洢����
	public ArrayList<ArrayList> StoreCategory(ArrayList<Point> Points,int Category) {
		ArrayList<ArrayList> OriginArray=new ArrayList<ArrayList>(Category);
		for(int i=1;i<=Category;i++) {
			ArrayList<Point> Temp=new ArrayList<Point>();
			SelectCategory(Points,Temp,i);
			OriginArray.add(Temp);
		}
		return OriginArray;
	}
	//�����������ļ�
	public void WriteByCategory(ArrayList<ArrayList> Result,File Path) throws IOException{
		FileOperate file=new FileOperate();
		for(int i=0;i<Result.size()-1;i++) {
//			System.out.println("this is the point of Category "+(i+1)+"\n");
			//��ʼ��־
			String line="\n"+"this is the point of Category "+(i+1);
			file.WriteContext(Path, line);
			//д���������
			file.WriteResult(Path, Result.get(i));
			//������־
			file.WriteContext(Path, "end"+"\n");
		}
	}
	
	//������ӡ
	public void PrintByCategory(ArrayList<ArrayList> Result,File Path) {
		FileOperate file=new FileOperate();
		for(int i=0;i<Result.size()-1;i++) {
			String Name="Category "+(i+1);
			file.PrintArray(Result.get(i), Name);
		}
	}
}

	
	
