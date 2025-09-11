import * as Icons from "lucide-react";
type IconName = keyof typeof Icons;

export type PageRoutes = {
    name:string;
    route:string;
    icon:IconName;
}

export type TreeItemType = {
    id:number,
    label:string,
    description?:string,
    childern?:TreeItemType[]
}