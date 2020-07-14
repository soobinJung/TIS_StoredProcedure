package java_sql_sp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;


public class DBGuestTis { 
		Connection CN=null;//DB���������� user/pwd���, CN�����ؼ� ��ɾ����
		Statement ST=null;//�����θ�ɾ� ST=CN.createStatement(X);
		PreparedStatement PST=null; //�����θ�ɾ� PST=CN.prepareStatememt(msg)
		CallableStatement CST=null; //storedprocedure����
		ResultSet RS=null;//RS=ST.executeQuery("select~") ; ��ȸ����� RS���
		String msg="" ; 
		int Gtotal=0; //��ü���ڵ尹��
		Scanner sc = new Scanner(System.in);
	
	 public DBGuestTis() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //����̺�ε�
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("����Ŭ���Ἲ��success ������");
	     ST=CN.createStatement();
		 }catch (Exception e) {	}
	 }//������
	 
	public static void main(String[] args) {
		DBGuestTis gg = new DBGuestTis();
				
		Scanner scin = new Scanner(System.in);
		while(true) {
			System.out.print("\nsp 1���  2��ü���  3���� 4.������� 5.Ÿ��Ʋ���� 6.��ü���    9����>>> ");
			int sel=scin.nextInt();
			if(sel==1){gg.guestInsert();}
			else if(sel==2){gg.guestSelectAll(); }
			else if(sel==3){gg.guestEdit();      }
			else if(sel==4) {gg.GuestUpdate2();  }
			else if(sel==5) {gg.GuestUpdate3();  }
			else if(sel==6) {gg.GuestUpdate4();  }
			else if(sel==9){ gg.myexit(); break; }
		}
		scin.close();
	}//main end
	
	public void guestEdit( ) {//�ѰǼ��� SPó�� 
		try {
			
		}catch (Exception e) { System.out.println("��������");}
	} //end

	public void guestSelectAll( ) {//��ü���
		try {
		msg="select * from guest  order by sabun" ;
		RS = ST.executeQuery(msg);
		while(RS.next()==true) {
			int s = RS.getInt("sabun");
			String n = RS.getString("name");
			String t = RS.getString("title");
			int p = RS.getInt("pay");
			String e = RS.getString("email");
		  System.out.println(s+"\t"+n+"\t"+t+"\t"+p+"\t"+e);
		}
		System.out.println("=============================================");
		}catch (Exception e) { System.out.println("��ü��ȸ����");}
	}//end--------------------
	

	
	public void myexit() {
		System.out.println("7-13-������ ���α׷��� �����մϴ�");
		System.exit(1);
	}//end--------------------
	
	public void GuestUpdate2() {
	      try {        
	         
	         System.out.println("�������� ������ ����� �Է����ּ���.");
	         while(true) {
	             System.out.print("������ ��� >> ");    int sabun_BEFORE = Integer.parseInt(sc.next());
	             System.out.print("������ ��� >> ");    int sabun_AFTER = Integer.parseInt(sc.next());
	               
	             CST=CN.prepareCall("{call GUEST_SP_UPDATE_SABUN(?, ?)}");
	     
	             CST.setInt(1, sabun_BEFORE);
	             CST.setInt(2, sabun_AFTER);
	             CST.executeUpdate();
	                  System.out.println("Stored Procedure ���� �Ǿ����ϴ�.");
	               System.out.println("==================================================");
	               break;
	            }
	      } catch (Exception e) {
	    	  System.out.println("Stored Procedure ���� ����" + e.toString());
	    	  }
	  }
	
	public void GuestUpdate3() {
		try {
			System.out.println("������ ����� �ٲٰ����ϴ� TITLE�� �Է��ϼ���.");
			System.out.print("��� >> "); int subun = Integer.parseInt(sc.next()) ;
			System.out.print("TITLE >> "); String title =sc.next();
			
			CST = CN.prepareCall("{call GUEST_SP_UPDATE_TITLE(?,?)}");
			CST.setInt(1, subun );
			CST.setString(2, title );
			CST.executeUpdate();
				System.out.println("Store Procedure �����Ǿ����ϴ�." );
			System.out.println();
		} catch (Exception e) {
			 System.out.println("Stored Procedure ���� ����" + e.toString());
		}
	}
	
	public void GuestUpdate4() {
		try {
			CST = CN.prepareCall("{call guest_sp_select(?)}");
			CST.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			CST.executeUpdate();
			
			RS = (ResultSet)CST.getObject(1);
			
			while(RS.next()==true) {
				int s = RS.getInt("sabun");
				String n = RS.getString("name");
				String t = RS.getString("title");
				java.util.Date dt  = RS.getDate("wdate");
				int p = RS.getInt("pay");
				int h = RS.getInt("hit");
				String e = RS.getString("email");
				System.out.println( s + "\t" + n + "\t" + t  + "\t" + p  + "\t" + e );
			}
		} catch ( Exception e) {
			System.out.println(e);
		}
	}
	
	public void guestInsert() {
		try{
			System.out.print("sp����Է�>>"); 
			int s=Integer.parseInt(sc.nextLine()) ;
			
			System.out.print("sp�̸��Է�>>");	String n=sc.nextLine(); 
			System.out.print("sp�����Է�>>");	String t=sc.nextLine();
			System.out.print("sp�޿��Է�>>"); 
			int p=Integer.parseInt(sc.nextLine()) ;
			
			int h=3;  //��ȸ�� 
			System.out.print("sp�����Է�>>"); 	String e=sc.nextLine();
			
		 
		}catch(Exception ex) { System.out.println(ex+" SP�������"); }
	}// guestInsert()  end--------------------
	

	
}//class END





