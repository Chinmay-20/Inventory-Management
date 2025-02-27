import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ProductList from "./pages/ProductList";
import AddProduct from "./pages/AddProduct";
import EditProduct from "./pages/EditProduct";
import Navbar from "./components/Navbar";

function App() {
    return (
        <Router>
            <Navbar />
            <Routes>
                <Route path="/" element={<ProductList />} />
                <Route path="/add" element={<AddProduct />} />
                <Route path="/edit/:id" element={<EditProduct />} />
            </Routes>
        </Router>
    );
}

export default App;
