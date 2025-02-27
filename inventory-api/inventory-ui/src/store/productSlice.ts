// import { createSlice, PayloadAction, createAsyncThunk } from "@reduxjs/toolkit";
// import { addProduct as addProductApi } from "../api/productApi";

// interface Product {
//     id: number;
//     name: string;
//     quantity: number;
//     price: number;
// }

// interface ProductState {
//     products: Product[];
// }

// const initialState: ProductState = {
//     products: [],
// };

// export const addProductAsync = createAsyncThunk(
//     "products/addProduct",
//     async (newProduct) => {
//         const response = await addProductApi(newProduct);
//         return response;
//     }
// );

// const productSlice = createSlice({
//     name: "products",
//     initialState: {products: []},
//     reducers: {
//         setProducts: (state, action) => {
//             state.products = action.payload;
//         },
//         addProduct: (state, action: PayloadAction<Product>) => {
//             state.products.push(action.payload);
//         },
//         updateProduct: (state, action: PayloadAction<Product>) => {
//             const index = state.products.findIndex((p) => p.id === action.payload.id);
//             if (index !== -1) {
//                 state.products[index] = action.payload;
//             }
//         },
//         deleteProduct: (state, action: PayloadAction<number>) => {
//             state.products = state.products.filter((p) => p.id !== action.payload);
//         },
       
//     },
//     extraReducers: (builder) => {
//         builder.addCase(addProductAsync.fulfilled, (state, action) => {
//             state.products.push(action.payload);
//         });
//     }
// });

// export const { setProducts, addProduct, updateProduct, deleteProduct } = productSlice.actions;
// export default productSlice.reducer;

import { createSlice, PayloadAction, createAsyncThunk } from "@reduxjs/toolkit";
import { addProduct as addProductApi } from "../api/productApi";

interface Product {
    id: number;
    name: string;
    quantity: number;
    price: number;
}

interface ProductState {
    products: Product[];
}

const initialState: ProductState = {
    products: [],
};

// ✅ Ensure newProduct has correct typing
export const addProductAsync = createAsyncThunk<Product, Omit<Product, "id">>(
    "products/addProduct",
    async (newProduct) => {
        const response = await addProductApi(newProduct);
        return response;  // ✅ Ensure API returns `id`
    }
);

const productSlice = createSlice({
    name: "products",
    initialState,
    reducers: {
        setProducts: (state, action: PayloadAction<Product[]>) => {
            state.products = action.payload;
        },
        addProduct: (state, action: PayloadAction<Product>) => {
            state.products.push(action.payload);
        },
        updateProduct: (state, action: PayloadAction<Product>) => {
            const index = state.products.findIndex((p) => p.id === action.payload.id);
            if (index !== -1) {
                state.products[index] = action.payload;
            }
        },
        deleteProduct: (state, action: PayloadAction<number>) => {
            state.products = state.products.filter((p) => p.id !== action.payload);
        }
    },
    extraReducers: (builder) => {
        builder.addCase(addProductAsync.fulfilled, (state, action) => {
            state.products.push(action.payload);
        });
    }
});

export const { setProducts, addProduct, updateProduct, deleteProduct } = productSlice.actions;
export default productSlice.reducer;
