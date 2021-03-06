//
// Generated by JTB 1.3.2
//

// Author :- T.Bhanu Shashank

package visitor;
import java.util.regex.Pattern;
import java.util.*;
import java.util.regex.Matcher;
import syntaxtree.*;


/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class phase2<R,A> extends phase1<R,A> {
 
     String ansToprint = "";
     String printchecker = "";

     int[] usedforchecking = {0,1,1,1,1,1,1,1,1,1};
     int paramcount = 0;
     int variablesinmethod = 0;

     HashMap<String,Integer> classreference = new HashMap<String,Integer>();


     void intialData(MemoryData a){
        answer.SymbolTable = a.SymbolTable;
        answer.SymbolTableForMemory = a.SymbolTableForMemory;
     }
     int i;

   //
   // Auto class visitors--probably don't need to be overridden.
   //
   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return null; }

   //
   // User-generated visitor methods below
   //

   HashMap <String,Integer> Variable_Signature=new HashMap <String,Integer>();
 
 int updateLabel(){
       int pro = newLabel++;
       return pro;
   }


   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public R visit(Goal n, A argu) {

      R _ret=null;

      intialData((MemoryData)argu);
      argu = null;

      newLabel=0;

      n.f0.accept(this, argu);
 

      printchecker += "MAIN";

      n.f1.accept(this, argu);
 
      printchecker += RunningClassName;

      n.f2.accept(this, argu);

      if(usedforchecking[0]>0){
         //System.out.println(printchecker);
      }

      System.out.println(ansToprint);
      
      return _ret;
   }

        String RunningClassName;
        int newLabel;


   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> PrintStatement()
    * f15 -> "}"
    * f16 -> "}"
    */
   public R visit(MainClass n, A argu) {

      usedforchecking[3]++;

      R _ret = null;

      ansToprint += "MAIN\n";

      variablesinmethod = 0;

      n.f0.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
 
      classreference.put("Main",1);

      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
 
      usedforchecking[6]++;


      RunningClassName = (String) n.f1.accept(this, argu);

      Table temp = answer.SymbolTable.get(RunningClassName).methodInfo.get(0).get("main");

      if(record.get(RunningClassName)!=null){
         for(int j=0;j<9;j++){
            usedforchecking[j] = 0;
         }
      }

      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
 
      usedforchecking[7]++;

      if(usedforchecking[8]==0){
         printchecker += "//NO VARIABLES";
      }

      n.f11.accept(this, (A)temp);
      n.f12.accept(this, (A)temp);
      n.f13.accept(this, (A)temp);
      n.f14.accept(this, (A)temp);
      n.f15.accept(this, (A)temp);
      n.f16.accept(this, (A)temp);

      ansToprint += "END\n";

      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public R visit(TypeDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public R visit(ClassDeclaration n, A argu) {
 
      usedforchecking[4]++; //class count without extension

      R _ret=null;
      n.f0.accept(this, argu);
      n.f2.accept(this, argu);

      RunningClassName = (String)n.f1.accept(this, argu);

      classreference.put(RunningClassName,usedforchecking[4]);

      Table temp = answer.SymbolTable.get(RunningClassName);
 
      n.f3.accept(this, (A)temp);
      n.f4.accept(this, (A)temp);
      n.f5.accept(this, (A)temp);

      return _ret;
   }


void variableAllocation(newTable t){
 
         Iterator<String> it = t.functionInfo.get(0).keySet().iterator();

          while(it.hasNext()){
             String v = it.next();
             ansToprint +=" MOVE TEMP "+(i+1)+" 0";
             ansToprint +="\n";
             ansToprint +=" HSTORE TEMP "+(i-1)+" ";
             ansToprint += t.functionInfo.get(0).get(v);
             ansToprint +=" TEMP "+(i+1);
             ansToprint +="\n";
          }
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public R visit(ClassExtendsDeclaration n, A argu) {
 
      usedforchecking[5]++; //classes count with extensions

       R _ret=null;
      n.f0.accept(this, argu);
      n.f2.accept(this, argu);

      RunningClassName = (String)n.f1.accept(this, argu);

      classreference.put(RunningClassName,usedforchecking[5]);

      Table temp = answer.SymbolTable.get(RunningClassName);

 
      n.f3.accept(this, (A)temp);
      n.f4.accept(this, (A)temp);
      n.f5.accept(this, (A)temp);
      n.f6.accept(this, (A)temp);
      n.f7.accept(this, (A)temp);

      return _ret;
   }


   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public R visit(VarDeclaration n, A argu) {

     R _ret=null;
     variablesinmethod++;
     n.f0.accept(this, argu);
     n.f2.accept(this, argu);

     String pro = (String)n.f1.accept(this,argu);

     Variable_Signature.put(pro,i++);
     
      return (R)pro;
   }

   void initialSignature(LinkedList<R> temp){
      i=1;
      Variable_Signature = new HashMap<String,Integer>();
      int l = temp.size();
      int j = 0;
      while(j<l){
            Variable_Signature.put((String)temp.get(j),i++);
            j++;
      }
   }

   /**
    * f0 -> AccessType()
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public R visit(MethodDeclaration n, A argu) {
 
     usedforchecking[2]++; //no of methods tracking

      R _ret=null;
      n.f0.accept(this, (A)null);
  
      if(recordTypes.get(RunningClassName)!=null){
         for(int j= 0;j<9;j++){
            usedforchecking[j] = 0;
         }
      }

      n.f1.accept(this, (A)null);

      String NameOfTheFunction = (String)n.f2.accept(this, (A)null); 

      if(classreference.get(NameOfTheFunction)!=null){
         usedforchecking[2]--;
      }
     

      Iterator<String> it = answer.SymbolTable.keySet().iterator();

     String classname = null;
     LinkedList<R> help = new LinkedList<R> ();

      while(it.hasNext()){
          String s = it.next();
          if(answer.SymbolTable.get(s)==argu){
              classname = s;
          }
      }
      

      LinkedList<R> temp = (LinkedList<R>)n.f4.accept(this, (A)help);

      classreference.put(classname,usedforchecking[2]);

      if(temp==null){
        usedforchecking[8] = 1;
        temp = new LinkedList<R>();
      } 
      else{
         usedforchecking[8] = help.size()+1;
         temp = help;
      }

      initialSignature(temp);

      ansToprint += classname;
      ansToprint += "_";
      ansToprint += NameOfTheFunction;
      ansToprint += " [";
      ansToprint += (temp.size()+1);
      ansToprint += "]";
      ansToprint += "\n";
      ansToprint += " BEGIN";
      ansToprint += "\n";

      Table b = ((Table)argu).methodInfo.get(0).get(NameOfTheFunction);

      A hi = (A)b;

      recordTypes.put(NameOfTheFunction,classname);
 
      n.f5.accept(this, hi);
      n.f6.accept(this, hi);
      n.f7.accept(this, hi);
      n.f8.accept(this, hi);
      n.f9.accept(this, hi);
      n.f10.accept(this, hi);
      n.f11.accept(this, hi);
      n.f12.accept(this, hi);

      n.f3.accept(this, (A)null);
 
      ansToprint += " RETURN TEMP "+(i-1);
      ansToprint += "\n";
      ansToprint += " END ";
      ansToprint += "\n";

      printchecker += usedforchecking[2];

      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public R visit(FormalParameterList n, A argu) {

      usedforchecking[1]++;

      R _ret=null;
      String pro = (String)n.f0.accept(this,argu);
      LinkedList<R> temp = (LinkedList<R>)n.f1.accept(this,argu);
      
      if (pro == null){
          return _ret;
       }

       if (temp==null) {
          temp = new LinkedList<R>();
          temp.addFirst((R)pro);
       }
       else{
         temp.addFirst((R)pro);
       }

      return (R)temp;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public R visit(FormalParameter n, A argu) {
      R _ret=null;
      
      n.f0.accept(this, argu);
      String id = (String)n.f1.accept(this, argu);

      paramcount ++;
      usedforchecking[4]++;

      if(id!=null){
         ((LinkedList<R>)argu).addFirst((R)id);
      }

      return (R)id;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public R visit(FormalParameterRest n, A argu) {
      R _ret=null;
      paramcount++;
      n.f0.accept(this, argu);
      return n.f1.accept(this,argu);
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public R visit(Type n, A argu) {
       R _ret=null;
      String pro = (String)n.f0.accept(this,argu);
      return (R)pro;
   }

   void methodAllocation(String a,newTable t){

         Iterator<String> it = t.functionInfo.get(1).keySet().iterator();

          while(it.hasNext()){
             String m  = it.next();
             if(!Pattern.matches(a+"_"+".*", m)){
                     ansToprint += " MOVE TEMP ";
                     ansToprint += (i+1)+" "+m;
                     ansToprint += "\n";
                     ansToprint += " HSTORE TEMP "+i+" ";
                     ansToprint += t.functionInfo.get(1).get(m)+" ";
                     ansToprint += "TEMP "+(i+1);
                     ansToprint += "\n";
               }
          } 

          it = t.functionInfo.get(1).keySet().iterator();

           while(it.hasNext()){
             String m  = it.next();
             if(Pattern.matches(a+"_"+".*", m)){
                     ansToprint += " MOVE TEMP ";
                     ansToprint += (i+1)+" "+m;
                     ansToprint += "\n";
                     ansToprint += " HSTORE TEMP "+i+" ";
                     ansToprint += t.functionInfo.get(1).get(m)+" ";
                     ansToprint += "TEMP "+(i+1);
                     ansToprint += "\n";
               }
          }
   }

   void checkingstrings(){
      Iterator<String> it = record.keySet().iterator();
      while(it.hasNext()){
         String pro = it.next();
         System.out.println(pro);
      }
   }

   /**
    * f0 -> PublicType()
    *       | PrivateType()
    *       | ProtectedType()
    */
   public R visit(AccessType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public R visit(ArrayType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public R visit(BooleanType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public R visit(IntegerType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "public"
    */
   public R visit(PublicType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "private"
    */
   public R visit(PrivateType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "protected"
    */
   public R visit(ProtectedType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public R visit(Statement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public R visit(Block n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   public String typeIdentifier(String id, Table a){
        if(a.variablesInfo.get(id)!=null){
              return a.variablesInfo.get(id);
         }
         return typeIdentifier(id,answer.SymbolTable.get(a.parent));
    }
    
   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public R visit(AssignmentStatement n, A argu) {
      R _ret = null;
      String pro = (String)n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      if(Variable_Signature.get(pro)!=null){
            ansToprint += "MOVE TEMP ";
            ansToprint += Variable_Signature.get(pro);
            ansToprint += " " +" TEMP "+(i-1);
            ansToprint += "\n";

      }
      else{
            String[] thop = new String[2];
            thop[0] = RunningClassName;
            thop[1] = pro;
            ansToprint += " HSTORE TEMP 0 ";
            ansToprint += VariableOffsetFinding(thop);
            ansToprint += " " + " TEMP "+(i-1);
            ansToprint += "\n";

      }
      return (R)pro;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   
    public R visit(ArrayAssignmentStatement n, A argu) {
      R _ret=null;

      String pro = (String)n.f0.accept(this,argu);

      int[] temp = new int[6];          
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      temp[0] = i-1;
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);

      int i1=i++;
       for(int j = 1;j<5;j++){
          temp[j] = i++;
       } 


   if(Variable_Signature.get(pro)!=null){

       ansToprint += "MOVE TEMP "+ temp[1] + " TEMP "+(i-5);      
       ansToprint += "\n";

       ansToprint += "MOVE TEMP "+ temp[2];
       ansToprint += " TIMES TEMP " + temp[0] + " 4" ;      
       ansToprint += "\n";
       ansToprint += "MOVE TEMP " + temp[3];
       ansToprint += " PLUS TEMP " + temp[2] + " 4" ;      
       ansToprint += "\n";
       ansToprint += "MOVE TEMP " + temp[4];
       ansToprint += " PLUS TEMP " + temp[3] + " TEMP ";
       ansToprint += Variable_Signature.get(pro);
       ansToprint += "\n";
       ansToprint += "HSTORE TEMP " + temp[4] + " 0 TEMP " + temp[1];      
       ansToprint += "\n";

      
      }
    else{
         
      ansToprint += " MOVE TEMP "+i1+" TEMP "+(i-6);      
      ansToprint += "\n";

      String[] thop = new String[2];
      thop[0] = RunningClassName;
      thop[1] = pro;

      ansToprint += " HLOAD TEMP "+temp[1]+" TEMP 0 "; 
      ansToprint +=  VariableOffsetFinding(thop);    
      ansToprint += "\n";
      ansToprint += "MOVE TEMP "+ temp[2];
      ansToprint += " TIMES TEMP " + temp[0] + " 4" ;      
      ansToprint += "\n";
      ansToprint += "MOVE TEMP " + temp[3];
      ansToprint += " PLUS TEMP " + temp[2] + " 4" ;      
      ansToprint += "\n";
      ansToprint += "MOVE TEMP " + temp[4];
      ansToprint += " PLUS TEMP " + temp[3] + " TEMP " + temp[1];      
      ansToprint += "\n";
      ansToprint += "HSTORE TEMP " + temp[4] + " 0 TEMP " + i1;      
      ansToprint += "\n";

    }
            return (R)pro;
   }

   /**
    * f0 -> IfthenElseStatement()
    *       | IfthenStatement()
    */
   public R visit(IfStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(IfthenStatement n, A argu) {
       R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      ansToprint += " CJUMP TEMP "+(i-1);      
      ansToprint += "\n";

      ansToprint += " L"+ newLabel +" NOOP";      
      ansToprint += "\n";


      int temp = updateLabel();
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      ansToprint += " L"+ temp +" NOOP ";      
      ansToprint += "\n";


      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public R visit(IfthenElseStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);


     ansToprint += " CJUMP TEMP "+(i-1);
     ansToprint += " L"+ newLabel;      
     ansToprint += "\n";


      int l1=updateLabel();
      int l2=updateLabel();
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

      ansToprint += " JUMP L"+l2;
      ansToprint += "\n";
      ansToprint += "L"+l1+" NOOP ";      
      ansToprint += "\n";


      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      
      ansToprint += " L"+l2+" NOOP ";      
      ansToprint += "\n";


      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(WhileStatement n, A argu) {
       R _ret=null;

      ansToprint += " L"+newLabel+" NOOP ";      
      ansToprint += "\n";


      int l1=updateLabel();
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      ansToprint += " CJUMP TEMP "+(i-1);
      ansToprint += " L"+ newLabel;      
      ansToprint += "\n";


      int l2=updateLabel();
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);

 

     ansToprint += " JUMP L"+l1;      
     ansToprint += "\n";
     ansToprint += " L"+l2+" NOOP";      
     ansToprint += "\n";

      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public R visit(PrintStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);


      ansToprint += " PRINT "+"TEMP "+(i-1);      
      ansToprint += "\n";


      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   void printingCallVariables(){
      ListIterator<Integer> k = storingLabels.listIterator();
      while (k.hasNext()){
          int temp = k.next();
          ansToprint += " TEMP " + temp + " ";      
          ansToprint += "\n";
      }
   }


   /**
    * f0 -> OrExpression()
    *       | AndExpression()
    *       | CompareExpression()
    *       | neqExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | DivExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | TernaryExpression()
    *       | PrimaryExpression()
    */
   public R visit(Expression n, A argu) {
      R _ret=null;
      return n.f0.accept(this,argu);
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public R visit(AndExpression n, A argu) {
       R _ret=null;
      n.f0.accept(this, argu);
      int pro1 = i-1;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      int pro2 = i-1;

      ansToprint += " MOVE TEMP " + i++;
      ansToprint += " TIMES TEMP "+pro1 + " TEMP "+pro2;      
      ansToprint += "\n";


      return (R)"boolean";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "||"
    * f2 -> PrimaryExpression()
    */
   public R visit(OrExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      int pro1 = i-1;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      int pro2 = i-1;
      ansToprint += " MOVE TEMP " + i++;
      ansToprint += " PLUS TEMP "+pro1 + " TEMP "+pro2;      
      ansToprint += "\n";


      return (R)"boolean";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<="
    * f2 -> PrimaryExpression()
    */
   public R visit(CompareExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      int pro1 = i-1;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      int pro2 = i-1;
      ansToprint += " MOVE TEMP " + i++;
      ansToprint += " LE TEMP "+pro1 + " TEMP "+pro2;      
      ansToprint += "\n";


      return (R)"boolean";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "!="
    * f2 -> PrimaryExpression()
    */
   public R visit(neqExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      int pro1 = i-1;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      int pro2 = i-1;
      ansToprint += " MOVE TEMP " + i++;
      ansToprint += " NE TEMP "+pro1 + " TEMP "+pro2;      
      ansToprint += "\n";


      return (R)"boolean";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public R visit(PlusExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      int pro1 = i-1;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      int pro2 = i-1;
      ansToprint += " MOVE TEMP " + i++;
      ansToprint += " PLUS TEMP "+pro1 + " TEMP "+pro2;      
      ansToprint += "\n";


      return (R)"int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public R visit(MinusExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      int pro1 = i-1;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      int pro2 = i-1;

      ansToprint += " MOVE TEMP " + i++;
      ansToprint += " MINUS TEMP "+pro1 + " TEMP "+pro2;      
      ansToprint += "\n";


      return (R)"int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public R visit(TimesExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      int pro1 = i-1;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      int pro2 = i-1;
 
      ansToprint += " MOVE TEMP " + i++;
      ansToprint += " TIMES TEMP "+pro1 + " TEMP "+pro2;      
      ansToprint += "\n";

      return (R)"int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "/"
    * f2 -> PrimaryExpression()
    */
   public R visit(DivExpression n, A argu) {
       R _ret=null;
       n.f0.accept(this, argu);
      int pro1 = i-1;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      int pro2 = i-1;
      ansToprint += " MOVE TEMP " + i++;
      ansToprint += " DIV TEMP "+pro1 + " TEMP "+pro2;      
      ansToprint += "\n";


      return (R)"int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public R visit(ArrayLookup n, A argu) {
 
     int[] temp = new int[7];

      R _ret=null;
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      temp[1]=i++;

      ansToprint += "MOVE TEMP "+temp[1]+" TEMP "+(i-2);      
      ansToprint += "\n";

      
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      temp[2]=i++;
      temp[3]=i++;
      i++;
      temp[4] = i++;
      temp[5] = i++;
      temp[6] = i++;


      ansToprint += "MOVE TEMP "+temp[2]+" TEMP "+(i-7);      
      ansToprint += "\n";


       ansToprint += " MOVE TEMP " + temp[4];
       ansToprint += " TIMES TEMP " + temp[2] + " 4";      
       ansToprint += "\n";
       ansToprint += " MOVE TEMP " + temp[5];
       ansToprint += " PLUS TEMP " + temp[4] + " 4";      
       ansToprint += "\n";
       ansToprint += " MOVE TEMP " + temp[6];
       ansToprint += " PLUS TEMP " + temp[1] + " TEMP " + temp[5];      
       ansToprint += "\n";
       ansToprint += " HLOAD TEMP " + temp[3];
       ansToprint += " TEMP " + temp[6] + " 0";      
       ansToprint += "\n";
       ansToprint += "MOVE TEMP "+ i++ + " TEMP "+temp[3];      
       ansToprint += "\n";

       printchecker += "MOVE TEMP "+temp[2]+" TEMP "+(i-7);      
       printchecker += "\n"; 
       return (R)"int";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public R visit(ArrayLength n, A argu) {

      int pro2 = i;
      printchecker += "MOVE TEMP "+pro2;
    
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);

      int pro1 = i++;

      ansToprint += " MOVE TEMP "+ pro1;
      ansToprint += " TEMP "+(i-2);      
      ansToprint += "\n";
      ansToprint += " HLOAD TEMP "+ i++;
      ansToprint += " TEMP "+ pro1 + " 0 ";      
      ansToprint += "\n";

      return (R)"int";
   }


   
   String typemethod(String a,Table b){
        if(b.structureOfFunctions.get(a)==null){
             return typemethod(a,answer.SymbolTable.get(b.parent));
         }
         else{
             return (String)b.structureOfFunctions.get(a).get(0);
         }
   }


    int MethodOffsetFinding(String[] pro){
         if(answer.SymbolTable.get(pro[0]).methodInfo.get(0).get(pro[1])!=null){
                String temp = pro[0]+"_"+pro[1];
                return answer.SymbolTableForMemory.get(pro[0]).functionInfo.get(1).get(temp);
         }
         else{
            String[] thop = new String[2];
            thop[0] = answer.SymbolTable.get(pro[0]).parent;
            thop[1] = pro[1];
            return MethodOffsetFinding(thop);
         }
    }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public R visit(MessageSend n, A argu) {
      R _ret=null;
      String classname=(String)n.f0.accept(this, argu);

      int temp2 = i-1;
      
      n.f1.accept(this, argu);
      String methodname=(String)n.f2.accept(this, argu);
      int temp = i;
     
      ansToprint += " MOVE TEMP "+ temp;
      ansToprint +=  " TEMP "+ temp2;     
      ansToprint += "\n";

      //i++;

      int pro1 = ++i;

      ansToprint += " HLOAD TEMP " + i++;
      ansToprint += " TEMP " + temp + " 0";      
      ansToprint += "\n";

      int pro2 = i++;

      String[] cry = new String[2];
      cry[0] = classname;
      cry[1] = methodname;


      ansToprint += " HLOAD TEMP " + pro2;
      ansToprint += " TEMP " + pro1;
      ansToprint += " "+ MethodOffsetFinding(cry);      
      ansToprint += "\n";


      n.f3.accept(this, argu);

      storingLabels.clear();
      n.f4.accept(this, argu);


      ansToprint += " MOVE TEMP " + i++;
      ansToprint += " CALL  TEMP "+pro2;
      ansToprint += " ( TEMP " +temp2;      
      ansToprint += "\n";


      printingCallVariables();

      n.f5.accept(this, argu);

      ansToprint += " )";      
      ansToprint += "\n";

      
      return (R)typemethod(methodname,answer.SymbolTable.get(classname));
   }



   /**
    * f0 -> PrimaryExpression()
    * f1 -> "?"
    * f2 -> PrimaryExpression()
    * f3 -> ":"
    * f4 -> PrimaryExpression()
    */
   public R visit(TernaryExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);

      ansToprint += " CJUMP TEMP "+ (i-1);
      ansToprint += "L" + newLabel;      
      ansToprint += "\n";

       
      n.f1.accept(this, argu);
 
      int l1 = updateLabel();
      int l2 = updateLabel();
      int pro = i+50;

      String b = (String)n.f2.accept(this, argu);

      int temp = i-1;

      ansToprint += "MOVE TEMP "+pro;
      ansToprint +=" TEMP "+temp;      
      ansToprint += "\n";

      ansToprint += " JUMP L"+l2;
      ansToprint += " \n";
      ansToprint += "L"+l1+" NOOP";      
      ansToprint += "\n";


      n.f3.accept(this, argu);

      n.f4.accept(this, argu);

      int temp2 =  i-1;

      ansToprint += "MOVE TEMP "+pro;
      ansToprint += " TEMP "+temp2;      
      ansToprint += "\n"; 
      ansToprint +=  " L"+l2+" NOOP ";      
      ansToprint += "\n";


      i = pro; i++;

      return (R) b;
   }

   LinkedList<Integer> storingLabels = new LinkedList<Integer> ();

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public R visit(ExpressionList n, A argu) {

      R _ret=null;

      String pro = (String)n.f0.accept(this,argu);

      storingLabels.add(i-1);

      LinkedList<R> temp = (LinkedList<R>)n.f1.accept(this,argu);

      return (R)temp;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public R visit(ExpressionRest n, A argu) {
      n.f0.accept(this, argu);
      R _ret=null;
      String pro = (String)n.f1.accept(this,argu);
      storingLabels.add(i-1);
      return (R)pro;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */




   public R visit(PrimaryExpression n, A argu) {
       R _ret=null;

       String pro = (String)n.f0.accept(this,argu); 

      if(n.f0.which==3){
            if(Variable_Signature.get(pro) != null){
                ansToprint += "MOVE TEMP ";
                ansToprint += i++;
                ansToprint += " TEMP ";
                ansToprint += Variable_Signature.get(pro);      
                ansToprint += "\n";
            }
            else{
               String[] thop = new String[2];
               thop[0] = RunningClassName;
               thop[1] = pro;
                ansToprint += "HLOAD TEMP ";
                ansToprint += i++;
                ansToprint +=" TEMP 0 ";
                ansToprint += VariableOffsetFinding(thop);    
                ansToprint += "\n";

            }
            return (R)typeIdentifier(pro,(Table)argu);
      }
        
      return (R)pro;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */

   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      int pro = i++;
      ansToprint += " MOVE TEMP ";
      ansToprint += pro;
      ansToprint += " ";
      ansToprint += n.f0.toString();      
      ansToprint += "\n";

      return (R)"int";
   }

   /**
    * f0 -> "true"
    */
   public R visit(TrueLiteral n, A argu) {
      R _ret=null;

      ansToprint += " MOVE TEMP "+ i++ + " 1";   
      n.f0.accept(this, argu);   
      ansToprint += "\n";

      return (R)"boolean";
   }

   /**
    * f0 -> "false"
    */
   public R visit(FalseLiteral n, A argu) {
      R _ret=null;

      ansToprint += " MOVE TEMP "+ i++ + " 0";
      n.f0.accept(this, argu);      
      ansToprint += "\n";


      return (R)"boolean";
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Identifier n, A argu) {
      usedforchecking[7]++; //count number of variables
      R _ret=null;
      n.f0.accept(this, argu);
      String hi = n.f0.toString();
      printchecker += hi;
      return (R) hi;
   }

   String correspondingname(Table pro){

      Iterator<String> it = answer.SymbolTable.keySet().iterator();

      while(it.hasNext()){
          String s = it.next();
          if(answer.SymbolTable.get(s)==pro){
            return s;
          }
 
          Iterator<String> it1 = answer.SymbolTable.get(s).methodInfo.get(0).keySet().iterator();

          while(it1.hasNext()){
             String b = it1.next();
             if(answer.SymbolTable.get(s).methodInfo.get(0).get(b)==pro){
                return s;
            }
          }
      }
      return "$";
   }

   /**
    * f0 -> "this"
    */
   public R visit(ThisExpression n, A argu) {
      R _ret=null;
      ansToprint += "MOVE TEMP "; 
      ansToprint += i++;
      ansToprint += " TEMP 0 ";   
      ansToprint += "\n";

       n.f0.accept(this, argu);
       String pro = correspondingname((Table)argu);

       if(pro != "$"){
           return (R)pro;
       }

       return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   
   public R visit(ArrayAllocationExpression n, A argu) {

      int[] temp = new int[20];

      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      for(int j=1;j<=4;j++){
         temp[j] = i++;
      }

 
      ansToprint += "MOVE TEMP "+temp[1]+" TEMP "+(i-5);      
      ansToprint += "\n";
      ansToprint += "MOVE TEMP "+temp[3];
      ansToprint += " TIMES "+" TEMP "+temp[1] + " 4";      
      ansToprint += "\n";
      ansToprint += "MOVE TEMP "+temp[4];
      ansToprint += " PLUS "+" TEMP "+temp[3] + " 4";      
      ansToprint += "\n";
      ansToprint += " MOVE TEMP "+temp[2];
      ansToprint += " HALLOCATE TEMP "+temp[4];      
      ansToprint += "\n";
      ansToprint += "HSTORE TEMP "+temp[2];
      ansToprint += " 0 TEMP "+temp[1];      
      ansToprint += "\n";


       int l1=updateLabel();
       int l2=updateLabel();


       for(int j=5;j<=8;j++){
          temp[j] = i++;
       }

       ansToprint += " MOVE TEMP "+temp[5]+" 4";      
       ansToprint += "\n";


       ansToprint += " L"+l1;      
       ansToprint += "\n";
       ansToprint += "MOVE TEMP "+temp[6];
       ansToprint += " TIMES TEMP "+temp[1]+" 4";      
       ansToprint += "\n";
       ansToprint += "MOVE TEMP "+temp[7];
       ansToprint += " PLUS TEMP "+temp[6]+ " 4";      
       ansToprint += "\n";
       ansToprint += "MOVE TEMP "+temp[8];
       ansToprint += " LE " + " TEMP " + temp[5] + " TEMP " + temp[7];      
       ansToprint += "\n";
       ansToprint += " CJUMP TEMP "+temp[8] + " L"+l2;      
       ansToprint += "\n";
      

    for(int j = 9;j<=10;j++){
       temp[j] = i++;
    }

    

    ansToprint += " MOVE TEMP " + temp[9];
    ansToprint += " PLUS TEMP "+temp[2]+" TEMP "+temp[5];      
    ansToprint += "\n";

  
    ansToprint += " MOVE TEMP " + temp[10] + " 0";
    ansToprint += "\n";
    ansToprint += " HSTORE TEMP " + temp[9] + " 0 TEMP "+temp[10];  

    ansToprint += "\n";
    ansToprint += " MOVE TEMP " + temp[10];
    ansToprint += " PLUS TEMP "+temp[5]+" 4";      
    ansToprint += "\n";
    ansToprint += " MOVE TEMP " + temp[5] + " TEMP " + temp[10];      
    ansToprint += "\n";
    ansToprint += " JUMP L"+l1;
    ansToprint += "\n";
    ansToprint += " L"+l2+" NOOP ";      
    ansToprint += "\n";
    ansToprint += "MOVE TEMP "+i++;
    ansToprint += " TEMP "+temp[2];      
    ansToprint += "\n";

      
      n.f4.accept(this, argu);
      return (R)"int[]";
   }


      int VariableOffsetFinding(String[] pro){
        if(answer.SymbolTable.get(pro[0]).variablesInfo.get(pro[1]) != null){
                String thop = pro[0]+"_"+pro[1];
                return answer.SymbolTableForMemory.get(pro[0]).functionInfo.get(0).get(thop);                
        }
        String[] thop = new String[2];
        thop[0] = answer.SymbolTable.get(pro[0]).parent;
        thop[1] = pro[1];
        return VariableOffsetFinding(thop);
      }



   void AllocateRecord(String a){
          newTable t = answer.SymbolTableForMemory.get(a);

           ansToprint += "MOVE TEMP ";
           ansToprint += i++;
           ansToprint += " HALLOCATE ";
           ansToprint += (t.functionInfo.get(0).size()+1)*4;
           ansToprint += "\n";


          ansToprint += " MOVE TEMP ";
          ansToprint += i;
          ansToprint += " HALLOCATE ";
          ansToprint += (t.functionInfo.get(1).size())*4;
          ansToprint += "\n";

          methodAllocation(a,t);

          variableAllocation(t);

          ansToprint += " HSTORE TEMP ";
          ansToprint += (i-1)+" 0 TEMP ";
          ansToprint += i++;
          ansToprint += "\n";

          return ;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public R visit(AllocationExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);

      String id = (String)n.f1.accept(this, argu);
      AllocateRecord(id);

      ansToprint += "MOVE TEMP "+ i++;
      ansToprint += " TEMP "+(i-3);      
      ansToprint += "\n";


      
      return (R)id;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public R visit(NotExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
    

      ansToprint += "MOVE TEMP "+ i++ + " TEMP "+(i-2);      
      ansToprint += "\n";
      ansToprint += "MOVE TEMP " + i++ + " 0";      
      ansToprint += "\n";
      ansToprint += "MOVE TEMP "+ i++;
      ansToprint += " MINUS TEMP "+(i-2)+ " TEMP " + (i-3);      
      ansToprint += "\n";


      return (R)"boolean";
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public R visit(BracketExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f2.accept(this, argu);
      return n.f1.accept(this, argu);
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( IdentifierRest() )*
    */
   public R visit(IdentifierList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public R visit(IdentifierRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

}
