import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Customer2 {
	static File f;
	private int index;
	
	
	public Customer2(File file) {
		f=file;
		file.Read();
		file.Read_f_file();
		index=0;
	}
	
	//Register new customer
	public int Register_New_C(String n,String p,File f) {
		
		f.members_list[f.count_id]=n;
		f.password_list[f.count_id]=p;
		f.fur_count[f.count_id]=0;
		if(f.count_id==0) {
			f.id_list[f.count_id]=0;
		}
		else {
			f.id_list[f.count_id]=f.id_list[f.count_id-1]+1;
		}
		
		this.index=f.count_id;
		f.count_id++;
		f.Save_file();
		
		return this.index;
	}

	
	// get index of loyal customer
	public int Get_information(String n, String p,File f){
		
		for(int i=0;i<f.count_id;i++) {
			if(n.equals(f.members_list[i])) {
				if(p.equals(f.password_list[i])) {
						return i;
				}
			}
		}
		return -1;
	}
	
	//edit and save customer list
	public void Edit_C_Fur_List(DefaultListModel<String> listModelr,File f,int indexx) {
		
		for(int i=0;i<listModelr.size();i++) {
			f.furniture_list[indexx][i]=listModelr.get(i);
		}
		
		f.fur_count[indexx]=listModelr.size();
		f.Save_file();
	}
	
	//edit system_fur_list
	//decrease the stock of the furniture when someone rent.
	public void Rent_System_Fur_List(String fur,File f) {
		
		for(int i=0;i<f.count_num;i++) {
			if(fur.equals(f.system_fur_list[i][0])) {
				int num=Integer.valueOf(f.system_fur_list[i][1]);
				num--;
				f.system_fur_list[i][1]=String.valueOf(num);
			}
		}
		
		f.Save_f_file();
	}
	
	//edit system_fur_list
	//increase the stock of the furniture when someone rent.
	public void Return_System_Fur_List(String fur,File f) {	
		for(int i=0;i<f.count_num;i++) {
			if(fur.equals(f.system_fur_list[i][0])) {
				int num=Integer.valueOf(f.system_fur_list[i][1]);
				num++;
				f.system_fur_list[i][1]=String.valueOf(num);
			}
		}
		
		f.Save_f_file();
	}
	
	
	// secession = delete the customer from lists 
	public void Secession(int id,File f) {
		f.members_list[id]="";
		f.password_list[id]="";
		f.id_list[id]=0;
		for(int i=0;i<f.fur_count[id];i++) {
			f.furniture_list[id][i]="";
		}
		f.fur_count[id]=0;
		if(f.count_id > id+1) {
			f.id_list[id]=f.id_list[id+1];
			f.members_list[id]=f.members_list[id+1];
			f.password_list[id]=f.password_list[id+1];
			for(int i=0;i<f.fur_count[id+1];i++) {
				f.furniture_list[id][i]=f.furniture_list[id+1][i];
			}
			f.fur_count[id]=f.fur_count[id+1];
			
			for(int j=id+1;j<f.count_id-1;j++) {
				f.id_list[j]=f.id_list[j+1];
				f.members_list[j]=f.members_list[j+1];
				f.password_list[j]=f.password_list[j+1];
				for(int i=0;i<f.fur_count[id+1];i++) {
					f.furniture_list[j][i]=f.furniture_list[j+1][i];
				}
				f.fur_count[j]=f.fur_count[j+1];
				f.id_list[j]=0;
				f.members_list[j+1]="";
				f.password_list[j+1]="";
				f.fur_count[j+1]=0;
			}
		}	
		f.count_id--;
		f.Save_file();
	}
}
