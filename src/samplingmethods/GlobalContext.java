/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samplingmethods;

/**
 *
 * @author Juan Carlos
 */
import java.util.Random;
import java.util.*;
import java.lang.Math;

//singleton class

public class GlobalContext {
    private final static GlobalContext gc = new GlobalContext();
   
    static String methodChoice;    
    
    static boolean randomOption;
    static boolean stratifiedOption;
    static boolean systematicOption;
    static int N;
    static int option;    
    static String[] arr;
    static int counter;
    static int n;
    static float percentage;
    static boolean[] tracker;
    static String[] sampleArray;
    static Integer[] indexArray;
    static String[] stratifiedIndexArray;
    
    
    public static GlobalContext getInstance() {
        return gc;
    }    
    
    public static void initialize(){
        methodChoice = "";
        N = 0;
        option = 0;
        arr = new String[0];
        counter = 0;
        n = 0;
        percentage = 0;
        tracker = new boolean[0];
        sampleArray = new String[0];
        indexArray = new Integer[0];
    }
   
    public static String doRandomSampling(String inp){
        Random r = new Random();
        counter = 0;
        
        tracker = new boolean[N];
        for(int i = 0; i<N; i++){
            tracker[i] = false;
        }
        
        int choice;
        if(inp.isEmpty()){
            n = (int) Math.ceil((0.2 * (float)N));            
        } else {
            n = Integer.parseInt(inp);                        
        }
        sampleArray = new String[n];
        indexArray = new Integer[n];
        String ans = "{ \n";
        do{
            choice = r.nextInt(N);
            if(!tracker[choice]){
                ans += String.format("Index %d = %s\n", choice+1, arr[choice]);
                sampleArray[counter] = arr[choice];
                indexArray[counter] = choice + 1;
                tracker[choice] = true;
                counter++;
            }
        } while(counter < n);
        ans+= "  }";
        return ans;
    }
    
    public static String doStratifiedSampling(String inp){
        String ans = "";
        Random r = new Random();
        tracker = new boolean[N];
        for (int j = 0; j < N; j++) {
            tracker[j] = true;
        }

        if(inp.isEmpty()){
            percentage = 20;
        } else {
            percentage = Float.parseFloat(inp);
        }
                        
        Queue<String>[] l = new LinkedList[N];
        
        counter = 0;
        
        for (int i = 0, current; i < N; i++) {
            if (tracker[i] == true) {
                if (option == 1)
                    current = Integer.parseInt(arr[i]);
                else
                    current = arr[i].charAt(0);

                l[counter] = new LinkedList();
                l[counter].add(Integer.toString(current));

                for (int j = 0; j < N; j++) {
                    if (option == 1 && Integer.parseInt(arr[j]) == current || option == 2 && arr[j].charAt(0) == current) {
                        tracker[j] = false;
                        l[counter].add(Integer.toString(j));
                    }
                }
                counter++;
            }
        }
        
        percentage /= 100;
        
        int sampleSizePerStratum;        
        int a = 0;

        sampleArray = new String[N];
        stratifiedIndexArray = new String[N];
        for(int i = 0; i < N; i++){
            stratifiedIndexArray[i] = "";
            sampleArray[i] = "";
        }
        for (int i = 0; i < N; i++) {
            if (l[i] != null) {
                boolean trigger = false;
                ans += String.format("Stratum %d (%d = total number of samples in Stratum %d){ ", i + 1, l[i].size()-1, i+1);
                stratifiedIndexArray[a] = String.format("Stratum %d (total = %d): ", i + 1, l[i].size()-1);
                String item = l[i].peek();
                l[i].remove();

                sampleSizePerStratum = (int) (Math.ceil((double) (percentage * l[i].size())));                
                for (int j = 0; j < sampleSizePerStratum; j++) {
                    int k = 0, rTmp = r.nextInt(l[i].size()) + 1;                                        
                    do {
                        String t = l[i].peek();
                        l[i].remove();
                        l[i].add(t);
                        k++;
                    } while (k < rTmp);
                    
                    int index = Integer.parseInt(l[i].peek());
                    if (option == 1) {
                        ans += String.format((j + 1 != sampleSizePerStratum) ? "Index %d : Item %s\n " : "Index %d : Item %s } \n", Integer.parseInt(l[i].peek()) + 1, item);
                        stratifiedIndexArray[a] += (!trigger) ? "\t" + Integer.toString(index+1) : "\t\t\t\t\t" + Integer.toString(index+1);                        
                        sampleArray[a++] = item;
                        trigger = true;
                    } else {
                        ans += String.format((j + 1 != sampleSizePerStratum) ? "Index %d : Item '%s'\n, " : "Index %d : Item '%c' } \n", Integer.parseInt(l[i].peek()) + 1, (char) Integer.parseInt(item));
                        stratifiedIndexArray[a] += (!trigger) ? "\t" + Integer.toString(index+1) : "\t\t\t\t\t" + Integer.toString(index+1);
                        int tmp = Integer.parseInt(item);                        
                        sampleArray[a++] = "" + (char)tmp;                        
                        trigger = true;
                    }
                    l[i].remove();
                }
            }
        }
               
        return ans;
    }
    
    public static String doSystematicSampling(String inp){
        String ans = "";
        Random r = new Random();
        
        if(inp.isEmpty()) {
            n = (int) Math.floor(0.20 * (float)(N));
        } else {
            n = Integer.parseInt(inp);
        }        
        
        tracker = new boolean[N];        
        sampleArray = new String[n];
        indexArray = new Integer[n];
        
        int k = N / n, c = r.nextInt(k);
        for(int i = 0; i<N; i++){
            tracker[i] = false;
        }

        ans = "{  \n";
        System.out.println(k +" " + N + " " + n);
        int counter = 0;
        do {
            if (!tracker[c]) {
                ans += String.format("Index %d = %s\n", c+1, arr[c]);
                tracker[c] = true;
                sampleArray[counter] = arr[c];
                indexArray[counter] = c+1;
                counter++;
            }
            c += k;
        } while (c < N);
        
        ans += "  }";        
        return ans;
    }
}
