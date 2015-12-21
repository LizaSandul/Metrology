/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spen.chepin;

/**
 *
 * @author 123
 */
public class Matrix {
    private int[][] m;
    private int length;
    private int h;
    private String[] mName;

    
    public void add(int i, int j,String name)
    {
        m[i][j]=1;
        mName[j]=name;
    }
    
    public void setH(int h)
    {
        this.h=h;
    }
    
    public int getH()
    {
        return h;
    }
    
    public int get(int i, int j)
    {
        return m[i][j];
    }
    public String getName(int i)
    {
        return mName[i];
    }
    
    public void setLength(int length)
    {
        this.length = length;
    }
    
    public int getLength()
    {
        return length;
    }
    
    public Matrix()
    {
        m=new int[100][100];
        for (int i=0;i<100;i++)
            for (int j=0;j<100;j++)
                m[i][j]=0;
        length=0;
        mName=new String[100];
        mName[0]="Start";
    }
}
