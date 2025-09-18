import {Home, Package} from 'lucide-react';
import type { PageRoutes } from '../../utils/TypesList';
import NavbarButton from './navbarButton';

export default function Navbar(){
    const Pages:PageRoutes[] = [
    {name:"Dashboard",route:"/",icon:"Home"},
    {name:"Inventory",route:"/inventory",icon:"Boxes"},
    {name:"Products",route:"/products",icon:"Package"}
    ];

    return(
        <header className="text-color flex flex-col"> 
        <div className='p-5 flex flex-col gap-3'>
            <h1 className="text-lg font-bold flex flex-row items-center">
                <Package size={40} fill='true' className='text-logo'/>
                <span>
                Inventory Management System
                </span>
            </h1>
            <div className='flex flex-col'>
            <div className='flex flex-row scrollbar-hide overflow-auto gap-1'>
                {Pages.map((item,key)=><NavbarButton name={item.name} route={item.route} icon={item.icon} key={key}/>)}
            </div>
            <span className='w-full bg-black/10 h-0.5'></span>
            </div>
        </div>
        <div>

        </div>
        </header>
    )
}

