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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
/**
 *
 * @author 123
 */
public class Pars {
    
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
        
        pat = Pattern.compile("end[^ ;]");
        mat = pat.matcher(res);
        res = mat.replaceAll("");
        
        
        pat = Pattern.compile("\\s+");
        mat = pat.matcher(res);
        res = mat.replaceAll(" ");
        
        pat = Pattern.compile("([\\s\\)](and|or|xor)[\\s\\(])|([\"\'].+?[\"\'])");
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
        Pattern pat = Pattern.compile("\\s+");
        Matcher mat = pat.matcher(code);
        String str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("([\\s\\)](and|or|xor)[\\s\\(])|([\"\'].+?[\"\'])");
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
        
        pat = Pattern.compile("[//(]");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("[//)]");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("function");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("procedure");
        
        pat = Pattern.compile("begin");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ; begin ; ");
        
        pat = Pattern.compile("then");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(";");
        
        pat = Pattern.compile("else");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ; else ;");
        
        pat = Pattern.compile("var");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ; var ; ");
        
        pat = Pattern.compile("inc");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("dec");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("readln");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("read");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile("const");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ; const ;");
                     
        pat = Pattern.compile("=");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" = ");
        
        pat = Pattern.compile("\\*");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("\\+");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("-");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
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

        pat = Pattern.compile(" =[\\s\\d\\.]*;");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ;");

        pat = Pattern.compile(" case [^:]*:");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" case ;");
        
        pat = Pattern.compile(": [^;]+");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll("");
        
        pat = Pattern.compile(" do ");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ; ");
        
        pat = Pattern.compile(" to ");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile(" = ");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile(" [\\d]* ");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        pat = Pattern.compile("( ;)+");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ;");
        
        pat = Pattern.compile("\\s+");
        mat = pat.matcher(str1);
        str1 = mat.replaceAll(" ");
        
        return str1;
    }
    
    static String namespace="";
    enum ID {cnst,var,pf,noth};
    
    static boolean isConst(String s)
    {
        if (s.equals(" const "))
            return true;
        else
            return false;
    }
    
    static boolean isVar(String s)
    {
        if (s.equals(" var "))
            return true;
        else
            return false;
    }
    
    static boolean isBegin(String s)
    {
        if (s.equals(" begin "))
            return true;
        else
            return false;
    }
    
    static boolean isEnd(String s)
    {
        if (s.equals(" end "))
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
    
    static void constWorkingRoutin(String s)
    {
        Pattern pat = Pattern.compile("\\s[^\\s]*\\s");
        Matcher mat = pat.matcher(s);
        int start=0;
        int begin,end;
        String tempStr;

        while (mat.find(start))
        {
            begin = mat.start()+1;
            end = mat.end()-1;
            tempStr = s.substring(begin, end);
            if (namespace.equals(""))
                tempStr="main."+tempStr;
            else
                tempStr=namespace+"."+tempStr;
            if (!MainMenu.vars.containsKey(tempStr))
                MainMenu.vars.put(tempStr, new Atributes(true));
            
            start = end;
        }
    }
    
    static void varWorkingRoutin(String s)
    {
        Pattern pat = Pattern.compile("\\s[^\\s]*\\s");
        Matcher mat = pat.matcher(s);
        int start=0;
        int begin,end;
        String tempStr;

        while (mat.find(start))
        {
            begin = mat.start()+1;
            end = mat.end()-1;
            tempStr = s.substring(begin, end);
            if (namespace.equals(""))
                tempStr="main."+tempStr;
            else
                tempStr=namespace+"."+tempStr;
            if (!MainMenu.vars.containsKey(tempStr))
                MainMenu.vars.put(tempStr, new Atributes(false));
            
            start = end;
        }
    }
    
    static boolean isControl(String s)
    {
        if (s.contains(" for "))
            return true;
        if (s.contains(" case "))
            return true;
        if (s.contains(" switch "))
            return true;
        if (s.contains(" if "))
            return true;
        if (s.contains(" while "))
            return true;
        if (s.contains(" until "))
            return true;
        return false;
    }
    
    static void analizFor(String line,String namesp,String var)
    {
        if (line.contains(" for "+var))
        {
            MainMenu.vars.get(namesp+var).M=true;
            MainMenu.vars.get(namesp+var).initial=true;
            MainMenu.vars.get(namesp+var).P=false;
        }
    }
    
    static void pfWorkingRoutin(String s)
    {
        Pattern pat = Pattern.compile("\\s[^\\s]*\\s");
        Matcher mat = pat.matcher(s);
        int start=0;
        int begin,end;
        String tempStr;
        if (mat.find(start))
        {
            begin = mat.start()+1;
            end = mat.end()-1;
            tempStr = s.substring(begin, end);
            if (MainMenu.vars.containsKey(namespace+"."+tempStr))
            {
                MainMenu.vars.get(namespace+"."+tempStr).count++;
                MainMenu.vars.get(namespace+"."+tempStr).T=false;
                if (isControl(s))
                    MainMenu.vars.get(namespace+"."+tempStr).C=true;
                else
                {
                    if (MainMenu.vars.get(namespace+"."+tempStr).initial)
                    {
                        MainMenu.vars.get(namespace+"."+tempStr).M=true;
                        MainMenu.vars.get(namespace+"."+tempStr).P=false;
                    }
                    else
                    {
                        MainMenu.vars.get(namespace+"."+tempStr).initial=true;
                        MainMenu.vars.get(namespace+"."+tempStr).P=true;
                    }
                }
            }
            else if (MainMenu.vars.containsKey("main."+tempStr))
            {
                MainMenu.vars.get("main."+tempStr).count++;
                MainMenu.vars.get("main."+tempStr).T=false;
                if (isControl(s))
                    MainMenu.vars.get("main."+tempStr).C=true;
                else
                {
                    if (MainMenu.vars.get("main."+tempStr).initial)
                    {
                        MainMenu.vars.get("main."+tempStr).M=true;
                        MainMenu.vars.get("main."+tempStr).P=false;
                    }
                    else
                    {
                        MainMenu.vars.get("main."+tempStr).initial=true;
                        MainMenu.vars.get("main."+tempStr).P=true;
                    }
                }
            }
            
            start = end;
        }     
        
        while (mat.find(start))
        {
            begin = mat.start()+1;
            end = mat.end()-1;
            tempStr = s.substring(begin, end);
            if (MainMenu.vars.containsKey(namespace+"."+tempStr))
            {
                MainMenu.vars.get(namespace+"."+tempStr).count++;
                MainMenu.vars.get(namespace+"."+tempStr).T=false;
                if (isControl(s))
                {
                    analizFor(s, namespace+".", tempStr);
                    MainMenu.vars.get(namespace+"."+tempStr).C=true;
                }
            }
            else if (MainMenu.vars.containsKey("main."+tempStr))
            {
                MainMenu.vars.get("main."+tempStr).count++;
                MainMenu.vars.get("main."+tempStr).T=false;
                if (isControl(s))
                {
                    analizFor(s, "main.", tempStr);
                    MainMenu.vars.get("main."+tempStr).C=true;
                }
            }
            
            start = end;
        }
    }
    
    static void identifyProcedure(String s)
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
        start=end;
        String tempStr;

        while (mat.find(start))
        {
            begin = mat.start()+1;
            end = mat.end()-1;
            tempStr = s.substring(begin, end);
            tempStr=namespace+"."+tempStr;
            if (!MainMenu.vars.containsKey(tempStr))
                MainMenu.vars.put(tempStr, new Atributes(false));
            
            start = end;
        }
    }
    
    static boolean newProcedure=false;
    static int begins=0;
    
    static void analiz()
    {         
        namespace="";
        begins=0;
        newProcedure=false;
        ID id=ID.noth;
        
        String tempStr = "";
        String str = new String(MainMenu.code);
        int start = 0;
        
        Pattern pat = Pattern.compile(";[^;]*;");
        Matcher mat = pat.matcher(str);
        int begin,end;
        boolean DontEnd = true;
        while (DontEnd)
        {
            if (mat.find(start))
            {
                begin = mat.start()+1;
                end = mat.end()-1;
                start = end;
                tempStr = str.substring(begin, end);
                if (isConst(tempStr))
                {
                   id = ID.cnst;
                   continue;
                }
                if (isVar(tempStr))
                {
                   id = ID.var;
                   continue;
                }
                if (isProcedure(tempStr))
                {
                    newProcedure=true;
                    identifyProcedure(tempStr);
                    id= ID.noth;
                    continue;
                }
                if (isBegin(tempStr))
                {
                    if (newProcedure)
                    {
                        newProcedure=false;
                        id=ID.pf;
                    }
                    else if (begins==0)
                    {
                        id=ID.pf;
                    }
                    begins++;
                    continue;
                }
                if (isEnd(tempStr))
                {
                    begins--;
                    if (begins==0)
                    {
                        namespace="";
                        id=ID.noth;
                    }
                    if (begins<0) begins=0;
                    continue;
                }
                switch (id)
                {
                    case cnst:
                        constWorkingRoutin(tempStr);
                        break;
                    case var:
                        varWorkingRoutin(tempStr);
                        break;
                    case pf:
                        pfWorkingRoutin(tempStr);
                        break;
                }
                start = end;
            }
            else
                DontEnd = false;
        }
    }
    
}
