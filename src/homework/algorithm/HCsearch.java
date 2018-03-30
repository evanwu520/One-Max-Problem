package homework.algorithm;

import java.util.List;

import homework.util.CommonUtil;

public class HCsearch {
	
	
	public HCsearch() throws Exception{
		long lStartTime = System.nanoTime();
		int n = 1000; //default bit
		String startPoint = CommonUtil.genRandomPoint(n);
		CommonUtil.log("startPoint:"+startPoint);
		hillClimbing(startPoint);
		long lEndTime = System.nanoTime();
		long milliseconds = (lEndTime-lStartTime)/1000000;
		CommonUtil.log("羆禣睝计:"+milliseconds);
	}
	
	public static void hillClimbing(String currentPoint){
		//т綟﹡
		List<String> list = CommonUtil.findNeighbor(currentPoint);
		//綟﹡计
	    int count = list.size();
	    String maxStr = currentPoint;
		
		for(String point:list){
			//т程
			currentPoint = CommonUtil.compareStr(currentPoint, point);
			//CommonUtil.log(point);//log
			if(maxStr.equals(currentPoint)){
				count--;//仓璸
			}else{
				maxStr = currentPoint;
			}
		}
		//綟﹡⊿Τゑcurrent pont 临眔程跋办程ㄎ秆
		if(count == 0){
			CommonUtil.log("程ㄎ秆:"+currentPoint);
		}else{
			hillClimbing(currentPoint);//膥尿碝т
		}
	}
}
