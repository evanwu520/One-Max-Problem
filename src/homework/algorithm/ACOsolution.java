package homework.algorithm;

import java.io.IOException;
import java.util.HashMap;

import homework.bean.Ant;

public interface ACOsolution {
	
	//Ū���������
	void readTSPfile() throws IOException;
	
	//�p��C�ӫ��������Z���Ϊ�l�O���X�A �إ߹�����Ȧs�b�O����
	void calculateDistance();

	//�H���_�I
	void initAnt();
	
	//�}�l�M��
	void antExplore();
	
	//�p��ثe�Ҧb������U�@�ӥi��Ҧ�������
	HashMap<String, Double> calculateProbability(Ant ant);
	
	//�M�w�U�@�ӫ���
	void moveNextCity(Ant ant, HashMap<String, Double> probability);
	
	//�p����X�����|�`�M
	void calaculateAntRoutePath();
	
	//��s�O���X
	void updatePheromone();
	
	//�D�{��
	void main() throws IOException, CloneNotSupportedException;
	
	
	
}
