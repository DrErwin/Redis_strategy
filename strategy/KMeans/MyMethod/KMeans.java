package KMeans.MyMethod;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Arrays;


/**
 * KMeans算法的实现
 * 包装成工具类，直接传参数调用即可使用
 */

public class KMeans {
    //聚类的个数
    int clusterNum;
    //数据集中的点
    List<Point> points = new ArrayList<>();
    //簇的中心点
    List<Point> centerPoints = new ArrayList<>();
    //聚类结果的集合簇，key为聚类中心点在centerPoints中的下标，value为该类簇下的数据点
    HashMap<Integer, List<Point>> clusters = new HashMap<>();

    public KMeans(int clusterNum){
        this.clusterNum = clusterNum;
    }

    public void addData(double temp) {
        points.add(new Point(temp));
    }

    public void initial(){
        //初始化KMeans模型，这里选数据集前classNum个点作为初始中心点
        for (int i = 0; i < clusterNum; i++) {
            centerPoints.add(new Point(points.get(i).getTemp()));
            clusters.put(i, new ArrayList<>());
        }
    }

    //KMeans聚类
    public int doKMeans(){
        double err = Integer.MAX_VALUE;
        int count = 0;
        while (err > 0.01 * clusterNum){
            count++;
            //每次聚类前清空原聚类结果的list
            for (int key : clusters.keySet()){
                List<Point> list = clusters.get(key);
                list.clear();
                clusters.put(key, list);
            }
            //计算每个点所属类簇
            for (int i=0; i<points.size(); i++){
                dispatchPointToCluster(points.get(i), centerPoints);
            }
            //计算每个簇的中心点，并得到中心点偏移误差
            err = getClusterCenterPoint(centerPoints, clusters);
            // show(centerPoints, clusters);

        }
        // System.out.println(count);
        double clusterTemp = 1000;
        int listSize = 0;
        for (int i=0; i<centerPoints.size(); i++){
            // System.out.println(MessageFormat.format("类{0}的中心点: <{1}>",(i+1), centerPoints.get(i).getTemp()));
            List<Point> lists = clusters.get(i);
            Point maxPoit = Collections.max(lists);
            if (clusterTemp > maxPoit.getTemp()){
                clusterTemp = maxPoit.getTemp();
                listSize = lists.size();
            }
        }
        // System.out.println(clusterTemp + " "+listSize);

        return listSize;

    }

    //计算点对应的中心点，并将该点划分到距离最近的中心点的簇中
    public void dispatchPointToCluster(Point point, List<Point> centerPoints){
        int index = 0;
        double tmpMinDistance = Double.MAX_VALUE;
        for (int i=0; i<centerPoints.size(); i++){
            double distance = calDistance(point, centerPoints.get(i));
            if (distance < tmpMinDistance){
                tmpMinDistance = distance;
                index = i;
            }
            else if (distance == tmpMinDistance) index = Math.random() > 0.5 ? index : i;
        }
        List<Point> list = clusters.get(index);
        list.add(point);
        clusters.put(index, list);
    }

    //计算每个类簇的中心点，并返回中心点偏移误差
    public double getClusterCenterPoint(List<Point> centerPoints, HashMap<Integer, List<Point>> clusters){
        double error = 0;
        for (int i=0; i<centerPoints.size(); i++){
            Point tmpCenterPoint = centerPoints.get(i);
            double centertemp = 0;
            List<Point> lists = clusters.get(i);
            for (int j=0; j<lists.size(); j++){
                centertemp += lists.get(j).getTemp();
            }
            centertemp /= lists.size();
            error += Math.abs(centertemp - tmpCenterPoint.getTemp());
            centerPoints.set(i, new Point(centertemp));
        }
        return error;
    }

    //计算点之间的距离，这里计算欧氏距离（不开方）
    public double calDistance(Point point1, Point point2){
        return Math.pow((point1.getTemp() - point2.getTemp()), 2);
    }

    //打印簇中心点坐标，及簇中其他点坐标
    public void show(List<Point> centerPoints, HashMap<Integer, List<Point>> clusters){
        for (int i=0; i<centerPoints.size(); i++){
            System.out.print(MessageFormat.format("类{0}的中心点: <{1}>",(i+1), centerPoints.get(i).getTemp()));
            List<Point> lists = clusters.get(i);
            System.out.print("\t类中成员点有：");
            for (int j=0; j<lists.size(); j++){
                System.out.print("<"+lists.get(j).getTemp()+">\t");
            }
            System.out.println();
        }
    }

}
