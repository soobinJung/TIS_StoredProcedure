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
		Connection CN=null;//DB서버정보및 user/pwd기억, CN참조해서 명령어생성
		Statement ST=null;//정적인명령어 ST=CN.createStatement(X);
		PreparedStatement PST=null; //동적인명령어 PST=CN.prepareStatememt(msg)
		CallableStatement CST=null; //storedprocedure연결
		ResultSet RS=null;//RS=ST.executeQuery("select~") ; 조회결과를 RS기억
		String msg="" ; 
		int Gtotal=0; //전체레코드갯수
		Scanner sc = new Scanner(System.in);
	
	 public DBGuestTis() {
		 try{
	     Class.forName("oracle.jdbc.driver.OracleDriver"); //드라이브로드
	     String url="jdbc:oracle:thin:@127.0.0.1:1521:XE" ;
	     CN=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE","system","1234");
	     System.out.println("오라클연결성공success 월요일");
	     ST=CN.createStatement();
		 }catch (Exception e) {	}
	 }//생성자
	 
	public static void main(String[] args) {
		DBGuestTis gg = new DBGuestTis();
				
		Scanner scin = new Scanner(System.in);
		while(true) {
			System.out.print("\nsp 1등록  2전체출력  3수정 4.사번수정 5.타이틀수정 6.전체출력    9종료>>> ");
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
	
	public void guestEdit( ) {//한건수정 SP처리 
		try {
			
		}catch (Exception e) { System.out.println("수정에러");}
	} //end

	public void guestSelectAll( ) {//전체출력
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
		}catch (Exception e) { System.out.println("전체조회에러");}
	}//end--------------------
	

	
	public void myexit() {
		System.out.println("7-13-월요일 프로그램을 종료합니다");
		System.exit(1);
	}//end--------------------
	
	public void GuestUpdate2() {
	      try {        
	         
	         System.out.println("수정전과 수정후 사번을 입력해주세요.");
	         while(true) {
	             System.out.print("수정전 사번 >> ");    int sabun_BEFORE = Integer.parseInt(sc.next());
	             System.out.print("수정후 사번 >> ");    int sabun_AFTER = Integer.parseInt(sc.next());
	               
	             CST=CN.prepareCall("{call GUEST_SP_UPDATE_SABUN(?, ?)}");
	     
	             CST.setInt(1, sabun_BEFORE);
	             CST.setInt(2, sabun_AFTER);
	             CST.executeUpdate();
	                  System.out.println("Stored Procedure 수정 되었습니다.");
	               System.out.println("==================================================");
	               break;
	            }
	      } catch (Exception e) {
	    	  System.out.println("Stored Procedure 수정 실패" + e.toString());
	    	  }
	  }
	
	public void GuestUpdate3() {
		try {
			System.out.println("수정전 사번과 바꾸고자하는 TITLE을 입력하세요.");
			System.out.print("사번 >> "); int subun = Integer.parseInt(sc.next()) ;
			System.out.print("TITLE >> "); String title =sc.next();
			
			CST = CN.prepareCall("{call GUEST_SP_UPDATE_TITLE(?,?)}");
			CST.setInt(1, subun );
			CST.setString(2, title );
			CST.executeUpdate();
				System.out.println("Store Procedure 수정되었습니다." );
			System.out.println();
		} catch (Exception e) {
			 System.out.println("Stored Procedure 수정 실패" + e.toString());
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
			System.out.print("sp사번입력>>"); 
			int s=Integer.parseInt(sc.nextLine()) ;
			
			System.out.print("sp이름입력>>");	String n=sc.nextLine(); 
			System.out.print("sp제목입력>>");	String t=sc.nextLine();
			System.out.print("sp급여입력>>"); 
			int p=Integer.parseInt(sc.nextLine()) ;
			
			int h=3;  //조회수 
			System.out.print("sp메일입력>>"); 	String e=sc.nextLine();
			
		 
		}catch(Exception ex) { System.out.println(ex+" SP저장실패"); }
	}// guestInsert()  end--------------------
	

	
}//class END





