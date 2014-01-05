package history;


import java.util.ArrayList;

import textGetter.*;

public class HistoryList {

	ArrayList<PetDBox> myNumSet = new ArrayList<PetDBox>();
	
	public interface OnNumberBoxChange{
		void OnNumberChange(HistoryList dual_Number);
	}
	
	private OnNumberBoxChange onNumberBoxChange;
	
	public int size(){
		return myNumSet.size();
	}
	
	public void setOnNumberBoxChange(OnNumberBoxChange onNumberBoxChange) {
		this.onNumberBoxChange = onNumberBoxChange;
	}
	
	public PetDBox getNumber(int index){
		return myNumSet.get(index);
	}
	
	public void addNumberBox(PetDBox temp){
		myNumSet.add(temp);
		NorifyOnNumChange();
	}
	public void replaceData(ArrayList<PetDBox> petData) {
		this.myNumSet = petData;
		NorifyOnNumChange();
	}
	
	private void NorifyOnNumChange(){
		if(onNumberBoxChange != null)
			onNumberBoxChange.OnNumberChange(this);
	}
	
}
