package Web;
//https://mdago.tistory.com/2
// good

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import Student.StudentInfo;

public class StatusManager {

	public static void main(String[] args) {
		try { 
			Scanner s=new Scanner(System.in);
			int n,i,j=1,p;
			while(j>0) {
				for(i=0;i<StudentInfo.ClassNumber;i++)
					System.out.print(i+1+": "+StudentInfo.Class[i]+"  ");
				System.out.print("\n0 to end: ");
				n=s.nextInt()-1;
				System.out.print("C?: 0-N 1-Y ");
				p=s.nextInt();
				if(n<0)break;
				for(i=0;i<StudentInfo.StudentNumber[n];i++)
					Desktop.getDesktop().browse(new URI("https://www.acmicpc.net/status?problem_id=&user_id="+StudentInfo.ID_LIST[n][i]+"&language_id=-1&result_id="+(p>0?4:-1)));
			}
		}
		catch (IOException e) { 
			e.printStackTrace(); 
		} 
		catch (URISyntaxException e) { 
			e.printStackTrace(); 
		}

	}

}