import * as Icons from "lucide-react";
type IconName = keyof typeof Icons;

export type PageRoutes = {
    name:string;
    route:string;
    icon:IconName;
}

export type LocationType = {
    id:number,
    name:string,
    parentId:number | null,
    type?:string,
    code?:string,
    description?:string,
    created_at?:Date,
    updated_at?:Date,
    children?:LocationType[]
}