import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';
import './Login.css';


const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);


    const { login } = useAuth();
    const navigate = useNavigate();


    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);


        try {
            console.log('Attempting login with:', { username, password });


            const response = await axios.post('http://localhost:8080/api/auth/login', {
                username,
                password
            });


            console.log('Login response:', response.data);


            const { token, username: userName, email } = response.data;


            if (!token) {
                throw new Error('No token received from server');
            }


            login(token, { username: userName, email });
            console.log('Login successful, navigating to dashboard');
            navigate('/dashboard');


        } catch (err) {
            console.error('Login error:', err);
            console.error('Error response:', err.response);


            if (err.response) {
                // Server responded with error
                setError(err.response?.data?.message || 'Invalid username or password');
            } else if (err.request) {
                // Request made but no response
                setError('Cannot connect to server. Make sure backend is running on port 8080.');
            } else {
                // Something else happened
                setError('Login failed. Please try again.');
            }
        } finally {
            setLoading(false);
        }
    };


    return (
        <div className="login-container">
            <div className="login-box">
                <h2>Login</h2>
                {error && <div className="error-message">{error}</div>}


                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Username</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                            placeholder="Enter your username"
                        />
                    </div>


                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            placeholder="Enter your password"
                        />
                    </div>


                    <button type="submit" disabled={loading}>
                        {loading ? 'Logging in...' : 'Login'}
                    </button>
                </form>


                <p className="signup-link">
                    Don't have an account? <Link to="/signup">Sign up</Link>
                </p>
            </div>
        </div>
    );
};


export default Login;