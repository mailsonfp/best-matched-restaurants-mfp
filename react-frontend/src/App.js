import React, { useState } from "react";
import LoginForm from "./components/LoginForm";
import TokenDisplay from "./components/TokenDisplay";
import "./style.css";

function App() {
  const [token, setToken] = useState(null);

  return (
    <div className="app-container">
      <h1>Best Matched Restaurants - Login</h1>
      <LoginForm setToken={setToken} />
      <TokenDisplay token={token} />
    </div>
  );
}

export default App;