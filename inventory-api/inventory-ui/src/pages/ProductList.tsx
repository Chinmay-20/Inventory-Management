import { useEffect, useState } from "react";
import { fetchProducts, deleteProduct } from "../api/productApi";
import { Button, Card, CardContent } from "@mui/material";
import Typography from "@mui/material/Typography";
import { Link } from "react-router-dom";

interface Product {
    id: number;
    name: string;
    quantity: number;
    price: number;
}

export default function ProductList() {
    const [products, setProducts] = useState<Product[]>([]);

    const loadProducts = async() => {
        const data = await fetchProducts();
        setProducts(data);
    };

    useEffect(() => {
        loadProducts();
    }, []);

    const handleDelete = async (id: number) => {
        await deleteProduct(id);
        setProducts(products.filter((p) => p.id !== id));
    };

    return (
        <div>
            {products.map((product) => (
                <Card key={product.id} sx={{ marginBottom: 2 }}>
                    <CardContent>
                        <Typography variant="h6">{product.name}</Typography>
                        <Typography>Stock: {product.quantity}</Typography>
                        <Typography>Price: ${product.price}</Typography>
                        <Button
                            component={Link}
                            to={`/edit/${product.id}`}
                            color="primary"
                        >
                            Edit
                        </Button>
                        <Button color="error" onClick={() => handleDelete(product.id)}>
                            Delete
                        </Button>
                    </CardContent>
                </Card>
            ))}
        </div>
    );
}
