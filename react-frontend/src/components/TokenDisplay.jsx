import React from "react";

function TokenDisplay({ token, onSearch }) {
  const copyToken = () => {
    navigator.clipboard.writeText(token).then(() => {
      alert('Token copied to clipboard!');
    }).catch(err => {
      console.error('Failed to copy: ', err);
      alert('Failed to copy token. Please copy manually.');
    });
  };

  return (
    <div className="token-display">
      <h2>Generated Token</h2>
      {token ? (
        <>
          <p className="token-text">{token}</p>
          <div className="token-actions">
            <button className="btn-secondary" onClick={copyToken}>Copy Token</button>
            <button className="btn-primary" onClick={onSearch}>Restaurants Search</button>
          </div>
        </>
      ) : (
        <p>No token generated yet.</p>
      )}
    </div>
  );
}

export default TokenDisplay;