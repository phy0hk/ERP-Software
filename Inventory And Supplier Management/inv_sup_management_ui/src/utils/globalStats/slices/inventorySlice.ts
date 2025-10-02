import {createSlice,PayloadAction} from "@reduxjs/toolkit";
import type {LocationType} from "../../TypesList"
export interface InventoryStates{
  AllLocations:LocationType[],
  AddNewLocPopUpVisible:boolean,
}

export const initialState:InventoryStates = {
  AllLocations:[],
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
      stae.AddNewLocPopUpVisible = action.payload;
    }
  }
})

export const {
  setAllLocations,
  setAddNewLocPopUpVisible
} = InventorySlice.actions;

export default InventorySlice.reducer;
