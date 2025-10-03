import {configureStore} from "@reduxjs/toolkit"
import {InventorySlice} from "./slices/inventorySlice"
export const store = configureStore({
  reducer:{
    inventory:InventorySlice.reducer
  }
})
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
