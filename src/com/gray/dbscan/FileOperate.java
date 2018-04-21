package com.grayfu.dbscan;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class FileOperate {	
	//读取数据
	public void ReadSrc(File FileIn,ArrayList<Point> Points) throws IOException{
		FileReader Fred=new FileReader(FileIn);
		BufferedReader BR=new BufferedReader(Fred);
		String temp="";
		try {
			while(null!=(temp=BR.readLine())) {
				Point point=new Point();
				String[] arr=temp.split(" 	");
				point.X=Double.parseDouble(arr[0]);
				point.Y=Double.parseDouble(arr[1]);
				Points.add(point);
			}
			BR.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println("the size of data is:"+Points.size());
	}
	
	//检测读入数据是否成功
	public void ReadTest(ArrayList<Point> Points) {
		String line=null;
		for(int i=0;i<Points.size();i++) {
			line=Double.toString(Points.get(i).X)+"  "+Double.toString(Points.get(i).Y);
			System.out.println(line);
		}
	}
	
	//写入任意内容
	public void WriteContext(File FileOut,String Context) throws IOException{
		FileWriter Fwr=new FileWriter(FileOut,true);
		BufferedWriter BW=new BufferedWriter(Fwr);
		BW.write(Context+"\n");
		BW.close();
	}
	
	//写入数据
	public void WriteResult(File FileOut,ArrayList<Point> Points) throws IOException {
		FileWriter Fwr=new FileWriter(FileOut,true);
		BufferedWriter BW=new BufferedWriter(Fwr);
		String line = null;
		for(int i=0;i<Points.size();i++) {
			line=Double.toString(Points.get(i).X)+" 	"+Double.toString(Points.get(i).Y)+" ";
			BW.write(line);
			BW.newLine();
			BW.flush();
		}
		BW.close();
	}
	
	//打印数组
	public void PrintArray(ArrayList<Point> Array,String Name) {
		System.out.println("the number of "+Name+" is:"+Array.size());
	}
}

