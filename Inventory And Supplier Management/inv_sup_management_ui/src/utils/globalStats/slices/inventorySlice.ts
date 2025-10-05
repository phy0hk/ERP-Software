import {createSlice,PayloadAction} from "@reduxjs/toolkit";
import type {LocationType} from "../../TypesList"
export interface InventoryStates{
  AllLocations:LocationType[],
  BigTree:LocationType[],
  AddNewLocPopUp:{
    isOpen:boolean,
    selectedItem?:LocationType
  },
}

export const initialState:InventoryStates = {
  AllLocations:[],
  BigTree:[],
  AddNewLocPopUp:{
    isOpen:false,
    selectedItem:{id:0,name:"None"}
  },
}
export const InventorySlice = createSlice({
  name:"InventorySlice",
  initialState,
  reducers:{
    setAllLocations(state,action:PayloadAction<LocationType[]>){
      state.AllLocations = action.payload;
    },
    setAddNewLocPopUp(state,action:PayloadAction<InventoryStates.AddNewLocPopUp>){
      state.AddNewLocPopUp = action.payload;
    },
    setBigTree(state,action:PayloadAction<LocationType[]>){
      state.BigTree = action.payload;
    }
  }
})

export const {
  setAllLocations,
  setAddNewLocPopUp,
  setBigTree
} = InventorySlice.actions;

export default InventorySlice;
