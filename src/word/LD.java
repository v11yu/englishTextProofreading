package word;

import util.Log;
import util.WordMap;

public class LD {
	public static WordResult getResult(String s) {
		WordResult res = new WordResult(s);
		WordMap w = WordMap.getUniqueWordMap();
		int line =0;
		for (String p : w.getWords()) {
			line++;
			//if(line%1000 == 0) Log.logInfo("LD:"+line);
			int dis = LetterDistance(s, p);
			res.update(p, dis);
		}
		return res;
	}
	public static Integer LetterDistance(String t,String s){
		Integer res = null;
		int dp[][] = new int [t.length()+1][s.length()+1];
		for (int i = 0; i <= t.length(); i++) {
			for (int j = 0; j <= s.length(); j++) {
				dp[i][j] = i+j;
			}
		}
		for (int i = 1; i <= t.length(); i++) {
			for (int j = 1; j <= s.length(); j++) {
				if(t.charAt(i-1) == s.charAt(j-1)) dp[i][j] = dp[i-1][j-1];
				else{
					//System.out.println("i,j "+i+","+j+" "+dp[i-1][j-1]+" "+dp[i-1][j]+" "+dp[i][j-1]);
					dp[i][j] = Math.min(dp[i-1][j-1]+1, Math.min(dp[i-1][j]+1, dp[i][j-1]+1));
				}
			}
		}
		return dp[t.length()][s.length()];
	}
	public static void main(String[] args) {
		System.out.println(LD.LetterDistance("he", "eh"));
	}
}
