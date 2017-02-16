package test;

import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.JSpinner.ListEditor;

public class ChouShu {
	//2*3*5*
	//第1500
	public static void main(String[] args) {
		int start = 1;
		int count = 0;
		long startTime = System.currentTimeMillis();
		LinkedList<Integer> result = new LinkedList<>();
		while(count <1500){
			if(is235(start)){
//				System.out.println(start + "is 235Num");
				result.addLast(start);
				count++;
			}
			start++;
		}
//		System.out.println(start);
		System.out.println(result);
		long end = System.currentTimeMillis();
		System.out.println((end-startTime));
		
		method2();
		
	}
	
	public static boolean is235(int num){
		while(num%2 == 0){
			num = num/2;
		}
		while(num%3 == 0){
			num = num/3;
		}
		
		while(num%5 ==0){
			num = num/5;
		}
		return num == 1?true:false;
	}
	
	public static void method2(){
		long startTime = System.currentTimeMillis();
		LinkedList<Integer> store = new LinkedList<>();
		store.add(1);
		int index = 0;
		while(store.size()<1600){
			int x2 = store.get(index) * 2;
			int x3 = store.get(index) * 3;
			int x5 = store.get(index) * 5;
			if(!store.contains(x2)){
				store.add(x2);
			}
			if(!store.contains(x3)){
				store.add(x3);
			}
			if(!store.contains(x5)){
				store.add(x5);
			}
			store.sort((Integer a,Integer b)->(a-b));
			index ++ ;
		}
		System.out.println(store.get(1499));
		System.out.println(store);
		long end = System.currentTimeMillis();
		System.out.println((end-startTime));
//		store.add(1);
//		store.add(3);
//		store.add(1, 2);
//		System.out.println(store);
	}

}
