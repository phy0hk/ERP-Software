import {TableType,TableCellType} from "../../utils/TypesList.ts"
import {useState,useEffect,useRef,React} from "react"

export function Table({ColumnNames,RowValues,className,TableWidth=1000,rowHeight,colResize=false,rowResize=false}:TableType){
  const [colLength,setColLength] = useState(0);
  const [rowLength,setRowLength] = useState(0);
  const [colWidths,setColWidths] = useState<number[]>([]);
  const [rowHeights,setRowHeights] = useState<number[]>([40]);
  
  useEffect(()=>{
    if(ColumnNames!==undefined){
      setColLength(ColumnNames.length)
    }
    if(RowValues!==undefined){
      setRowLength(RowValues.length)
    }
  },[ColumnNames,RowValues])
  useEffect(()=>{
    if(ColumnNames){
      setColWidths(new Array(ColumnNames.length).fill(TableWidth/ColumnNames.length))
    }
  },[ColumnNames])
  useEffect(()=>{
    if(RowValues){
      setRowHeights(new Array(RowValues.length+1).fill(rowHeight | 40))
    }
  },[RowValues])

  const handleWidthChange = (changedWidth:number,colID:number)=>{
    setColWidths((prev)=>{
      const temp = [...prev];
      const newWidth = temp[colID]+changedWidth;
      const neightborWidth = temp[colID+1]-changedWidth;
      if(neightborWidth>=60 && newWidth>=60){
        temp[colID] = newWidth;
        temp[colID+1] = neightborWidth;
      }      
      return temp;
    })
  }

  const handleHeightChange = (changedHeight:number,rowID:number)=>{
    setRowHeights((prev)=>{
      const temp = [...prev];
      const newHeight = temp[rowID]+changedHeight;
      const neightborHeight = temp[rowID+1]-changedHeight;
      if(newHeight>=20 && neightborHeight>=20){
      temp[rowID] = newHeight;
      temp[rowID+1] = neightborHeight;
      }
      return temp;
    })
  }

  return (
    <div className={`flex flex-col ${className}`}>
     <div className={`flex flex-row w`}>
     {ColumnNames && ColumnNames.map((item,index)=>{
       return (<TableCell rowID={0} colID={index} key={index} className={"font-semibold"} width={colWidths[index]} colResize={colResize} height={40} lastColID={colLength} onWidthChange={(changed)=>handleWidthChange(changed,index)} lastRowID={rowLength} value={item}/>)
     })}
     </div>
     {
       RowValues && RowValues.map((Row,RowIndex)=>{
return (<div className={`flex flex-row`} key={RowIndex}>
        {new Array(colLength).fill(0).map((val,ColIndex)=>{
          const item = RowValues[RowIndex][ColIndex];
  return (<TableCell rowID={RowIndex+1} colID={ColIndex} key={ColIndex}
          width={colWidths[ColIndex]} rowResize={rowResize} 
          onHeightChange={(changedHeight)=>handleHeightChange(changedHeight,RowIndex+1)} 
          height={rowHeights[RowIndex+1]} lastColID={colLength} 
          lastRowID={rowLength} value={item}/>)
})}</div>)
       })
     }
    </div>
  );
}

export function TableCell({rowID,colID,lastRowID,lastColID,value,width=150,height=50,className,onWidthChange,onHeightChange,colResize=false,rowResize=false}:TableCellType){
// const [insideWidth,setInsideWidth] = useState<number>(width);
// const [insideHeight,setInsideHeight] = useState<number>(height);
const startPos = useRef<{x:number,y:number}>({x:0,y:0});
const DragSide= useRef<string|null>(null);

// useEffect(()=>{
// console.log("Height "+insideHeight,"Width"+insideWidth);
//
// },[insideWidth,insideHeight])

const handleMouseDown = (e:React.MouseEvent,Side:string) =>{  
  startPos.current = {x:e.clientX,y:e.clientY}
  DragSide.current = Side
  window.addEventListener("mousemove",handleMouseMove);
  window.addEventListener("mouseup",handleMouseUp);
}

const handleMouseUp = (e:React.MouseEvent)=>{
  DragSide.current = null
  window.removeEventListener("mousemove",handleMouseMove);
  window.removeEventListener("mouseup",handleMouseUp);
}

const handleMouseMove = (e:React.MouseEvent)=>{
  
  if(DragSide===null) return;
const changedPosX = e.clientX - startPos.current.x;
const changedPosY = e.clientY - startPos.current.y;

startPos.current = {x:e.clientX,y:e.clientY}
updatePos(changedPosX,changedPosY)
}

function updatePos(changedPosX:number,changedPosY:number){
if(DragSide.current==="Right" && colID!==lastColID-1 && colResize){
widthChange(changedPosX)
}else if(DragSide.current==="Left" && colID!==0 && colResize){ 
widthChange(-changedPosX)
}else if(DragSide.current==="Top" && rowID!==0 && rowResize){
heightChange(-changedPosY)
}else if(DragSide.current==="Bottom" && rowID!==lastRowID && rowID!==0 && rowResize){
heightChange(changedPosY)
}
}

function widthChange(changedWidth:number){
if(onWidthChange!==undefined){
  onWidthChange(changedWidth)
}
}

function heightChange(changedHeight:number){
  if(onHeightChange!==undefined){
    onHeightChange(changedHeight)
  }
}

  return (
    <div className={`relative px-3 py-2 overflow-hidden  ${className}`} style={{width,height}}>
    {/*top border*/}
      <div className={`absolute top-0 left-0 right-0 h-2 border-grayscale border-t-1 select-none ${rowID===0?"":"hidden"}`}></div>
{/*bottom border*/}
      <div onMouseDown={(e)=>handleMouseDown(e,"Bottom")} 
      className={`absolute bottom-0 left-0 right-0 h-2 border-grayscale  border-b-1 select-none
      ${rowID!==lastRowID && rowID!==0 && rowResize?"hover:cursor-row-resize":""}`}></div>
 {/*left border*/} 
      <div className={`absolute top-0 bottom-0 left-0 w-2 border-grayscale border-l-1 select-none ${colID===0?"":"hidden"}`}></div>
{/*right border*/}
      <div onMouseDown={(e)=>handleMouseDown(e,"Right")} 
      className={`absolute top-0 right-0 bottom-0 border-grayscale border-r-1 w-2 select-none
        ${lastColID-1!==colID && rowID===0 && colResize?"hover:cursor-col-resize":""}`}></div>
      <div className={`overflow-x-auto whitespace-nowrap scrollbar-hide ${rowID===0?"select-none":""}`}>
      {value}
      </div>
    </div>
  )
}
