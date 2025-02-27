// import { useState } from "react";
// import { useNavigate } from "react-router-dom";
// // import { addProduct } from "../api/productApi";
// import { TextField, Button, Container } from "@mui/material";
// import Typography from "@mui/material/Typography";
// import { useDispatch } from "react-redux";
// // import { addProduct as addProductApi } from "../api/productApi";
// import { addProductAsync } from "../store/productSlice";
// import { AppDispatch } from "../store/store";



// export default function AddProduct() {
//     const [name, setName] = useState("");
//     const [quantity, setQuantity] = useState<number>(0);
//     const [price, setPrice] = useState<number>(0);
//     const navigate = useNavigate();
//     const dispatch = useDispatch<AppDispatch>();

//     const handleSubmit = async (event: React.FormEvent) => {
//         event.preventDefault();
//         const newProduct = { name, quantity, price };
//         try{
//             await dispatch(addProductAsync(newProduct)).unwrap();
//             // dispatch(addProduct(response));
//             navigate("/");
//         }catch(error){
//             console.error("Error adding products:", error);

//         }
        
//     };

//     return (
//         <Container maxWidth="sm">
//             <Typography variant="h4" gutterBottom>
//                 Add Product
//             </Typography>
//             <form onSubmit={handleSubmit}>
//                 <TextField
//                     fullWidth
//                     label="Product Name"
//                     variant="outlined"
//                     margin="normal"
//                     required
//                     value={name}
                    
//                     onChange={(e: React.ChangeEvent<HTMLInputElement>) => setName(e.target.value)}

//                 />
//                 <TextField
//                     fullWidth
//                     label="Quantity"
//                     type="number"
//                     variant="outlined"
//                     margin="normal"
//                     required
//                     value={quantity}
//                     // onChange={(e) => setQuantity(Number(e.target.value))}
//                     onChange={(e: React.ChangeEvent<HTMLInputElement>) => setQuantity(Number(e.target.value))}

//                 />
//                 <TextField
//                     fullWidth
//                     label="Price"
//                     type="number"
//                     variant="outlined"
//                     margin="normal"
//                     required
//                     value={price}
//                     // onChange={(e) => setPrice(Number(e.target.value))}

//                     onChange={(e: React.ChangeEvent<HTMLInputElement>) => setPrice(Number(e.target.value))}
//                     />
//                 <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
//                     Add Product
//                 </Button>
//             </form>
//         </Container>
//     );
// }

import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { TextField, Button, Container } from "@mui/material";
import { useDispatch } from "react-redux";
import { addProductAsync } from "../store/productSlice";
import { AppDispatch } from "../store/store";

export default function AddProduct() {
    const [name, setName] = useState("");
    const [quantity, setQuantity] = useState<number>(0);
    const [price, setPrice] = useState<number>(0);
    const navigate = useNavigate();
    const dispatch = useDispatch<AppDispatch>();

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        const newProduct = { name, quantity, price };

        try {
            await dispatch(addProductAsync(newProduct)).unwrap();  // ✅ Ensure API response is properly handled
            navigate("/");
        } catch (error) {
            console.error("Error adding product:", error);
        }
    };

    return (
        <Container maxWidth="sm">
            <h2>Add Product</h2> {/* ✅ Replaced Typography for simplicity */}
            <form onSubmit={handleSubmit}>
                <TextField
                    fullWidth
                    label="Product Name"
                    variant="outlined"
                    margin="normal"
                    required
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <TextField
                    fullWidth
                    label="Quantity"
                    type="number"
                    variant="outlined"
                    margin="normal"
                    required
                    value={quantity}
                    onChange={(e) => setQuantity(Number(e.target.value))}
                />
                <TextField
                    fullWidth
                    label="Price"
                    type="number"
                    variant="outlined"
                    margin="normal"
                    required
                    value={price}
                    onChange={(e) => setPrice(Number(e.target.value))}
                />
                <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                    Add Product
                </Button>
            </form>
        </Container>
    );
}
