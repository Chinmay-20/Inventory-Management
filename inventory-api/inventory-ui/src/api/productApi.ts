import axios from "axios";

const API_URL = "http://localhost:8080/api/products";  // Ensure this matches backend

export const fetchProducts = async () => {
    const response = await axios.get(API_URL);
    return response.data;
};

export const addProduct = async (product: any) => {
    const response = await axios.post(API_URL, product);
    return response.data;
};

export const updateProduct = async (id: number, product: any) => {
    const response = await axios.put(`${API_URL}/${id}`, product);
    return response.data;
};

export const deleteProduct = async (id: number) => {
    await axios.delete(`${API_URL}/${id}`);
};
