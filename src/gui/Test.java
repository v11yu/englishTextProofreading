package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Group;

import util.Log;
import word.Bpr;
import word.LD;
import word.WordResult;
import java.util.*;
public class Test {

	protected Shell shlLd;
	private Text text;
	private Text text_1;
	private ArrayList<WordResult> wordResults;
	private ArrayList<String> errorList = new ArrayList<String>();
	private ArrayList<Integer> errorIndex;
	List list ;
	Button btnRadioButton_1;
	Button btnRadioButton_2;
	Button btnRadioButton;
	Integer wordIndex;
	Label label;
	Button button;
	Button btnNewButton_3;
	boolean []isError;
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Test window = new Test();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlLd.open();
		shlLd.layout();
		while (!shlLd.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public String[] getStringList(Text text){
		String textString = text.getText();
		return textString.split(" ");
	}
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlLd = new Shell();
		shlLd.setSize(567, 471);
		shlLd.setText("英文单词拼写错误自动检查系统");
		shlLd.setLayout(new FormLayout());
		list =new List(shlLd, SWT.MULTI | SWT.H_SCROLL);
		Button btnNewButton = new Button(shlLd, SWT.NONE);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.bottom = new FormAttachment(20);
		fd_btnNewButton.right = new FormAttachment(95);
		fd_btnNewButton.top = new FormAttachment(10);
		fd_btnNewButton.left = new FormAttachment(80);
		btnNewButton.setLayoutData(fd_btnNewButton);
		
		btnNewButton.setText("BPR算法");

		text = new Text(shlLd, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		FormData fd_text = new FormData();
		fd_text.bottom = new FormAttachment(50, -5);
		fd_text.right = new FormAttachment(70, -5);
		fd_text.top = new FormAttachment(0, 35);
		fd_text.left = new FormAttachment(0, 10);
		text.setLayoutData(fd_text);

		Button btnNewButton_1 = new Button(shlLd, SWT.NONE);
		FormData fd_btnNewButton_1 = new FormData();
		fd_btnNewButton_1.bottom = new FormAttachment(35);
		fd_btnNewButton_1.right = new FormAttachment(95);
		fd_btnNewButton_1.top = new FormAttachment(25);
		fd_btnNewButton_1.left = new FormAttachment(80);
		btnNewButton_1.setLayoutData(fd_btnNewButton_1);
		btnNewButton_1.setText("LD算法");
		

		Button btnNewButton_2 = new Button(shlLd, SWT.NONE);
		FormData fd_btnNewButton_2 = new FormData();
		fd_btnNewButton_2.bottom = new FormAttachment(50);
		fd_btnNewButton_2.right = new FormAttachment(95);
		fd_btnNewButton_2.top = new FormAttachment(40);
		fd_btnNewButton_2.left = new FormAttachment(80);
		btnNewButton_2.setLayoutData(fd_btnNewButton_2);
		btnNewButton_2.setText("生成新文本");
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(wordIndex == null){
					MessageBox dialog=new MessageBox(shlLd,SWT.OK|SWT.ICON_INFORMATION);
			        dialog.setText("Error");
			        dialog.setMessage("请先选择检测错误算法!");
			        dialog.open();
					return ;
				}
				showResult();
				wordIndex = wordResults.size();
				showChooseButton();
			}
		});

		text_1 = new Text(shlLd, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		FormData fd_text_1 = new FormData();
		fd_text_1.bottom = new FormAttachment(100, -10);
		fd_text_1.right = new FormAttachment(100, -10);
		fd_text_1.top = new FormAttachment(50, 39);
		fd_text_1.left = new FormAttachment(40, 58);
		text_1.setLayoutData(fd_text_1);

		Label lblNewLabel = new Label(shlLd, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.right = new FormAttachment(0, 252);
		fd_lblNewLabel.top = new FormAttachment(0, 10);
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("请输入需要拼写查错的英文文本：");

		Label lblNewLabel_1 = new Label(shlLd, SWT.NONE);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.bottom = new FormAttachment(text_1, -6);
		fd_lblNewLabel_1.right = new FormAttachment(100, -52);
		fd_lblNewLabel_1.left = new FormAttachment(40, 58);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("拼写查错后的文本：");
		
		
		FormData fd_list = new FormData();
		fd_list.top = new FormAttachment(text_1, 0, SWT.TOP);
		fd_list.left = new FormAttachment(text, 0, SWT.LEFT);
		fd_list.bottom = new FormAttachment(100, -10);
		fd_list.right = new FormAttachment(20);
		list.setLayoutData(fd_list);
		
		list.setItems((String[]) errorList.toArray(new String[errorList.size()]));
		
		Group group = new Group(shlLd, SWT.NONE);
		group.setText("请选择正确单词：");
		FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(text_1, 0, SWT.BOTTOM);
		fd_group.top = new FormAttachment(text_1, 0, SWT.TOP);
		fd_group.right = new FormAttachment(list, 124, SWT.RIGHT);
		fd_group.left = new FormAttachment(list, 9);
		group.setLayoutData(fd_group);
		
		btnRadioButton = new Button(group, SWT.RADIO);
		btnRadioButton.setBounds(10, 62, 97, 17);
		btnRadioButton.setText("选择1");
		btnRadioButton.setSelection(true);
		
		btnRadioButton_1 = new Button(group, SWT.RADIO);
		btnRadioButton_1.setBounds(10, 85, 97, 17);
		btnRadioButton_1.setText("选择2");
		
		btnRadioButton_2 = new Button(group, SWT.RADIO);
		btnRadioButton_2.setBounds(10, 108, 97, 17);
		btnRadioButton_2.setText("选择3");
		btnRadioButton.setVisible(false);
		btnRadioButton_1.setVisible(false);
		btnRadioButton_2.setVisible(false);
		button = new Button(group, SWT.NONE);
		button.setBounds(10, 131, 46, 27);
		button.setText("确认");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int c=0;
				if(wordIndex >=wordResults.size()) {
					showResult();
					return ;
				}
				if(btnRadioButton.getSelection()) c=0;
				else if(btnRadioButton_1.getSelection()) c=1;
				else c=2;
				chooseWord(c);
				wordIndex++;
				showChooseButton();
			}
		});
		btnNewButton_3 = new Button(group, SWT.NONE);
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(wordIndex >=wordResults.size()) 
					return ;
				chooseWord(-1);
				wordIndex++;
				showChooseButton();
			}
		});
		btnNewButton_3.setBounds(62, 131, 46, 27);
		btnNewButton_3.setText("跳过");
		
		label = new Label(group, SWT.NONE);
		label.setBounds(10, 30, 61, 17);
		label.setText("错误单词：");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				wordResults = new ArrayList<WordResult>();
				errorList = new ArrayList<String>();
				errorIndex = new ArrayList<Integer>();
				String[] textList = getStringList(text);
				isError = new boolean[textList.length];
				for(int i=0;i<textList.length;i++){
					isError[i] = true;
				}
				wordIndex = 0;
				int idx = 0;
				for(String word:textList){
					
					Log.logInfo("word "+idx);
					WordResult res = Bpr.getResult(word);
					if(res.getRes()>0) {
						wordResults.add(res);
						errorList.add(word);
						isError[idx] = false;
					}
					idx++;
				}				
				list.setItems((String[]) errorList.toArray(new String[errorList.size()]));
				showChooseButton();
			}
		});
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				wordResults = new ArrayList<WordResult>();
				errorList = new ArrayList<String>();
				errorIndex = new ArrayList<Integer>();
				String[] textList = getStringList(text);
				isError = new boolean[textList.length];
				for(int i=0;i<textList.length;i++){
					isError[i] = true;
				}
				wordIndex = 0;
				int idx = 0;
				for(String word:textList){
					
					Log.logInfo("word "+idx);
					WordResult res = LD.getResult(word);
					if(res.getRes()>0) {
						wordResults.add(res);
						errorList.add(word);
						isError[idx] = false;
					}
					idx++;
				}				
				list.setItems((String[]) errorList.toArray(new String[errorList.size()]));
				showChooseButton();
				
			}
		});
	}
	private void showResult(){
		String[] textList = getStringList(text);
		String res = new String();
		int idx = 0,eidx=0;
		for(String word:textList){
			if(isError[idx] == false){
				String c = wordResults.get(eidx).getChoose();
				if(c != null) 
					res = res + wordResults.get(eidx).getChoose()+" ";
				else 
					res = res + wordResults.get(eidx).getLs().get(0)+" ";
				eidx ++;
			}
			else{
				res = res + word+" ";
			}
			idx++;
		}
		text_1.setText(res);
		
	}

	private void showChooseButton(){
		btnRadioButton.setVisible(true);
		btnRadioButton_1.setVisible(true);
		btnRadioButton_2.setVisible(true);
		btnNewButton_3.setVisible(true);
		label.setText("错误单词：");
		button.setText("确认");
		if(wordIndex >=wordResults.size()){
			label.setText("选择结束：");
			btnRadioButton.setVisible(false);
			btnRadioButton_1.setVisible(false);
			btnRadioButton_2.setVisible(false);
			button.setText("生成");
			btnNewButton_3.setVisible(false);
			return ;
		}
		WordResult wr = wordResults.get(wordIndex);
		label.setText(wr.getS()+"：");
		
		if(wr.getLs().size()==3){
			btnRadioButton.setText(wr.getLs().get(0));
			btnRadioButton_1.setText(wr.getLs().get(1));
			btnRadioButton_2.setText(wr.getLs().get(2));
		}
		else if(wr.getLs().size()==2){
			btnRadioButton.setText(wr.getLs().get(0));
			btnRadioButton_1.setText(wr.getLs().get(1));
			btnRadioButton_2.setVisible(false);
		}
		else{
			btnRadioButton.setText(wr.getLs().get(0));
			btnRadioButton_1.setVisible(false);
			btnRadioButton_2.setVisible(false);
		}
		btnRadioButton.setSelection(true);
		btnRadioButton_1.setSelection(false);
		btnRadioButton_2.setSelection(false);
	}
	/**
	 * C == -1 not choose
	 * @param c
	 */
	private void chooseWord(int c){
		if(wordIndex >=wordResults.size())
			return ;
		WordResult wr = wordResults.get(wordIndex);
		if(c == -1)
			wordResults.get(wordIndex).setChoose(wr.getS());
		else 
			wordResults.get(wordIndex).setChoose(wr.getLs().get(c));
	}
}
