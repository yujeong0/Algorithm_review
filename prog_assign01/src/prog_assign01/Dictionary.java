package prog_assign01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
	static String[] dict = new String[5000000];
	static String[] dict_word = new String[5000000];
	static String[] dict_type = new String[5000000];
	static String[] dict_mean = new String[5000000];
	static int dict_size = 0;
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String command;
		while(true) {
			System.out.print("$ ");
			command = sc.next();
			if(command.compareToIgnoreCase("read") == 0) {
				command = sc.next();
				read(command);
			}
			else if(command.compareToIgnoreCase("size") == 0) {
				System.out.println(dict_size);
			}
			else if(command.compareToIgnoreCase("find") == 0) {
				command = sc.next();
				find(command);
			}
			else if(command.compareToIgnoreCase("exit") == 0) {
				break;
			}
			else {
				System.out.println("잘못된 명령입니다.");
			}
			
		}
		sc.close();
	}
	
	public static void find(String word) {
		int idx = binary_search(0, dict_size-1, word);
		System.out.println(idx);
		if(idx == -1 || idx == -2) {
			System.out.println("Not found.");
			if(idx == -1) {
				System.out.println("-----------");
				System.out.println(dict[0]);
			}
			else {
				System.out.println(dict[dict_size-1]);
				System.out.println("-----------");
			}
		}
		else {
			if(dict_word[idx].compareToIgnoreCase(word) != 0) {
				System.out.println(dict[idx-1]);
				System.out.println("-----------");
				System.out.println(dict[idx+1]);
			}
			else {
				List listw = new ArrayList();
				listw.add(idx);
				int tmp = idx;
				if(idx != 0) {
					while(tmp > 0) {
						tmp--;
						if(dict_word[tmp].compareToIgnoreCase(word) == 0) {
							listw.add(tmp);
						}
						else break;
					}
				}
				if(idx != dict_size-1) {
					tmp = idx;
					while(tmp < dict_size-1) {
						tmp++;
						if(dict_word[tmp].compareToIgnoreCase(word) == 0) {
							listw.add(tmp);
						}
						else break;
					}
				}
				
				listw.sort(null);
				System.out.println("Found " + listw.size() + " items.");
				Iterator iter = listw.iterator();
				while(iter.hasNext()) 
					System.out.println(dict[(int)iter.next()]);
			}
		}
	}
	
	public static int binary_search(int begin, int end, String target) { //recursion
		if(target.compareToIgnoreCase(dict_word[0]) == 0) {
			return 0;
		}
		if(target.compareToIgnoreCase(dict_word[dict_size-1]) == 0) {
			return dict_size-1;
		}
		if(begin > end) {
			if(end < 0)  return -1;		// 맨 앞 단어보다 앞에 있는 단어인 경우
			else if(end == dict_size-1)  return -2;	// 맨 뒤 단어보다 뒤에 있는 단어인 경우
			else  return end;	// 단순히 없는 단어일 경우
		}
		
		int middle = (begin + end) / 2;
		int result = target.compareToIgnoreCase(dict_word[middle]);
		if(result == 0) {
			return middle;
		}
		else if(result < 0){
			return binary_search(begin, middle-1, target);
		}
		else {	//result > 0
			return binary_search(middle+1, end, target);
		}
	}
	
	public static void read(String filename) {
		try {
			Scanner in = new Scanner(new File(filename));
			while(in.hasNext()) {
				String line = in.nextLine();
				if(line.length() == 0 || line == null)
					continue;
//				System.out.println(line);
//				int count = 0;
//				for(int i=0;i<line.length();i++) 
//					if(line.charAt(i) == '(')
//						count++;
				
				int idx1 = line.indexOf("(");
				int idx2 = line.lastIndexOf(")");
			
//				System.out.println(idx1);
//				System.out.println(idx2);
				
				dict[dict_size] = line;
				dict_word[dict_size] = line.substring(0, idx1-1);
				dict_type[dict_size] = line.substring(idx1, idx2);
//				dict_mean[dict_size] = line.substring(idx2+2, line.length());
//				System.out.println(dict_word[dict_size]);
//				System.out.println(dict_type[dict_size]);
//				System.out.println(dict_mean[dict_size]);
				dict_size++;
			}
			System.out.println("끝");
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
