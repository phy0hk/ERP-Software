import {SimpleTreeView, TreeItem} from '@mui/x-tree-view'
import type { LocationType } from '../../utils/TypesList'
import { cabinetFiling } from '@lucide/lab';
import {useState} from "react"
import {useDispatch,useSelector} from "react-redux"
import type {RootState} from "../../utils/globalStats/store"
import {setAddNewLocPopUpVisible} from "../../utils/globalStats/slices/inventorySlice" 
import { BetweenHorizontalStart, ChevronDown, ChevronUp, Container, Dot, Icon, Warehouse,Plus} from 'lucide-react'
import {useEffect} from "react"
export default function CusTreeView({Data,onSelect}:props){
  const handleOnSelect = (event:React.SyntheticEvent,nodeId) => {
    if(onSelect!==undefined){
      onSelect(nodeId);      
    }
  }
    return (
    <SimpleTreeView onItemClick={handleOnSelect}>
        {Data?.map((branch)=>RenderLeaf(branch))}
    </SimpleTreeView>
    )
}

function RenderLeaf(node:LocationType){
  
  return (
    <TreeItem  slots={{expandIcon:ChevronDown,collapseIcon:ChevronUp}} key={node.id} itemId={node.id.toString()} label={CustomTreeItem(node)}>
        {Array.isArray(node.children)?node.children.map((leaf)=>RenderLeaf(leaf)):null}
        </TreeItem>)
}

function IconSelector(type:string|undefined){
    switch (type) {
        case "Warehouse":
            return <Warehouse size={15}/>;
        case "Floor":
            return <BetweenHorizontalStart size={15}/>;
        case "Cabinet":
            return <Icon iconNode={cabinetFiling} size={15}/>;
        default:

            break;
    }
}

function CustomTreeItem(location:LocationType){
  const dispatch = useDispatch();
  const handleOnClick = () =>{
    dispatch(setAddNewLocPopUpVisible(true));
  }  

  return(
        <div className='flex flex-row gap-2 items-center group justify-between'>
            <div className="flex flex-row items-center">{IconSelector(location.type)}<span>{location.name}</span></div>
            <Plus className="hidden group-hover:inline-block rounded hover:bg-black/10" onClick={handleOnClick} />
        </div>
    )
}

type props = {
    Data:LocationType[];
    onSelect:any|undefined;
}
