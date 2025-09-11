import { Route, Routes } from "react-router"
import HomePage from "./pages/home"
import ProductsPage from "./pages/products"
import InventoryPage from "./pages/inventory"

function App() {

  return (
    <Routes>
      <Route path="/" element={<HomePage/>}/>
      <Route path="/inventory" element={<InventoryPage/>}/>
      <Route path="/products" element={<ProductsPage/>}/>
    </Routes>
  )
}

export default App
