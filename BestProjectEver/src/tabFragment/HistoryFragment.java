package tabFragment;


import historyDatabase.HistoryAdapter;
import historyDatabase.HistoryBox;
import historyDatabase.HistoryDatabase;
//import historyDatabase.HistoryDatabase.OnNumberBoxChange;


import com.projnsc.bestprojectever.HistoryDetail;
import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryFragment extends Fragment implements OnItemClickListener,
historyDatabase.HistoryDatabase.OnNumberBoxChange {

View mView;
// private Button btnWrite;
// private Button btnUpdate;
// private EditText inputPath;

private ListView myListView;
//private HistoryList myDual;
// private Button button;
private HistoryAdapter myAdapter;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
	Bundle savedInstanceState) {
mView = inflater.inflate(R.layout.activity_history, container, false);

// setContentView(R.layout.activity_history);

HistoryDatabase.hd.setOnNumberBoxChange(this);
myListView = (ListView) mView.findViewById(R.id.testList);
// btnWrite = (Button) mView.findViewById(R.id.btnWrite);
// btnUpdate = (Button) mView.findViewById(R.id.btnUpdate);
// inputPath = (EditText) mView.findViewById(R.id.inputPath);

/*
 * btnWrite.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) { RandomNumBox(); } });
 * btnUpdate.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) { fetchAndUpdate();
 * 
 * } });
 */

myAdapter = new HistoryAdapter(getActivity()) {

	@Override
	public Object getItem(int position) {
		return HistoryDatabase.historyList.get(position);
	}

	@Override
	public int getCount() {
			return HistoryDatabase.getDataSize();
	}

};

myListView.setAdapter(myAdapter);
myListView.setOnItemClickListener(this);
HistoryDatabase.SelectAllData();
return mView;
}

	/*
	 * private void RandomNumBox(){ String stringData =
	 * String.valueOf(inputPath.
	 * getText())+",12.5,18.8,KaomunKai,30,10,15,20,11,12,2013,23,00";
	 * PetDataGet.Write(stringData); fetchAndUpdate();
	 * 
	 * }
	 */

	@Override
	public void OnNumberChange(HistoryDatabase dual_Number) {
		// TODO Auto-generated method stub
		myAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		// Toast.makeText(getActivity(), position+"",
		// Toast.LENGTH_SHORT).show();
		Intent inend = new Intent(getActivity(), HistoryDetail.class);
		HistoryBox box =  HistoryDatabase.getDataBox(position);
		inend.putExtra("imagePath", box.getPicturePath());
		inend.putExtra("index", position);
		startActivity(inend);

	}

}
