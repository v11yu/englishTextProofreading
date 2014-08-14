package word;
import java.util.*;
public class WordResult {
	private String s;
	private Integer res;
	private List<String> ls;
	private String choose;
	public WordResult(String s){
		this.s = s;
		res = s.length();
		ls = new ArrayList<String>();
		choose = null;
	}
	public void update(String p,Integer dis){
		if(dis <res){
			res = dis;
			ls = new ArrayList<String>();
			ls.add(p);
		}
		else if(dis == res && ls.size()<3){
			ls.add(p);
		}
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public Integer getRes() {
		return res;
	}
	public void setRes(Integer res) {
		this.res = res;
	}
	public List<String> getLs() {
		return ls;
	}
	public void setLs(List<String> ls) {
		this.ls = ls;
	}
	public String getChoose() {
		return choose;
	}
	public void setChoose(String choose) {
		this.choose = choose;
	}
}
