import javax.swing.DefaultListModel;

public class Staff2 {
	static File f;
	
	public Staff2(File file) {
		f=file;
		file.Read();
		file.Read_f_file();
	}
	
	//edit customer_list
	public void Edit_F_C_List(DefaultListModel<String> listModel,File f) {
		
		for(int i=0;i<listModel.size();i++) {
			String s=listModel.get(i);
			String[] ss=s.split("\\s+");
			f.id_list[i]=Integer.valueOf(ss[0]);
			f.members_list[i]=ss[1];
			f.password_list[i]=ss[2];
		}
		f.count_id=listModel.size();
		f.Save_file();
	}
	
	//edit customer's fur_list
	public void Edit_F_C_List_from_fur_name(String fur,int id,File f) {
		int index=0;
		for(int i=0;i<f.fur_count[id];i++) {
			if(f.furniture_list[id][i].equals(fur)) {
				f.furniture_list[id][i]="";
				index=i;
				break;
			}
		}
		
		if(index!=f.fur_count[id]-1 && f.furniture_list[id][index+1].length()!=0) {
			f.furniture_list[id][index]=f.furniture_list[id][index+1];
			for(int j=index+1;j<f.fur_count[id]-1;j++) {
				f.furniture_list[id][j]=f.furniture_list[id][j+1];
			}
		}
		f.fur_count[id]--;
		
		for(int i=0;i<f.count_num;i++) {
			if(fur.equals(f.system_fur_list[i][0])) {
				int num=Integer.valueOf(f.system_fur_list[i][1]);
				num++;
				f.system_fur_list[i][1]=String.valueOf(num);
			}
		}
		f.Save_file();
		f.Save_f_file();
	}
	
	//edit system's furniture_list
	public void Edit_F_Fur_List(DefaultListModel<String> listModelr,File f) {	
		for(int i=0;i<listModelr.size();i++) {
			String s=listModelr.get(i);
			String[] ss=s.split("\\s+");
			f.system_fur_list[i][0]=ss[0];
			f.system_fur_list[i][1]=ss[1];
		}
		f.count_num=listModelr.size();
		f.Save_f_file();
	}
}
