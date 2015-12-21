/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spen.chepin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 123
 */
public class Pars2 {
    static String MatchingOfUses(String tmpUses)
    {
        String res = "";
        
        String buf;
        File f = new File(MainMenu.path+"\\"+tmpUses+".pas");
        FileReader fr = null; 
        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br = new BufferedReader(fr);
        try {
            while ((buf = br.readLine()) != null)
            {
                res = res + buf;
            }
        } catch (IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Pattern pat = Pattern.compile("unit.*implementation");
        Matcher mat = pat.matcher(res);
        res = mat.replaceAll("");
        
        pat = Pattern.compile("\\s+");
        mat = pat.matcher(res);
        res = mat.replaceAll(" ");

        pat = Pattern.compile("([\"\'].+?[\"\'])");
        mat = pat.matcher(res);
        res = mat.replaceAll(" ");
        
        pat = Pattern.compile(";");
        mat = pat.matcher(res);		
        res = mat.replaceAll(" ; ");

        pat = Pattern.compile(":");
        mat = pat.matcher(res);		
        res = mat.replaceAll(" : ");
        
        pat = Pattern.compile(",");
        mat = pat.matcher(res);		
        res = mat.replaceAll(" ");
        
        pat = Pattern.compile("end[^\\s]");
        mat = pat.matcher(res);
        res = mat.replaceAll("");
        
        pat = Pattern.compile("uses[^;]*;");
        mat = pat.matcher(res);
        String uses="";
        if (mat.find())
        {
            int start = mat.start();
            int end = mat.end();
            uses=res.substring(start+4, end-1);
        }
        if (!uses.equals(""))
        {
            String curUses="";
            for (int i = 1; i<uses.length();i++)
            {
                if (uses.charAt(i)!=' ')
                    curUses = curUses+uses.charAt(i);
                else
                    if (!curUses.equals(""))
                    {
                        String result = ""; 
                        if (!MainMenu.usesSet.contains(curUses))
                        {
                            MainMenu.usesSet.add(curUses);
                            result = MatchingOfUses(curUses);
                        }
                        pat = Pattern.compile(curUses);
                        mat = pat.matcher(res);		
                        res = mat.replaceAll(result);
                        curUses = "";
                    }
            }
        }
        pat = Pattern.compile("uses");
        mat = pat.matcher(res);		
        res = mat.replaceAll("");
        
        return res;
    }
    
    static String LoadAllUses(String code)
    {
        MainMenu.usesSet=new HashSet<>();
        Pattern pat = Pattern.compile("\\s+");
        Matcher mat = pat.matcher(code);
        String str1 = mat.replaceAll(" ");

        pat = Pattern.compile("([\"\'].+?[\"\'])");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile(";");
        mat = pat.matcher(str1);		
        str1 = mat.replaceAll(" ; ");

        pat = Pattern.compile(":");
        mat = pat.matcher(str1);		
        str1 = mat.replaceAll(" : ");
        
        pat = Pattern.compile(",");
        mat = pat.matcher(str1);		
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("uses[^;]*;");
        mat = pat.matcher(str1);
        String uses="";
        if (mat.find())
        {
            int start = mat.start();
            int end = mat.end();
            uses=str1.substring(start+4, end-1);
        }
        if (!uses.equals(""))
        {
            String curUses="";
            for (int i = 1; i<uses.length();i++)
            {
                if (uses.charAt(i)!=' ')
                    curUses = curUses+uses.charAt(i);
                else
                    if (!curUses.equals(""))
                    {
                        String result = ""; 
                        if (!MainMenu.usesSet.contains(curUses))
                        {
                            MainMenu.usesSet.add(curUses);
                            result = MatchingOfUses(curUses);
                        }
                        pat = Pattern.compile(curUses);
                        mat = pat.matcher(str1);		
                        str1 = mat.replaceAll(result);
                        curUses = "";
                    }                     
            }
        }
        pat = Pattern.compile("uses");
        mat = pat.matcher(str1);		
        str1 = mat.replaceAll("");
        
        
        pat = Pattern.compile("function");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("procedure");
        
        pat = Pattern.compile("begin");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ; begin ; ");
        
        pat = Pattern.compile("then");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ; ");
        
        pat = Pattern.compile("else");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ; else ;");
        
        pat = Pattern.compile("var");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("inc");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("dec");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("readln");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
                
        pat = Pattern.compile("default [^:]*:");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("case :");
        
        pat = Pattern.compile("case [^:]*:");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("case ;");
        
        pat = Pattern.compile("switch [^:]*:");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("switch ; ");
        
        pat = Pattern.compile("repeat");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("repeat ;");
        
        pat = Pattern.compile("read");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("writeln");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("write");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("const");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("[(][^)]*[)]");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("[)]");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
                        
        pat = Pattern.compile("=");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" = ");
        
        pat = Pattern.compile(">");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("<");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" "); 
        
