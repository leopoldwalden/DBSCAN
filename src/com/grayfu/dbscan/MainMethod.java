package com.grayfu.dbscan;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

public class MainMethod {
	public static void main(String[] args) throws IOException {
		ArrayList<Point> Points=new ArrayList<Point>();
		File FileIn=new File("g:\\","dataset.txt");
		File FileOut=new File("g:\\","clustering.txt");
		FileIn.createNewFile();
		if(FileOut.exists()) {
			FileOut.delete();
		}
		FileOut.createNewFile();
		FileOperate file=new FileOperate();
		DBSCAN dbscan=new DBSCAN();
		
		//显示初始参数
		System.out.println("eps="+dbscan.eps+"\r\n"+"MinPts="+dbscan.MinPts+"\n");
		
		//读取数据
		file.ReadSrc(FileIn,Points);
		
		//聚类处理
		dbscan.CalNeighbor(Points);
		dbscan.DividePoint(Points);
		dbscan.ExpandPoint(Points);
		
		//输出核心点并打印
		System.out.println("Num Category is:"+(dbscan.Category-1));
		file.PrintArray(dbscan.CoreOutput(Points),"CorePoint");
		System.out.println("\n\n\n");
		System.out.println("----------------------------------------------");
		System.out.println("\n\n\n");
		
		//存储结果并按类别输出数据
		dbscan.WriteByCategory(dbscan.StoreCategory(Points, dbscan.Category), FileOut);	
		file.WriteContext(FileOut, "Noise Points:\n");
		file.WriteResult(FileOut, dbscan.NoiseOutput(Points));
	}
}
