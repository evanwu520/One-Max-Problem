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
		CommonUtil.log("�`��O�@���:"+milliseconds);
	}
	
	public static void hillClimbing(String currentPoint){
		//��F�~
		List<String> list = CommonUtil.findNeighbor(currentPoint);
		//�F�~�Ӽ�
	    int count = list.size();
	    String maxStr = currentPoint;
		
		for(String point:list){
			//��̤j��
			currentPoint = CommonUtil.compareStr(currentPoint, point);
			//CommonUtil.log(point);//log
			if(maxStr.equals(currentPoint)){
				count--;//�֭p
			}else{
				maxStr = currentPoint;
			}
		}
		//�F�~�S����current pont �٤j�A�o�̰ϰ�̨θ�
		if(count == 0){
			CommonUtil.log("�̨θ�:"+currentPoint);
		}else{
			hillClimbing(currentPoint);//�~��M��
		}
	}
}
