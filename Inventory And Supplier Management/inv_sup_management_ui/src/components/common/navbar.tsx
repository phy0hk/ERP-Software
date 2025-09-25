import {Home, Package} from 'lucide-react';
import type { PageRoutes } from '../../utils/TypesList';
import NavbarButton from './navbarButton';
import {useLocation} from 'react-router';
export default function Navbar(){
    const Pages:PageRoutes[] = [
    {name:"Dashboard",route:"/",icon:"Home",detail:"This is a dashboard"},
    {name:"Inventory",route:"/inventory",icon:"Boxes",detail:"Manage your inventory and stocks."},
    {name:"Products",route:"/products",icon:"Package",detail:"GG"}
    ];
    const location = useLocation();
 const Desc = Pages.filter((item)=>item.route===location.pathname)[0]
    
    return(
        <header className="text-color flex flex-col"> 
        <div className='px-5 pt-5 flex flex-col gap-3'>
            <h1 className="text-md font-bold flex flex-row items-center gap-3">
                <Package size={30} className='text-primary'/>
                <span>
                Inventory Management System
                </span>
            </h1>
            <div className='flex flex-col relative'>
            <div className='flex flex-row scrollbar-hide overflow-auto gap-1'>
                {Pages.map((item,key)=><NavbarButton name={item.name} route={item.route} icon={item.icon} key={key}/>)}
            </div>
            <span className='w-full h-1 bg-grayscale/10'></span>
            </div>
        </div>
        <div className='py-3 px-5 border-b-2 border-grayscale/10'>
 <h3 className={`font-bold text-grayscale`}>{Desc.name}</h3>
              <p className={`text-sm text-grayscale`}>{Desc.detail}</p>

        </div>
        </header>
    )
}

