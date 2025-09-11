import {SimpleTreeView, TreeItem} from '@mui/x-tree-view'
import type { TreeItemType } from '../../utils/TypesList'
export default function CusTreeView({Data}:props){
    
    return (
    <SimpleTreeView>
        {Data?.map((branch)=>RenderLeaf(branch))}
    </SimpleTreeView>
    )
}

function RenderLeaf(node:TreeItemType){
    return (<TreeItem key={node.id} itemId={node.id.toString()} label={node.label}>
        {Array.isArray(node.childern)?node.childern.map((leaf)=>RenderLeaf(leaf)):null}
    </TreeItem>)
}

type props = {
    Data:TreeItemType[]
}