import { useState } from "react";
import CusTreeView from "../components/inventory/cusTreeView";
import RootLayout from "../layout/layout";
import type { TreeItemType } from "../utils/TypesList";

export default function InventoryPage(){
  const [BigTree,setBigTree] = useState<TreeItemType[]>([
        {
            id:0,
            label:"Test",
            childern:[
                {id:1,label:"GG"},{id:2,label:"Double GG"}
            ]
        }
    ])

    return(
        <RootLayout>
            <div className="w-100 h-100">
                <CusTreeView Data={BigTree}/>
            </div>
        </RootLayout>
    )
}
