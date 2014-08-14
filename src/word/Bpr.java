package word;

import util.Log;
import util.WordMap;

public class Bpr {
	private static int MAX_VAL = 30;
	/*
		if ans <= K ans = ans;
		else ans =1;
		t
	*/
	public static WordResult getResult(String s) {
		WordResult res = new WordResult(s);

		WordMap w = WordMap.getUniqueWordMap();

		for (String p : w.getWords()) {

			//if(line%10000 == 0) Log.logInfo("Bpr"+line);
			int dis = BPR(s, p, res.getRes());
			res.update(p, dis);
		}
		return res;
	}
	/**
	 * 
	 * @param p targe String
	 * @param t source String
	 * @param k max error
	 * @return similar Num
	 */
	public static boolean isLetter(char c){
		return (c>='a'&&c<='z')|| (c>='A'&&c<='A');
	}
	public static int BPR(String p, String t, int k) {
		int Pstr_len, Tstr_len, i, pos;
		if (p.length() < t.length()) {
			String tt = t;
			t = p;
			p = tt;

		}
		
		int pR, nR;
		Pstr_len = p.length();
		Tstr_len = t.length();
		int[] R = new int[128];
		int[] B = new int[200];
		//System.out.println("Pstr_len" + Pstr_len + "Tstr_len" + Tstr_len);
		// Preprocessing
		for (i = 0; i < 200; i++) {
			B[i] = 0;
		}
		for (i = 0; i < Pstr_len; i++) {

			int tmp = (int) p.charAt(i);
			if(!isLetter(p.charAt(i))){
				return 30;
			}
			B[tmp] = B[tmp] | (1 << i);

			// System.out.println("p:"+p.charAt(i)+" tmp:"+tmp+" B[tmp]:"+B[tmp]);
		}

		
		for (i = 0; i <= k; i++) {
			R[i] = (1 << i) - 1;
		}
		//matching
		for (pos = 0; pos < Tstr_len; pos++) {
			int tmp = (int) t.charAt(pos);
			if(!isLetter(t.charAt(pos))){
				return 30;
			}
			pR = R[0];//preMatchScore
			/*
			 * pR<<1 & B[tmp] 目的是最高位是否被匹配
			 */
			nR = ((pR << 1) | 1) & B[tmp];  
			R[0] = nR;
			for (i = 1; i <= k; i++) {
				nR = ((R[i] << 1) & B[tmp]) | pR | ((pR | nR) << 1);
				pR = R[i];
				R[i] = nR;
			}
			//System.out.println("pR:"+pR+" nR:"+nR+" R[0]:"+R[0]);
		}
		/*
		 * 检测匹配的最高位
		 */
		for (i = 0; i <= k; i++) {
			if ((R[i] & (1 << (Pstr_len - 1))) != 0)
				return i;
		}
		return MAX_VAL;
	}
	public static void main(String[] args) {
		Bpr.getResult("hllo");
		
	}
}
