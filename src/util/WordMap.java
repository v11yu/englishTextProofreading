package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class WordMap {
	private List<String> words;
	private Map<String,Integer> map;
	private final String folderPath = wordConfig.getValue("system_Folder");
	private final String linkFolderPath = wordConfig.getValue("link_Folder");
	private final String chooseVal = wordConfig.getValue("system_val");
	private final String linkTxt = wordConfig.getValue("link_txt");
	private static WordMap uniqueWordMap;
	private WordMap(){
		words = new ArrayList<String>();
		map = new HashMap<String, Integer>();
		readFile(linkTxt);
		readFolder(folderPath);
		readFolder(linkFolderPath);
		map.clear();
		
	}
	public static WordMap getUniqueWordMap(){
		if(uniqueWordMap == null){
			uniqueWordMap = new WordMap();
		}
		return uniqueWordMap;
	}
	private void readFolder(String path){
		File dir = new File(path);
		File[] files = dir.listFiles();
		for (File file : files) {
			readFile(file);
		}
	}
	private void readFile(String fileName){
		File file = new File(fileName);
		readFile(file);
	}
	private void readFile(File file){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if(map.containsKey(line))
					continue;
				map.put(line, 1);
				words.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public List<String> getWords() {
		return words;
	}

	public static void main(String[] args) {
		WordMap wordMap = WordMap.getUniqueWordMap();
		System.out.println(wordMap.getWords().size());
	}
}
