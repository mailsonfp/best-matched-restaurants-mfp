import React, { useState } from "react";
import LoginForm from "./components/LoginForm";
import TokenDisplay from "./components/TokenDisplay";
import RestaurantSearch from "./components/RestaurantSearch";
import "./style.css";

function App() {
  const [token, setToken] = useState(null);
  const [showSearch, setShowSearch] = useState(false);

  const handleGoToSearch = () => {
    if (token) {
      setShowSearch(true);
    }
  };

  const handleBackToLogin = () => {
    setShowSearch(false);
  };

  return (
    <div className="app-container">
      {showSearch ? (
        <>
          <h1>Best Matched Restaurants - Search</h1>
          <RestaurantSearch token={token} setToken={setToken} onBackToLogin={handleBackToLogin} />
        </>
      ) : (
        <>
          <h1>Best Matched Restaurants - Login</h1>
          <LoginForm setToken={setToken} />
          <TokenDisplay token={token} onSearch={handleGoToSearch} />
        </>
      )}
    </div>
  );
}

export default App;