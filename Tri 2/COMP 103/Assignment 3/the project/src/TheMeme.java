public class TheMeme {
    private int test1;
    private int test2;
    private int test3;
    
    public TheMeme(int one, int two, int three){
        test1 = one;
        test2 = two;
        test3 = three;
    }
    
    // Getters
    public int getTest1(){
        return test1;
    }
    public int getTest2(){
        return test2;
    }
    public int getTest3(){
        return test3;
    }
    
    public boolean equals(Object obj){
        if(this == obj) return true;
        if( obj == null) return false;
        if( !(obj instanceof TheMeme)){ return false;}
    
        TheMeme compare = (TheMeme)obj;
        return (test1 == compare.getTest1() && test2 == compare.getTest2() && test3 == compare.getTest3());
    }
    
    public int hashCode(){
        int prime = 104417;
        int hash = ((test1*51)*(test2*51)*(test3*51)) % prime;
        return (hash);
    }
    
    public String toString(){
        return String.format("Test 1 = %d\nTest 2 = %d\nTest 3 = %d", test1, test2, test3);
    }
}
