import {TableHeaderType,TableRowType,TableType,TableCellType} from "../../utils/TypesList.ts"
import {useState,useEffect,useRef,React} from "react"
export function TableHeaders({ColumnNames,className}:TableHeaderType){
return (
  <div className={`flex flex-row w-fit border-grayscele border-1
    ${ColumnNames===undefined || ColumnNames===null ?"hidden":""} 
    ${className}`}>

  {ColumnNames && ColumnNames.map((name,index)=>{ 
    const lastIndex:number = ColumnNames.length-1;
    const isLast:string = lastIndex===index?"flex-1":"border-r-1";
    return (
     <div key={index} className={`p-2 hover:bg-grayscale/10 w-50 ${isLast}`}>{name}</div>
    )
  }
   )}
  </div>
)
}

export function TableRow({RowValues,className}:TableRowType){
return (
  <div className={`flex flex-row border-grayscele border-1 ${className}`}>
    
  {RowValues && RowValues.map((name,index)=>{
    const lastIndex:number = RowValues.length-1;
    const isLast:string = lastIndex===index?"flex-1":"border-r-1";
    return(
      <div key={index} className={`p-2 hover:bg-grayscale/10 ${isLast}`}>{name}</div>
    )
  })}
  </div>
)
}

export function Table({ColumnNames,RowValues,className}:TableType){
  const [colLength,setColLength] = useState(0);
  const [rowLength,setRowLength] = useState(0);
  const [colWidth,setColWidth] = useState<number[]>([]);
  const [rowHeight,setRowHeight] = useState<number[]>([]);

  useEffect(()=>{
    if(ColumnNames!==undefined){
      setColLength(ColumnNames.length)

    }
    if(RowValues!==undefined){
      setRowLength(RowValues.length)
    }
  },[ColumnNames,RowValues])

  return (
    <div className={`flex flex-col`}>
     <div className={`flex flex-row`}>
     {ColumnNames.map((item,index)=>{return (<TableCell rowID={0} colID={index} key={index} lastColID={colLength} lastRowID={rowLength} value={item}/>)})}     
     </div>
    </div>
  );
}

export function TableCell({rowID,colID,lastRowID,lastColID,value,width=100,height=100,className}:TableCellType){
const [insideWidth,setInsideWidth] = useState<number>(width);
const [insideHeight,setInsideHeight] = useState<number>(height);
const [startPos,setStartPos] = useState<{x:number,y:number}>({x:0,y:0});
const [endPos,setEndPos] = useState<{x:number,y:number}>({x:0,y:0});
const [isDragging,setIsDragging] = useState<boolean>(false);
useEffect(()=>{
console.log(insideHeight,insideWidth);

},[insideWidth,insideHeight])

const handleMouseDown = (e:React.MouseEvent) =>{  
  setIsDragging(true);
  window.addEventListener("mousemove",handleMouseMove as any);
  window.addEventListener("mouseup",handleMouseUp as any);
}
const handleMouseUp = (e:React.MouseEvent)=>{
  setIsDragging(false);
  window.removeEventListener("mousemove",handleMouseMove as any);
  window.removeEventListener("mouseup",handleMouseUp as any);
}
const handleMouseMove = (e:React.MouseEvent)=>{
  if(isDragging){
    console.log("GGG");
  }
 }

useEffect(()=>{
 setInsideWidth(insideWidth+(endPos.x-startPos.x));
},[endPos])

  return (
    <div className={`relative px-3 py-2 ${className}`} style={{width:insideWidth,height}}>
    {/*Top Border*/}
      <div onMouseDown={handleMouseDown} className={`absolute top-0 left-0 right-0 h-1 bg-grayscale ${rowID===0?"":"hover:cursor-row-resize"}`}></div>
{/*Bottom Border*/}
      <div onMouseDown={handleMouseDown} className={`absolute bottom-0 left-0 right-0 h-1 bg-grayscale ${lastRowID===rowID?"":"hover:cursor-row-resize"}`}></div>
 {/*Left Border*/} 
      <div onMouseDown={handleMouseDown} className={`absolute top-0 bottom-0 left-0 w-1 bg-grayscale ${colID===0?"":"hover:cursor-col-resize"}`}></div>
{/*Right Border*/}
      <div onMouseDown={handleMouseDown} className={`absolute top-0 right-0 bottom-0 w-1 bg-grayscale ${lastColID-1===colID?"":"hover:cursor-col-resize"}`}></div>
      <div className={`overflow-auto scrollbar-hide select-none`}>{value}</div>
    </div>
  )
}
