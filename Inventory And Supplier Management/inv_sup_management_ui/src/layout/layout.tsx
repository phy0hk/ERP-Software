import Navbar from "../components/common/navbar";

export default function RootLayout({children}:any){
    return(
        <div className="font-inter">
        <Navbar/>
        <div className="px-5 py-1">
        {children}
        </div>
        </div>
    )
}