import {createSlice,PayloadAction} from "@reduxjs/toolkit";
import type {LocationType} from "../../TypesList"
export interface InventoryStates{
  AllLocations:LocationType[],
  BigTree:LocationType[],
  AddNewLocPopUpVisible:boolean,
}

export const initialState:InventoryStates = {
  AllLocations:[],
  BigTree:[],
  AddNewLocPopUpVisible:false,
}
export const InventorySlice = createSlice({
  name:"InventorySlice",
  initialState,
  reducers:{
    setAllLocations(state,action:PayloadAction<LocationType[]>){
      state.AllLocations = action.payload;
    },
    setAddNewLocPopUpVisible(state,action:PayloadAction<boolean>){
      state.AddNewLocPopUpVisible = action.payload;
    },
    setBigTree(state,action:PayloadAction<LocationType[]>){
      state.BigTree = action.payload;
    }
  }
})

export const {
  setAllLocations,
  setAddNewLocPopUpVisible,
  setBigTree
} = InventorySlice.actions;

export default InventorySlice;
