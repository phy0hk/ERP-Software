import {SimpleTreeView, TreeItem} from '@mui/x-tree-view'
import type { LocationType } from '../../utils/TypesList'
import { cabinetFiling } from '@lucide/lab';
import {useState} from "react"
import { BetweenHorizontalStart, ChevronDown, ChevronUp, Container, Dot, Icon, Warehouse } from 'lucide-react'
export default function CusTreeView({Data}:props){
    const [Selected,setSelected] = useState<any|null>(null);  
  const handleOnSelect = (event:React.SyntheticEvent,nodeId) => {
    console.log("Current node is "+nodeId);
    setSelected(nodeId);
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

function CustomTreeItem({type,name}:LocationType){
    return(
        <div className='flex flex-row gap-2 items-center'>
            {IconSelector(type)}<span>{name}</span>
        </div>
    )
}

type props = {
    Data:LocationType[]
}
