import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';
import './Dashboard.css';


const Dashboard = () => {
    const { user, token, logout } = useAuth();
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();


    const fetchProtectedData = useCallback(async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/protected/dashboard', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setMessage(response.data.message);
        } catch (err) {
            console.error('Error fetching protected data:', err);
            setMessage('Failed to load dashboard data');
        } finally {
            setLoading(false);
        }
    }, [token]);


    useEffect(() => {
        fetchProtectedData();
    }, [fetchProtectedData]);


    const handleLogout = () => {
        logout();
        navigate('/login');
    };


    return (
        <div className="dashboard-container">
            <div className="dashboard-box">
                <h2>Dashboard</h2>


                <div className="user-info">
                    <p><strong>Username:</strong> {user?.username}</p>
                    <p><strong>Email:</strong> {user?.email}</p>
                </div>


                <div className="protected-content">
                    {loading ? (
                        <p>Loading...</p>
                    ) : (
                        <div className="message-box">
                            <h3>Protected Message:</h3>
                            <p>{message}</p>
                        </div>
                    )}
                </div>


                <button onClick={handleLogout} className="logout-btn">
                    Logout
                </button>
            </div>
        </div>
    );
};


export default Dashboard;