package com.example.ox;

import java.util.Arrays;

public class ModelOX {

	private int[] OXTable = new int[9];
	private int Player = 0;
	private int PressedID = 0;
	
	ModelOX(){
		Arrays.fill(OXTable, 0);
	}
	
	public int getPressedID() {
		return PressedID;
	}

	interface OnOXChangeListener{
		void OnOXChange(ModelOX modelOX);
	}
	
	private OnOXChangeListener onOXChangeListener;

	public void setOXTable(int index){
		int Add;
		PressedID = index;
		if(OXTable[index]==0){
			if(Player==0){
				Add = 1;
			}else{
				Add = -1;
			}
			OXTable[index]=Add;
			ChangeTurn();
		}	
	}
	
	

	public int getOXindex(int index){
		return OXTable[index];
	}
	
	private void ChangeTurn(){
		if(Player==0)
			Player++;
		else
			Player=0;
		if(onOXChangeListener!=null)
			this.onOXChangeListener.OnOXChange(this);
	}
	
	public void setOnOXChangeListener(OnOXChangeListener onOXChangeListener) {
		this.onOXChangeListener = onOXChangeListener;
	}

	public int[] getOXTable(){
		return OXTable;
	}
	
	public void clear() {
		Arrays.fill(OXTable, 0);
		Player=0;
		if(onOXChangeListener!=null)
			this.onOXChangeListener.OnOXChange(this);
	}

	public char getRealPlayer() {
		// TODO Auto-generated method stub
		return (Player==0)? 'X' : 'O';
	}

	public void setDisable() {
		for(int i=0; i<OXTable.length; i++){
			if(OXTable[i]!=0||OXTable[i]!=1||OXTable[i]!=-1)
				OXTable[i]=' '; 
		}
	}
	
	
	
}
