import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { fetchProducts, updateProduct } from "../api/productApi";
import { TextField, Button, Container } from "@mui/material";
import Typography from "@mui/material/Typography";


export default function EditProduct() {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [name, setName] = useState("");
    const [quantity, setQuantity] = useState<number>(0);
    const [price, setPrice] = useState<number>(0);

    useEffect(() => {
        fetchProducts().then((products) => {
            const product = products.find((p: any) => p.id === Number(id));
            if (product) {
                setName(product.name);
                setQuantity(product.quantity);
                setPrice(product.price);
            }
        });
    }, [id]);

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        await updateProduct(Number(id), { name, quantity, price });
        navigate("/");
    };

    return (
        <Container maxWidth="sm">
            <Typography variant="h4" gutterBottom>
                Edit Product
            </Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    fullWidth
                    label="Product Name"
                    variant="outlined"
                    margin="normal"
                    required
                    value={name}
                    // onChange={(e) => setName(e.target.value)}
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => setName(e.target.value)}

                />
                <TextField
                    fullWidth
                    label="Quantity"
                    type="number"
                    variant="outlined"
                    margin="normal"
                    required
                    value={quantity}
                    // onChange={(e) => setQuantity(Number(e.target.value))}
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => setQuantity(Number(e.target.value))}

                />
                <TextField
                    fullWidth
                    label="Price"
                    type="number"
                    variant="outlined"
                    margin="normal"
                    required
                    value={price}
                    // onChange={(e) => setPrice(Number(e.target.value))}
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => setPrice(Number(e.target.value))}

                />
                <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                    Update Product
                </Button>
            </form>
        </Container>
    );
}