        pat = Pattern.compile("\\s+");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile(" : = ");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
                      
        pat = Pattern.compile(" =[^;]*");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile(": [^;]+");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("do");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(";");
        
        pat = Pattern.compile(" to ");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile(" [0123456789]* ");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        String tmp;
        for (String var : MainMenu.vars.keySet())
        {
            tmp = var.substring(var.indexOf('.')+1);
            pat = Pattern.compile(" "+tmp+" ");
            mat = pat.matcher(str1);
            str1 = mat.replaceAll(" ");
                    
            pat = Pattern.compile("\\s+");
            mat = pat.matcher(str1);
            str1 = mat.replaceAll(" ");
        }
        
        pat = Pattern.compile("\\s+");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("(;)+");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(";");
        
        pat = Pattern.compile("( ;)+");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ;");
        
        pat = Pattern.compile("end[^//s]");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("end ;");
        
        pat = Pattern.compile(";;");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(";");
        
        pat = Pattern.compile("switch ; begin");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("switch");

        //System.out.println(str1);
        
        return str1;
    }
    
    static boolean isBegin(String s)
    {
        if (s.contains(" begin "))
            return true;
        else
            return false;
    }
    
    static boolean isEnd(String s)
    {
        if (s.contains(" end "))
            return true;
        else
            return false;
    }
    
    static boolean isWhile(String s)
    {
        if (s.contains(" while "))
            return true;
        else
            return false;
    }
    
    static boolean isFor(String s)
    {
        if (s.contains(" for "))
            return true;
        else
            return false;
    }
    
    static boolean isProcedure(String s)
    {
        if (s.contains(" procedure "))
            return true;
        else
            return false;
    }
    
    static boolean isRepeat(String s)
    {
        if (s.contains(" repeat "))
            return true;
        else
            return false;
    }
    
    static boolean isUntil(String s)
    {
        if (s.contains(" until "))
            return true;
        else
            return false;
    }
    
    static boolean isIf(String s)
    {
        if (s.contains(" if "))
            return true;
        else
            return false;
    }
    
    static boolean isElse(String s)
    {
        if (s.contains(" else "))
            return true;
        else
            return false;
    }
    
    static boolean isSwitch(String s)
    {
        if (s.contains(" switch "))
            return true;
        else
            return false;
    }
    
    static boolean isCase(String s)
    {
        if (s.contains(" case "))
            return true;
        else
            return false;
    }
    
    static boolean isBreak(String s)
    {
        if (s.contains(" break "))
            return true;
        else
            return false;
    }
    
    static void procWorkingRoutine(String s)
    {
        int start=0;
        int begin,end;
        Pattern pat = Pattern.compile(" procedure ");
        Matcher mat = pat.matcher(s);
        s = mat.replaceAll(" ");
        pat = Pattern.compile("\\s[^\\s]*\\s");
        mat = pat.matcher(s);
        mat.find();
        begin=mat.start()+1;
        end=mat.end()-1;
        namespace=s.substring(begin, end);
        curMatrix = new Matrix();
        MainMenu.matrix.put(namespace, curMatrix);
        j_index=0;
    }
    
    static void beginWorkingRoutine()
    {
        if (stack.empty())
        {
            if (namespace.equals("main"))
            {
                curMatrix = new Matrix();
                MainMenu.matrix.put(namespace, curMatrix);
                j_index=0;
            }
            lastIndex=j_index;
            j_index++;
        }
        else if ((stack.peek().equals("then"))||
                (stack.peek().equals("else")))
        {            
            Boolean tmp = then_else_begin.pop();
            tmp=true;
            then_else_begin.push(tmp);
        }
        else if (stack.peek().equals("for"))
        {            
            Boolean tmp = for_begin.pop();
            tmp=true;
            for_begin.push(tmp);
        }
    }
    
    static void endWorkingRoutine()
    {
        if (stack.empty())
        {
            curMatrix.add(lastIndex,j_index,"end");
            curMatrix.setLength(j_index+1);
            if (namespace.equals("main"))
                DontEnd=false;
            else
                namespace="main";
            curMatrix.setH(h);
            h=0;
        }
        else if (stack.peek().equals("else"))//куча pop'ов!!!!!!!!!!!!!!!!!!!
        {          
            curMatrix.add(then_end.pop(),j_index,"end");
            curMatrix.add(lastIndex,j_index,"end");
            lastIndex=j_index;
            j_index++;
            stack.pop();
            stack.pop();
            i_index.pop();
            i_index.pop();
            then_else_begin.pop();
            carryOut.pop();
        }
        else if (stack.peek().equals("then"))
        {      
            Integer tmp = then_end.pop();
            tmp=lastIndex;
            then_end.push(tmp);
            stack.pop();
            i_index.pop();
            lastIndex=i_index.peek();
            Boolean tmp2 = then_else_begin.pop();
            tmp2=false;
            then_else_begin.push(tmp2);
            carryOut.pop();
        }
        else if (stack.peek().equals("switch"))
        {
            ArrayList<Integer> tmp = case_ends.pop();
            for (Integer case_end : tmp)
                curMatrix.add(case_end, j_index,"end");
            lastIndex=j_index;
            j_index++;
            stack.pop();
            i_index.pop();
        }
        else if (stack.peek().equals("while"))
        {
            curMatrix.add(lastIndex, j_index,"end");//можно curMatrix.add(lastIndex,i_index.pop());
            curMatrix.add(j_index,i_index.pop(),"while");
            lastIndex=j_index;//без этого
            j_index++;//и этого
            stack.pop();
        }
        else if (stack.peek().equals("for"))
        {
            curMatrix.add(lastIndex, j_index,"end");//то же самое что для while
            curMatrix.add(j_index,i_index.pop(),"for");
            lastIndex=j_index;
            j_index++;
            stack.pop();
            carryOut.pop();
        }
    }
    
    static void whileWorkingRoutine(String s)
    {
        curMatrix.add(lastIndex, j_index,"while");
        stack.push("while");
        i_index.push(j_index);
        lastIndex=j_index;
        j_index++;
        
        int begin,end,start=0;
        Pattern pat = Pattern.compile(" while ");
        Matcher mat = pat.matcher(s);
        s = mat.replaceAll(" ");
        pat = Pattern.compile("\\s[^\\s]*\\s");
        mat = pat.matcher(s);
        while (mat.find(start))
        {
            begin=mat.start()+1;
            end=mat.end()-1;
            h++;
            start=end;
        }       
    }
    
    static void forWorkingRoutine()
    {
        curMatrix.add(lastIndex, j_index,"for");
        stack.push("for");
        i_index.push(j_index);
        lastIndex=j_index;
        j_index++;
        for_begin.push(new Boolean(false));
        carryOut.push(new Boolean(false));
    }
    
    static void repeatWorkingRoutine()
    {
        curMatrix.add(lastIndex,j_index,"repeat");
        stack.push("repeat");
        i_index.push(j_index);
        lastIndex=j_index;
        j_index++;
    }
    
    static void untilWorkingRoutine(String s)
    {
        curMatrix.add(lastIndex, j_index,"until");
        curMatrix.add(j_index,i_index.pop(),"repeat");
        lastIndex=j_index;
        j_index++;
        stack.pop();
            
        int begin,end,start=0;
        Pattern pat = Pattern.compile(" until ");
        Matcher mat = pat.matcher(s);
        s = mat.replaceAll(" ");
        pat = Pattern.compile("\\s[^\\s]*\\s");
        mat = pat.matcher(s);
        while (mat.find(start))
        {
            begin=mat.start()+1;
            end=mat.end()-1;
            h++;
            start=end;
        } 
    }
    
    static void ifWorkingRoutine(String s)
    {
        curMatrix.add(lastIndex, j_index,"if");
        stack.push("if");
        i_index.push(j_index);
        lastIndex=j_index;
        j_index++;
        curMatrix.add(lastIndex, j_index,"then");
        stack.push("then");
        i_index.push(j_index);
        lastIndex=j_index;
        j_index++;
        then_end.push(new Integer(lastIndex));
        then_else_begin.push(new Boolean(false));
        carryOut.push(new Boolean(false));
        
        int begin,end,start=0;
        Pattern pat = Pattern.compile(" if ");
        Matcher mat = pat.matcher(s);
        s = mat.replaceAll(" ");
        pat = Pattern.compile("\\s[^\\s]*\\s");
        mat = pat.matcher(s);
        while (mat.find(start))
        {
            begin=mat.start()+1;
            end=mat.end()-1;
            h++;
            start=end;
        }
    }
    
    static void elseWorkingRoutine()
    {
        if (stack.peek().equals("then"))
        {
            Integer tmp = then_end.pop();
            tmp=lastIndex;
            then_end.push(tmp);
            stack.pop();
            i_index.pop();
            lastIndex=i_index.peek();
            Boolean tmp2 = then_else_begin.pop();
            tmp2=false;
            then_else_begin.push(tmp2);
            carryOut.pop();
        }
        curMatrix.add(lastIndex, j_index,"else");
        carryOut.push(new Boolean(false));
        stack.push("else");
        i_index.push(j_index);
        lastIndex=j_index;
        j_index++;
    }
    
    static void switchWorkingRoutine()
    {
        curMatrix.add(lastIndex, j_index,"switch");
        stack.push("switch");
        i_index.push(j_index);
        lastIndex=j_index;
        j_index++;
        
        case_ends.push(new ArrayList<Integer>());
    }
    
    static void caseWorkingRoutine()
    {
        curMatrix.add(lastIndex,j_index,"case");
        stack.push("case");
        i_index.push(j_index);
        lastIndex=j_index;
        j_index++;
    }
    
    static void breakWorkingRoutine()
    {
        while(!stack.peek().equals("case"))
            canIWork(false);
        ArrayList<Integer> tmp = case_ends.peek();
        tmp.add(lastIndex);
        stack.pop();
        i_index.pop();
        lastIndex=i_index.peek();
    }
    
    static boolean canIWork(boolean isElse)
    {
        if (!stack.empty())//если это end то НИКАКОГО CARRYOUT!!!
        {
            if (stack.peek().equals("for"))
            {
                if (!for_begin.peek())
                    if (carryOut.peek())
                    {
                        curMatrix.add(lastIndex, j_index,"end");//можно last + i_index.pop()
                        curMatrix.add(j_index,i_index.pop(),"for");
                        lastIndex=j_index;
                        j_index++;
                        stack.pop();
                        carryOut.pop();
                        return false;
                    }
                    else
                    {
                        Boolean tmp=carryOut.pop();
                        tmp=true;
                        carryOut.push(tmp);
                        return true;
                    }
                else
                    return true;
            }
            if (stack.peek().equals("then"))
            {
                if (!then_else_begin.peek())
                    if (carryOut.peek())
                    {
                        Integer tmp = then_end.pop();
                        tmp=lastIndex;
                        then_end.push(tmp);
                        stack.pop();
                        i_index.pop();
                        lastIndex=i_index.peek();
                        Boolean tmp2 = then_else_begin.pop();
                        tmp2=false;
                        then_else_begin.push(tmp2);
                        carryOut.pop();
                        return false;
                    }
                    else
                    {
                        Boolean tmp=carryOut.pop();
                        tmp=true;
                        carryOut.push(tmp);
                        return true;
                    }
                else
                    return true;
            }
            if (stack.peek().equals("else"))
            {
                if (!then_else_begin.peek())
                    if (carryOut.peek())
                    {
                        curMatrix.add(then_end.pop(),j_index,"end");
                        curMatrix.add(lastIndex,j_index,"end");
                        lastIndex=j_index;
                        j_index++;
                        stack.pop();
                        stack.pop();
                        i_index.pop();
                        i_index.pop();
                        then_else_begin.pop();
                        carryOut.pop();
                        return false;
                    }
                    else
                    {
                        Boolean tmp=carryOut.pop();
                        tmp=true;
                        carryOut.push(tmp);
                        return true;
                    }
                else
                    return true;
            }
            if ((stack.peek().equals("if"))&&(!isElse))
            {
                        curMatrix.add(then_end.pop(),j_index,"end");
                        curMatrix.add(lastIndex,j_index,"end");
                        lastIndex=j_index;
                        j_index++;
                        stack.pop();
                        i_index.pop();
                        then_else_begin.pop();
                        return false;
            }
            else
                return true;
        }
        return true;        
    }
    
    static boolean canIWorkCritical()
    {
        if (!stack.empty())//если это end то НИКАКОГО CARRYOUT!!!
        {
            if (stack.peek().equals("for"))
            {
                if (!for_begin.peek())
                {
                        curMatrix.add(lastIndex, j_index,"end");//можно last + i_index.pop()
                        curMatrix.add(j_index,i_index.pop(),"for");
                        lastIndex=j_index;
                        j_index++;
                        stack.pop();
                        carryOut.pop();
                        return false;
                 }
                else
                    return true;
            }
            if (stack.peek().equals("then"))
            {
                if (!then_else_begin.peek())
                    {
                        Integer tmp = then_end.pop();
                        tmp=lastIndex;
                        then_end.push(tmp);
                        stack.pop();
                        i_index.pop();
                        lastIndex=i_index.peek();
                        Boolean tmp2 = then_else_begin.pop();
                        tmp2=false;
                        then_else_begin.push(tmp2);
                        carryOut.pop();
                        return false;
                    }
                else
                    return true;
            }
            if (stack.peek().equals("else"))
            {
                if (!then_else_begin.peek())
                    {
                        curMatrix.add(then_end.pop(),j_index,"end");
                        curMatrix.add(lastIndex,j_index,"end");
                        lastIndex=j_index;
                        j_index++;
                        stack.pop();
                        stack.pop();
                        i_index.pop();
                        i_index.pop();
                        then_else_begin.pop();
                        carryOut.pop();
                        return false;
                    }
                else
                    return true;
            }
            if ((stack.peek().equals("if")))
            {
                        curMatrix.add(then_end.pop(),j_index,"end");
                        curMatrix.add(lastIndex,j_index,"end");
                        lastIndex=j_index;
                        j_index++;
                        stack.pop();
                        i_index.pop();
                        then_else_begin.pop();
                        return false;
            }
            else
                return true;
        }
        return true;        
    }
    
    static Stack<Boolean> carryOut;
    static Stack<Integer> then_end;
    static Stack<ArrayList<Integer>> case_ends;
    static int j_index, lastIndex;
    static Stack<Boolean> then_else_begin;
    static Stack<Boolean> for_begin;
    static Matrix curMatrix;
    static int h;
    static Stack<String> stack;
    static Stack<Integer> i_index;   
    static String namespace;
    static boolean DontEnd;
    
    static void analiz()
    {
        namespace="main";
        j_index=0;
        carryOut = new Stack<Boolean>();
        then_else_begin= new Stack<Boolean>();
        for_begin= new Stack<Boolean>();
        then_end=new Stack<Integer>();
        case_ends=new Stack<ArrayList<Integer>>();
        h=0;
        i_index = new Stack<Integer>();
        stack = new Stack<String>();
        String tempStr = "";
        String str = new String(MainMenu.code2);
        int start = 0;
        
        Pattern pat = Pattern.compile(";[^;]*;");
        Matcher mat = pat.matcher(str);
        int begin,end;
        DontEnd = true;
        while (DontEnd)
        {
            if (mat.find(start))
            {
                begin = mat.start()+1;
                end = mat.end()-1;
                start = end;
                tempStr = str.substring(begin, end);
               // System.out.println(tempStr);
                if (isProcedure(tempStr))
                {                    
                    procWorkingRoutine(tempStr);
                }
                if (isBegin(tempStr))
                {
                    beginWorkingRoutine();
                }
                if (isEnd(tempStr))
                {
                  //  while(!canIWork(false));
                    while(!canIWorkCritical());
                    endWorkingRoutine();
                }
                if (isWhile(tempStr))
                {
                    while(!canIWork(false));
                    whileWorkingRoutine(tempStr);
                }
                if (isFor(tempStr))
                {
                    while(!canIWork(false));
                    forWorkingRoutine();
                }
                if (isRepeat(tempStr))
                {
                    while(!canIWork(false));
                    repeatWorkingRoutine();
                }
                if (isUntil(tempStr))
                {
                  //  while(!canIWork(false));
                    while(!canIWorkCritical());
                    untilWorkingRoutine(tempStr);
                }
                if (isIf(tempStr))
                {
                    while(!canIWork(false));
                    ifWorkingRoutine(tempStr);
                }
                if (isElse(tempStr))
                {
                    while(!canIWork(true));
                    elseWorkingRoutine();
                }
                if (isSwitch(tempStr))
                {
                    while(!canIWork(false));
                    switchWorkingRoutine();
                }
                if (isCase(tempStr))
                {
                    caseWorkingRoutine();
                }
                if (isBreak(tempStr))
                {
                   // while(!canIWork(false));
                    while(!canIWorkCritical());
                    breakWorkingRoutine();
                }
                
                start = end;
            }
            else
                DontEnd = false;
        }
    }
}
