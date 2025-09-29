import * as Icons from "lucide-react";
type IconName = keyof typeof Icons;

export type PageRoutes = {
    name:string;
    route:string;
    icon:IconName;
    detail:string;
}

export type LocationType = {
    id:number;
    name:string;
    parentId:number | null;
    type?:string;
    code?:string;
    description?:string;
    created_at?:Date;
    updated_at?:Date;
    children?:LocationType[];
}
export type ColumnName = {
  name:string,
  sizePercent:string
}
export type TableCellType = {
  rowID:string;
  colID:string;
  width:string | number;
  height:string | number;
  value:string;
  lastRowID:number;
  lastColID:number;
  className:string;
  colResize:boolean;
  rowResize:boolean;
}
export type TableType = {
  ColumnNames:string[] | null | undefined;
  RowValues:string[][] | null | undefined;
  className?:string | null | undefined;
  TableWidth?:number | null;
  rowHeights?:number | undefined;
  colResize?:boolean;
  rowResize?:boolean;
}
