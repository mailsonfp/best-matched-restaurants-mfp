import React, { useState } from "react";
import axios from "axios";

function LoginForm({ setToken }) {
  const [userName, setUserName] = useState("");
  const [authorities, setAuthorities] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8082/v1/authentication/login", {
        userName,
        authorities: authorities.split(",")
      });
      setToken(response.data.token);
    } catch (error) {
      console.error("Erro ao fazer login:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="login-form">
      <label>Username:</label>
      <input
        type="text"
        value={userName}
        onChange={(e) => setUserName(e.target.value)}
        required
      />

      <label>Authorities (comma separated):</label>
      <input
        type="text"
        value={authorities}
        onChange={(e) => setAuthorities(e.target.value)}
        required
      />

      <button type="submit">Login</button>
    </form>
  );
}

export default LoginForm;