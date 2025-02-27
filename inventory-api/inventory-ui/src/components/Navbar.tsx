import { AppBar, Toolbar, Button } from "@mui/material";
import Typography from "@mui/material/Typography";

import { Link } from "react-router-dom";

export default function Navbar() {
    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" sx={{ flexGrow: 1 }}>
                    Inventory Management
                </Typography>
                <Button color="inherit" component={Link} to="/">Products</Button>
                <Button color="inherit" component={Link} to="/add">Add Product</Button>
            </Toolbar>
        </AppBar>
    );
}
