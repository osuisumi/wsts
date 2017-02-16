package test;

public class Test {
	
	long Rescuvie(long n) {
		 
	    return (n == 1) ? 1 : n * Rescuvie(n - 1);
	 
	}
	
	long TailRescuvie(long n, long a) {
	 
	    return (n == 1) ? a : TailRescuvie(n - 1, a * n);
	 
	}
	 
	 
	long TailRescuvie(long n) {//封装用的
	     
	    return (n == 0) ? 1 : TailRescuvie(n, 1);
	 
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		System.out.println(t.Rescuvie(10));
		//10*rescuvie(9)
		System.out.println(t.TailRescuvie(10));
	}

}
