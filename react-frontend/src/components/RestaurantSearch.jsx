import React, { useEffect, useRef, useState } from "react";
import axios from "axios";

function RestaurantSearch({ token, setToken, onBackToLogin }) {
  const [restaurantName, setRestaurantName] = useState("");
  const [distance, setDistance] = useState("");
  const [customerRating, setCustomerRating] = useState("");
  const [price, setPrice] = useState("");
  const [cuisineName, setCuisineName] = useState("");
  const [restaurants, setRestaurants] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [submitted, setSubmitted] = useState(false);
  const [isRedirecting, setIsRedirecting] = useState(false);
  const redirectTimeoutRef = useRef(null);

  useEffect(() => {
    return () => {
      if (redirectTimeoutRef.current) {
        clearTimeout(redirectTimeoutRef.current);
      }
    };
  }, []);

  const handleExpiredTokenRedirect = () => {
    if (redirectTimeoutRef.current) {
      return;
    }

    setIsRedirecting(true);
    setError("Your session has expired. You will be redirected to the login page.");

    redirectTimeoutRef.current = setTimeout(() => {
      setToken(null);
      onBackToLogin();
    }, 2200);
  };

  const resolveApiErrorMessage = (err) => {
    const status = err?.response?.status;
    const apiMessage = err?.response?.data?.messageDetail || err?.response?.data?.messageTitle;

    if (status === 401) {
      return apiMessage || "Authentication is required to access this resource.";
    }

    if (status === 403) {
      return apiMessage || "You do not have permission to access this resource.";
    }

    return apiMessage || "Error searching restaurants. Please try again.";
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    setSubmitted(true);

    // Validar se pelo menos um parâmetro foi informado
    if (!restaurantName && !distance && !customerRating && !price && !cuisineName) {
      return;
    }

    setLoading(true);
    setError(null);
    setRestaurants(null);

    try {
      const params = new URLSearchParams();
      if (restaurantName) params.append("restaurantName", restaurantName);
      if (distance) params.append("distance", distance);
      if (customerRating) params.append("customerRating", customerRating);
      if (price) params.append("price", price);
      if (cuisineName) params.append("cuisineName", cuisineName);

      const response = await axios.get(
        `http://localhost:8082/v1/restaurants/search?${params.toString()}`,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      setRestaurants(response.data);
    } catch (err) {
      const status = err?.response?.status;
      console.error("Error searching restaurants:", err);

      // Invalid/expired token: show warning and redirect to login.
      if (status === 401) {
        handleExpiredTokenRedirect();
      } else {
        setError(resolveApiErrorMessage(err));
      }
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    setToken(null);
    onBackToLogin();
  };

  const handleClearSearch = () => {
    setRestaurantName("");
    setDistance("");
    setCustomerRating("");
    setPrice("");
    setCuisineName("");
    setSubmitted(false);
    setError(null);
    setRestaurants(null);
  };

  if (!token) {
    return null;
  }

  // Verificar se algum parâmetro foi preenchido
  const hasSearchParams = restaurantName || distance || customerRating || price || cuisineName;

  return (
    <div className="search-container">
      <div className="search-header">
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      </div>

      <form onSubmit={handleSearch} className="search-form">
        <label>Restaurant Name:</label>
        <input
          type="text"
          value={restaurantName}
          onChange={(e) => setRestaurantName(e.target.value)}
          placeholder="Optional"
        />

        <label>Distance (miles):</label>
        <div className="range-field">
          <div className="range-header">
            <span>{distance === "" ? "Not set" : `${distance} mi`}</span>
          </div>
          <div className="range-with-button">
            <input
              type="range"
              value={distance === "" ? 1 : distance}
              onChange={(e) => setDistance(parseInt(e.target.value, 10))}
              min="1"
              max="10"
              step="1"
            />
            <button type="button" className="clear-range-btn" onClick={() => setDistance("")}>Clear</button>
          </div>
          <div className="range-scale">
            <span>1</span>
            <span>10</span>
          </div>
        </div>

        <label>Customer Rating (stars):</label>
        <div className="star-rating" role="radiogroup" aria-label="Customer rating">
          {[1, 2, 3, 4, 5].map((star) => (
            <button
              key={star}
              type="button"
              className={`star-btn ${customerRating >= star ? "selected" : ""}`}
              onClick={() => setCustomerRating(star)}
              role="radio"
              aria-checked={customerRating === star}
              aria-label={`${star} star${star > 1 ? "s" : ""}`}
            >
              ★
            </button>
          ))}
          <button
            type="button"
            className="clear-rating-btn"
            onClick={() => setCustomerRating("")}
            aria-label="Clear rating"
          >
            Clear
          </button>
        </div>

        <label>Average Price per Person ($):</label>
        <div className="range-field">
          <div className="range-header">
            <span>{price === "" ? "Not set" : `$${price}`}</span>
          </div>
          <div className="range-with-button">
            <input
              type="range"
              value={price === "" ? 10 : price}
              onChange={(e) => setPrice(parseInt(e.target.value, 10))}
              min="10"
              max="50"
              step="1"
            />
            <button type="button" className="clear-range-btn" onClick={() => setPrice("")}>Clear</button>
          </div>
          <div className="range-scale">
            <span>$10</span>
            <span>$50</span>
          </div>
        </div>

        <label>Cuisine Name:</label>
        <input
          type="text"
          value={cuisineName}
          onChange={(e) => setCuisineName(e.target.value)}
          placeholder="e.g., Chinese, American"
        />

        <div className="search-actions">
          <button type="submit" className="submit-search-btn" disabled={loading || isRedirecting}>
            {loading ? "Searching..." : "Search"}
          </button>
          <button type="button" className="clear-search-btn" onClick={handleClearSearch} disabled={isRedirecting}>
            Clear Search
          </button>
        </div>
        {submitted && !hasSearchParams && (
          <p className="validation-error">Please fill in at least one search parameter</p>
        )}
      </form>

      {!submitted && (
        <p className="search-hint">Fill the parameters to find the best matched restaurants</p>
      )}

      {error && <p className="error-message">{error}</p>}

      {restaurants !== null && (
        <div className="results-container">
          {restaurants.length === 0 ? (
            <p className="no-results">Sorry, no restaurants found matching your criteria</p>
          ) : (
            <table className="restaurants-table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Distance (mi)</th>
                  <th>Rating</th>
                  <th>Price per Person</th>
                  <th>Cuisine</th>
                </tr>
              </thead>
              <tbody>
                {restaurants.map((restaurant, index) => (
                  <tr key={index}>
                    <td>{restaurant.restaurantName}</td>
                    <td>{restaurant.distance}</td>
                    <td>{restaurant.customerRating}</td>
                    <td>${restaurant.price}</td>
                    <td>{restaurant.cuisineName}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
    </div>
  );
}

export default RestaurantSearch;


