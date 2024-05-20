import java.util.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;

public class File {
	static int first=0;
	static int count_id=0;//total_members_number
	static String[] members_list=new String[10001];
	static String[] password_list=new String[10001];
	static int[] id_list=new int[10001];
	static String[][] furniture_list=new String[10001][10];//10 things per one term
	static int[] fur_count=new int[10001];//the number of furniture that each person rent
	static int io=0;//flag
	
	static String[][] system_fur_list=new String[1000][2];
	static int count_num=0;//total_system_fur_number
	static int f_first=0;
	static int iof=0;
	
	public void Read(){
		//from file to mem_list
		
		try {
			List<String> list=Files.readAllLines(Paths.get("customer_list.txt"),Charset.defaultCharset());
			
		}catch(IOException e) {
			// for there was no_file
			io++;
		}
		try {
			if(io==0) {
				List<String> list=Files.readAllLines(Paths.get("customer_list.txt"),Charset.defaultCharset());
				for(Iterator<String> line=list.iterator();line.hasNext();) {
					String l=line.next();
					String[] inputs=l.split("\\s+");
					
					//run at only the first time
					if(first==0 & inputs.length!=0) {
						id_list[count_id]=Integer.valueOf(inputs[0]);
						members_list[count_id]=inputs[1];
						password_list[count_id]=inputs[2];
						
						fur_count[count_id]=0;
						for(int i=3;i<inputs.length;i++) {
							String s=inputs[i].substring(0,inputs[i].length());
							furniture_list[count_id][i-3]=s;
							fur_count[count_id]++;
						}
						
						count_id++;
					}
				}
				if(first==0) {
					first++;
				}
			}
		}catch(IOException e) {
			System.out.println("There was an file Error.");
		}
	}
	
	
	public void Save_file() {
		
		try {
			BufferedWriter output=Files.newBufferedWriter(Paths.get("customer_list.txt"), Charset.defaultCharset());
			
			String l="";
			for(int i=0;i<count_id;i++) {		
				l=id_list[i]+" "+members_list[i]+" "+password_list[i]+" ";
				for(int k=0;k<fur_count[i];k++) {
						l+=furniture_list[i][k]+" ";
				}
				output.write(l);
				output.newLine();
			}			
			output.close();	
		}
		catch(IOException e) {
			System.out.println("There was an file Error.");
		}	
	}
	
	public void Read_f_file(){
		
		try {
			List<String> list=Files.readAllLines(Paths.get("System_fur_list.txt"),Charset.defaultCharset());
		}catch(IOException e) {
			// for there was no_file
			iof++;
			Initialize_system_f_list();
		}
		
		//from file to system_fur_list
		try {
			if(iof==0) {
				int l_c=0;
				List<String> list=Files.readAllLines(Paths.get("System_fur_list.txt"),Charset.defaultCharset());
				for(Iterator<String> line=list.iterator();line.hasNext();) {
					l_c++;
					String l=line.next();
					String[] inputs=l.split("\\s+");
					
					//run at only the first time
					if(f_first==0 & inputs.length!=0) {
						system_fur_list[count_num][0]=inputs[0];
						system_fur_list[count_num][1]=inputs[1];
						count_num++;
					}
				}
				if(l_c==0) {
					Initialize_system_f_list();
				}
				if(f_first==0) {
					f_first++;
				}
			}
		}
		catch(IOException e) {
			System.out.println("There was an file Error.");
		}
	}
	
	public void Save_f_file() {
		try {	
			BufferedWriter output=Files.newBufferedWriter(Paths.get("System_fur_list.txt"), Charset.defaultCharset());
			
			String l="";
			for(int i=0;i<count_num;i++) {		
				l=system_fur_list[i][0]+"  "+system_fur_list[i][1];
				output.write(l);
				output.newLine();
			}			
			output.close();	
			
		}
		catch(IOException e) {
			System.out.println("There was an file Error.");
		}	
	}
	
	public void Initialize_system_f_list() {
		system_fur_list[0][0] = "Table";
		system_fur_list[1][0] = "Chair";
		system_fur_list[2][0] = "Desk";
		system_fur_list[3][0] = "Shelve";
		system_fur_list[4][0] = "Kushion";
		system_fur_list[5][0] = "Sofa";
		system_fur_list[6][0] = "Bed";
		system_fur_list[7][0] = "Small_Chair";
		system_fur_list[8][0] = "Refrigerator";
		system_fur_list[9][0] = "Air_Conditioner";
		system_fur_list[10][0] = "Sweeper";
		system_fur_list[11][0] = "Auto_Sweeper";
		count_num=12;
		for(int i=0;i<count_num;i++) {
			system_fur_list[i][1]="15";
		}
		
		Save_f_file();
	}
	
	
	
}
