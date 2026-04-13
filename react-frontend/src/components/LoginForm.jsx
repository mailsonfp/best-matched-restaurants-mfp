import React, { useState, useEffect, useRef } from "react";
import axios from "axios";

function LoginForm({ setToken }) {
  const [userName, setUserName] = useState("");
  const [availableAuthorities, setAvailableAuthorities] = useState([]);
  const [selectedAuthorities, setSelectedAuthorities] = useState([]);
  const availableRef = useRef();
  const selectedRef = useRef();

  useEffect(() => {
    const fetchAuthorities = async () => {
      try {
        const response = await axios.get("http://localhost:8082/v1/authentication/authorities");
        setAvailableAuthorities(response.data);
      } catch (error) {
        console.error("Erro ao buscar authorities:", error);
      }
    };
    fetchAuthorities();
  }, []);

  const moveToSelected = () => {
    const selectedOptions = Array.from(availableRef.current.selectedOptions).map(option => option.value);
    setSelectedAuthorities([...selectedAuthorities, ...selectedOptions]);
    setAvailableAuthorities(
      availableAuthorities.filter(auth => !selectedOptions.includes(auth))
    );
    // Limpa a seleção após mover
    availableRef.current.selectedIndex = -1;
  };

  const moveToAvailable = () => {
    const selectedOptions = Array.from(selectedRef.current.selectedOptions).map(option => option.value);
    setAvailableAuthorities([...availableAuthorities, ...selectedOptions]);
    setSelectedAuthorities(
      selectedAuthorities.filter(auth => !selectedOptions.includes(auth))
    );
    // Limpa a seleção após mover
    selectedRef.current.selectedIndex = -1;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8082/v1/authentication/login", {
        userName,
        authorities: selectedAuthorities
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

      <label>Authorities:</label>
      <div className="authorities-container">
        <div className="authorities-list">
          <label>Available:</label>
          <select
            ref={availableRef}
            multiple
            size="4"
          >
            {availableAuthorities.map(auth => {
              return <option key={auth} value={auth}>{auth}</option>;
            })}
          </select>
        </div>

        <div className="authorities-buttons">
          <button type="button" onClick={moveToSelected}>Add</button>
          <button type="button" onClick={moveToAvailable}>Remove</button>
        </div>

        <div className="authorities-list">
          <label>Selected:</label>
          <select
            ref={selectedRef}
            multiple
            size="4"
            required
          >
            {selectedAuthorities.map(auth => {
              return <option key={auth} value={auth} selected>{auth}</option>;
            })}
          </select>
        </div>
      </div>

      <button type="submit">Login</button>
    </form>
  );
}

export default LoginForm;